package com.example.order.config;

import java.time.Duration;

import com.example.order.client.InventoryServiceClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {
	
	@Value("${inventory.uri}")
	private String inventoryServiceUrl;

	@Bean
	public InventoryServiceClient inventoryServiceClient() {
		
		RestClient restClient = RestClient.builder()
				.baseUrl(this.inventoryServiceUrl)
				.requestFactory(this.getClientHttpRequestFactory())
				.build();
		RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
		HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
		
		return httpServiceProxyFactory.createClient(InventoryServiceClient.class);
	}
	
	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		
		ClientHttpRequestFactorySettings clientHttpRequestFactorySettings = ClientHttpRequestFactorySettings.DEFAULTS
				.withConnectTimeout(Duration.ofSeconds(3))
				.withReadTimeout(Duration.ofSeconds(3));
		
		return ClientHttpRequestFactories.get(clientHttpRequestFactorySettings);
	}
	
}
