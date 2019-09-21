package com.np.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQProducerController {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private RabbitAdmin rabbitAdmin;

	@RequestMapping(value = "/api/home", method = RequestMethod.GET)
	public ResponseEntity<String> welcome() {
		return ResponseEntity.ok("Hello");
	}
	
	@RequestMapping(value = "/api/testKill", method = RequestMethod.GET)
	public ResponseEntity<String> testKill() {
		System.out.println("Sleeping..");
		
		try {
			Thread.sleep(300000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Woke up..");
		
		return ResponseEntity.ok("Hello from testKill()");
	}
	
	@RequestMapping(value = "/api/addRabbitMQMessage", method = RequestMethod.POST)
	public ResponseEntity<String> processConfigurationServiceEvent(@RequestBody RequestVO request) {
		try {
			String queueName = request.getQueueName();
			String message = request.getMessage();

			rabbitTemplate.convertAndSend(queueName,message);
		} catch (RuntimeException e) {
			System.out.println("Exception");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/createQueue", method = RequestMethod.POST)
	public ResponseEntity<String> createQueues(@RequestBody String queues) {
		
		String[] queueNames = queues.split(",");
		
		for (String queueName : queueNames) {
			System.out.println("Declaring queue " + queueName + " ...");
			
			Queue queue = new Queue(queueName);
			rabbitAdmin.declareQueue(queue);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/dummy", method = RequestMethod.GET)
	public ResponseEntity<String> dummy() {

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
