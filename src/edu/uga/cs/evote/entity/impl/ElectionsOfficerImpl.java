package edu.uga.cs.evote.entity.impl;

import edu.uga.cs.evote.entity.ElectionsOfficer;

public class ElectionsOfficerImpl extends UserImpl implements ElectionsOfficer {
	
	//default constructor, setting everything to null
	public ElectionsOfficerImpl()
	{
		
		super.setFirstName(null);
		super.setLastName(null);
		super.setUserName(null);
		super.setPassword(null);
		super.setAddress(null);
		UserImpl.setPersistencaLayer(getPersistencaLayer());
		super.setEmailAddress(null);
	}
	
	
	
	public ElectionsOfficerImpl(int id, String firstName, String lastName, String userName, String password, String emailAddress, String address)
	{
            	
		super.setFirstName(firstName);
		super.setLastName(lastName);
		super.setUserName(userName);
		super.setPassword(password);
		super.setAddress(address);
		super.setEmailAddress(emailAddress);
		
	}
	
        @Override
        public String toString()
    {
        return "ElectionsOfficer[" + getId() + "] " + getUserName() + " " + getFirstName() + " " + getLastName() + " " 
                + getEmailAddress() + " " + getAddress() + " ";
    }
	
}