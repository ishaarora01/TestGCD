package org.isha.jms;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.isha.exceptions.JMSQueueException;
import org.isha.util.Constants;

/*
 * Retrieves message from queue synchronously.
 */
public class Receiver {

	InitialContext ctx;
	Queue queue;
	QueueConnectionFactory connFactory;
	QueueConnection queueConn;
	QueueSession queueSession;
	QueueReceiver queueReceiver;
	TextMessage message;

	String msgStr = "Queue is empty.";
	String errorStr = "Error while reading message from queue. ";

	public String readMsg() throws JMSQueueException
	{
		try{
			// get the initial context
			ctx = new InitialContext();

			// lookup the queue object
			queue = (Queue) ctx.lookup(Constants.QUEUE_NAME);

			// lookup the queue connection factory
			connFactory = (QueueConnectionFactory) ctx.lookup(Constants.QUEUE_CONN_FACTORY);

			// create a queue connection
			queueConn = connFactory.createQueueConnection();

			// create a queue session
			queueSession = queueConn.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);

			// create a queue receiver
			queueReceiver = queueSession.createReceiver(queue);

			// start the connection
			queueConn.start();

			// receive a message
			message = (TextMessage) queueReceiver.receiveNoWait();

			// print the message
			if(message != null)
			{
				msgStr = message.getText();
				System.out.println("Test received: " + message.getText());
			}
		}
		catch(JMSException e){
			throw new JMSQueueException(errorStr + e.getMessage());
		}
		catch(NamingException e){
			throw new JMSQueueException(errorStr + e.getMessage());
		}
		catch(Exception e){
			throw new JMSQueueException(errorStr + e.getMessage());
		}
		finally{
			// close the queue connection
			try {
				queueSession.close();
				queueConn.close();
			} catch (JMSException e) {
				throw new JMSQueueException(errorStr + e.getMessage());
			}
		}
		return msgStr;
	}

}
