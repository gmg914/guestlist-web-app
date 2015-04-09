package com.my.demo.coffee;

import java.net.URI;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

@Path("/coffeeshop")
@Produces(MediaType.APPLICATION_JSON)
public class CoffeeShopResource {
	private Datastore datastore;
	
	public CoffeeShopResource(/*final MongoClient mongoClient*/) {
		//datastore = new Morphia().createDatastore(mongoClient, "Cafelito");
	}

	@Path("order")
	@POST()
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doPost(Order order) {
		//datastore.save(order);
		System.out.println("doPost");
		return Response.created(URI.create(order.getId())).entity(order).build();
	}
	
}
