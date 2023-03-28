package com.ecommerce.orderservice.event;

import com.ecommerce.orderservice.dto.OrderLineItemsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {
  private List<String> listOfSkuCodes;
}
