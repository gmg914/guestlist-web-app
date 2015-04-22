package com.guestlist.web.server;

public abstract class AbstractEvent {
	protected String eventName;
	protected String eventKey;
	
	public String getEventName() {
		return eventName;
	}
	
	public String getEventKey() {
		return eventKey;
	}
	
	public abstract void populateEventKey();
}
