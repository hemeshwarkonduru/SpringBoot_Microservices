package com.everythingin.order.repository;

import com.everythingin.order.entity.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {

    Optional<List<Order>> findByUserId(String userid);

    Optional<Order> findFirstByOrderByCreatedAtDesc();
}
