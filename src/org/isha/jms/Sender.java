package org.isha.jms;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.isha.exceptions.JMSQueueException;
import org.isha.util.Constants;

/*
 * Sends message to queue.
 */
public class Sender {

	InitialContext ctx;
	Queue queue;
	QueueConnectionFactory connFactory;
	QueueConnection queueConn;
	QueueSession queueSession;
	QueueSender queueSender;
	TextMessage message;

	String msgStr = "Queue is full.";
	String errorStr = "Error while sending message to queue. ";

	public String addNoOnQueue(String input1, String input2) throws JMSQueueException
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

			// create a queue sender
			queueSender = queueSession.createSender(queue);
			queueSender.setDeliveryMode(DeliveryMode.PERSISTENT);

			// create a simple message to say "Hello"
			message = queueSession.createTextMessage(input1+","+input2);

			// send the message
			queueSender.send(message);

			// print what we did
			msgStr = message.getText();
			System.out.println("sent: " + message.getText());

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
				queueConn.close();
			} catch (JMSException e) {
				throw new JMSQueueException(errorStr + e.getMessage());
			}
		}
		return msgStr;
	}
}
