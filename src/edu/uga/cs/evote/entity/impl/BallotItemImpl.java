package edu.uga.cs.evote.entity.impl;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.BallotItem;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.persistence.impl.Persistent;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BallotItemImpl extends Persistent implements BallotItem {

	Ballot ballot;
	int voteCount = 0;
        long electionId;
        long issueId;
	//default constructor. 
    public BallotItemImpl()
	{
    	 this.ballot = null;
         this.voteCount = 0;
         this.electionId = -1;
         this.issueId = -1;
         
    }
	public BallotItemImpl(Ballot ballot, int count, long electionId, long issueId)
	{
    	 this.ballot = ballot;
         this.voteCount = count;
         this.electionId = electionId;
         this.issueId = issueId;
         
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
		//this feels a bit wrong but it should be as simple as incrementing the voteCount;
		this.voteCount++;
	}

	@Override
	public Ballot getBallot() throws EVException {
		if(this.ballot == null)
		{
			if(isPersistent())
			{
				ballot = getPersistencaLayer().restoreBallotIncludesBallotItem(this);
			}
			else
				throw new EVException("Cannot get the ballot for this ballot item. (not persistent)");
		}
		// TODO Auto-generated method stub
		return ballot;
	}

	@Override
	public void setBallot(Ballot ballot) throws EVException {
		// TODO Auto-generated method stub
		this.ballot = ballot;
		//the below line should be ok. 
		//getPersistencaLayer().storeBallot(this.ballot);
	}
        
        
        @Override
	public void setElectionId(long electionID) throws EVException {
		// TODO Auto-generated method stub
		this.electionId = electionID;
	}
        
        @Override
        public long getElectionId() throws EVException {
		// TODO Auto-generated method stub
		return this.electionId;
	}
        
        @Override
        public void setIssueId(long issueID) throws EVException {
		// TODO Auto-generated method stub
		this.issueId = issueID;
		
	}
        
        @Override
        public long getIssueId() throws EVException {
		// TODO Auto-generated method stub
		return this.issueId;
	
	}
        
         @Override
        public String toString()
    {
            try {
                return "BallotItem[" + getId() + "] " + getVoteCount() + " " + this.getElectionId() + " " 
                        + this.getIssueId() + " " + this.getBallot().getId() + " ";
            } catch (EVException ex) {
                Logger.getLogger(BallotItemImpl.class.getName()).log(Level.SEVERE, null, ex);
            } return null;
    }

}