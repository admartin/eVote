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
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.entity.impl.ElectoralDistrictImpl;
import edu.uga.cs.evote.object.ObjectLayer;

class BallotManager
{
    private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public BallotManager ( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( Ballot Ballot ) 
            throws EVException
    {
        String               insertBallotSql = "INSERT INTO ballot (openDate, closeDate, edID, name) Values (?,?,?,?)";
        		//"insert into Ballot ( username, userpass, emailAddress, firstname, lastname, address, phone ) values ( ?, ?, ?, ?, ?, ?, ? )";              
        String               updateBallotSql = "update ballot  set openDate = ?, closeDate = ?, approved = ?";              
        PreparedStatement    stmt;
        int                  inscnt;
        long                 BallotId;
        
        try {
            
            if( !Ballot.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertBallotSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateBallotSql );

            
            
            if( Ballot.getOpenDate() != null ) // ballot is unique, so it is sufficient to get a ballot
                stmt.setString( 1, Ballot.getOpenDate() );
            else 
                throw new EVException( "BallotManager.save: can't save a Ballot: opendate undefined" );
          
            if( Ballot.getName() != null ) // ballot is unique, so it is sufficient to get a ballot
                stmt.setString( 4, Ballot.getName());
            else 
                throw new EVException( "BallotManager.save: can't save a Ballot: opendate undefined" );

            if( Ballot.getCloseDate() != null )
                stmt.setString( 2, Ballot.getCloseDate());
            else
                throw new EVException( "BallotManager.save: can't save a Ballot: closedate undefined" );
           
            if( Ballot.getElectoralDistrict() != null )
                stmt.setLong( 3, Ballot.getElectoralDistrict().getId());
            else
                throw new EVException( "BallotManager.save: can't save a Ballot: closedate undefined" );
           
            
            inscnt = stmt.executeUpdate();

            if( !Ballot.isPersistent() ) {
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
                            BallotId = r.getLong( 1 );
                            if( BallotId > 0 )
                                Ballot.setId( BallotId ); // set this Ballot's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "BallotManager.save: failed to save a Ballot" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "BallotManager.save: failed to save a Ballot: " + e );
        }
    }

    public List<Ballot> restore( Ballot modelBallot ) 
            throws EVException
    {
        String       selectBallotSql = "select ballotID, openDate, closeDate, edID,name from Ballot";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Ballot> Ballots = new ArrayList<Ballot>();

        condition.setLength( 0 );
        
        // form the query based on the given Ballot object instance
        query.append( selectBallotSql );
        
        if( modelBallot != null ) {
            if( modelBallot.getId() >= 0 ) // id is unique, so it is sufficient to get a Ballot
                {
            	System.out.println("ID GOOD");
            	query.append( " where ballotID = " + modelBallot.getId() );
                }
            else {
                if( modelBallot.getOpenDate() != null )
                    condition.append( " openDate = '" + modelBallot.getOpenDate() + "'" );

                if( modelBallot.getCloseDate() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " closeDate = '" + modelBallot.getCloseDate() + "'" );
                }

                if( modelBallot.getElectoralDistrict() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " edID = '" + modelBallot.getElectoralDistrict().getId() + "'" );
                    System.out.println("ID GOOD" + modelBallot.getElectoralDistrict().getId() );
                }
                
                if( modelBallot.getName() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " name = '" + modelBallot.getName() + "'" );
                    
                }


                if( condition.length() > 0 ) {
                    query.append(  " where " );
                    query.append( condition );
                }
            }
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent Ballot objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                long   id;
                String openDate;
                String closeDate;
                long edID;
                String name;
                
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    closeDate = rs.getString( 2 );
                    openDate = rs.getString( 3 );
                    edID =  rs.getLong(4);
                    name=rs.getString(5);
                    
                    ElectoralDistrict dis = new ElectoralDistrictImpl();
                    dis.setId(edID);
                    Ballot Ballot = objectLayer.createBallot(name,openDate,closeDate,true,dis);
                    Ballot.setId( id );

                
                    Ballots.add( Ballot );
                  

                }
                
                
                return Ballots;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "BallotManager.restore: Could not restore persistent Ballot object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new EVException( "BallotManager.restore: Could not restore persistent Ballot objects" );
    }
    
    public List<Ballot> restoreBallotEstablishedByBallot( Ballot Ballot ) 
            throws EVException
    {
        String selectBallotSql = "select c.id, c.openDate, c.closeDate, c.established, p.id, " +
                                 "p.username, p.userpass, p.emailAddress, p.firstname, p.lastname, p.address, " +
                                 "p.phone from Ballot c, Ballot p where c.founderid = p.id";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Ballot>   Ballots = new ArrayList<Ballot>();

        condition.setLength( 0 );
        
        // form the query based on the given Ballot object instance
        query.append( selectBallotSql );
        
        if( Ballot != null ) {
            if( Ballot.getId() >= 0 ) // id is unique, so it is sufficient to get a Ballot
                query.append( " and p.id = " + Ballot.getId() );
            else if( Ballot.getOpenDate() != null ) // userName is unique, so it is sufficient to get a User
                query.append( " and p.openDate = '" + Ballot.getOpenDate() + "'" );
            else {
                if( Ballot.getCloseDate() != null )
                    condition.append( " p.closeDate = '" + Ballot.getCloseDate() + "'" );

                if( Ballot.getElectoralDistrict() != null && condition.length() == 0 )
                    condition.append( " p.electoralDistrict = '" + Ballot.getElectoralDistrict() + "'" );

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
                String closeDate;
                String openDate;
                ElectoralDistrict electoralDistrict;
                String name;
                
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    closeDate = rs.getString( 2 );
                    openDate = rs.getString( 3 );
                    electoralDistrict = (ElectoralDistrict) rs.getObject(4);
                    name = rs.getString(5);
                    Ballot nextBallot = objectLayer.createBallot(name,openDate,closeDate,true,electoralDistrict);
                    nextBallot.setId( id );

                
                    
                    Ballots.add( nextBallot);
                }
                
                return Ballots;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "UserManager.restoreEstablishedBy: Could not restore persistent Ballot objects; Root cause: " + e );
        }

        throw new EVException( "UserManager.restoreEstablishedBy: Could not restore persistent Ballot objects" );
    }
    
    public void delete( Ballot Ballot ) 
            throws EVException
    {
        String               deleteUserSql = "delete from ballot where ballotID = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given User object instance
        if( !Ballot.isPersistent() ) // is the User object persistent?  If not, nothing to actually delete
            return;
        
        try {
            
            //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
            //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
            stmt = (PreparedStatement) conn.prepareStatement( deleteUserSql );
            
            stmt.setLong( 1, Ballot.getId() );
            
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