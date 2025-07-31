package com.myapp.desk.controllers;

import com.myapp.desk.domain.Trade;
import com.myapp.desk.service.TradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Trades", description = "Trade Management API with Redis Caching")
@RestController
@RequestMapping("/api/trades")
public class TradeController {
    @Autowired
    private TradeService tradeService;

    @Operation(
            summary = "Retrieve all trades",
            description = "Get a list of all trades in the system.",
            tags = { "trades", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Trade.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping
    public List<Trade> getAllTrades() {
        return tradeService.getAllTrades();
    }

    @Operation(
            summary = "Retrieve a trade by ID",
            description = "Get a specific trade by its ID. This endpoint uses Redis caching for improved performance.",
            tags = { "trades", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Trade.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/{id}")
    public ResponseEntity<Trade> getTradeById(
            @Parameter(description = "ID of the trade to be retrieved", required = true)
            @PathVariable Long id) {
        Optional<Trade> trade = tradeService.getTradeById(id);
        return trade.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create a new trade",
            description = "Create a new trade. The created trade will be cached in Redis.",
            tags = { "trades", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = Trade.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping
    public ResponseEntity<Trade> createTrade(
            @Parameter(description = "Trade object to be created", required = true)
            @RequestBody Trade trade) {
        Trade savedTrade = tradeService.createTrade(trade);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTrade);
    }

    @Operation(
            summary = "Update an existing trade",
            description = "Update an existing trade. The updated trade will be cached in Redis.",
            tags = { "trades", "put" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Trade.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PutMapping("/{id}")
    public ResponseEntity<Trade> updateTrade(
            @Parameter(description = "ID of the trade to be updated", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated trade object", required = true)
            @RequestBody Trade trade) {
        if (!tradeService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Trade updatedTrade = tradeService.updateTrade(id, trade);
        return ResponseEntity.ok(updatedTrade);
    }

    @Operation(
            summary = "Delete a trade",
            description = "Delete a trade by ID. The trade will be removed from Redis cache.",
            tags = { "trades", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrade(
            @Parameter(description = "ID of the trade to be deleted", required = true)
            @PathVariable Long id) {
        if (!tradeService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tradeService.deleteTrade(id);
        return ResponseEntity.noContent().build();
    }
}