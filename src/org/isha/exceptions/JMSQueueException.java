package org.isha.exceptions;

public class JMSQueueException extends Exception{

	public JMSQueueException(){
        super();
    }

    public JMSQueueException(String message){
        super(message);
    }

}
