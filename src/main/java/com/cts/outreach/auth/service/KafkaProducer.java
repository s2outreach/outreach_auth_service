package com.cts.outreach.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
	
	@Value("${kafka.add-log}")
	private String addLogTopic;
	
	@Autowired
	private KafkaTemplate<String,String> kafkaTemplate;
		
	public void sendLog(String message){
		this.kafkaTemplate.send(addLogTopic, message);
	}

}

