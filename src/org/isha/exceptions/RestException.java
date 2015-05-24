package org.isha.exceptions;

public class RestException extends Exception{
	
	public RestException(){
        super();
    }

    public RestException(String message){
        super(message);
    }

}
