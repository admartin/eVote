package edu.uga.cs.evote.persistence.impl;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.PoliticalParty;

//import edu.uga.PoliticalPartysOfficers.entity.Voter;

import edu.uga.cs.evote.object.ObjectLayer;

class PoliticalPartyManager
{
    private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public PoliticalPartyManager ( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( PoliticalParty PoliticalParty ) 
            throws EVException
    {
        String               insertPoliticalPartySql = "INSERT INTO politicalparty (partyName) Values (?)";
        		//"insert into PoliticalParty ( username, userpass, emailAddress, firstname, lastname, address, phone ) values ( ?, ?, ?, ?, ?, ?, ? )";              
        String               updatePoliticalPartySql = "update candidate  set Office = ?, isPartisan = ? where id = ?";              
        PreparedStatement    stmt;
        int                  inscnt;
        long                 PoliticalPartyId;
        
        try {
            
            if( !PoliticalParty.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertPoliticalPartySql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updatePoliticalPartySql );

             if( PoliticalParty.getName() != null)
                stmt.setString( 1, PoliticalParty.getName() );
            else
                throw new EVException( "VoterManager.save: can't save a Voter: first name undefined" );
             
          
//            if( PoliticalParty.isPersistent() )
//                stmt.setLong( 3, PoliticalParty.getId() );

            
            inscnt = stmt.executeUpdate();

            if( !PoliticalParty.isPersistent() ) {
                // in case this this object is stored for the first time,
                // we need to establish its persistent identifier (primary key)
                if( inscnt == 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result
                        // retrieve the result
                        ResultSet r = stmt.getResultSet();
                        // we will use only the first row!
                        while( r.next() ) {
                            // retrieve the last insert auto_increment value
                            PoliticalPartyId = r.getLong( 1 );
                            if( PoliticalPartyId > 0 )
                                PoliticalParty.setId( PoliticalPartyId ); // set this PoliticalParty's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "PoliticalPartyManager.save: failed to save a PoliticalParty" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "PoliticalPartyManager.save: failed to save a PoliticalParty: " + e );
        }
    }

    public List<PoliticalParty> restore( PoliticalParty modelPoliticalParty ) 
            throws EVException
    {
        String       selectPoliticalPartySql = "select partyName from PoliticalParty";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<PoliticalParty> PoliticalPartys = new ArrayList<PoliticalParty>();

        condition.setLength( 0 );
        
        // form the query based on the given PoliticalParty object instance
        query.append( selectPoliticalPartySql );
        
        if( modelPoliticalParty != null ) {
           
            if( modelPoliticalParty.getName() != null) // id is unique, so it is sufficient to get a PoliticalParty
                query.append( " where partyName = " + modelPoliticalParty.getName() );
            
            
                if( condition.length() > 0 ) {
                    query.append(  " where " );
                    query.append( condition );
                }
            
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent PoliticalParty objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                
                String name;
               
                while( rs.next() ) {

                    name = rs.getString( 1 );
                   
                    PoliticalParty PoliticalParty = objectLayer.createPoliticalParty(-1,name, null);
                   
                    PoliticalPartys.add( PoliticalParty );

                }
                
                return PoliticalPartys;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "PoliticalPartyManager.restore: Could not restore persistent PoliticalParty object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new EVException( "PoliticalPartyManager.restore: Could not restore persistent PoliticalParty objects" );
    }
    
    public List<PoliticalParty> restorePoliticalPartyEstablishedByPoliticalParty( PoliticalParty PoliticalParty ) 
            throws EVException
    {
        String selectPoliticalPartySql = "select c.id, c.name, c.address, c.established, p.id, " +
                                 "p.username, p.userpass, p.emailAddress, p.firstname, p.lastname, p.address, " +
                                 "p.phone from PoliticalParty c, PoliticalParty p where c.founderid = p.id";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<PoliticalParty>   PoliticalPartys = new ArrayList<PoliticalParty>();

        condition.setLength( 0 );
        
        // form the query based on the given PoliticalParty object instance
        query.append( selectPoliticalPartySql );
        
        if( PoliticalParty != null ) {
            if( PoliticalParty.getId() >= 0 ) // id is unique, so it is sufficient to get a PoliticalParty
                query.append( " and p.id = " + PoliticalParty.getId() );
            else if( PoliticalParty.getName()!= null ) // userName is unique, so it is sufficient to get a User
                query.append( " and p.name = '" + PoliticalParty.getName() + "'" );
           
                if( condition.length() > 0 ) {
                    query.append( condition );
                }
            
        }
                
        try {

            stmt = conn.createStatement();

            // retrieve the persistent PoliticalParty objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                long   id;
                String office;
                boolean isPartisan;
                PoliticalParty nextPoliticalParty = null;
                
                ResultSet rs = stmt.getResultSet();
                
                while( rs.next() ) {
                    
                    id = rs.getLong( 1 );
                    office = rs.getString( 2 );
                    isPartisan =  rs.getBoolean ( 3 );
                   
                    nextPoliticalParty = objectLayer.createPoliticalParty(); // create a proxy PoliticalParty object
                    // and now set its retrieved attributes
//                    nextPoliticalParty.setId( id );
//                    nextPoliticalParty.setOffice( office );
//                    nextPoliticalParty.setIsPartisan(isPartisan) ;
                    
                    //nextPoliticalParty.setEstablishedOn( establishedOn );
                    // set this to null for the "lazy" association traversal
                    //nextPoliticalParty.setPoliticalPartyFounder( null );
                    
                    PoliticalPartys.add( nextPoliticalParty );
                }
                
                return PoliticalPartys;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "PoliticalPartyManager.restoreEstablishedBy: Could not restore persistent PoliticalParty objects; Root cause: " + e );
        }

        throw new EVException( "PoliticalPartyManager.restoreEstablishedBy: Could not restore persistent PoliticalParty objects" );
    }
    
    public void delete( PoliticalParty PoliticalParty ) 
            throws EVException
    {
        String               deletePoliticalPartySql = "delete from PoliticalParty where partyname = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given PoliticalParty object instance
        if( !PoliticalParty.isPersistent() ) // is the PoliticalParty object persistent?  If not, nothing to actually delete
            return;
        
        try {
            
            //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
            //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
            stmt = (PreparedStatement) conn.prepareStatement( deletePoliticalPartySql );
            
            stmt.setString( 1, PoliticalParty.getName() );
            
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new EVException( "PoliticalPartyManager.delete: failed to delete this PoliticalParty" );
            }
        }
        catch( SQLException e ) {
            throw new EVException( "PoliticalPartyManager.delete: failed to delete this PoliticalParty: " + e.getMessage() );
        }
    }
}
