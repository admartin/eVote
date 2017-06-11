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
import edu.uga.cs.evote.entity.VoteRecord;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.entity.PoliticalParty;
//import edu.uga.ElectionsOfficers.entity.Voter;

import edu.uga.cs.evote.object.ObjectLayer;

class VoteRecordManager
{
    private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public VoteRecordManager ( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( VoteRecord VoteRecord ) 
            throws EVException
    {
        String               insertVoteRecordSql = "INSERT INTO record (date, itemID, voterID) Values (?,?,?)";
        		//"insert into VoteRecord ( username, userpass, emailAddress, firstname, lastname, address, phone ) values ( ?, ?, ?, ?, ?, ?, ? )";              
        String               updateVoteRecordSql = "update record set date = ?, itemID = ?, voterID = ? where recordId=? ";              
        PreparedStatement    stmt;
        int                  inscnt;
        long                 VoteRecordId;
        
        try {
            
            if( !VoteRecord.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertVoteRecordSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateVoteRecordSql );

            if( VoteRecord.getDate() != null ) // Votersuser is unique, so it is sufficient to get a User
                stmt.setString( 1, VoteRecord.getDate().toString() );
            else 
                throw new EVException( "VoteRecordManager.save: can't save a VoteRecord: Name undefined" );

            if( VoteRecord.getBallot().getId() != -1 )
                stmt.setLong(2, VoteRecord.getBallot().getId() );
            else
                throw new EVException( "VoteRecordManager.save: can't save a VoteRecord: party undefined" );
            
            if( VoteRecord.getVoter().getId() != -1 )
                stmt.setLong(3, VoteRecord.getVoter().getId() );
            else
                throw new EVException( "VoteRecordManager.save: can't save a VoteRecord: election undefined" );
            
            if( VoteRecord.isPersistent() )
                stmt.setLong( 4, VoteRecord.getId() );

            inscnt = stmt.executeUpdate();

            if( !VoteRecord.isPersistent() ) {
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
                            VoteRecordId = r.getLong( 1 );
                            if( VoteRecordId > 0 )
                                VoteRecord.setId( VoteRecordId ); // set this VoteRecord's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "VoteRecordManager.save: failed to save a VoteRecord" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "VoteRecordManager.save: failed to save a VoteRecord: " + e );
        }
    }

    public List<VoteRecord> restore( VoteRecord modelVoteRecord ) 
            throws EVException
    {
        String       selectVoteRecordSql = "select name, voteCount from VoteRecord";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<VoteRecord> VoteRecords = new ArrayList<VoteRecord>();

        condition.setLength( 0 );
        
        // form the query based on the given VoteRecord object instance
        query.append( selectVoteRecordSql );
        
        if( modelVoteRecord != null ) {
            if( modelVoteRecord.getId() >= 0 ) // id is unique, so it is sufficient to get a VoteRecord
                query.append( " where id = " + modelVoteRecord.getId() );
            
            else if( modelVoteRecord.getDate() != null ) // userName is unique, so it is sufficient to get a VoteRecord
                query.append( " where name = '" + modelVoteRecord.getDate() + "'" );
            else {
                if( modelVoteRecord.getBallot().getId() != -1 ) 
                query.append( " where political party = '" + modelVoteRecord.getBallot().getId() + "'" );
                
                if( modelVoteRecord.getVoter().getId() != -1 ) 
                query.append( " where election = '" + modelVoteRecord.getVoter().getId() + "'" );
                
                if( condition.length() > 0 ) {
                    query.append(  " where " );
                    query.append( condition );
                }
            }
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent VoteRecord objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                long   id;
                Date date;
                long voterId;
                long itemId;
                
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    date = rs.getDate( 2 );
                    voterId =  rs.getLong( 3 );
                    itemId = rs.getLong(4);
                 

                  //  VoteRecord VoteRecord = objectLayer.createVoteRecord(id, voterId, itemId,date);
                    //VoteRecord.setId( id );

                    //VoteRecords.add( VoteRecord );

                }
                
                return VoteRecords;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "VoteRecordManager.restore: Could not restore persistent VoteRecord object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new EVException( "VoteRecordManager.restore: Could not restore persistent VoteRecord objects" );
    }
    
    public List<VoteRecord> restoreVoteRecordEstablishedByVoteRecord( VoteRecord VoteRecord ) 
            throws EVException
    {
        String selectVoteRecordSql = "select c.id, c.name, c.address, c.established, p.id, " +
                                 "p.username, p.userpass, p.emailAddress, p.firstname, p.lastname, p.address, " +
                                 "p.phone from VoteRecord c, VoteRecord p where c.founderid = p.id";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<VoteRecord>   VoteRecords = new ArrayList<VoteRecord>();

        condition.setLength( 0 );
        
        // form the query based on the given VoteRecord object instance
        query.append( selectVoteRecordSql );
        
        if( VoteRecord != null ) {
            if( VoteRecord.getId() >= 0 ) // id is unique, so it is sufficient to get a VoteRecord
                query.append( " and p.id = " + VoteRecord.getId() );
            else if( VoteRecord.getDate() != null ) // userName is unique, so it is sufficient to get a User
                query.append( " and p.name = '" + VoteRecord.getDate() + "'" );
            else {
//                if( VoteRecord.getPoliticalParty() != null )
//                    condition.append( " p.politicalParty = '" + VoteRecord.getPoliticalParty() + "'" );

//                if( VoteRecord.getVoter().getId() != null && condition.length() == 0 )
//                    condition.append( " p.election = '" + VoteRecord.getVoter().getId() + "'" );
//                else
//                    condition.append( " AND p.election = '" + VoteRecord.getVoter().getId() + "'" );
//                            
                if( condition.length() > 0 ) {
                    query.append( condition );
                }
            }
        }
                
        try {

            stmt = conn.createStatement();

            // retrieve the persistent VoteRecord objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                long   id;
                String name;
                PoliticalParty politicalParty;
                Election election;
                VoteRecord   nextVoteRecord = null;
                
                ResultSet rs = stmt.getResultSet();
                 
                while( rs.next() ) {
                    
                    id = rs.getLong( 1 );
                    name = rs.getString( 2 );
                    politicalParty = (PoliticalParty) rs.getObject( 3 );
                    election = (Election) rs.getObject( 4 );
                    
                    nextVoteRecord = objectLayer.createVoteRecord(); // create a proxy VoteRecord object
                    // and now set its retrieved attributes
                    nextVoteRecord.setId( id );
//                    nextVoteRecord.setName( name );
//                    nextVoteRecord.setPoliticalParty(politicalParty) ;
//                    nextVoteRecord.setElection(election) ;
                    //nextVoteRecord.setEstablishedOn( establishedOn );
                    // set this to null for the "lazy" association traversal
                    //nextVoteRecord.setVoteRecordFounder( null );
                    
                    VoteRecords.add( nextVoteRecord );
                }
                
                return VoteRecords;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "VoteRecordManager.restoreEstablishedBy: Could not restore persistent VoteRecord objects; Root cause: " + e );
        }

        throw new EVException( "VoteRecordManager.restoreEstablishedBy: Could not restore persistent VoteRecord objects" );
    }
    
    public void delete( VoteRecord VoteRecord ) 
            throws EVException
    {
        String               deleteVoteRecordSql = "delete from record where recordID =?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given VoteRecord object instance
        if( !VoteRecord.isPersistent() ) // is the VoteRecord object persistent?  If not, nothing to actually delete
            return;
        
        try {
            
            //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
            //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
            stmt = (PreparedStatement) conn.prepareStatement( deleteVoteRecordSql );
            
            stmt.setLong( 1, VoteRecord.getId() );
            
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new EVException( "VoteRecordManager.delete: failed to delete this VoteRecord" );
            }
        }
        catch( SQLException e ) {
            throw new EVException( "VoteRecordManager.delete: failed to delete this VoteRecord: " + e.getMessage() );
        }
    }
}