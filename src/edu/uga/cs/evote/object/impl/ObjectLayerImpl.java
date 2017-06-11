package edu.uga.cs.evote.object.impl;

import java.util.Date;
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
import edu.uga.cs.evote.entity.VoteRecord;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.entity.impl.BallotImpl;
import edu.uga.cs.evote.entity.impl.BallotItemImpl;
import edu.uga.cs.evote.entity.impl.CandidateImpl;
import edu.uga.cs.evote.entity.impl.ElectionImpl;
import edu.uga.cs.evote.entity.impl.ElectionsOfficerImpl;
import edu.uga.cs.evote.entity.impl.ElectoralDistrictImpl;
import edu.uga.cs.evote.entity.impl.IssueImpl;
import edu.uga.cs.evote.entity.impl.PoliticalPartyImpl;
import edu.uga.cs.evote.entity.impl.VoteRecordImpl;
import edu.uga.cs.evote.entity.impl.VoterImpl;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.persistence.PersistenceLayer;

public class ObjectLayerImpl implements ObjectLayer {

    PersistenceLayer p = null;
    
       
        public ObjectLayerImpl()
        {
            this.p = null;
            System.out.println("ObjectLayerImpl.ObjectLayerImpl(): initialized");
        }
    
        
        public ObjectLayerImpl(PersistenceLayer p)
        {
            this.p = p;
            System.out.println("ObjectLayerImpl.ObjectLayerImpl(p): initialized");
        }
        
	@Override
	public ElectionsOfficer createElectionsOfficer(String firstName, String lastName, String userName, String password,
			 String address, String emailAddress)
        {
            ElectionsOfficerImpl eo1 = new ElectionsOfficerImpl( -1, firstName, lastName, userName, password, emailAddress, address);
            eo1.setPersistencaLayer(p);
            return eo1;
        }
        
	@Override
	public ElectionsOfficer createElectionsOfficer() 
        {
            ElectionsOfficerImpl eo1 = new ElectionsOfficerImpl( -1,null, null, null, null, null, null);
            eo1.setId(-1);
            eo1.setPersistencaLayer(p);
            return eo1;
	}
 
	@Override
	public List<ElectionsOfficer> findElectionsOfficer(ElectionsOfficer eo1) 
                throws EVException 
        {
            return p.restoreElectionsOfficer( eo1 );
	}

	@Override
	public void storeElectionsOfficer(ElectionsOfficer eo1) 
                throws EVException 
        {
            p.storeElectionsOfficer( eo1 );
	}

	@Override
	public void deleteElectionsOfficer(ElectionsOfficer eo1) 
                throws EVException 
        {
            p.deleteElectionsOfficer( eo1 );
	}

	@Override
	public Voter createVoter(String firstName, ElectoralDistrict dist, String lastName, String userName, String password, String emailAddress,
			String address, int age, List<VoteRecord> record, long district) 
        {
            VoterImpl voter = new VoterImpl(-1, dist, firstName, lastName, userName, password, emailAddress, address, age, record, district);
            
            return voter;
        }

	@Override
	public Voter createVoter() 
        {
            VoterImpl voter = new VoterImpl(-1, null, null, null, null, null, null, null, -1, null,-1);
            
            return voter;
	}

	@Override
	public List<Voter> findVoter(Voter voter) 
                throws EVException 
            {
                return p.restoreVoter( voter );
            }

	@Override
	public void storeVoter(Voter voter) 
                throws EVException 
            {
                p.storeVoter( voter );
            }

	@Override
	public void deleteVoter(Voter voter) 
                throws EVException 
            {
                p.deleteVoter( voter );
            }

	@Override
	public PoliticalParty createPoliticalParty(long id, String name, List<Candidate> candidates) 
        {
            return new PoliticalPartyImpl( id, name, candidates);
                      
        }

	@Override
	public PoliticalParty createPoliticalParty() 
        {
            PoliticalPartyImpl polParty = new PoliticalPartyImpl(-1,null,null);
            
            return polParty;
	}

	@Override
	public List<PoliticalParty> findPoliticalParty(PoliticalParty polParty) 
                throws EVException 
        {
            return p.restorePoliticalParty( polParty );
	}

	@Override
	public void storePoliticalParty(PoliticalParty polParty) 
                throws EVException 
        {
            p.storePoliticalParty( polParty );
	}

	@Override
	public void deletePoliticalParty(PoliticalParty polParty) 
                throws EVException 
        {
            p.deletePoliticalParty( polParty );
	}

	@Override
	public ElectoralDistrict createElectoralDistrict(int id, String Name, List<Ballot> ballotList, List<Voter> voterList) 
        {
            ElectoralDistrictImpl eDistrict = new ElectoralDistrictImpl( id,Name,ballotList, voterList);
          
            return eDistrict; 
	}

	@Override
	public ElectoralDistrict createElectoralDistrict() 
        {
            ElectoralDistrictImpl eDistrict = new ElectoralDistrictImpl(-1,null,null,null);
            
            return eDistrict;
	}

	@Override
	public List<ElectoralDistrict> findElectoralDistrict(ElectoralDistrict eDistrict) 
                throws EVException 
        {
                        return p.restoreElectoralDistrict( eDistrict );
        }

	@Override
	public void storeElectoralDistrict(ElectoralDistrict eDistrict) 
                throws EVException 
        {
                        p.storeElectoralDistrict( eDistrict );
	}

	@Override
	public void deleteElectoralDistrict(ElectoralDistrict eDistrict) 
                throws EVException 
        {
                        p.deleteElectoralDistrict( eDistrict );

	}
        @Override
	public Ballot createBallot(String name, String openDate, String closeDate, boolean approved, ElectoralDistrict electoralDistrict) 
        {
            BallotImpl ballot = new BallotImpl( name,openDate, closeDate, approved, electoralDistrict);
           
            return ballot; 
	}

	@Override
	public Ballot createBallot() 
        {
            BallotImpl ballot = new BallotImpl(null,null, null, false, null);
            ballot.setId(-1);
           
            return ballot;
	}

	@Override
	public List<Ballot> findBallot(Ballot ballot) 
                throws EVException 
        {
                        return p.restoreBallot( ballot );
        }

	@Override
	public void storeBallot(Ballot ballot) 
                throws EVException 
        {
                        p.storeBallot( ballot );
	}

	@Override
	public void deleteBallot(Ballot ballot) 
                throws EVException 
        {
                        p.deleteBallot( ballot );

	}
        
         
	@Override
	public Candidate createCandidate(String name, PoliticalParty politicalParty, long electionid, int voteCount) throws EVException {
		// TODO Auto-generated method stub
		return new CandidateImpl(name,politicalParty,electionid, voteCount);
	}


	@Override
	public Candidate createCandidate() 
        {
            return new CandidateImpl(null,null,-1,0);
	}

	@Override
	public List<Candidate> findCandidate(Candidate candidate) 
                throws EVException 
        {
                        return p.restoreCandidate( candidate );
        }

	@Override
	public void storeCandidate(Candidate candidate) 
                throws EVException 
        {
                        p.storeCandidate( candidate );
	}

	@Override
	public void deleteCandidate(Candidate candidate) 
                throws EVException 
        {
                        p.deleteCandidate( candidate );

	}
        
       @Override
	public Issue createIssue(String question, int yesCount, int noCount, Ballot Ballot) throws EVException {
		// TODO Auto-generated method stub
		return new IssueImpl(-1,question, yesCount, noCount, Ballot);
	}

	@Override
	public Issue createIssue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Issue> findIssue(Issue issue) 
                throws EVException 
        {
                        return p.restoreIssue( issue );
        }

	@Override
	public void storeIssue(Issue issue) 
                throws EVException 
        {
                        p.storeIssue( issue );
	}

	@Override
	public void deleteIssue(Issue issue) 
                throws EVException 
        {
                        p.deleteIssue( issue );

	}
        @Override
	public Election createElection( String office, boolean isPartisan) 
        {
            ElectionImpl election = new ElectionImpl(  isPartisan, office);
           
            return election; 
	}

	@Override
	public Election createElection() 
        {
            ElectionImpl election = new ElectionImpl(false, null);
            election.setId(-1);
           
            return election;
	}

	@Override
	public List<Election> findElection(Election election) 
                throws EVException 
        {
                        return p.restoreElection( election );
        }

	@Override
	public void storeElection(Election election) 
                throws EVException 
        {
                        p.storeElection( election );
	}

	@Override
	public void deleteElection(Election election) 
                throws EVException 
        {
                        p.deleteElection( election );

	}
        
        @Override
	public VoteRecord createVoteRecord(long id, Ballot ballot, Voter voter,Date date) 
        {
            VoteRecordImpl vRecord = new VoteRecordImpl(id,  date, ballot, voter);
            
            return vRecord; 
	}

	@Override
	public VoteRecord createVoteRecord() 
        {
            VoteRecordImpl vRecord = new VoteRecordImpl(-1, null, null, null);
            vRecord.setId(-1);
            
            return vRecord;
	}

	@Override
	public List<VoteRecord> findVoteRecord(VoteRecord vRecord) 
                throws EVException 
        {
                        return p.restoreVoteRecord( vRecord );
        }

	@Override
	public void storeVoteRecord(VoteRecord vRecord) 
                throws EVException 
        {
                        p.storeVoteRecord( vRecord );
	}

	@Override
	public void deleteVoteRecord(VoteRecord vRecord) 
                throws EVException 
        {
                        p.deleteVoteRecord( vRecord );

	}
        
        @Override
	public BallotItem createBallotItem(Ballot ballot, int count, long electionId, long issueId) 
        {
            return new BallotItemImpl( ballot, count, electionId, issueId);
	}
        
        @Override
	public BallotItem createBallotItem() 
        {
            
        return new BallotItemImpl( null, 0, -1,-1);	
        }

	@Override
	public List<BallotItem> findBallotItem(BallotItem bItem) 
                throws EVException 
        {
                        return p.restoreBallotItem( bItem );
        }

	@Override
	public void storeBallotItem(BallotItem bItem) 
                throws EVException 
        {
                        p.storeBallotItem( bItem );
	}

	@Override
	public void deleteBallotItem(BallotItem bItem) 
                throws EVException 
        {
                        p.deleteBallotItem(bItem);

	}
        public void setPersistence( PersistenceLayer p )
        {
            this.p = p;        
        }
        
       
}