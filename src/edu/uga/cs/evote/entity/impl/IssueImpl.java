package edu.uga.cs.evote.entity.impl;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.Issue;
import edu.uga.cs.evote.persistence.impl.Persistent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IssueImpl extends Persistent implements Issue {

	  
        private String question;
        private int yesCount;
        private int noCount;
        private Ballot ballot;
        
        public IssueImpl() {
          
            this.question = null;
            this.yesCount =0;
            this.noCount = 0;
            this.ballot = null;
        }
        
        public IssueImpl(int id, String question, int yesCount, int noCount, Ballot ballot) {
            
            this.question = question;
            this.yesCount = yesCount;
            this.noCount = noCount;
            this.ballot = ballot;
        }
        
	@Override
	public int getVoteCount() {
		return yesCount+noCount;
	}

	@Override
	//we might need to change this but its only setting yes
	public void setVoteCount(int voteCount) throws EVException {
		if(voteCount > -1)
			yesCount = voteCount;

	}

	@Override
	//only adding to yes
	public void addVote() {
		yesCount++;

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
		super.setId(id);
	}

	@Override
	public boolean isPersistent() {
		// TODO Auto-generated method stub
		return super.isPersistent();
	}

	@Override
	public String getQuestion() {
		// TODO Auto-generated method stub
		return this.question;
	}

	@Override
	public void setQuestion(String question) {
		// TODO Auto-generated method stub
                this.question = question;
	}

	@Override
	public int getYesCount() {
		// TODO Auto-generated method stub
		return this.yesCount;
	}

	@Override
	public void setYesCount(int yesCount) throws EVException {
		if(yesCount > -1)
			this.yesCount = yesCount;
	}

	@Override
	public int getNoCount() {
		// TODO Auto-generated method stub
		return this.noCount;
	}
        

	@Override
	public void addYesVote() {
		yesCount++;

	}

	@Override
	public void addNoVote() {
		noCount++;

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