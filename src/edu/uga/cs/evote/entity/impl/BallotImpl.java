package edu.uga.cs.evote.entity.impl;

import java.util.Date;
import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.BallotItem;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.VoteRecord;
import edu.uga.cs.evote.persistence.impl.Persistent;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BallotImpl extends Persistent implements Ballot {

    String openDate;
    String closeDate;
    String name;
    ElectoralDistrict electoralDistrict = null;
    boolean approved;
    
    //This might be a little extra, but it might be needed. 
    long id;
    List<BallotItem> ballotItems;
    List<VoteRecord> voteRecords;
    
    
    public BallotImpl()
	{
    	this.name=null;
        this.setOpenDate(null);
		this.setCloseDate(null);
        try {
            this.setElectoralDistrict(null);
        } catch (EVException ex) {
            Logger.getLogger(BallotImpl.class.getName()).log(Level.SEVERE, null, ex);
        }         

    }
	

	public BallotImpl(String name, String openDate, String closeDate, boolean approved, ElectoralDistrict electoralDistrict)
	{
        this.name = name;    
		this.setOpenDate(openDate);
                this.setCloseDate(closeDate);
        try {
            this.setElectoralDistrict(electoralDistrict);
        } catch (EVException ex) {
            Logger.getLogger(BallotImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
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
	public String getOpenDate() {
		// TODO Auto-generated method stub
		return openDate;
	}

	@Override
	public void setOpenDate(String openDate) 
	{
		// TODO Auto-generated method stub
		this.openDate = openDate;
	}
	
	@Override
	public void setName(String name) 
	{ 
		// TODO Auto-generated method stub
		this.name= name;
	} 
	@Override
	public String getName() 
	{
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public String getCloseDate() {
		// TODO Auto-generated method stub
		return this.closeDate;
	}

	@Override
	public void setCloseDate(String closeDate) {
		// TODO Auto-generated method stub
            this.closeDate = closeDate;
        }

	@Override
	public ElectoralDistrict getElectoralDistrict() throws EVException {
		// TODO Auto-generated method stub
//		if(electoralDistrict == null)
//		{
//			if(isPersistent())
//			{
//				electoralDistrict = getPersistencaLayer().restoreElectoralDistrictHasBallotBallot(this);
//			}
//			else
//				throw new EVException("This ballot is not persistent");
//		}
		return electoralDistrict;
	}

	@Override
	public void setElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
		// TODO Auto-generated method stub
                this.electoralDistrict = electoralDistrict;
        }

	@Override
	public List<BallotItem> getBallotItems() throws EVException {
		// TODO Auto-generated method stub
		if(ballotItems == null)
		{
			if(isPersistent())
			{
				ballotItems = getPersistencaLayer().restoreBallotIncludesBallotItem(this);
			}
			else
				throw new EVException("This ballot item is not persistent");
		}
		return ballotItems;
	}

	@Override
	public void addBallotItem(BallotItem ballotItem) throws EVException {
		// TODO Auto-generated method stub
		//!!!!!!!!!!!! this might need to go through the persistence layer. 
		this.ballotItems.add(ballotItem);

	}

	@Override
	public void deleteBallotItem(BallotItem ballotItem) throws EVException {
		// TODO Auto-generated method stub
		//!!!!!!!!!!!! this might need to go through the persistence layer. 
		//loop to iterate through all the ballotitems and check then delete
		if(this.ballotItems.indexOf(ballotItem) != -1)
		{
			this.ballotItems.remove(this.ballotItems.indexOf(ballotItem));
		}
		else
			throw new EVException("This ballot item does not exist, so cannot remove");
	}

	@Override
	public List<VoteRecord> getVoterVoteRecords() throws EVException {
		// TODO Auto-generated method stub
		if(this.voteRecords == null)
		{
			if(isPersistent())
			{
				voteRecords = getPersistencaLayer().restoreVoteRecord(null);
			}
			else
				throw new EVException("This is not persistent, so getting vote records is a no can do");
		}
		return this.voteRecords;
	}

        @Override
        public String toString()
    {
        return "Ballot[" + getId() + "] " + getOpenDate() + " " + getCloseDate() + " ";
    }
}