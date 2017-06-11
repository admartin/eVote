package edu.uga.cs.evote.persistence.impl;


import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.BallotItem;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.entity.ElectionsOfficer;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.Issue;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.entity.User;
import edu.uga.cs.evote.entity.VoteRecord;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.persistence.PersistenceLayer;
import java.sql.Connection;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenceLayerImpl implements PersistenceLayer {

    
    
    private ElectionsOfficerManager electionsOfficerManager = null;
    private VoterManager voterManager = null;
    private BallotManager ballotManager = null;
    private CandidateManager candidateManager = null;
    private ElectionManager electionManager = null;
    private ElectoralDistrictManager electoralDistrictManager = null;
    private IssueManager issueManager = null;
    private PoliticalPartyManager politicalPartyManager = null;  
    private BallotItemManager ballotItemManager = null;  
    private VoteRecordManager voteRecordManager = null; 
     
    
    public PersistenceLayerImpl( Connection conn, ObjectLayer objectLayer )
    {
        electionsOfficerManager = new ElectionsOfficerManager( conn, objectLayer );
        voterManager = new VoterManager( conn, objectLayer );
        ballotManager = new BallotManager( conn, objectLayer );
        candidateManager = new CandidateManager( conn, objectLayer );
        electionManager = new ElectionManager( conn, objectLayer );
        electoralDistrictManager = new ElectoralDistrictManager( conn, objectLayer );
        issueManager = new IssueManager(conn, objectLayer);
        politicalPartyManager = new PoliticalPartyManager(conn, objectLayer);
        ballotItemManager = new BallotItemManager(conn,objectLayer);
        voteRecordManager = new VoteRecordManager(conn,objectLayer);
        
        System.out.println( "PersistenceLayerImpl.PersistenceLayerImpl(conn,objectLayer): initialized" );
    }
	@Override
	public List<ElectionsOfficer> restoreElectionsOfficer(ElectionsOfficer modelElectionsOfficer) throws EVException {
		return electionsOfficerManager.restore(modelElectionsOfficer);
	}

	@Override
	public void storeElectionsOfficer(ElectionsOfficer electionsOfficer) throws EVException {
            
            electionsOfficerManager.store(electionsOfficer);
	}

	@Override
	public void deleteElectionsOfficer(ElectionsOfficer electionsOfficer) throws EVException {
		
             electionsOfficerManager.delete(electionsOfficer);
	}

	@Override
	public List<Voter> restoreVoter(Voter modelVoter) throws EVException {
		return voterManager.restore(modelVoter);
	}

	@Override
	public void storeVoter(Voter voter) throws EVException {
		voterManager.store(voter);

	}

	@Override
	public void deleteVoter(Voter voter) throws EVException {
		voterManager.delete(voter);

	}

	@Override
	public List<Ballot> restoreBallot(Ballot modelBallot) throws EVException {
		// TODO Auto-generated method stub
		return ballotManager.restore(modelBallot);
	}

	@Override
	public void storeBallot(Ballot ballot) throws EVException {
		ballotManager.store(ballot);

	}

	@Override
	public void deleteBallot(Ballot ballot) throws EVException {
		ballotManager.delete(ballot);

	}

	@Override
	public List<Candidate> restoreCandidate(Candidate modelCandidate) throws EVException {
		// TODO Auto-generated method stub
		return candidateManager.restore(modelCandidate);
	}

	@Override
	public void storeCandidate(Candidate candidate) throws EVException {
		candidateManager.store(candidate);

	}

	@Override
	public void deleteCandidate(Candidate candidate) throws EVException {
		candidateManager.delete(candidate);

	}

	@Override
	public List<Election> restoreElection(Election modelElection) throws EVException {
		// TODO Auto-generated method stub
		return electionManager.restore(modelElection);
	}

	@Override
	public void storeElection(Election election) throws EVException {
		electionManager.store(election);

	}

	@Override
	public void deleteElection(Election election) throws EVException {
		electionManager.delete(election);

	}

	@Override
	public List<ElectoralDistrict> restoreElectoralDistrict(ElectoralDistrict modelElectoralDistrict)
			throws EVException {
		// TODO Auto-generated method stub
		return electoralDistrictManager.restore(modelElectoralDistrict);
	}

	@Override
	public void storeElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
                    
            electoralDistrictManager.store(electoralDistrict);
	}

	@Override
	public void deleteElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
		// TODO Auto-generated method stub
                electoralDistrictManager.delete(electoralDistrict);
	}

	@Override
	public List<Issue> restoreIssue(Issue modelIssue) throws EVException {
		// TODO Auto-generated method stub
		return issueManager.restore(modelIssue);
	}

	@Override
	public void storeIssue(Issue issue) throws EVException {
                issueManager.store(issue);
	}

	@Override
	public void deleteIssue(Issue issue) throws EVException {
		
            issueManager.delete(issue);

	}

	@Override
	public List<PoliticalParty> restorePoliticalParty(PoliticalParty modelPoliticalParty) throws EVException {
		// TODO Auto-generated method stub
		return politicalPartyManager.restore(modelPoliticalParty);
	}

	@Override
	public void storePoliticalParty(PoliticalParty politicalParty) throws EVException {
		// TODO Auto-generated method stub
                politicalPartyManager.store(politicalParty);
	}

	@Override
	public void deletePoliticalParty(PoliticalParty politicalParty) throws EVException {
		politicalPartyManager.delete(politicalParty);

	}

	@Override
	public List<VoteRecord> restoreVoteRecord(VoteRecord modelVoteRecord) throws EVException {
		// TODO Auto-generated method stub
		return voteRecordManager.restore(modelVoteRecord);
	}

	@Override
	public void storeVoteRecord(VoteRecord voteRecord) throws EVException {
	
            voteRecordManager.store(voteRecord);
	}

	@Override
	public void deleteVoteRecord(VoteRecord voteRecord) throws EVException {
		voteRecordManager.delete(voteRecord);

	}
        
        @Override
	public List<BallotItem> restoreBallotItem(BallotItem modelBallotItem) throws EVException {
		// TODO Auto-generated method stub
		return ballotItemManager.restore(modelBallotItem);
	}

	@Override
	public void storeBallotItem(BallotItem ballotItem) throws EVException {
	
            ballotItemManager.store(ballotItem);
	}

	@Override
	public void deleteBallotItem(BallotItem ballotItem) throws EVException {
                
            ballotItemManager.delete(ballotItem);
	}

	@Override
	public void storeBallotIncludesBallotItem(Ballot ballot, BallotItem ballotItem) throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public Ballot restoreBallotIncludesBallotItem(BallotItem ballotItem) throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BallotItem> restoreBallotIncludesBallotItem(Ballot ballot) throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBallotIncludesBallotItem(Ballot ballot, BallotItem ballotItem) throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public void storeCandidateIsCandidateInElection(Candidate candidate, Election election) throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public Election restoreCandidateIsCandidateInElection(Candidate candidate) throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Candidate> restoreCandidateIsCandidateInElection(Election election) throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCandidateIsCandidateInElection(Candidate candidate, Election election) throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public void storeElectoralDistrictHasBallotBallot(ElectoralDistrict electoralDistrict, Ballot ballot)
			throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public ElectoralDistrict restoreElectoralDistrictHasBallotBallot(Ballot ballot) throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ballot> restoreElectoralDistrictHasBallotBallot(ElectoralDistrict electoralDistrict)
			throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteElectoralDistrictHasBallotBallot(ElectoralDistrict electoralDistrict, Ballot ballot)
			throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public void storeCandidateIsMemberOfPoliticalParty(Candidate candidate, PoliticalParty politicalParty)
			throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public PoliticalParty restoreCandidateIsMemberOfPoliticalParty(Candidate candidate) throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Candidate> restoreCandidateIsMemberOfPoliticalParty(PoliticalParty politicalParty) throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCandidateIsMemberOfElection(Candidate candidate, PoliticalParty politicalParty)
			throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public void storeVoterBelongsToElectoralDistrict(Voter voter, ElectoralDistrict electoralDistrict)
			throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public ElectoralDistrict restoreVoterBelongsToElectoralDistrict(Voter voter) throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Voter> restoreVoterBelongsToElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteVoterBelongsToElection(Voter voter, ElectoralDistrict electoralDistrict) throws EVException {
		// TODO Auto-generated method stub

	}

}
