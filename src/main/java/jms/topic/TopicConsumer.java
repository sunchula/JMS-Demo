package jms.topic;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.Message;

public class TopicConsumer implements Runnable{

	ActiveMQConnectionFactory connectionFactory = null;
	
	public TopicConsumer(ActiveMQConnectionFactory connectionFactory) {
		super();
		this.connectionFactory = connectionFactory;
	}

	public void run() {
		
		try {
			Connection connection = connectionFactory.createConnection();
			connection.start();
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination topicDestination = session.createTopic("FedEx-Demo");
			
			MessageConsumer messageConsumer = session.createConsumer(topicDestination);
			
			javax.jms.Message message = messageConsumer.receive();
			TextMessage textMessage = (TextMessage) message;
			
			System.out.println(textMessage.getText());

			session.close();
			connection.close();
			
		}catch(JMSException jmse) {
			System.out.println("Exception - "+jmse.getMessage());
		}
		
	}
	
}
