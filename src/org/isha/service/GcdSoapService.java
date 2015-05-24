package org.isha.service;

import java.util.List;

import org.isha.db.GcdDAO;
import org.isha.exceptions.DBException;
import org.isha.exceptions.JMSQueueException;
import org.isha.exceptions.SoapException;
import org.isha.jms.Receiver;
import org.isha.util.Util;

public class GcdSoapService {
	
	Receiver receiver = new Receiver();
	GcdDAO dbObj = new GcdDAO();
	
	String errorStr = "Error occurred while accessing one the methods of SOAP service. ";
	
	/*
	 * returns the greatest common divisor* of the two integers at the head of the queue. 
	 */
	public String gcd() throws SoapException 
	{
		String msgStr;
		int gcd;
		try {
			msgStr = receiver.readMsg();
			
			if(msgStr.contains(","))
			{
				String[] strArray = msgStr.split(",");
				gcd = Util.calculateGcd(Integer.parseInt(strArray[0]), Integer.parseInt(strArray[1]));
				msgStr = "The GCD of numbers " + strArray[0] + " and " + strArray[1] + " is " + gcd;
			}
		} catch (JMSQueueException e) {
			throw new SoapException(errorStr + e.getMessage());
		}catch(Exception e){
			throw new SoapException(errorStr + e.getMessage());
		}
		
		return msgStr;
	}
	
	/*
	 * returns a list of all the computed greatest common divisors from a database. 
	 */
	public List<Integer> gcdList() throws SoapException
	{
		try {
			return dbObj.selectGcdFromTbl();
		} catch (DBException e) {
			throw new SoapException(errorStr + e.getMessage());
		}
	}
	
	/* 
	 * returns the sum of all computed greatest common divisors from a database.
	 */
	public int gcdSum() throws SoapException
	{
		try {
			return dbObj.getGcdSum();
		} catch (DBException e) {
			throw new SoapException(errorStr + e.getMessage());
		}
	}

}
