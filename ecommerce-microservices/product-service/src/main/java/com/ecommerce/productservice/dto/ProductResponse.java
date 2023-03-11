package com.ecommerce.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

  // why cant we use product class?

  // good practice to separate model entities with data transfer objects class
  // should not expose to the fields of model entities
  private String id;
  private String name;
  private String description;
  private BigDecimal price;
}
