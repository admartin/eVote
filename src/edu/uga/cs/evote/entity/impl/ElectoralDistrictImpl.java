package edu.uga.cs.evote.entity.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.persistence.impl.Persistent;

public class ElectoralDistrictImpl extends Persistent implements ElectoralDistrict {
	private List<Ballot> ballotList;
	private List<Voter> voterList;
	private String name;
	

	public ElectoralDistrictImpl()
	{
		name = null;
		ballotList = null;
		voterList = null;
	}

	public ElectoralDistrictImpl(long id, String Name, List<Ballot> ballotList, List<Voter> voterList)
	{
		
		this.name = Name;
		this.ballotList = ballotList;
                this.voterList = voterList;

	}
	@Override
	public long getId() {
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
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;

	}

	@Override
	public List<Voter> getVoters() throws EVException {
		if(voterList == null )
            if( isPersistent() ) {
                VoterImpl voter = new VoterImpl();
                voter.setElectoralDistrict(this);
                voterList = getPersistencaLayer().restoreVoter(voter);
            }
            else
                throw new EVException( "Not Persistant" );
	    
		return voterList;
	}

	@Override
	public List<Ballot> getBallots() throws EVException {
		if(ballotList == null )
            if( isPersistent() ) {
                BallotImpl ballot = new BallotImpl();
                ballot.setElectoralDistrict(this);
                ballotList = getPersistencaLayer().restoreBallot(ballot);
            }
            else
                throw new EVException( "Not Persistant" );
	
                return ballotList;
	}

	@Override
	public void addBallot(Ballot ballot) throws EVException {
		ballotList.add(ballot);

	}

	@Override
	public void deleteBallot(Ballot ballot) throws EVException {
		if(ballotList.indexOf(ballot) != -1)
			ballotList.remove(ballot);
		else
			throw new EVException("Does not exist");

	}
        
        @Override
        public String toString()
        {
        return "ElectoralDistrict[" + getId() + "] " + getName();
        }
}