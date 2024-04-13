package com.everythingin.order.service;

import com.everythingin.order.configUtil.UserDetails;
import com.everythingin.order.dto.SuccessOutput;
import com.everythingin.order.dto.OrderDetails;
import com.everythingin.order.dto.UserOrderDetails;
import com.everythingin.order.entity.Order;
import com.everythingin.order.exception.MyCustomException;
import com.everythingin.order.repository.OrderRepository;
import com.everythingin.order.request.OrderRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.everythingin.order.constants.OrderConstants.*;

@Service
public class OrderService {


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    WebClient.Builder webClientOrderBuilder;

    private static final Logger logger = LogManager.getLogger(OrderService.class);

    public UserOrderDetails userOrderDetailsService(String userId) throws MyCustomException {
        try{
            UserOrderDetails response = new UserOrderDetails();
            if (userId != null && !userId.isEmpty()) {
                Optional<List<Order>> orderOptional = orderRepository.findByUserId(userId);
                if (orderOptional != null && orderOptional.isPresent()) {
                    List<Order> order = orderOptional.get();
                    logger.info(order);

                    List<OrderDetails> st = order.stream()
                            .map(iter -> OrderDetails.builder()
                                    .orderId(iter.getOrderId())
                                    .userId(iter.getUserId())
                                    .item(iter.getItem())
                                    .itemDescription(iter.getItemDescription())
                                    .build())
                            .collect(Collectors.toList());

                    response.setMessage(SUCCESS);
                    response.setOrderDetailsList(st);


                } else {

                    response.setMessage(SUCCESS);
                    response.setOrderDetailsList(null);
                }

            } else {
                response.setMessage("User ID is null or Empty");
                response.setOrderDetailsList(null);
            }

            return response;
        } catch (Exception e){
            logger.error(e.getMessage());
            throw e;
        }
    }


    public SuccessOutput createOrderService(OrderRequest orderRequest) throws MyCustomException,Exception{
        try {
            SuccessOutput response = SuccessOutput.builder().build();

            if (orderRequest.getUserId() != null && !orderRequest.getUserId().isEmpty()) {
                if (orderRequest.getItem() != null && !orderRequest.getItem().isEmpty()) {

                    //When creating order need to check if user is present in userDb or not
                    UserDetails userDetails = webClientOrderBuilder.build().get()
                            .uri("http://user-service/user/userDetailsByMail"
                            ,uriBuilder -> uriBuilder.queryParam("mail" ,orderRequest.getUserId()).build())
                            .retrieve()
                            .bodyToMono(UserDetails.class)
                            .block();

                    if(userDetails != null && userDetails.getGmail() != null
                    && ! userDetails.getGmail().isEmpty()){
                        Date createdAt = new Date();
                        Order order = Order.builder()
                                .orderId(orderIdGenerator())
                                .userId(orderRequest.getUserId())
                                .itemDescription(orderRequest.getItemDescription())
                                .item(orderRequest.getItem())
                                .createdAt(createdAt)
                                .build();

                        orderRepository.save(order);

                        response.setMessage(SUCCESS);
                    }else {
                        response.setMessage(NO_USER_FOUND);
                    }
                } else {
                    response.setMessage("Item should not be Empty");
                }

            } else {
                response.setMessage("User Id cannot be empty");
            }
            return response;
        }catch (Exception e){
            logger.error(e);
            throw e;
        }



    }

    public String orderIdGenerator(){

        Optional<Order> latestIdOpt = orderRepository.findFirstByOrderByCreatedAtDesc();
        logger.info("HEEEEREEEE ->" + latestIdOpt.get());
        if(!latestIdOpt.isPresent()){
            String firstRecord = "ORD" + "000001";
            return firstRecord;
        }
        else {
            String numericPartString = latestIdOpt.get().getOrderId().substring(3);
            logger.info(numericPartString);
            int numericPart = Integer.parseInt(numericPartString);
            numericPart++;
            logger.info(numericPart);
            return String.format("ORD%06d", numericPart);
        }

    }
}
