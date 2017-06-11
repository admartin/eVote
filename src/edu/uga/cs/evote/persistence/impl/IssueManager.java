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
import edu.uga.cs.evote.entity.Issue;
import edu.uga.cs.evote.entity.Issue;
import edu.uga.cs.evote.entity.PoliticalParty;
//import edu.uga.IssuesOfficers.entity.Voter;

import edu.uga.cs.evote.object.ObjectLayer;

class IssueManager
{
    private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public IssueManager ( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( Issue Issue ) 
            throws EVException
    {
        String               insertIssueSql = "INSERT INTO issue (question, yesCount, noCount) Values (?,?,?)";
        		//"insert into Issue ( username, userpass, emailAddress, firstname, lastname, address, phone ) values ( ?, ?, ?, ?, ?, ?, ? )";              
        String               updateIssueSql = "update  set Office = ?, isPartisan = ? where id = ?";              
        PreparedStatement    stmt;
        int                  inscnt;
        long                 IssueId;
        
        try {
            
            if( !Issue.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertIssueSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateIssueSql );

            
            if( Issue.getQuestion() != null ) // question is unique, so it is sufficient to get a User
                stmt.setString( 1, Issue.getQuestion() );
            else 
                throw new EVException( "IssueManager.save: can't save a Issue: Name undefined" );

            if( Issue.getYesCount() != -1 )
                stmt.setObject(2, Issue.getYesCount() );
            else
                throw new EVException( "IssueManager.save: can't save a Issue: party undefined" );
            
            if( Issue.getNoCount() != -1 )
                stmt.setObject(3, Issue.getNoCount() );
            else
                throw new EVException( "IssueManager.save: can't save a Issue: party undefined" );
            
           
//            if( Issue.isPersistent() )
//                stmt.setLong( 3, Issue.getId() );

            System.out.println(Issue.getQuestion());
            inscnt = stmt.executeUpdate();

            if( !Issue.isPersistent() ) {
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
                            IssueId = r.getLong( 1 );
                            if( IssueId > 0 )
                                Issue.setId( IssueId ); // set this Issue's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "IssueManager.save: failed to save a Issue" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "IssueManager.save: failed to save a Issue: " + e );
        }
    }

    public List<Issue> restore( Issue modelIssue ) 
            throws EVException
    {
        String       selectIssueSql = "select issueID, question, yesCount, noCount from Issue";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Issue> Issues = new ArrayList<Issue>();

        condition.setLength( 0 );
        
        // form the query based on the given Issue object instance
        query.append( selectIssueSql );
        
        if( modelIssue != null ) {
            if( modelIssue.getId() >= 0 ) // id is unique, so it is sufficient to get a Issue
                query.append( " where issueID = " + modelIssue.getId() );
            
            else if( modelIssue.getQuestion() != null ) // userName is unique, so it is sufficient to get a Issue
                query.append( " where question = '" + modelIssue.getQuestion() + "'" );
            else {
               
                if( modelIssue.getYesCount() != -1 )
                    condition.append( " yesCount = '" + modelIssue.getYesCount() + "'" );
                 
                if( modelIssue.getYesCount() != -1 )
                    condition.append( " noCount = '" + modelIssue.getNoCount() + "'" );
                
                if( condition.length() > 0 ) {
                    query.append(  " where " );
                    query.append( condition );
                }
            }
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent Issue objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                long   id;
                String question;
                int yesCount;
                int noCount;
           
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    question = rs.getString( 2 );
                    yesCount = rs.getInt(3 );
                    noCount = rs.getInt(3 );
                    
                   
                    Issue Issue = objectLayer.createIssue(question,yesCount,noCount,null);
                    Issue.setId( id );

                    Issues.add( Issue );

                }
                
                return Issues;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "IssueManager.restore: Could not restore persistent Issue object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new EVException( "IssueManager.restore: Could not restore persistent Issue objects" );
    }
    
    public List<Issue> restoreIssueEstablishedByIssue( Issue Issue ) 
            throws EVException
    {
        String selectIssueSql = "select c.id, c.name, c.address, c.established, p.id, " +
                                 "p.username, p.userpass, p.emailAddress, p.firstname, p.lastname, p.address, " +
                                 "p.phone from Issue c, Issue p where c.founderid = p.id";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Issue>   Issues = new ArrayList<Issue>();

        condition.setLength( 0 );
        
        // form the query based on the given Issue object instance
        query.append( selectIssueSql );
        
        if( Issue != null ) {
            if( Issue.getId() >= 0 ) // id is unique, so it is sufficient to get a Issue
                query.append( " and p.id = " + Issue.getId() );
            else if( Issue.getQuestion()!= null ) // userName is unique, so it is sufficient to get a User
                query.append( " and p.question = '" + Issue.getQuestion() + "'" );
            else {
               
                    condition.append( " p.yesCount = '" + Issue.getYesCount() + "'" );

                if( condition.length() > 0 ) {
                    query.append( condition );
                }
            }
        }
                
        try {

            stmt = conn.createStatement();

            // retrieve the persistent Issue objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                long   id;
                String office;
                boolean isPartisan;
                Issue nextIssue = null;
                
                ResultSet rs = stmt.getResultSet();
                
                while( rs.next() ) {
                    
                    id = rs.getLong( 1 );
                    office = rs.getString( 2 );
                    isPartisan =  rs.getBoolean ( 3 );
                   
                    nextIssue = objectLayer.createIssue(); // create a proxy Issue object
                    // and now set its retrieved attributes
//                    nextIssue.setId( id );
//                    nextIssue.setOffice( office );
//                    nextIssue.setIsPartisan(isPartisan) ;
                    
                    //nextIssue.setEstablishedOn( establishedOn );
                    // set this to null for the "lazy" association traversal
                    //nextIssue.setIssueFounder( null );
                    
                    Issues.add( nextIssue );
                }
                
                return Issues;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "IssueManager.restoreEstablishedBy: Could not restore persistent Issue objects; Root cause: " + e );
        }

        throw new EVException( "IssueManager.restoreEstablishedBy: Could not restore persistent Issue objects" );
    }
    
    public void delete( Issue Issue ) 
            throws EVException
    {
        String               deleteIssueSql = "delete from Issue where issueID = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given Issue object instance
        if( !Issue.isPersistent() ) // is the Issue object persistent?  If not, nothing to actually delete
            return;
        
        try {
            
            //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
            //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
            stmt = (PreparedStatement) conn.prepareStatement( deleteIssueSql );
            
            stmt.setLong( 1, Issue.getId() );
            
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new EVException( "IssueManager.delete: failed to delete this Issue" );
            }
        }
        catch( SQLException e ) {
            throw new EVException( "IssueManager.delete: failed to delete this Issue: " + e.getMessage() );
        }
    }
}
