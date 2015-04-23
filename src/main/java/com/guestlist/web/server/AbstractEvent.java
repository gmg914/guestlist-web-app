package com.guestlist.web.server;

import java.util.Collection;

public abstract class AbstractEvent {
	protected String eventName;
	protected String eventKey;
	protected Collection<Guest> invitedGuests;
	
	public String getEventName() {
		return eventName;
	}
	
	public String getEventKey() {
		return eventKey;
	}
	
	public Collection<Guest> getInvitedGuests() {
		return invitedGuests;
	}
	
	public void addGuest(Guest guest) {
		invitedGuests.add(guest);
	}
	
	public void deleteGuest(Guest guest) {
		invitedGuests.remove(guest);
	}
	
	public abstract void populateEventKey();
}
