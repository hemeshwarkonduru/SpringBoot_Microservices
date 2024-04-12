package com.everythingin.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document(value = "order")
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    private String orderId;

    private String item;

    private String userId;

    private String itemDescription;

    @CreatedDate
    private Date createdAt;


}
