package com.leumas.finance.repository;

import com.leumas.finance.entity.MarketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketItemRepository extends JpaRepository<MarketItem, Long> {
}
