package com.cts.outreach.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cts.outreach.auth.entity.UserEntity;
import com.cts.outreach.auth.model.LogModel;
import com.cts.outreach.auth.service.KafkaProducer;
import com.cts.outreach.auth.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.AmazonInfo;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableDiscoveryClient
public class OutreachAuthServiceApplication {
	
	private Logger LOGGER = LoggerFactory.getLogger(OutreachAuthServiceApplication.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(OutreachAuthServiceApplication.class, args);
	}

	@Primary
	@Bean
	@Autowired
	@Profile("aws")
	public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
	    EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
	    AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("auth");
	    config.setHostname(info.get(AmazonInfo.MetaDataKey.publicHostname));
	    config.setIpAddress(info.get(AmazonInfo.MetaDataKey.publicIpv4));
	    config.setNonSecurePort(1111);
	    config.setDataCenterInfo(info);
	    LOGGER.info(info.get(AmazonInfo.MetaDataKey.publicIpv4));
	    LOGGER.info(info.get(AmazonInfo.MetaDataKey.publicHostname));
	    return config;
	   }
	
	@Bean
	public CommandLineRunner loadAdmin() {
		return (args) -> {
			UserEntity userEntity = new UserEntity(
					"admin",
					bCryptPasswordEncoder.encode("admin"),
					"admin@testmail.com",
					"ADMIN");
			Long userid = userService.addNewUser(userEntity);
			int i = 1;
			while (i <= 25) {
				userEntity = new UserEntity(
						"volunteer" + Integer.toString(i),
						bCryptPasswordEncoder.encode("user"),
						"volunteer" + Integer.toString(i) + "@testmail.com",
						"USER");
				userid = userService.addNewUser(userEntity);
				i = i + 1;
			}
		};
	}
}
