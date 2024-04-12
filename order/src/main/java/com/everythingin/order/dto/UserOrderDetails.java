package com.everythingin.order.dto;

import java.util.List;

public class UserOrderDetails {

    private String message;

    private List<OrderDetails> orderDetailsList;

    public UserOrderDetails(){

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<OrderDetails> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }
}
