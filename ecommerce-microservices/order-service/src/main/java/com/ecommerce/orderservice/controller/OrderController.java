package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.prometheus.client.Counter;
import io.prometheus.client.Summary;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  static final Counter requests = Counter.build()
          .name("http_request_total").help("Total requests.")
          .labelNames("method").register();
  private static final Summary requestLatency = Summary.build()
          .name("requests_latency_seconds")
          .help("request latency in seconds")
          .register();
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
  @TimeLimiter(name = "inventory")
  @Retry(name = "inventory")
  public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
    Summary.Timer requestTimer = requestLatency.startTimer();
    requests.labels("post").inc();
    return CompletableFuture.supplyAsync(() -> {
      try {
        return orderService.placeOrder(orderRequest);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      } finally {
        requestTimer.observeDuration();
      }
    });
  }

  // time out error
  public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
    return CompletableFuture.supplyAsync(() -> "Oops! Something went wrong, please order after some time!");
  }

}
