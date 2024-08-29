package com.sebas.shoppingcart.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sebas.shoppingcart.model.OrderHistory;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Integer>{

}
