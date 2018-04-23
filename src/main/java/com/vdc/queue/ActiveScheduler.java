package com.vdc.queue;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ActiveScheduler {
	

	private static final Logger log = LoggerFactory.getLogger(ActiveScheduler.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	@Scheduled(fixedRate = 10000)
	public void consumeMessage() {
		ConnectionFactory connectionFactory = null;
		Context context = null;
		Connection connection = null;
		Destination destination = null;
		Session session = null;
		MessageConsumer messageConsumer = null;
		MessageProducer messageProducer = null;
		String data = null;
		try {
			context = SingletonFactory.getContext();

			connectionFactory = SingletonFactory.getConnectionFactory();

			connection = connectionFactory.createConnection("jmsuser", "jmsuser@123");

			connection.start();

			destination = (Destination) context.lookup("jms/queue/ProcessingQueue");
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			messageConsumer = session.createConsumer(destination);

			TextMessage textMessage = null;

			textMessage = (TextMessage) messageConsumer.receive();

			System.out.println("Messsage Recieved by the ACTIVE-SCHEDULER" + textMessage.getText());

			data = textMessage.getText();
			if (data != null) {

				destination = (Destination) context.lookup("jms/queue/DeliveryQueue");

				session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

				messageProducer = session.createProducer(destination);

				textMessage = session.createTextMessage();

				textMessage.setText(data);

				messageProducer.send(textMessage);
			}
			connection.close();

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return data;
	}
}
