package com.myapp.desk.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Schema(description = "Trade entity representing a financial transaction")
@Entity
@Table(name = "trade")
public class Trade {

    @Schema(description = "Unique identifier of the trade", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "External trade identifier", example = "TRD001", required = true)
    private String tradeId;
    
    @Schema(description = "Financial instrument being traded", example = "AAPL", required = true)
    private String instrument;
    
    @Schema(description = "Quantity of shares traded", example = "100.0", required = true)
    private double quantity;
    
    @Schema(description = "Price per share", example = "150.75", required = true)
    private double price;
    
    @Schema(description = "Date when the trade was executed", required = true)
    private Date tradeDate;
    
    @Schema(description = "Source system that originated the trade", example = "BLOOMBERG")
    private String sourceSystem;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }


}