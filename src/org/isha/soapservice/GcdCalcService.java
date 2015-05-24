package org.isha.soapservice;

import java.util.List;







import javax.jms.JMSException;
import javax.jws.WebMethod;
import javax.jws.WebService;



import javax.naming.NamingException;

import org.isha.exceptions.JMSQueueException;
import org.isha.exceptions.SoapException;
import org.isha.service.GcdRestService;
import org.isha.service.GcdSoapService;

@WebService
public class GcdCalcService {
	
	GcdSoapService gcdService = new GcdSoapService();
	
	/*
	 * returns the greatest common divisor* of the two integers at the head of the queue. 
	 */
	@WebMethod
	public String gcd() throws SoapException 
	{
		return gcdService.gcd();
	}
	
	/*
	 * returns a list of all the computed greatest common divisors from a database. 
	 */
	@WebMethod
	public List<Integer> gcdList() throws SoapException
	{
		return gcdService.gcdList();
	}
	
	/* 
	 * returns the sum of all computed greatest common divisors from a database.
	 */
	@WebMethod
	public int gcdSum() throws SoapException
	{
		return gcdService.gcdSum();
	}

}
