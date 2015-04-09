package com.guestlist.web.server;


public class Guest {

	private String firstName;
	private String middleName;
	private String lastName;
	private String emailAddress;
	private String id;

	public Guest() {
        // Jackson deserialization
    }
	
    public Guest(String id, String firstName, String middleName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }
	
	public String getId() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getMiddleName() {
		return middleName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
}
