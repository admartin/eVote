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
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.entity.PoliticalParty;
//import edu.uga.ElectionsOfficers.entity.Voter;

import edu.uga.cs.evote.object.ObjectLayer;

class ElectionManager
{
    private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public ElectionManager ( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( Election Election ) 
            throws EVException
    {
        String               insertElectionSql = "INSERT INTO election ( office, isPartison) Values (?,?)";
        		//"insert into Election ( username, userpass, emailAddress, firstname, lastname, address, phone ) values ( ?, ?, ?, ?, ?, ?, ? )";              
        String               updateElectionSql = "update election  set Office = ?, isPartison = ? where id = ?";              
        PreparedStatement    stmt;
        int                  inscnt;
        long                 ElectionId;
        
        try {
            
            if( !Election.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertElectionSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateElectionSql );

            
            if( Election.getOffice() != null ) // Votersuser is unique, so it is sufficient to get a User
                stmt.setString( 1, Election.getOffice() );
            else 
                throw new EVException( "ElectionManager.save: can't save a Election: Name undefined" );

            if( (Boolean)Election.getIsPartisan() != null )
                stmt.setBoolean(2, Election.getIsPartisan() );
            else
                throw new EVException( "ElectionManager.save: can't save a Election: party undefined" );
            
            inscnt = stmt.executeUpdate();

            if( !Election.isPersistent() ) {
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
                            ElectionId = r.getLong( 1 );
                            if( ElectionId > 0 )
                                Election.setId( ElectionId ); // set this Election's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "ElectionManager.save: failed to save a Election" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();

        }
    }

    public List<Election> restore( Election modelElection ) 
            throws EVException
    {
        String       selectElectionSql = "select electionID, office, isPartison from Election";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Election> Elections = new ArrayList<Election>();

        condition.setLength( 0 );
        
        // form the query based on the given Election object instance
        query.append( selectElectionSql );
        
        if( modelElection != null ) {
            if( modelElection.getId() >= 0 ) {// id is unique, so it is sufficient to get a Election
                query.append( " where electionID = " + modelElection.getId() );
                
            }
            else if( modelElection.getOffice() != null ){ // userName is unique, so it is sufficient to get a Election
                query.append( " where office = '" + modelElection.getOffice() + "'" );
                System.out.println("WORKINGXXXX");
            }
            else {
            	
                query.append( " where isPartison = '" + modelElection.getIsPartisan() + "'" );
                
                if( condition.length() > 0 ) {
                    query.append(  " where " );
                    query.append( condition );
                }
            }
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent Election objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                long   id;
                String office;
                boolean isPartisan;
           
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    office = rs.getString( 2 );
                    isPartisan = rs.getBoolean(3 );
                   
                    Election Election = objectLayer.createElection(office, isPartisan);
                    Election.setId( id );

                    Elections.add( Election );

                }
                
                return Elections;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "ElectionManager.restore: Could not restore persistent Election object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new EVException( "ElectionManager.restore: Could not restore persistent Election objects" );
    }
    
    public List<Election> restoreElectionEstablishedByElection( Election Election ) 
            throws EVException
    {
        String selectElectionSql = "select c.id, c.name, c.address, c.established, p.id, " +
                                 "p.username, p.userpass, p.emailAddress, p.firstname, p.lastname, p.address, " +
                                 "p.phone from Election c, Election p where c.founderid = p.id";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Election>   Elections = new ArrayList<Election>();

        condition.setLength( 0 );
        
        // form the query based on the given Election object instance
        query.append( selectElectionSql );
        
        if( Election != null ) {
            if( Election.getId() >= 0 ) // id is unique, so it is sufficient to get a Election
                query.append( " and p.id = " + Election.getId() );
            else if( Election.getOffice() != null ) // userName is unique, so it is sufficient to get a User
                query.append( " and p.office = '" + Election.getOffice() + "'" );
            else {
               
                    condition.append( " p.isPartison = '" + Election.getIsPartisan() + "'" );

                if( condition.length() > 0 ) {
                    query.append( condition );
                }
            }
        }
                
        try {

            stmt = conn.createStatement();

            // retrieve the persistent Election objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                long   id;
                String office;
                boolean isPartisan;
                Election nextElection = null;
                
                ResultSet rs = stmt.getResultSet();
                
                while( rs.next() ) {
                    
                    id = rs.getLong( 1 );
                    office = rs.getString( 2 );
                    isPartisan =  rs.getBoolean ( 3 );
                   
                    nextElection = objectLayer.createElection(); // create a proxy Election object
                    // and now set its retrieved attributes
                    nextElection.setId( id );
                    nextElection.setOffice( office );
                    nextElection.setIsPartisan(isPartisan) ;
                    
                    //nextElection.setEstablishedOn( establishedOn );
                    // set this to null for the "lazy" association traversal
                    //nextElection.setElectionFounder( null );
                    
                    Elections.add( nextElection );
                }
                
                return Elections;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "ElectionManager.restoreEstablishedBy: Could not restore persistent Election objects; Root cause: " + e );
        }

        throw new EVException( "ElectionManager.restoreEstablishedBy: Could not restore persistent Election objects" );
    }
    
    public void delete( Election Election ) 
            throws EVException
    {
        String               deleteElectionSql = "delete from Election where electionid = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given Election object instance
        if( !Election.isPersistent() ) // is the Election object persistent?  If not, nothing to actually delete
            return;
        
        try {
            
            //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
            //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
            stmt = (PreparedStatement) conn.prepareStatement( deleteElectionSql );
            
            stmt.setLong( 1, Election.getId() );
            
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new EVException( "ElectionManager.delete: failed to delete this Election" );
            }
        }
        catch( SQLException e ) {
            throw new EVException( "ElectionManager.delete: failed to delete this Election: " + e.getMessage() );
        }
    }
}
