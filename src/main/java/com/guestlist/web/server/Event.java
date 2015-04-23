package com.guestlist.web.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Event extends AbstractEvent {
	private Collection<Event> subEvents;

	public Event() {
        // Jackson deserialization
		this.subEvents = new ArrayList<Event>();
		this.invitedGuests = new HashSet<Guest>();
    }
	
	public Event(String eventName) {
		this.eventName = eventName;
		this.eventKey = eventName.toUpperCase();
		this.subEvents = new ArrayList<Event>();
		this.invitedGuests = new HashSet<Guest>();
	}
		
	public void populateEventKey() {
		this.eventKey = eventName.toUpperCase().replaceAll("\\s+","");
	}
	
	public void addSubEvent(Event event) {
		subEvents.add(event);
	}
	
	public Collection<Event> getSubEvents() {
		return subEvents;
	}
}
