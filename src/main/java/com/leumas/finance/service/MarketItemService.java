package com.leumas.finance.service;

import com.leumas.finance.entity.MarketItem;
import com.leumas.finance.repository.MarketItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketItemService {
    private final MarketItemRepository marketItemRepository;

    public MarketItemService(MarketItemRepository marketItemRepository) {
        this.marketItemRepository = marketItemRepository;
    }

    public List<MarketItem> findAll() {
        return marketItemRepository.findAll();
    }

    public MarketItem save(MarketItem marketItem) {
        return marketItemRepository.save(marketItem);
    }

    public MarketItem findById(Long id) {
        return marketItemRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        marketItemRepository.deleteById(id);
    }
}
