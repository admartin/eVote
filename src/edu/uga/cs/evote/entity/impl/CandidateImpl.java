package edu.uga.cs.evote.entity.impl;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.persistence.impl.Persistent;

public class CandidateImpl extends Persistent implements Candidate {
        
        private PoliticalParty party;
        private long electionid;
        private String name;
        private int voteCount;
        private String partyName;
        
        public CandidateImpl() {
            this.party = null;
            this.name = null;
            this.electionid = -1;
            this.voteCount = 0;
            
        }
        
        public CandidateImpl(String name, PoliticalParty party, long electionid, int voteCount) {
            this.party = party;
            this.name = name;
            this.electionid = electionid;
            this.voteCount = voteCount;
            
        }
        
	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}

	@Override
	public void setId(long id) {
		// TODO Auto-generated method stub
		super.setId(id);
	}

	@Override
	public boolean isPersistent() {
		// TODO Auto-generated method stub
		return super.isPersistent();
	}
	
	public void setPersistent() {
		
	super.setPersistent();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
                this.name = name;
	}

	@Override
	public int getVoteCount() {
		// TODO Auto-generated method stub
		return this.voteCount;
	}

	@Override
	public void setVoteCount(int voteCount) throws EVException {
		// TODO Auto-generated method stub
		this.voteCount = voteCount;
	}

	@Override
	public void addVote() {
		// TODO Auto-generated method stub
		this.voteCount++;
	}

	@Override
	public long getElection() throws EVException {
		// TODO Auto-generated method stub
		return this.electionid;
	}

	@Override
	public void setElection(long electionid) throws EVException {
		// TODO Auto-generated method stub
                this.electionid = electionid;
	}

	@Override
	public PoliticalParty getPoliticalParty() throws EVException {
		// TODO Auto-generated method stub
		return this.party;
	}

	@Override
	public void setPoliticalParty(PoliticalParty politicalParty) throws EVException {
		// TODO Auto-generated method stub
                this.party = politicalParty;
	}
        
        @Override
            public String toString()
        {
            return "Candidate[" + getName() + "] " + this.getVoteCount() + " " + partyName + " ";  
        }
    
}