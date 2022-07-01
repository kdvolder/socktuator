package com.example.demo;

import java.net.InetSocketAddress;

import org.springframework.scheduling.annotation.Scheduled;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import socktuator.client.SimpleSocketClient;
import socktuator.config.SocktuatorServerProperties;

//@Component
public class SelfHealthPinger {

	SimpleSocketClient client;
	ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	
	public SelfHealthPinger(SocktuatorServerProperties props) {
		client = new SimpleSocketClient(
				new InetSocketAddress(props.getHost(), props.getPort()),
				props.getTimeout()
		);
	}
	
	@Scheduled(fixedRate = 5000)
	void selfHealthCheck() throws Exception {
		Object h = client.health();
		System.out.println("Health = "+mapper.writeValueAsString(h));

		Object ph = client.healthForPath("ping");
		System.out.println("Health.ping = "+mapper.writeValueAsString(ph));
		
		Object md = client.getEndpointMetadata();
		System.out.println("ops = "+mapper.writeValueAsString(md));

	}
	
}
