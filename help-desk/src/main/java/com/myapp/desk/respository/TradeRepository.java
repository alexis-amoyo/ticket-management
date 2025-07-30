package com.myapp.desk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myapp.desk.domain.Trade;

public interface TradeRepository extends JpaRepository<Trade, Long> {

}