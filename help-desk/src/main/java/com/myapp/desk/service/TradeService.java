package com.myapp.desk.service;

import com.myapp.desk.domain.Trade;
import com.myapp.desk.repository.TradeRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

@Service
public class TradeService {
    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    // Business logic methods for Trade
}