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
//import edu.uga.ElectionsOfficers.entity.ElectionsOfficer;
import edu.uga.cs.evote.entity.ElectionsOfficer;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.object.ObjectLayer;

class ElectoralDistrictManager
{
    private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public ElectoralDistrictManager ( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( ElectoralDistrict ElectoralDistrict ) 
            throws EVException
    {
        String               insertElectoralDistrictSql = "INSERT INTO electoraldistrict(name) Values (?)";
        		//"insert into ElectoralDistrict ( username, userpass, emailAddress, firstname, lastname, address, phone ) values ( ?, ?, ?, ?, ?, ?, ? )";              
        String               updateElectoralDistrictSql = "update electoraldistrict  set name = ?";              
        PreparedStatement    stmt;
        int                  inscnt;
        long                 ElectoralDistrictId;
        
        try {
            
            if( !ElectoralDistrict.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertElectoralDistrictSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateElectoralDistrictSql );

            
             
            if( ElectoralDistrict.getName() != null ) // ballot is unique, so it is sufficient to get a ballot
                stmt.setString( 1, ElectoralDistrict.getName());
            else 
                throw new EVException( "ElectoralDistrictManager.save: can't save a ElectoralDistrict: name undefined" );

            inscnt = stmt.executeUpdate();

            if( !ElectoralDistrict.isPersistent() ) {
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
                            ElectoralDistrictId = r.getLong( 1 );
                            if( ElectoralDistrictId > 0 )
                                ElectoralDistrict.setId( ElectoralDistrictId ); // set this ElectoralDistrict's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "ElectoralDistrictManager.save: failed to save a ElectoralDistrict" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "ElectoralDistrictManager.save: failed to save a ElectoralDistrict: " + e );
        }
    }

    public List<ElectoralDistrict> restore( ElectoralDistrict modelElectoralDistrict ) 
            throws EVException
    {
        String       selectElectoralDistrictSql = "select edID, name from ElectoralDistrict";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<ElectoralDistrict> ElectoralDistricts = new ArrayList<ElectoralDistrict>();

        condition.setLength( 0 );
        
        // form the query based on the given ElectoralDistrict object instance
        query.append( selectElectoralDistrictSql );
        
        if( modelElectoralDistrict != null ) {
            if( modelElectoralDistrict.getName() != null ) // id is unique, so it is sufficient to get a ElectoralDistrict
                query.append( " where name = '" + modelElectoralDistrict.getName() +"'" );
                
            else {
                if( modelElectoralDistrict.getName() != null )
                    condition.append( " name = '" + modelElectoralDistrict.getName() + "'" );


                if( condition.length() > 0 ) {
                    query.append(  " where " );
                    query.append( condition );
                }
            }
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent ElectoralDistrict objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                long   id;
                String Name;
                
                
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    Name = rs.getString(2);

                    ElectoralDistrict ElectoralDistrict = objectLayer.createElectoralDistrict((int)id,Name,null,null);
                    ElectoralDistrict.setId( id );

                    ElectoralDistricts.add( ElectoralDistrict );

                }
                
                return ElectoralDistricts;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "ElectoralDistrictManager.restore: Could not restore persistent ElectoralDistrict object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new EVException( "ElectoralDistrictManager.restore: Could not restore persistent ElectoralDistrict objects" );
    }
    
    public List<ElectoralDistrict> restoreElectoralDistrictEstablishedByElectoralDistrict( ElectoralDistrict ElectoralDistrict ) 
            throws EVException
    {
        String selectElectoralDistrictSql = "select c.id, c.openDate, c.closeDate, c.established, p.id, " +
                                 "p.username, p.userpass, p.emailAddress, p.firstname, p.lastname, p.address, " +
                                 "p.phone from ElectoralDistrict c, ElectoralDistrict p where c.founderid = p.id";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<ElectoralDistrict>   ElectoralDistricts = new ArrayList<ElectoralDistrict>();

        condition.setLength( 0 );
        
        // form the query based on the given ElectoralDistrict object instance
        query.append( selectElectoralDistrictSql );
        
        if( ElectoralDistrict != null ) {
            if( ElectoralDistrict.getId() >= 0 ) // id is unique, so it is sufficient to get a ElectoralDistrict
                query.append( " and p.id = " + ElectoralDistrict.getId() );
            else if( ElectoralDistrict.getName() != null ) // userName is unique, so it is sufficient to get a User
                query.append( " and p.name = '" + ElectoralDistrict.getName() + "'" );
            else {
             
                /*if( User.getPhone() != null && condition.length() == 0 )
                    condition.append( " p.phone = '" + User.getPhone() + "'" );
                else
                    condition.append( " AND p.phone = '" + User.getPhone() + "'" );
                */
                if( condition.length() > 0 ) {
                    query.append( condition );
                }
            }
        }
                
        try {

            stmt = conn.createStatement();

            // retrieve the persistent ElectionsOfficer objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                          
                 ResultSet rs = stmt.getResultSet();
                long   id;
                Date closeDate;
                Date openDate;
                ElectoralDistrict electoralDistrict;
                
                
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    closeDate = rs.getDate( 2 );
                    openDate = rs.getDate( 3 );
                    electoralDistrict = (ElectoralDistrict) rs.getObject(4);

                    //ElectoralDistrict nextElectoralDistrict = objectLayer.createElectoralDistrict(openDate,closeDate,true,electoralDistrict);
                   // nextElectoralDistrict.setId( id );

                
                    
                 //   ElectoralDistricts.add( nextElectoralDistrict);
                }
                
                return ElectoralDistricts;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "UserManager.restoreEstablishedBy: Could not restore persistent ElectoralDistrict objects; Root cause: " + e );
        }

        throw new EVException( "UserManager.restoreEstablishedBy: Could not restore persistent ElectoralDistrict objects" );
    }
    
    public void delete( ElectoralDistrict ElectoralDistrict ) 
            throws EVException
    {
        String               deleteUserSql = "delete from ElectoralDistrict where edID = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given User object instance
        if( !ElectoralDistrict.isPersistent() ) // is the User object persistent?  If not, nothing to actually delete
            return;
        
        try {
            
            //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
            //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
            stmt = (PreparedStatement) conn.prepareStatement( deleteUserSql );
            
            stmt.setLong( 1, ElectoralDistrict.getId() );
            
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new EVException( "UserManager.delete: failed to delete this User" );
            }
        }
        catch( SQLException e ) {
            throw new EVException( "UserManager.delete: failed to delete this User: " + e.getMessage() );
        }
    }
}
