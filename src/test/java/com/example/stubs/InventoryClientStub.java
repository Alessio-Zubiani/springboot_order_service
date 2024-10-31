package com.example.stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;


public class InventoryClientStub {
	
	public static void stubInventoryCall(String skuCode, Integer quantity) {
		if(quantity <= 100) {
			stubFor(get(urlEqualTo("api/inventory?skuCode=" + skuCode + "&quantity=" + quantity))
					.willReturn(aResponse()
							.withStatus(200)
	                        .withHeader("Content-Type", "application/json")
	                        .withBody("true")));
		} else {
			stubFor(get(urlEqualTo("api/inventory?skuCode=" + skuCode + "&quantity=" + quantity))
					.willReturn(aResponse()
							.withStatus(200)
	                        .withHeader("Content-Type", "application/json")
	                        .withBody("false")));
		}
	}

}
