package com.myapp.desk.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Schema(description = "Financial Instrument entity representing tradeable financial assets")
@Entity
@Table(name = "instrument")
public class Instrument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the instrument", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Schema(description = "Trading symbol of the instrument", example = "AAPL", required = true)
    private String symbol;
    
    @Schema(description = "Full name of the instrument", example = "Apple Inc.", required = true)
    private String name;
    
    @Schema(description = "International Securities Identification Number", example = "US0378331005")
    private String isin;

    // Getters and Setters

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }
}
