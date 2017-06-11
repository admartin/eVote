package edu.uga.cs.evote.entity.impl;

import edu.uga.cs.evote.entity.User;
import edu.uga.cs.evote.persistence.impl.Persistent;

public class UserImpl extends Persistent implements User {
    
    private String           userName;
    private String           password;
    private String           email;
    private String           firstName;
    private String           lastName;
    private String           address;
  
    public UserImpl()
	{
                super(-1);
		this.setFirstName(null);
		this.setLastName(null);
		this.setUserName(null);
		this.setPassword(null);
		this.setAddress(null);
		this.setPersistencaLayer(getPersistencaLayer());
		this.setEmailAddress(null);
	}
    
   public UserImpl(String firstName, String lastName, String userName, String password, String emailAddress, String address)
	{
                super(-1);
		setFirstName(firstName);
                setLastName(lastName);
		setUserName(userName);
		setPassword(password);
		setAddress(address);
		setEmailAddress(emailAddress);
		
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
	public String getFirstName() {
		// TODO Auto-generated method stub
		return this.firstName;
	}

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;

	}

	@Override
	public String getLastName() {
		// TODO Auto-generated method stub
		return this.lastName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;

	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return this.userName;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;

	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;

	}

	@Override
	public String getEmailAddress() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public void setEmailAddress(String emailAddress) {
                this.email = emailAddress;
            
	}

	@Override
	public String getAddress() {
		// TODO Auto-generated method stub
		return this.address;
	}

	@Override
	public void setAddress(String address) {

            this.address = address;
	}

}
