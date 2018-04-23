package com.vdc.queue;

import java.util.logging.Logger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.NamingException;

import org.springframework.stereotype.Service;
@Service
public class JMSProducer {
	
	private static final Logger log = Logger.getLogger(JMSProducer.class.getName());

	public void sendSmsData(String data) {
	ConnectionFactory connectionFactory = null;
		Context context = null;
		Connection connection = null;
		Destination destination = null;
		Session session = null;
		MessageProducer messageProducer = null;
		try {
		context = SingletonFactory.getContext();
		
		connectionFactory = SingletonFactory.getConnectionFactory();
	
		connection = connectionFactory.createConnection("jmsuser", "jmsuser@123");
		

		connection.start();

		destination = (Destination) context.lookup("jms/queue/ProcessingQueue");
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		messageProducer = session.createProducer(destination);

		TextMessage textMessage = session.createTextMessage();
		textMessage.setText(data);

		messageProducer.send(textMessage);
		connection.close();
	
		}catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		 catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}