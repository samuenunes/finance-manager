package com.leumas.finance.controller;

import com.leumas.finance.entity.MarketItem;
import com.leumas.finance.service.MarketItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/market")
public class MarketItemController {

    private final MarketItemService marketItemService;

    public MarketItemController(MarketItemService marketItemService) {
        this.marketItemService = marketItemService;
    }

    @GetMapping
    public ResponseEntity<List<MarketItem>> findAll() {
        return ResponseEntity.ok(marketItemService.findAll());
    }

    @PostMapping
    public ResponseEntity<MarketItem> save(@RequestBody MarketItem marketItem) {
        return ResponseEntity.ok(marketItemService.save(marketItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        marketItemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
