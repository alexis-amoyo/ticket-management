package com.myapp.desk.controllers;

import com.myapp.desk.domain.Trade;
import com.myapp.desk.repository.TradeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*; 
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Trades", description = "Trade Management API")
@RestController
@RequestMapping("/api/trades")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class TradeController {
    @Autowired
    private TradeRepository tradeRepository;

    @Operation(
            summary = "Retrieve all trades",
            description = "Get a list of all trades in the system.",
            tags = { "trades", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Trade.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping
    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trade> getTradeById(@PathVariable Long id) {
        Optional<Trade> trade = tradeRepository.findById(id);
        return trade.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Trade> createTrade(@RequestBody Trade trade) {
        Trade savedTrade = tradeRepository.save(trade);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTrade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trade> updateTrade(@PathVariable Long id, @RequestBody Trade trade) {
        if (!tradeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        trade.setId(id);
        Trade updatedTrade = tradeRepository.save(trade);
        return ResponseEntity.ok(updatedTrade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrade(@PathVariable Long id) {
        if (!tradeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tradeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}