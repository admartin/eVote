package edu.uga.cs.evote.entity.impl;

import java.util.Date;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.VoteRecord;
import edu.uga.cs.evote.entity.Voter;

import edu.uga.cs.evote.persistence.impl.Persistent;

public class VoteRecordImpl extends Persistent implements VoteRecord
{
	
	Date date;
	Ballot ballot;
	Voter voter;

	public VoteRecordImpl()
	{
	
		this.date = null;
		this.ballot = null;
		this.voter = null;
		
	}
	
	public VoteRecordImpl(long recordID, Date date, Ballot ballot, Voter voter)
	{
		
		this.date = date;
		this.ballot = ballot;
		this.voter = voter;
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
	public boolean isPersistent() 
	{
		return super.isPersistent();
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public void setDate(Date date) {
		
			this.date = date;
		
	}

	@Override
	public Voter getVoter() throws EVException {
		return voter;
	}

	@Override
	public void setVoter(Voter voter) throws EVException {
		this.voter = voter;
	}

	@Override
	public Ballot getBallot() throws EVException {
		return ballot;
	}

	@Override
	public void setBallot(Ballot ballot) throws EVException {
		this.ballot = ballot;

	}

}