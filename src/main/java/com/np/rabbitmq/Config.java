package com.np.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
	private static final String RABBITMQ_HOST_PROP = "${spring.rabbitmq.host}";
	private static final String RABBITMQ_PORT_PROP = "${spring.rabbitmq.port}";
	private static final String RABBITMQ_USERNAME_PROP = "${spring.rabbitmq.username}";
	private static final String RABBITMQ_PASSWORD_PROP = "${spring.rabbitmq.password}";
	
	
	@Bean
	public ConnectionFactory getConnectionFactory(@Value(RABBITMQ_HOST_PROP) String host, @Value(RABBITMQ_PORT_PROP) int port,
			@Value(RABBITMQ_USERNAME_PROP) String userName, @Value(RABBITMQ_PASSWORD_PROP) String password) {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
		connectionFactory.setUsername(userName);
		connectionFactory.setPassword(password);
		return connectionFactory;
	}
	
	@Bean
	public RabbitTemplate consumerRabbitTemplate(ConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
	}
	
	@Bean
	public RabbitAdmin getRabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	public SimpleMessageListenerContainer getSimpleMessageListenerContainer(ConnectionFactory connectionFactory) {
		return new SimpleMessageListenerContainer(connectionFactory);
	}

//	@Bean
//	public RabbitMQDemo getRabbitMQDemo(ConnectionFactory connectionFactory, RabbitTemplate rabbitTemplate, RabbitAdmin rabbitAdmin) {
//		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//		
//		RabbitMQDemo rabbitMQDemo = new RabbitMQDemo(rabbitTemplate, container);
//		
//		MessageListenerAdapter adapter = new MessageListenerAdapter(rabbitMQDemo, "receiveMessage");
//		container.setMessageListener(adapter);
//		
//		Queue queue = new Queue("myQueue", true);
//		rabbitAdmin.declareQueue(queue);
//		container.addQueues(queue);
//		
//		/*Queue producerQueue   	
//  	
//= new Queue("myQueue", true);
//		rabbitAdmin.declareQueue(producerQueue);*/
//		
//		rabbitTemplate.convertAndSend("1_2_ACTIVATION_sms_INPUT", "{\"productId\":\"1\",\"partnerId\":\"2\",\"msisdn\":\"11111\",\"billingCode\":\"12345\",\"nextBillingDate\":\"2018-01-01 12:00:00\",\"activity\":\"activation\",\"activityResult\":\"Success\",\"languageId\":\"1\",\"smsType\":\"ACTIVATION_SMS\"}");
//		Thread t = new Thread(rabbitMQDemo);
//		t.start();
//		
//		return rabbitMQDemo;
//	}
}
