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
import edu.uga.cs.evote.entity.Ballot;
//import edu.uga.ElectionsOfficers.entity.ElectionsOfficer;
import edu.uga.cs.evote.entity.ElectionsOfficer;
import edu.uga.cs.evote.entity.BallotItem;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.entity.impl.BallotImpl;
import edu.uga.cs.evote.object.ObjectLayer;

class BallotItemManager
{
    private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public BallotItemManager ( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( BallotItem BallotItem ) 
            throws EVException
    {
        String               insertBallotItemSql = "INSERT INTO ballotitem (voteCount, ballotID, issueID, electionID) Values (?,?,?,?)";
        		//"insert into BallotItem ( username, userpass, emailAddress, firstname, lastname, address, phone ) values ( ?, ?, ?, ?, ?, ?, ? )";              
        String               updateBallotItemSql = "update ballotitem  set voteCount = ?, BallotId = ?";              
        PreparedStatement    stmt;
        int                  inscnt;
        long                 BallotItemId;
        
        try {
            
            if( !BallotItem.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertBallotItemSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateBallotItemSql );

            System.out.println("STUFF " + BallotItem.getElectionId() + " " + BallotItem.getIssueId() );
            
            if( BallotItem.getVoteCount() != -1 ) // ballot is unique, so it is sufficient to get a ballot
                stmt.setLong( 1, BallotItem.getVoteCount() );
            else 
                throw new EVException( "BallotItemManager.save: can't save a BallotItem: opendate undefined" );

            if( BallotItem.getBallot().getId() != -1 )
                stmt.setLong( 2, BallotItem.getBallot().getId());
            else
                throw new EVException( "BallotItemManager.save: can't save a BallotItem: closedate undefined" );
           
            if( BallotItem.getElectionId() != -1 ) {
                stmt.setLong( 4, BallotItem.getElectionId());
                stmt.setLong(3,-1);
                
            }
            else if( BallotItem.getIssueId() != -1 ) {
                stmt.setLong(4, -1);
                stmt.setLong( 3, BallotItem.getIssueId());
                
            }
            else
                throw new EVException( "BallotItemManager.save: can't save a BallotItem: closedate undefined" );
           
           
            inscnt = stmt.executeUpdate();

            if( !BallotItem.isPersistent() ) {
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
                            BallotItemId = r.getLong( 1 );
                            if( BallotItemId > 0 )
                                BallotItem.setId( BallotItemId ); // set this BallotItem's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "BallotItemManager.save: failed to save a BallotItem" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "BallotItemManager.save: failed to save a BallotItem: " + e );
        }
    }

    public List<BallotItem> restore( BallotItem modelBallotItem ) 
            throws EVException
    {
        String       selectBallotItemSql = "select itemID, voteCount,  ballotID, issueID, electionID from BallotItem";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<BallotItem> BallotItems = new ArrayList<BallotItem>();

        condition.setLength( 0 );
        
        // form the query based on the given BallotItem object instance
        query.append( selectBallotItemSql );
        
        if( modelBallotItem != null ) {
            if( modelBallotItem.getId() >= 0 ) // id is unique, so it is sufficient to get a BallotItem
                query.append( " where itemID = " + modelBallotItem.getId() );
            else {
                if( modelBallotItem.getVoteCount() != -1 )
                    condition.append( " voteCount = '" + modelBallotItem.getVoteCount() + "'" );

                if( modelBallotItem.getIssueId() != -1 ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " issueID = '" + modelBallotItem.getBallot().getId() + "'" );
                }
                
                if( modelBallotItem.getElectionId() != -1 ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " electionID = '" + modelBallotItem.getElectionId() + "'" );
                }
                
                if( modelBallotItem.getBallot().getId() != -1 ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " ballotID = '" + modelBallotItem.getBallot().getId() + "'" );
                }

               

                if( condition.length() > 0 ) {
                    query.append(  " where " );
                    query.append( condition );
                }
            }
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent BallotItem objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                long   id;
                int voteCount;
                long electionId;
                long issueId;
                long ballotId;
                
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    voteCount = rs.getInt( 2 );
                    electionId = rs.getLong( 5 );
                    issueId = rs.getLong(4  );
                    ballotId = rs.getLong( 3 );
                    
                    Ballot ballot = new BallotImpl(null,null,null,true,null);
                    ballot.setId(ballotId);
                    
                    BallotItem BallotItem = objectLayer.createBallotItem(ballot,voteCount,electionId,issueId);
                    BallotItem.setId( id );

                
                    BallotItems.add( BallotItem );

                }
                
                return BallotItems;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "BallotItemManager.restore: Could not restore persistent BallotItem object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new EVException( "BallotItemManager.restore: Could not restore persistent BallotItem objects" );
    }
    
    public List<BallotItem> restoreBallotItemEstablishedByBallotItem( BallotItem BallotItem ) 
            throws EVException
    {
        String selectBallotItemSql = "select c.id, c.openDate, c.closeDate, c.established, p.id, " +
                                 "p.username, p.userpass, p.emailAddress, p.firstname, p.lastname, p.address, " +
                                 "p.phone from BallotItem c, BallotItem p where c.founderid = p.id";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<BallotItem>   BallotItems = new ArrayList<BallotItem>();

        condition.setLength( 0 );
        
        // form the query based on the given BallotItem object instance
        query.append( selectBallotItemSql );
        
        if( BallotItem != null ) {
            if( BallotItem.getId() >= 0 ) // id is unique, so it is sufficient to get a BallotItem
                query.append( " and p.id = " + BallotItem.getId() );
//            else if( BallotItem.getOpenDate() != null ) // userName is unique, so it is sufficient to get a User
//                query.append( " and p.openDate = '" + BallotItem.getOpenDate() + "'" );
//            else {
//                if( BallotItem.getCloseDate() != null )
//                    condition.append( " p.closeDate = '" + BallotItem.getCloseDate() + "'" );
//
//                if( BallotItem.getElectoralDistrict() != null && condition.length() == 0 )
//                    condition.append( " p.electoralDistrict = '" + BallotItem.getElectoralDistrict() + "'" );

                /*if( User.getPhone() != null && condition.length() == 0 )
                    condition.append( " p.phone = '" + User.getPhone() + "'" );
                else
                    condition.append( " AND p.phone = '" + User.getPhone() + "'" );
                */
                if( condition.length() > 0 ) {
                    query.append( condition );
                }
            }
        //}
                
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

//                    BallotItem nextBallotItem = objectLayer.createBallotItem(openDate,closeDate,true,electoralDistrict);
//                    nextBallotItem.setId( id );
//
//                
//                    
//                    BallotItems.add( nextBallotItem);
                }
                
                return BallotItems;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "UserManager.restoreEstablishedBy: Could not restore persistent BallotItem objects; Root cause: " + e );
        }

        throw new EVException( "UserManager.restoreEstablishedBy: Could not restore persistent BallotItem objects" );
    }
    
    public void delete( BallotItem BallotItem ) 
            throws EVException
    {
        String               deleteUserSql = "delete from ballot where ballotID = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given User object instance
        if( !BallotItem.isPersistent() ) // is the User object persistent?  If not, nothing to actually delete
            return;
        
        try {
            
            //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
            //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
            stmt = (PreparedStatement) conn.prepareStatement( deleteUserSql );
            
            stmt.setLong( 1, BallotItem.getId() );
            
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