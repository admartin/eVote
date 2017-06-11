package edu.uga.cs.evote.entity.impl;

import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.persistence.impl.Persistent;

import static edu.uga.cs.evote.persistence.impl.Persistent.getPersistencaLayer;

public class ElectionImpl extends Persistent implements Election {

    private boolean isPartisan;
    private String office;
    private int voteCount = 0;
    private Ballot ballot;
    private List<Candidate> candidates;
    
    public ElectionImpl()
	{
		this.isPartisan = true;
                this.office = null;

    }
	
	
	
	public ElectionImpl(boolean isPartisan, String Office)
	{
            
		this.isPartisan = isPartisan;
                this.office = Office;
		
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
	public Ballot getBallot() throws EVException {
		// TODO Auto-generated method stub
		return this.ballot;
	}

	@Override
	public void setBallot(Ballot ballot) throws EVException {
		// TODO Auto-generated method stub
		this.ballot = ballot;
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

	@Override
	public String getOffice() {
		// TODO Auto-generated method stub
		return office;
	}

	@Override
	public void setOffice(String office) {
		this.office = office;

	}

	@Override
	public boolean getIsPartisan() {
		// TODO Auto-generated method stub
		return isPartisan;
	}

	@Override
	public void setIsPartisan(boolean isPartisan) {
		// TODO Auto-generated method stub
                this.isPartisan = isPartisan;
	}

	@Override
	public List<Candidate> getCandidates() throws EVException {
		// TODO Auto-generated method stub
		if(this.candidates == null)
		{
			if(isPersistent())
			{
				this.candidates = getPersistencaLayer().restoreCandidateIsCandidateInElection(this);
			}
			else
				throw new EVException("This election is not persistent, sooooo no");
		}
		return this.candidates;
	}

	@Override
	public void addCandidate(Candidate candidate) throws EVException {
		// TODO Auto-generated method stub
		if(this.candidates == null)
		{
			this.candidates = new ArrayList<Candidate>();
			this.candidates.add(candidate);
		}
		else
			this.candidates.add(candidate);
	}

	@Override
	public void deleteCandidate(Candidate candidate) throws EVException {
		// TODO Auto-generated method stub
		if(this.candidates != null)
		{
			if(this.candidates.indexOf(candidate) != -1)
			{
				this.candidates.add(candidate);
			}
			else
				throw new EVException("This candidate already exists");
		}
		else
			throw new EVException("No candidates to delete, man");

	}

        @Override
        public String toString()
    {
        return "Election[" + getId() + "] " + getOffice() + " " + getIsPartisan() + " ";
    }

    @Override
    public void setElectionId(long electionID) throws EVException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long getElectionId() throws EVException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setIssueId(long issueID) throws EVException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long getIssueId() throws EVException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}