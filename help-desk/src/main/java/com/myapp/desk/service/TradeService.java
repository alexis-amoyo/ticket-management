package com.myapp.desk.service;

import com.myapp.desk.domain.Trade;
import com.myapp.desk.exception.TradeNotFoundException;
import com.myapp.desk.repository.TradeRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {
    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    // Get all trades - cacheable
    @Cacheable(value = "trades", key = "'all'")
    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    // Get trade by ID - cacheable
    @Cacheable(value = "trades", key = "#id")
    public Optional<Trade> getTradeById(Long id) {
        return tradeRepository.findById(id);
    }

    // Create new trade - cache the result
    @CachePut(value = "trades", key = "#result.id")
    public Trade createTrade(Trade trade) {
        
        Trade savedTrade = tradeRepository.save(trade);
        // Evict the 'all' cache since we added a new trade
        evictAllTradesCache();
        return savedTrade;
    }

    // Update trade - update cache
    @CachePut(value = "trades", key = "#id")
    public Trade updateTrade(Long id, Trade trade) {
        // Validate that the trade exists before updating
        if (!tradeRepository.existsById(id)) {
            throw new TradeNotFoundException(id);
        }

        Trade updatedTrade = tradeRepository.save(trade);
        // Evict the 'all' cache since we updated a trade
        evictAllTradesCache();
        return updatedTrade;
    }

    // Delete trade - evict from cache
    @CacheEvict(value = "trades", key = "#id")
    public void deleteTrade(Long id) {
        // Validate that the trade exists before deleting
        if (!tradeRepository.existsById(id)) {
            throw new TradeNotFoundException(id);
        }
        
        tradeRepository.deleteById(id);
        // Evict the 'all' cache since we removed a trade
        evictAllTradesCache();
    }

    // Check if trade exists
    public boolean existsById(Long id) {
        return tradeRepository.existsById(id);
    }

    // Helper method to evict all trades cache
    @CacheEvict(value = "trades", key = "'all'")
    public void evictAllTradesCache() {
        // This method will evict the cache for all trades
    }

    // Business logic methods for Trade
}