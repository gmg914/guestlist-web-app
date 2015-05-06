package com.guestlist.web.server;

import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Path("/event")
@Produces(MediaType.APPLICATION_JSON)
public class EventResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EventResource.class);
	
	private Client client;
	//private static final ObjectMapper mapper = new ObjectMapper();
	private static final DateFormat eventDateFmt = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a");
	
	public EventResource(Client client) {
		this.client = client;
	}
	
	//@Path("event")
	@POST()
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Event> createEvent(Event event) {
        LOGGER.info("createEvent: {}", event);
        
        if(event == null || event.getEventName() == null) {
        	LOGGER.error("Invalid event {}", event);
        	throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    	
        event.populateEventKey();
    	IndexRequest indexRequest = new IndexRequest("guestlist","event", event.getEventKey());
    	indexRequest.source(new Gson().toJson(event));
    	IndexResponse response = client.index(indexRequest).actionGet();
    	
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	refreshIndices();
		return getEventsForUser(event.getUser());
	}
	
	@Path("/{user}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Event> getEventsForUser(@PathParam("user") String user) {
        LOGGER.info("getEventsForUser: {}", user);
        
        if(user == null) {
        	LOGGER.error("Invalid user {}", user);
        	throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    	
    	List<Event> events = new ArrayList<>();

    	QueryBuilder qb = QueryBuilders.matchQuery("user", user);    	
    	SearchRequestBuilder srb = client.prepareSearch("guestlist");
    	srb.setTypes("event");
    	srb.setQuery(qb);
    	srb.setSize(1000);
    	SearchResponse response = srb.execute().actionGet();
    	
    	ObjectMapper eventMapper = new ObjectMapper();
    	eventMapper.setDateFormat(eventDateFmt);
    	
    	for(SearchHit searchHit : response.getHits()) {
    		try {
    			Event g = eventMapper.readValue(searchHit.getSourceAsString(), Event.class);
    			events.add(g);
    		} catch (IOException e) {
    			LOGGER.error("Could not map search hit to Event", e);
    			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
    		}
    	}
    	
    	return events;
	}
	
	@Path("subevent")
	@POST()
	@Consumes(MediaType.APPLICATION_JSON)
	public SubEvent addSubEvent(SubEvent subEvent) {
        LOGGER.info("createEvent: {}", subEvent);
        
        if(subEvent == null || subEvent.getEventName() == null || subEvent.getParentEvent() == null) {
        	LOGGER.error("Invalid subEvent {}", subEvent);
        	throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    	
        subEvent.populateEventKey();
    	IndexRequest indexRequest = new IndexRequest("guestlist","event", subEvent.getEventKey());
    	indexRequest.source(new Gson().toJson(subEvent));
    	IndexResponse response = client.index(indexRequest).actionGet();
    	
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return subEvent;
	}

    @Path("subevent/{eventKey}")
    @GET
    public List<SubEvent> getSubEventsForEvent(@PathParam("eventKey") String eventKey) {
    	LOGGER.info("getSubEventsForEvent: {}", eventKey);
    	List<SubEvent> subEvents = new ArrayList<>();

    	QueryBuilder qb = QueryBuilders.matchQuery("parentEvent", eventKey);    	
    	SearchRequestBuilder srb = client.prepareSearch("guestlist");
    	srb.setTypes("event");
    	srb.setQuery(qb);
    	srb.setSize(100);
    	SearchResponse response = srb.execute().actionGet();
    	
    	ObjectMapper eventMapper = new ObjectMapper();
    	eventMapper.setDateFormat(eventDateFmt);
    	
    	for(SearchHit searchHit : response.getHits()) {
    		try {
    			SubEvent g = eventMapper.readValue(searchHit.getSourceAsString(), SubEvent.class);
    			subEvents.add(g);
    		} catch (IOException e) {
    			LOGGER.error("Could not map search hit to SubEvent", e);
    			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
    		}
    	}
    	
    	return subEvents;
    }
    
	private void refreshIndices(String... indices) {
		client.admin().indices().refresh(new RefreshRequest(indices)).actionGet();
	}
}
