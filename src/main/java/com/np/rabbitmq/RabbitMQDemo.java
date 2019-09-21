//package com.np.rabbitmq;
//
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//
//public class RabbitMQDemo implements Runnable {
//	private RabbitTemplate rabbitTemplate;
//	private SimpleMessageListenerContainer container;
//
//	public RabbitMQDemo(RabbitTemplate rabbitTemplate, SimpleMessageListenerContainer container) {
//		this.rabbitTemplate = rabbitTemplate;
//		this.container = container;
//	}
//
//	public void receiveMessage(String message) {
//		rabbitTemplate.convertAndSend("myQueueABC", message + " cosumed message");
//	}
//
//	@Override
//	public void run() {
//		container.start();
//	}
//
//}
