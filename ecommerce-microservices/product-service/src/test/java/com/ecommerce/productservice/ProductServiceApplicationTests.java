package com.ecommerce.productservice;

import com.ecommerce.productservice.dto.ProductRequest;
import com.ecommerce.productservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

  @Container
  static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.4.2"));

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  ProductRepository productRepository;

  // integration test
  // using the docker mongodb container
  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
    dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }

  @Test
  void shouldCreateProduct() throws Exception {

    ProductRequest productRequest = getProductRequest();
    String productRequestString = objectMapper.writeValueAsString(productRequest);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(productRequestString))
            .andExpect(status().isCreated());  // only accepts a string
    Assertions.assertEquals(1, productRepository.findAll().size());
  }

  private ProductRequest getProductRequest() {
    return ProductRequest.builder()
            .name("iPhone 13")
            .description("iPhone 13")
            .price(BigDecimal.valueOf(1200))
            .build();
  }

  // todo: create for GET endpoints
}
