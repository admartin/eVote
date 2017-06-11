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
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.VoteRecord;
//import edu.uga.ElectionsOfficers.entity.Voter;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.entity.impl.UserImpl;
import edu.uga.cs.evote.object.ObjectLayer;

class VoterManager
{
    private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public VoterManager ( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( Voter Voter ) 
            throws EVException
    {
        String               insertVoterSql = "INSERT INTO voter (username, password, age, fName, lName, address, district) Values (?,?,?,?,?,?,?)";
        String               insertUserSql = "insert into User ( username, pass, email ) values ( ?, ?, ? )";
        		//"insert into Voter ( username, userpass, emailAddress, firstname, lastname, address, phone ) values ( ?, ?, ?, ?, ?, ?, ? )";              
        String               updateVoterSql = "update voter  set username = ?, password = ?, emailAddress = ?, firstname = ?, lastname = ?, address = ?, district = ? where voterid = ?";              
        PreparedStatement    stmt;
        int                  inscnt;
        long                 VoterId;
        long                 UserId;
        
        UserImpl user = new UserImpl();
        user.setAddress(Voter.getAddress());
        user.setEmailAddress(Voter.getEmailAddress());
        user.setFirstName(Voter.getFirstName());
        user.setLastName(Voter.getLastName());
        user.setUserName(Voter.getUserName());
        user.setPassword(Voter.getPassword());
        
        System.out.println("NAME " + Voter.getFirstName());
        
        //try user insert
        try {
            
 
            stmt = (PreparedStatement) conn.prepareStatement( insertUserSql );
           
                
            if( Voter.getUserName() != null )
                stmt.setString( 1, Voter.getUserName() );
            else
                throw new EVException( "User.save: can't save a user: user name undefined" );

            if( Voter.getFirstName() != null )
                stmt.setString( 2, Voter.getPassword() );
            else
                throw new EVException( "User.save: can't save a user: pass undefined" );
            
            if( Voter.getLastName() != null )
                stmt.setString( 3, Voter.getEmailAddress() );
            else
                throw new EVException( "User.save: can't save a user: email undefined" );
                
           
          
          
            inscnt = stmt.executeUpdate();

            if( !user.isPersistent() ) {
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
                            if( UserId > 0 )
                                user.setId( UserId ); // set this user's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "userManager.save: failed to save a user" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "userManager.save: failed to save a user: " + e );
        }
        
        //try voter insert
        try {
            
            if( !Voter.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertVoterSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateVoterSql );

            if( Voter.getUserName() != null ) // Votersuser is unique, so it is sufficient to get a Voter
                stmt.setString( 1, Voter.getUserName() );
            else 
                throw new EVException( "VoterManager.save: can't save a Voter: userName undefined" );
            
            if( Voter.getPassword() != null ) // Votersuser is unique, so it is sufficient to get a Voter
                stmt.setString( 2, Voter.getPassword() );
            else 
                throw new EVException( "VoterManager.save: can't save a Voter: userName undefined" );

            if( Voter.getAge() != 0 )
                stmt.setLong( 3, Voter.getAge() );
            else
                throw new EVException( "VoterManager.save: can't save a Voter: password undefined" );

            if( Voter.getFirstName() != null )
                stmt.setString( 4,  Voter.getFirstName() );
            else
                throw new EVException( "VoterManager.save: can't save a Voter: emailAddress undefined" );

            if( Voter.getLastName() != null )
                stmt.setString( 5, Voter.getLastName() );
            else
                throw new EVException( "VoterManager.save: can't save a Voter: first name undefined" );

            if( Voter.getAddress() != null )
                stmt.setString( 6, Voter.getAddress() );
            else
                stmt.setNull(6, java.sql.Types.VARCHAR);
           
            stmt.setLong( 7, 1 );
           
            inscnt = stmt.executeUpdate();

            if( !Voter.isPersistent() ) {
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
                            VoterId = r.getLong( 1 );
                            if( VoterId > 0 )
                                Voter.setId( VoterId ); // set this Voter's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "VoterManager.save: failed to save a Voter" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "VoterManager.save: failed to save a Voter: " + e );
        }
    }

    public List<Voter> restore( Voter modelVoter ) 
            throws EVException
    {
        String       selectVoterSql = "select voterID, username, password, age,"
                + " fName, lName, address, district from Voter";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Voter> Voters = new ArrayList<Voter>();

        condition.setLength( 0 );
        
        // form the query based on the given Voter object instance
        query.append( selectVoterSql );
        
        if( modelVoter != null ) {
            if( modelVoter.getId() >= 0 ) // id is unique, so it is sufficient to get a Voter
                query.append( " where voterID = " + modelVoter.getId() );
            else if( modelVoter.getUserName() != null ) // userName is unique, so it is sufficient to get a Voter
                query.append( " where username = '" + modelVoter.getUserName() + "'" );
            else {
                if( modelVoter.getPassword() != null )
                    condition.append( " age = '" + modelVoter.getPassword() + "'" );

                if( modelVoter.getFirstName() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " fName = '" + modelVoter.getFirstName() + "'" );
                }

                if( modelVoter.getFirstName() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " lName = '" + modelVoter.getLastName() + "'" );
                }

                if( modelVoter.getAddress() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " address = '" + modelVoter.getAddress() + "'" );
                }        


                if( condition.length() > 0 ) {
                    query.append(  " where " );
                    query.append( condition );
                }
            }
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent Voter objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                long   id;
                String userName;
                String password;
                String firstName;
                String lastName;
                String address;
                long district;
                long age;
                String email = null;

                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    userName = rs.getString( 2 );
                    password = rs.getString( 3 );
                    age = rs.getLong( 4 );
                    firstName = rs.getString( 5 );
                    lastName = rs.getString( 6 );
                    address = rs.getString( 7 );
                    district = rs.getLong( 8 );
                    
                    //get user info ie email
                                      StringBuffer query2 = new StringBuffer( 100 );
                                        StringBuffer condition2 = new StringBuffer( 100 );
                                        Statement stmt2 = conn.createStatement();
                                        condition.setLength( 0 );
                                        
                                        // form the query based on the given User object instance
                                        String selectUserSql = "select email from User where username = '" + userName + "'";
                                        query2.append( selectUserSql );
                                        
                                        if( stmt2.execute( query2.toString() ) ) { // statement returned a result
                                            ResultSet rs2 = stmt2.getResultSet();
                                            while( rs2.next() ) {
                    
                                                email = rs2.getString(1);
                                            }
                                        }
                                        
                    
                    System.out.println("this " + district);
                    Voter Voter = objectLayer.createVoter( firstName, null, lastName,  userName, password, email,
			address,  (int)age, null,district);
            
                    Voter.setId( id );

                    Voters.add( Voter );

                }
                
                return Voters;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "VoterManager.restore: Could not restore persistent Voter object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new EVException( "VoterManager.restore: Could not restore persistent Voter objects" );
    }
    
    public List<Voter> restoreVoterEstablishedByVoter( Voter Voter ) 
            throws EVException
    {
        String selectVoterSql = "select c.id, c.name, c.address, c.established, p.id, " +
                                 "p.username, p.userpass, p.emailAddress, p.firstname, p.lastname, p.address, " +
                                 "p.phone from Voter c, Voter p where c.founderid = p.id";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Voter>   Voters = new ArrayList<Voter>();

        condition.setLength( 0 );
        
        // form the query based on the given Voter object instance
        query.append( selectVoterSql );
        
        if( Voter != null ) {
            if( Voter.getId() >= 0 ) // id is unique, so it is sufficient to get a Voter
                query.append( " and p.id = " + Voter.getId() );
            else if( Voter.getUserName() != null ) // userName is unique, so it is sufficient to get a Voter
                query.append( " and p.username = '" + Voter.getUserName() + "'" );
            else {
                if( Voter.getPassword() != null )
                    condition.append( " p.password = '" + Voter.getPassword() + "'" );

                if( Voter.getEmailAddress() != null && condition.length() == 0 )
                    condition.append( " p.emailAddress = '" + Voter.getEmailAddress() + "'" );
                else
                    condition.append( " AND p.emailAddress = '" + Voter.getEmailAddress() + "'" );

                if( Voter.getFirstName() != null && condition.length() == 0 )
                    condition.append( " p.firstname = '" + Voter.getFirstName() + "'" );
                else
                    condition.append( " AND p.firstname = '" + Voter.getFirstName() + "'" );

                if( Voter.getLastName() != null && condition.length() == 0 )
                    condition.append( " p.lastname = '" + Voter.getLastName() + "'" );
                else
                    condition.append( " AND p.lastname = '" + Voter.getLastName() + "'" );

                if( Voter.getAddress() != null && condition.length() == 0 )
                    condition.append( " p.address = '" + Voter.getAddress() + "'" );
                else
                    condition.append( " AND p.address = '" + Voter.getAddress() + "'" );         

                /*if( Voter.getPhone() != null && condition.length() == 0 )
                    condition.append( " p.phone = '" + Voter.getPhone() + "'" );
                else
                    condition.append( " AND p.phone = '" + Voter.getPhone() + "'" );
                */
                if( condition.length() > 0 ) {
                    query.append( condition );
                }
            }
        }
                
        try {

            stmt = conn.createStatement();

            // retrieve the persistent Voter objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                          
                long   id;
                String name;
                String address;
                Date   establishedOn;
                Voter   nextVoter = null;
                
                ResultSet rs = stmt.getResultSet();
                
                while( rs.next() ) {
                    
                    id = rs.getLong( 1 );
                    name = rs.getString( 2 );
                    address = rs.getString( 3 );
                    establishedOn = rs.getDate( 4 );
                    
                    nextVoter = objectLayer.createVoter(); // create a proxy Voter object
                    // and now set its retrieved attributes
                    nextVoter.setId( id );
                    nextVoter.setUserName( name );
                    nextVoter.setAddress( address );
                    //nextVoter.setEstablishedOn( establishedOn );
                    // set this to null for the "lazy" association traversal
                    //nextVoter.setVoterFounder( null );
                    
                    Voters.add( nextVoter );
                }
                
                return Voters;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "VoterManager.restoreEstablishedBy: Could not restore persistent Voter objects; Root cause: " + e );
        }

        throw new EVException( "VoterManager.restoreEstablishedBy: Could not restore persistent Voter objects" );
    }
    
    public void delete( Voter Voter ) 
            throws EVException
    {
        String               deleteVoterSql = "delete from Voter where voterID = ?";
        String               deleteUserSql = "delete from User where username = ?";
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given Voter object instance
        if( !Voter.isPersistent() ) // is the Voter object persistent?  If not, nothing to actually delete
            return;
        
        try {
            
            //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
            //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
            stmt = (PreparedStatement) conn.prepareStatement( deleteVoterSql );
            
            stmt.setLong( 1, Voter.getId() );
            
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new EVException( "VoterManager.delete: failed to delete this Voter" );
            }
        }
        catch( SQLException e ) {
            throw new EVException( "VoterManager.delete: failed to delete this Voter: " + e.getMessage() );
        }
        
        
        try {
            
            //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
            //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
            stmt = (PreparedStatement) conn.prepareStatement( deleteUserSql );
            
            stmt.setString( 1, Voter.getUserName() );
            
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