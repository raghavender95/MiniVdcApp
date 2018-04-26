package com.vdc.queue;

import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
@Service
public class JMSConsumer {

	private static final Logger log = Logger.getLogger(JMSConsumer.class.getName());

	@Autowired
	private JmsTemplate jmsTemplate;

	public String getsms() {
		
		String data = null;
		try {
			jmsTemplate.setDefaultDestinationName("Delivery");

			TextMessage textMessage = null;

			textMessage = (TextMessage) jmsTemplate.receive();

			System.out.println("Messsage Recieved by the ACTIVE-SCHEDULER" + textMessage.getText());



			data = textMessage.getText();
		} catch (JMSException e) {
			e.printStackTrace();
		}

		return data;
	}

}
