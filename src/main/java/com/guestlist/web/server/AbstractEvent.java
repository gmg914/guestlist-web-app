package com.guestlist.web.server;

import java.util.Collection;
import java.util.Date;

import org.joda.time.DateTime;

public abstract class AbstractEvent {
	private String user;
	protected String eventName;
	protected String eventKey;
	//protected DateTime eventDate;
	protected Date eventDate;
	//protected Collection<Guest> invitedGuests;
	
	public String getUser() {
		return user;
	}
	
	public String getEventName() {
		return eventName;
	}
	
	public String getEventKey() {
		return eventKey;
	}
	
	public Date getEventDate() {
		return eventDate;
	}
	
//	public Collection<Guest> getInvitedGuests() {
//		return invitedGuests;
//	}
//	
//	public void addGuest(Guest guest) {
//		invitedGuests.add(guest);
//	}
//	
//	public void deleteGuest(Guest guest) {
//		invitedGuests.remove(guest);
//	}
	
	public abstract void populateEventKey();
}
