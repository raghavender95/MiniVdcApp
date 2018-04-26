package com.vdc.queue;

import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
@Service
public class JMSProducer {

	private static final Logger log = Logger.getLogger(JMSProducer.class.getName());

	@Autowired
	private JmsTemplate jmsTemplate;

	public void sendSmsData(final String data) {
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(data);
			}
		});
	}
}
