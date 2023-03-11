package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductRequest;
import com.ecommerce.productservice.dto.ProductResponse;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

// business logic lies here
@Service
@RequiredArgsConstructor // creates the constructor that we need at runtime
@Slf4j
public class ProductService {

  private final ProductRepository productRepository;

  public void createProduct(ProductRequest productRequest) {
    // create the product
    Product product = Product.builder()
            .name(productRequest.getName())
            .description(productRequest.getDescription())
            .price(productRequest.getPrice())
            .build();

    // now need to save it into the repository
    productRepository.save(product);
    log.info("Product {} is saved", product.getId());
  }

  public List<ProductResponse> getAllProducts() {
    List<Product> products = productRepository.findAll(); // find all from the database

    // map each product to a product response object
    // return it as a list
    return products.stream().map(this::mapToProductResponse).toList();
  }

  private ProductResponse mapToProductResponse(Product product) {

    return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .build();

  }
}
