package com.example.order;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

}
