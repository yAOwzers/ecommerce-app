package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.InventoryResponse;
import com.ecommerce.orderservice.dto.OrderLineItemsDto;
import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderLineItems;
import com.ecommerce.orderservice.respository.OrderRepository;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

  private final OrderRepository orderRepository;
  private final WebClient.Builder webClientBuilder;

  static final Counter failures = Counter.build()
          .name("http_failures").help("Total number of http requests that fail.")
          .labelNames("failures").register();
  static final Gauge inprogressRequests = Gauge.build()
          .name("inprogress_requests").help("Inprogress requests.").register();


  public String placeOrder(OrderRequest orderRequest) throws IllegalAccessException {
    inprogressRequests.inc();
    Order order = new Order();
    order.setOrderNumber(UUID.randomUUID().toString());

    List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
            .stream()
            .map(this::mapToDto)
            .toList();

    order.setOrderLineItemsList(orderLineItems);

    List<String> skuCodes = order.getOrderLineItemsList()
            .stream()
            .map(OrderLineItems::getSkuCode)
            .toList();

    // separate into a consumer
    InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
            .uri("http://inventory-service/api/inventory",
                    uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
            .retrieve()
            .bodyToMono(InventoryResponse[].class)// todo: check the mono class
            .block(); // for synchronous

    boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);

    // if result is present,
    if (allProductsInStock) {
      orderRepository.save(order);
      // can send a notification
      inprogressRequests.dec();
      return "Order Placed Successfully."; // need to send a redirect to the URL
    } else {
      failures.labels("order_service").inc();
      inprogressRequests.dec();
      throw new IllegalAccessException("Product is not in stock, try again another time");
    }

  }


  private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
    OrderLineItems orderLineItems = new OrderLineItems();

    // set all the order line items variables
    orderLineItems.setPrice(orderLineItemsDto.getPrice());
    orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
    orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
    return orderLineItems;
  }
}
