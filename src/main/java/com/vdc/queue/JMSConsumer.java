package com.vdc.queue;

import java.util.logging.Logger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class JMSConsumer {

	
	
	private static final Logger log = Logger.getLogger(JMSConsumer.class.getName());

	public String getsms() {
		ConnectionFactory connectionFactory = null;
		Context context = null;
		Connection connection = null;
		Destination destination = null;
		Session session = null;
		MessageConsumer messageConsumer = null;
		String data=null;
		try {
		context = SingletonFactory.getContext();
		
		connectionFactory = SingletonFactory.getConnectionFactory();
	
		connection = connectionFactory.createConnection("jmsuser", "jmsuser@123");
		
		connection=connectionFactory.createConnection();
		connection.start();

		destination = (Destination) context.lookup("jms/queue/TestQ");
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		messageConsumer = session.createConsumer(destination);

		TextMessage textMessage = null;
		
		textMessage= (TextMessage)messageConsumer.receive();
		System.out.println("Messsage Recieved "+ textMessage.getText()); 
		data=textMessage.getText();
		connection.stop();
		//connection.close();
		}catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		 catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return data;
	}

}
