package edu.uga.cs.evote.entity.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.User;
import edu.uga.cs.evote.entity.VoteRecord;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.persistence.Persistable;
import edu.uga.cs.evote.persistence.impl.Persistent;
import static edu.uga.cs.evote.persistence.impl.Persistent.getPersistencaLayer;

public class VoterImpl extends UserImpl implements Voter {
	
        private int age;
        ElectoralDistrict ed;
        private List<VoteRecord> BallotVoteRecords;
        long district;
    public VoterImpl()
	{
        	
        	ed = null;
		super.setFirstName(null);
		super.setLastName(null);
		super.setUserName(null);
		super.setPassword(null);
		super.setAddress(null);
		UserImpl.setPersistencaLayer(getPersistencaLayer());
		super.setEmailAddress(null);
                this.setAge(0);
               BallotVoteRecords = null;
           this.district = -1;
	}       
	
	
	
	public VoterImpl(int voterID, ElectoralDistrict ed, String firstName, String lastName, String userName, String password, String emailAddress, String address, int age, List<VoteRecord> BallotVoteRecords, long district)
	{
               
                this.ed = ed;
		super.setFirstName(firstName);
		super.setLastName(lastName);
		super.setUserName(userName);
		super.setPassword(password);
		super.setAddress(address);
		super.setEmailAddress(emailAddress);
		this.setAge(age);
		this.BallotVoteRecords = BallotVoteRecords;
		this.district = district;
	}
        
    
	
	@Override
	public boolean isPersistent() {
		return super.isPersistent();
	}

	@Override
	public long getVoterId() {
		return super.getId();
	}
	
	
	public long getDistrict() {
		return this.district;
	}

	@Override
	public void setVoterId(int voterId) {
		super.setId(voterId);

	}
        

	@Override
	public int getAge() {
		// TODO Auto-generated method stub
		return age;
	}

	@Override
	public void setAge(int age) {
		this.age =age;

	}

	@Override
	public ElectoralDistrict getElectoralDistrict() throws EVException {
		return ed;
	}

	@Override
	public void setElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
		ed = electoralDistrict;

	}

	@Override
	public List<VoteRecord> getBallotVoteRecords() throws EVException {
		if(BallotVoteRecords == null )
            if( isPersistent() ) {
                VoteRecordImpl voteRecord = new VoteRecordImpl();
                voteRecord.setVoter(this);
                BallotVoteRecords = getPersistencaLayer().restoreVoteRecord(voteRecord);
            }
            else
                throw new EVException( "Not Persistant" );
        
        return BallotVoteRecords;
	}
        
        @Override
        public String toString()
    {
        return "Voter[" + getId() + "] " + getUserName() + " " + getFirstName() + " " + getLastName() + " " 
               + " " + getAddress() + " ";
    }

}