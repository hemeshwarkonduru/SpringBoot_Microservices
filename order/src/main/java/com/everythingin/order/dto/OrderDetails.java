package com.everythingin.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {

    private String orderId;

    private String item;

    private String userId;

    private String itemDescription;



}
