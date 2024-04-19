package com.everythingin.order.controller;

import com.everythingin.order.dto.SuccessOutput;
import com.everythingin.order.dto.OrderDetails;
import com.everythingin.order.dto.UserOrderDetails;
import com.everythingin.order.exception.MyCustomException;
import com.everythingin.order.request.OrderRequest;
import com.everythingin.order.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    private static final Logger logger = LogManager.getLogger(OrderService.class);

    @GetMapping("/userOrder")
    public ResponseEntity<UserOrderDetails> userOrderDetailsController(@RequestParam String userId) throws MyCustomException {
        UserOrderDetails response = orderService.userOrderDetailsService(userId);
        if(response != null){
            return ResponseEntity.ok().body(response);
        }
        else{
            logger.error(response.getMessage());
            throw new MyCustomException("Something Went Wrong");
        }
    }

    @PostMapping("/createOrder")
    @CircuitBreaker(name = "user", fallbackMethod = "fallbackMethodOrder")
    public CompletableFuture<ResponseEntity<SuccessOutput>> createOrder(@RequestBody OrderRequest orderRequest) throws MyCustomException {
        try {
            SuccessOutput response = orderService.createOrderService(orderRequest);
            if (response != null) {
                return CompletableFuture.supplyAsync( () -> ResponseEntity.ok().body(response));
            } else {
                return CompletableFuture.supplyAsync( () -> ResponseEntity.ok().body(null));
            }
        }catch (Exception e){
            throw new MyCustomException("Something Went Wrong");
        }
    }

    public CompletableFuture<SuccessOutput> fallbackMethodOrder(OrderRequest orderRequest, RuntimeException runtimeException){
        SuccessOutput output =  SuccessOutput.builder().build();
        logger.info("Fall Back Method");
        output.setMessage("Oops! Something went wrong, please order after some time!");
        return CompletableFuture.supplyAsync(() -> output);
    }


}
