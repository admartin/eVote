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
import edu.uga.cs.evote.entity.User;
import edu.uga.cs.evote.entity.impl.UserImpl;
import edu.uga.cs.evote.object.ObjectLayer;

class ElectionsOfficerManager
{
    private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public ElectionsOfficerManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( ElectionsOfficer ElectionOfficer ) 
            throws EVException
    {
        String               insertUserSql = "insert into User ( username, pass, email ) values ( ?, ?, ? )";
        String               insertElectionOfficerSql = "insert into ElectionsOfficer ( username, fName, lName, pass ) values (  ?, ?, ?, ? )";

        		//"insert into ElectionOfficer ( username, userpass, emailAddress, firstname, lastname, address, phone ) values ( ?, ?, ?, ?, ?, ?, ? )";              
        String               updateElectionOfficerSql = "update ElectionsOfficer set username = ?, fName = ?, lName = ?";              
        PreparedStatement    stmt;
        int                  inscnt;
        long                 ElectionOfficerId;
        long                 UserId;
          
        UserImpl user = new UserImpl();
        user.setAddress(ElectionOfficer.getAddress());
        user.setEmailAddress(ElectionOfficer.getEmailAddress());
        user.setFirstName(ElectionOfficer.getFirstName());
        user.setLastName(ElectionOfficer.getLastName());
        user.setUserName(ElectionOfficer.getUserName());
        user.setPassword(ElectionOfficer.getPassword());
        
        
        //try user insert
        try {
            
            stmt = (PreparedStatement) conn.prepareStatement( insertUserSql );
           
                
            if( ElectionOfficer.getUserName() != null )
                stmt.setString( 1, ElectionOfficer.getUserName() );
            else
                throw new EVException( "User.save: can't save a ElectionOfficer: user name undefined" );

            if( ElectionOfficer.getPassword() != null )
                stmt.setString( 2, ElectionOfficer.getPassword() );
            else
                throw new EVException( "User.save: can't save a ElectionOfficer: pass undefined" );
            
            if( ElectionOfficer.getEmailAddress() != null )
                stmt.setString( 3, ElectionOfficer.getEmailAddress() );
            else
                throw new EVException( "User.save: can't save a ElectionOfficer: email undefined" );
         
           
            inscnt = stmt.executeUpdate();

            if( !ElectionOfficer.isPersistent() ) {
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
                           UserId = r.getLong( 1 );
                           
                            if( UserId > 0 ) {
                                user.setId( UserId ); // set this ElectionOfficer's db id (proxy object)
                                
                            }}
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "UGH AN ElectionOfficerManager.save: failed to save a ElectionOfficer" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "ElectionOfficerManager.save: failed to save a ElectionOfficer: " + e );
        }
        
        
           //try elections insert
        try {
            
            if( !ElectionOfficer.isPersistent() ) 
                stmt = (PreparedStatement) conn.prepareStatement( insertElectionOfficerSql );
            else 
                stmt = (PreparedStatement) conn.prepareStatement( updateElectionOfficerSql );
            
            

            if( ElectionOfficer.getUserName() != null )
                stmt.setString( 1, ElectionOfficer.getUserName() );
            else
                throw new EVException( "ElectionOfficerManager.save: can't save a ElectionOfficer: user name undefined" );

            if( ElectionOfficer.getFirstName() != null )
                stmt.setString( 2, ElectionOfficer.getFirstName() );
            else
                throw new EVException( "ElectionOfficerManager.save: can't save a ElectionOfficer: first name undefined" );
            
            if( ElectionOfficer.getLastName() != null )
                stmt.setString( 3, ElectionOfficer.getLastName() );
            else
                throw new EVException( "ElectionOfficerManager.save: can't save a ElectionOfficer: last name undefined" );
         
            if( ElectionOfficer.getPassword() != null )
                stmt.setString( 4, ElectionOfficer.getPassword() );
            else
                throw new EVException( "ElectionOfficerManager.save: can't save a ElectionOfficer: last name undefined" );
            
            
            inscnt = stmt.executeUpdate();

            if( !ElectionOfficer.isPersistent() ) {
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
                            ElectionOfficerId = r.getLong( 1 );
                            System.out.println("IDSTUFF");
                            if( ElectionOfficerId > 0 )
                                ElectionOfficer.setId( ElectionOfficerId ); // set this ElectionOfficer's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "UGh an ElectionOfficerManager.save: failed to save a ElectionOfficer" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "ElectionOfficerManager.save: failed to save a ElectionOfficer: " + e );
        }
    }

    public List<ElectionsOfficer> restore( ElectionsOfficer modelElectionOfficer ) 
            throws EVException
    {
      
        String       selectElectionOfficerSql = "select eoID, username, pass, fName, lName from electionsofficer";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<ElectionsOfficer> ElectionOfficers = new ArrayList<ElectionsOfficer>();

        condition.setLength( 0 );
        
        // form the query based on the given ElectionOfficer object instance
        query.append( selectElectionOfficerSql );
        
        System.out.println("HERE1" + modelElectionOfficer.getUserName());
        if( modelElectionOfficer != null ) {
            if( modelElectionOfficer.getId() >= 0 ){ // id is unique, so it is sufficient to get a person
                query.append( " where eoID = " + modelElectionOfficer.getId() );
                 
            }
            else if( modelElectionOfficer.getUserName() != null ){ // userName is unique, so it is sufficient to get a person
                query.append( " where username = '" + modelElectionOfficer.getUserName() + "'" );
                
            }
            else {
                
                if( modelElectionOfficer.getPassword() != null )
                    condition.append( " password = '" + modelElectionOfficer.getPassword() + "'" );

                if( modelElectionOfficer.getEmailAddress() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " email = '" + modelElectionOfficer.getEmailAddress() + "'" );
                }

                if( modelElectionOfficer.getFirstName() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " fName = '" + modelElectionOfficer.getFirstName() + "'" );
                }

                if( modelElectionOfficer.getLastName() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " lName = '" + modelElectionOfficer.getLastName() + "'" );
                }

                if( modelElectionOfficer.getAddress() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " address = '" + modelElectionOfficer.getAddress() + "'" );
                }        
                
               

                if( condition.length() > 0 ) {
                    query.append(  " where " );
                    query.append( condition );
                }
            }
        }
        
        
        try {
 
            stmt = conn.createStatement();
           
            // retrieve the persistent ElectionOfficer objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                
               
                ResultSet rs = stmt.getResultSet();
                long   id;
                String userName;
                String password;
                String emailAddress;
                String firstName;
                String lastName;
                String address;
               
                
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    userName = rs.getString( 2 );
                    password = rs.getString( 3 );
//                    emailAddress = rs.getString( 4 );
                    firstName = rs.getString( 4 );
                    lastName = rs.getString( 5 );
//                    address = rs.getString( 7 );
                   System.out.println(firstName);
                    ElectionsOfficer ElectionOfficer = objectLayer.createElectionsOfficer(firstName, lastName, userName, password,null,null);
                    ElectionOfficer.setId( id );

                    ElectionOfficers.add( ElectionOfficer );

                }
                
                return ElectionOfficers;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "ElectionOfficerManager.restore: Could not restore persistent ElectionOfficer object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new EVException( "ElectionOfficerManager.restore: Could not restore persistent ElectionOfficer objects" );
    }
    
    public List<ElectionsOfficer> restoreElectionsOfficerEstablishedByElectionOfficer( ElectionsOfficer ElectionOfficer ) 
            throws EVException
    {
        String selectElectionOfficerSql = "select c.id, c.name, c.address, c.established, p.id, " +
                                 "p.username, p.userpass, p.emailAddress, p.firstname, p.lastname, p.address, " +
                                 "p.phone from ElectionsOfficer c, ElectionOfficer p where c.founderid = p.id";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<ElectionsOfficer>   ElectionsOfficers = new ArrayList<ElectionsOfficer>();

        condition.setLength( 0 );
        
        // form the query based on the given ElectionOfficer object instance
        query.append( selectElectionOfficerSql );
        
        if( ElectionOfficer != null ) {
            if( ElectionOfficer.getId() >= 0 ) // id is unique, so it is sufficient to get a ElectionOfficer
                query.append( " and p.id = " + ElectionOfficer.getId() );
            else if( ElectionOfficer.getFirstName() != null ) // userName is unique, so it is sufficient to get a ElectionOfficer
                query.append( " and p.username = '" + ElectionOfficer.getFirstName() + "'" );
            else {
                if( ElectionOfficer.getPassword() != null )
                    condition.append( " p.password = '" + ElectionOfficer.getPassword() + "'" );

                if( ElectionOfficer.getEmailAddress() != null && condition.length() == 0 )
                    condition.append( " p.emailAddress = '" + ElectionOfficer.getEmailAddress() + "'" );
                else
                    condition.append( " AND p.emailAddress = '" + ElectionOfficer.getEmailAddress() + "'" );

                if( ElectionOfficer.getFirstName() != null && condition.length() == 0 )
                    condition.append( " p.firstname = '" + ElectionOfficer.getFirstName() + "'" );
                else
                    condition.append( " AND p.firstname = '" + ElectionOfficer.getFirstName() + "'" );

                if( ElectionOfficer.getLastName() != null && condition.length() == 0 )
                    condition.append( " p.lastname = '" + ElectionOfficer.getLastName() + "'" );
                else
                    condition.append( " AND p.lastname = '" + ElectionOfficer.getLastName() + "'" );

                if( ElectionOfficer.getAddress() != null && condition.length() == 0 )
                    condition.append( " p.address = '" + ElectionOfficer.getAddress() + "'" );
                else
                    condition.append( " AND p.address = '" + ElectionOfficer.getAddress() + "'" );         

                /*if( ElectionOfficer.getPhone() != null && condition.length() == 0 )
                    condition.append( " p.phone = '" + ElectionOfficer.getPhone() + "'" );
                else
                    condition.append( " AND p.phone = '" + ElectionOfficer.getPhone() + "'" );
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
                          
                long   id;
                String name;
                String address;
                Date   establishedOn;
                ElectionsOfficer   nextElectionsOfficer = null;
                
                ResultSet rs = stmt.getResultSet();
                
                while( rs.next() ) {
                    
                    id = rs.getLong( 1 );
                    name = rs.getString( 2 );
                    address = rs.getString( 3 );
                    establishedOn = rs.getDate( 4 );
                    
                    nextElectionsOfficer = objectLayer.createElectionsOfficer(); // create a proxy ElectionsOfficer object
                    // and now set its retrieved attributes
                    nextElectionsOfficer.setId( id );
                    nextElectionsOfficer.setFirstName( name );
                    nextElectionsOfficer.setAddress( address );
                    //nextElectionsOfficer.setEstablishedOn( establishedOn );
                    // set this to null for the "lazy" association traversal
                    //nextElectionsOfficer.setElectionOfficerFounder( null );
                    
                    ElectionsOfficers.add( nextElectionsOfficer );
                }
                
                return ElectionsOfficers;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "ElectionOfficerManager.restoreEstablishedBy: Could not restore persistent ElectionsOfficer objects; Root cause: " + e );
        }

        throw new EVException( "ElectionOfficerManager.restoreEstablishedBy: Could not restore persistent ElectionsOfficer objects" );
    }
    
    public void delete( ElectionsOfficer ElectionOfficer ) 
            throws EVException
    {
        String               deleteElectionOfficerSql = "delete from ElectionsOfficer where eoID = ?";              
        String               deleteUserSql = "delete from User where username = ?";           
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given ElectionOfficer object instance
        if( !ElectionOfficer.isPersistent() ) { // is the ElectionOfficer object persistent?  If not, nothing to actually delete
            
            return;
        }
        try {
            
            //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
            //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
            stmt = (PreparedStatement) conn.prepareStatement( deleteElectionOfficerSql );
            
            stmt.setLong( 1, ElectionOfficer.getId() );
            
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new EVException( "ElectionOfficerManager.delete: failed to delete this ElectionOfficer" );
            }
        }
        catch( SQLException e ) {
            throw new EVException( "ElectionOfficerManager.delete: failed to delete this ElectionOfficer: " + e.getMessage() );
        }
        
        try {
            
            //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
            //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
            stmt = (PreparedStatement) conn.prepareStatement( deleteUserSql );
            
            stmt.setString( 1, ElectionOfficer.getUserName() );
            
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new EVException( "ElectionOfficerManager.delete: failed to delete this User" );
            }
        }
        catch( SQLException e ) {
            throw new EVException( "ElectionOfficerManager.delete: failed to delete this User: " + e.getMessage() );
        }
    }
}
