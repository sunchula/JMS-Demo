package jms.topic;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicProducer implements Runnable{

	ActiveMQConnectionFactory connectionFactory = null;
	
	public TopicProducer(ActiveMQConnectionFactory connectionFactory) {
		super();
		this.connectionFactory = connectionFactory;
	}



	public void run() {
		
		try {
			Connection connection = connectionFactory.createConnection();
			connection.start();
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination destination = session.createTopic("FedEx-Demo");
			
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			
			String text = "Hi Praveen";
			TextMessage message = session.createTextMessage(text);
			
			producer.send(message);

			session.close();
			connection.close();
			
		}catch(JMSException jmse) {
			System.out.println("Exception - "+jmse.getMessage());
		}
		
	}
	
}
