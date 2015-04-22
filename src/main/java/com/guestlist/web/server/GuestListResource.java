package com.guestlist.web.server;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
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

@Path("/guestlist")
@Produces(MediaType.APPLICATION_JSON)
public class GuestListResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GuestListResource.class);
	
	private Client client;
	private static final ObjectMapper mapper = new ObjectMapper();
	
	public GuestListResource(Client client) {
		this.client = client;
	}

	@Path("guest")
    @GET
    public Guest getGuest(@QueryParam("id") String id) {
    	LOGGER.info("getGuest: {}", id);
    	
    	if(id == null) {
    		LOGGER.error("QueryParam id missing");
    		throw new WebApplicationException(Response.Status.BAD_REQUEST);
    	}
    	
    	GetRequestBuilder getRequestBuilder = client.prepareGet("guestlist", "guest", id.toString());
    	GetResponse response = getRequestBuilder.execute().actionGet();

    	Guest guest = null;
    	try {
    		guest = mapper.readValue(response.getSourceAsString(), Guest.class);
    	}
    	catch(IOException e) {
    		LOGGER.error("Could not map search hit to Guest", e);
    		throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
    	}
    	
    	return guest;
    }	
    
    @Path("guests/{event}")
    @GET
    public List<Guest> getGuestsForEvent(@PathParam("event") String eventName) {
    	LOGGER.info("getGuestsForEvent: {}", eventName);
    	List<Guest> guests = new ArrayList<>();

    	QueryBuilder qb = QueryBuilders.matchQuery("eventKey", Guest.DEFAULT_EVENT_KEY);    	
    	SearchRequestBuilder srb = client.prepareSearch("guestlist");
    	srb.setTypes("guest");
    	srb.setQuery(qb);
    	srb.setSize(100);
    	SearchResponse response = srb.execute().actionGet();
    	LOGGER.info("getGuestsForEvent: {}", response);
    	
    	for(SearchHit searchHit : response.getHits()) {
    		try {
    			Guest g = mapper.readValue(searchHit.getSourceAsString(), Guest.class);
    			guests.add(g);
    		} catch (IOException e) {
    			LOGGER.error("Could not map search hit to Guest", e);
    			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
    		}
    	}
    	
    	return guests;
    }
    
    @Path("guests/delete/{id}")
    @DELETE
    public Response deleteGuestById(@PathParam("id") String id) {
    	LOGGER.info("deleteGuestById: {}", id);
    	
    	if(id == null) {
    		LOGGER.error("QueryParam id missing");
    		throw new WebApplicationException(Response.Status.BAD_REQUEST);
    	}
    	
    	DeleteRequestBuilder drb = client.prepareDelete("guestlist", "guest", id);
    	DeleteResponse response = drb.execute().actionGet();
   	
    	return Response.created(URI.create(id)).entity(response).build();
    }
    
    @Path("allguests")
    @GET
    public List<Guest> getAllGuests() {
    	LOGGER.info("getAllGuests");
    	return getGuestsForEvent(Guest.DEFAULT_EVENT_KEY);
    }
	
	@Path("guest")
	@POST()
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Guest> addGuest(Guest guest) {
        LOGGER.info("addGuest: {}", guest);
        
        if(guest == null || guest.getId() == null) {
        	LOGGER.error("Invalid guest {}", guest);
        	throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
    	
    	IndexRequest indexRequest = new IndexRequest("guestlist","guest", guest.getId().toString());
    	indexRequest.source(new Gson().toJson(guest));
    	IndexResponse response = client.index(indexRequest).actionGet();
    	
//    	try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return getAllGuests();
	}
	
	private void refreshIndices(String... indices) {
		client.admin().indices().refresh(new RefreshRequest(indices)).actionGet();
	}
	
}
