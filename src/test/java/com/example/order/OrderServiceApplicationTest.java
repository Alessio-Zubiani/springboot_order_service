package com.example.order;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.stubs.InventoryClientStub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.containers.MySQLContainer;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTest {
	
	@ServiceConnection
	static MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer("mysql:latest")
			.withDatabaseName("order_service")
			.withReuse(true);
	
	@LocalServerPort
	private Integer port;
	
	
	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = this.port;
	}
	
	static {
		mySQLContainer.start();
	}

	@Test
	void placeOrderTest() {
		String orderRequest = """
				{
				    "skuCode": "iphone_15",
				    "price": 1000,
				    "quantity": 50
				}
				""";
		
		InventoryClientStub.stubInventoryCall("iphone_15", 50);
		
		String orderResponse = RestAssured.given()
			.contentType("application/json")
			.body(orderRequest)
			.when()
			.post("/api/orders")
			.then()
			.log().all()
			.statusCode(201)
			.extract()
			.body().asString();
		
		assertThat(orderResponse).isEqualTo("Order placed successfully");
	}
	
	@Test
	void placeOrderErrorTest() {
		String orderRequest = """
				{
				    "skuCode": "iphone_15",
				    "price": 1000,
				    "quantity": 500
				}
				""";
		
		InventoryClientStub.stubInventoryCall("iphone_15", 500);
		
		RestAssured.given()
			.contentType("application/json")
			.body(orderRequest)
			.when()
			.post("/api/orders")
			.then()
			.log().all()
			.statusCode(500);
	}

}
