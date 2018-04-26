package com.vdc.queue;

import java.text.SimpleDateFormat;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ActiveScheduler {


	private static final Logger log = LoggerFactory.getLogger(ActiveScheduler.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	private JmsTemplate jmsTemplate;

	@Scheduled(fixedRate = 10000)
	public void consumeMessage() {
		try {
			jmsTemplate.setDefaultDestinationName("ProcessingRequest");

			TextMessage textMessage = null;

			textMessage = (TextMessage) jmsTemplate.receive();

			System.out.println("Messsage Recieved by the ACTIVE-SCHEDULER" + textMessage.getText());

			String data = textMessage.getText();
			if (data != null) {

				jmsTemplate.setDefaultDestinationName("Delivery");
				jmsTemplate.convertAndSend(textMessage);
			}

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
