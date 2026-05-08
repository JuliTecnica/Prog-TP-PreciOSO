package com.utn.ProgIII.repository;

import com.utn.ProgIII.model.Order.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrderRepository extends JpaRepository<OrderDetails,Long>, QuerydslPredicateExecutor<OrderDetails> {

}
