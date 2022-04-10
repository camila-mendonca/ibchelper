package com.ibc.ibchelper.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class ServerConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory>{

	@Override
	public void customize(ConfigurableWebServerFactory factory) {
		factory.setPort(Integer.parseInt(System.getenv("SPRING_APP_PORT")));
	}

}
