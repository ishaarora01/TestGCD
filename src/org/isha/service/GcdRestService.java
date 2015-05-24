package org.isha.service;

import java.util.List;

import org.isha.beans.Numbers;
import org.isha.db.GcdDAO;
import org.isha.exceptions.DBException;
import org.isha.exceptions.JMSQueueException;
import org.isha.exceptions.RestException;
import org.isha.jms.Sender;
import org.isha.util.Util;

public class GcdRestService {
	
	Sender sender = new Sender();
	GcdDAO dbObj = new GcdDAO();
		
	String errorStr = "Error occurred while accessing one the resources of REST service. ";
	
	/*
	 * This method will put input numbers on Queue,
	 * then calculate GCD
	 * and then insert them into database.
	 */
	public String push(String input1, String input2) throws RestException
	{
		String status = "";
		int gcd = -1;
		try
		{
			status = sender.addNoOnQueue(input1, input2);
			
			if(status.contains(","))
				gcd = Util.calculateGcd(Integer.parseInt(input1), Integer.parseInt(input2));
			
			if(gcd >= 0)
				dbObj.insertIntoTbl(Integer.parseInt(input1), Integer.parseInt(input2), gcd);
			
			status = "Numbers " + input1 + " and " + input2 + " added successfully.";
		}
		catch(JMSQueueException e){
			throw new RestException(errorStr + e.getMessage());
		}catch(NumberFormatException e){
			throw new RestException(errorStr + e.getMessage());
		}catch(DBException e){
			throw new RestException(errorStr + e.getMessage());
		}catch(Exception e){
			throw new RestException(errorStr + e.getMessage());
		}
		
		return status;
	}

	/*
	 * returns a list of all inputs
	 */
	public List<Numbers> list() throws RestException
	{				
		try {
			return dbObj.selectFromTbl();
		} catch (DBException e) {
			throw new RestException(errorStr + e.getMessage());
		}catch(Exception e){
			throw new RestException(errorStr + e.getMessage());
		}
	}	
}
