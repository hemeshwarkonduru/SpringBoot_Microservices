package com.everythingin.order.controller;

import com.everythingin.order.dto.SuccessOutput;
import com.everythingin.order.dto.OrderDetails;
import com.everythingin.order.dto.UserOrderDetails;
import com.everythingin.order.exception.MyCustomException;
import com.everythingin.order.request.OrderRequest;
import com.everythingin.order.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<SuccessOutput> createOrder(@RequestBody OrderRequest orderRequest) throws MyCustomException {
        try {
            SuccessOutput response = orderService.createOrderService(orderRequest);
            if (response != null) {
                return ResponseEntity.ok().body(response);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        }catch (Exception e){
            throw new MyCustomException("Something Went Wrong");
        }

    }



}
