package com.guestlist.web.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Sets;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Guest {

	@JsonIgnore
	public final static long NO_HOUSEHOLD_ID = -1;
	
	@JsonIgnore
	public final static String DEFAULT_EVENT_KEY = "_DEFAULT_";
	
	private String firstName;
	private String middleName;
	private String lastName;
	private String emailAddress;
	private String id;
	private long householdId = NO_HOUSEHOLD_ID;
	private String eventKey = DEFAULT_EVENT_KEY;
	
	private Map<String,Boolean> eventRSVPs;

	public Guest() {
        // Jackson deserialization
        this.eventRSVPs = new HashMap<String,Boolean>();
    }
	
    public Guest(String id, String firstName, String middleName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.eventRSVPs = new HashMap<String,Boolean>();
    }
    
    public Guest(String id, String firstName, String middleName, String lastName, Long householdId, String eventKey) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.householdId = householdId;
        this.eventKey = eventKey;
        this.eventRSVPs = new HashMap<String,Boolean>();
    }
	
	public String getId() {
		return id;
	}
	
	public Long getHouseholdId() {
		return householdId;
	}
	
	public String getEventKey() {
		return eventKey;
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
	
	public Map<String,Boolean> getEventRSVPs() {
		return eventRSVPs;
	}
	
	public boolean equals(Guest guest) {
		return guest.getId().equals(this.getId());
	}
	
	public int hashCode() {
		return new Integer(this.getId());
	}
}
