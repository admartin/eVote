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
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.entity.impl.ElectionImpl;
import edu.uga.cs.evote.entity.impl.PoliticalPartyImpl;
//import edu.uga.ElectionsOfficers.entity.Voter;

import edu.uga.cs.evote.object.ObjectLayer;

class CandidateManager
{
    private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public CandidateManager ( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( Candidate Candidate ) 
            throws EVException
    {
        String               insertCandidateSql = "INSERT INTO candidate (Name, partyName, electionID, voteCount) Values (?,?,?,?)";
        		//"insert into Candidate ( username, userpass, emailAddress, firstname, lastname, address, phone ) values ( ?, ?, ?, ?, ?, ?, ? )";              
        String               updateCandidateSql = "update candidate set name = ?, partyName = ?, electionID = ?, voteCount =? where candidateid = ?";              
        PreparedStatement    stmt;
        int                  inscnt;
        long                 CandidateId;
        
        try {
            
        	  if( !Candidate.isPersistent() ) {
                  stmt = (PreparedStatement) conn.prepareStatement( insertCandidateSql );
               
                
        	  }
              else{
                  stmt = (PreparedStatement) conn.prepareStatement( updateCandidateSql );
                  System.out.println("UPDATING!");
                  if(Candidate.getName() != null) {
             		 stmt.setLong(5, Candidate.getId());
             	 } else 
                      throw new EVException( "CandidateManager.save: can't save a Candidate: Name undefined" );

                  
              }
        	 
        	 /* if(Candidate.getId() != -1) {
        		 stmt.setLong(1, Candidate.getId());
        	 } else 
                 throw new EVException( "CandidateManager.save: can't save a Candidate: Name undefined" );
*/
        	  
            if( Candidate.getName() != null ) // Votersuser is unique, so it is sufficient to get a User
                stmt.setString( 1, Candidate.getName() );
            else 
                throw new EVException( "CandidateManager.save: can't save a Candidate: Name undefined" );

            if( Candidate.getPoliticalParty() != null )
                stmt.setString(2, Candidate.getPoliticalParty().getName() );
            else
                throw new EVException( "CandidateManager.save: can't save a Candidate: party undefined" );
            
            if( Candidate.getElection() != -1 )
                stmt.setLong(3, Candidate.getElection());
            else
                throw new EVException( "CandidateManager.save: can't save a Candidate: election undefined" );
            
            if( Candidate.getVoteCount() != -1 )
                stmt.setLong( 4, Candidate.getVoteCount() );
            
            
       	
            System.out.println("TRYING TO EXECUTE" + stmt);
            inscnt = stmt.executeUpdate();

            if( !Candidate.isPersistent() ) {
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
                            CandidateId = r.getLong( 1 );
                            if( CandidateId > 0 )
                                Candidate.setId( CandidateId ); // set this Candidate's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "CandidateManager.save: failed to save a Candidate" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "CandidateManager.save: failed to save a Candidate: " + e );
        }
    }

    public List<Candidate> restore( Candidate modelCandidate ) 
            throws EVException
    {
        String       selectCandidateSql = "select candidateID, name, voteCount, electionId, partyName from Candidate";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Candidate> Candidates = new ArrayList<Candidate>();

        condition.setLength( 0 );
        
        // form the query based on the given Candidate object instance
        query.append( selectCandidateSql );
        
        if( modelCandidate != null ) {
             if( modelCandidate.getId() >= 0 ) // id is unique, so it is sufficient to get a Ballot
                query.append( " where candidateid = " + modelCandidate.getId() );
           
             else if(modelCandidate.getName() != null) {
            	
             	query.append( " where name = '" + modelCandidate.getName() +  "'" );
             }
            else{
//                if( modelCandidate.getVoteCount() != -1 ){
//                    condition.append( " voteCount = '" + modelCandidate.getVoteCount() + "'" );
//                    System.out.println("HERE WOW2");   
//                }

                if( modelCandidate.getElection() != -1 ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " electionID = '" + modelCandidate.getElection() + "'" );
                   
                }

                if( modelCandidate.getPoliticalParty() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " partyName = '" + modelCandidate.getPoliticalParty().getName() + "'" );
                    
                }


                if( condition.length() > 0 ) {
                    query.append(  " where " );
                    query.append( condition );
                }
            }
        }
        
        try {
        	
        	
            stmt = conn.createStatement();

            // retrieve the persistent Candidate objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                long   id;
                long   electionID;
                int   voteCount;
                String name;
                String politicalParty;
                
                Election election;
                
                while( rs.next() ) {

                    id = rs.getLong(1);
                    name = rs.getString( 2 );
                   
                    voteCount = rs.getInt(3);
                    electionID =  rs.getLong(4);
                    politicalParty =  rs.getString( 5 );
                 
                    PoliticalParty politicalPartyo = new PoliticalPartyImpl(-1,politicalParty,null);
                    Election electiono = new ElectionImpl(true,"ELECTION");
                    Candidate Candidate = objectLayer.createCandidate(name, politicalPartyo, electionID,voteCount);
                    Candidate.setId( id );

                    Candidates.add( Candidate );
                    System.out.println("NEATO " + voteCount);
                }
                
                return Candidates;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "CandidateManager.restore: Could not restore persistent Candidate object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new EVException( "CandidateManager.restore: Could not restore persistent Candidate objects" );
    }
    
    public List<Candidate> restoreCandidateEstablishedByCandidate( Candidate Candidate ) 
            throws EVException
    {
        String selectCandidateSql = "select c.id, c.name, c.address, c.established, p.id, " +
                                 "p.username, p.userpass, p.emailAddress, p.firstname, p.lastname, p.address, " +
                                 "p.phone from Candidate c, Candidate p where c.founderid = p.id";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Candidate>   Candidates = new ArrayList<Candidate>();

        condition.setLength( 0 );
        
        // form the query based on the given Candidate object instance
        query.append( selectCandidateSql );
        
        if( Candidate != null ) {
            if( Candidate.getId() >= 0 ) // id is unique, so it is sufficient to get a Candidate
                query.append( " and p.id = " + Candidate.getId() );
            else if( Candidate.getName() != null ) // userName is unique, so it is sufficient to get a User
                query.append( " and p.name = '" + Candidate.getName() + "'" );
            else {
                if( Candidate.getPoliticalParty() != null )
                    condition.append( " p.politicalParty = '" + Candidate.getPoliticalParty() + "'" );

                if( Candidate.getElection() != -1 && condition.length() == 0 )
                    condition.append( " p.election = '" + Candidate.getElection() + "'" );
                else
                    condition.append( " AND p.election = '" + Candidate.getElection() + "'" );
                            
                if( condition.length() > 0 ) {
                    query.append( condition );
                }
            }
        }
                
        try {

            stmt = conn.createStatement();

            // retrieve the persistent Candidate objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                long   id;
                String name;
                PoliticalParty politicalParty;
                long election;
                Candidate   nextCandidate = null;
                
                ResultSet rs = stmt.getResultSet();
                 
                while( rs.next() ) {
                    
                    id = rs.getLong( 1 );
                    name = rs.getString( 2 );
                    politicalParty = (PoliticalParty) rs.getObject( 3 );
                    election = rs.getLong( 4 );
                    
                    nextCandidate = objectLayer.createCandidate(); // create a proxy Candidate object
                    // and now set its retrieved attributes
                    nextCandidate.setId( id );
                    nextCandidate.setName( name );
                    nextCandidate.setPoliticalParty(politicalParty) ;
                    nextCandidate.setElection(election) ;
                    //nextCandidate.setEstablishedOn( establishedOn );
                    // set this to null for the "lazy" association traversal
                    //nextCandidate.setCandidateFounder( null );
                    
                    Candidates.add( nextCandidate );
                }
                
                return Candidates;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "CandidateManager.restoreEstablishedBy: Could not restore persistent Candidate objects; Root cause: " + e );
        }

        throw new EVException( "CandidateManager.restoreEstablishedBy: Could not restore persistent Candidate objects" );
    }
    
    public void delete( Candidate Candidate ) 
            throws EVException
    {
        String               deleteCandidateSql = "delete from Candidate where name = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given Candidate object instance
        if( !Candidate.isPersistent() ) // is the Candidate object persistent?  If not, nothing to actually delete
            return;
        
        try {
            
            //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
            //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
            stmt = (PreparedStatement) conn.prepareStatement( deleteCandidateSql );
            
            stmt.setString( 1, Candidate.getName() );
            
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new EVException( "CandidateManager.delete: failed to delete this Candidate" );
            }
        }
        catch( SQLException e ) {
            throw new EVException( "CandidateManager.delete: failed to delete this Candidate: " + e.getMessage() );
        }
    }
}
