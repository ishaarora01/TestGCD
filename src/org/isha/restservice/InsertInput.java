package org.isha.restservice;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.isha.beans.Numbers;
import org.isha.db.GcdDAO;
import org.isha.exceptions.RestException;
import org.isha.jms.Sender;
import org.isha.service.GcdRestService;
import org.isha.util.Util;


@Path("/inputnos")
public class InsertInput {

	Sender sender = new Sender();
	GcdDAO dbObj = new GcdDAO();
	GcdRestService gcdService = new GcdRestService();
	
	/*
	 * adds input to the queue.
	 */
	@GET
	@Path("/{input1}/{input2}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getString(@PathParam("input1") String input1, @PathParam("input2") String input2) throws RestException
	{		
		if(Util.isNumeric(input1, input2) && Util.isInt(input1, input2))
			return gcdService.push(input1, input2);	
		
		return "Only Integer input accepted. Please enter again.";
	}
	
	/*
	 * returns a list of all inputs in JSON format
	 */
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Numbers> list() throws RestException
	{
		return gcdService.list();
	}
}
