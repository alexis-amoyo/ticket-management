package com.myapp.desk.controllers;

import com.myapp.desk.domain.Instrument;
import com.myapp.desk.service.InstrumentService;
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

@Tag(name = "Instruments", description = "Financial Instruments Management API with Redis Caching")
@RestController
@RequestMapping("/api/instruments")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class InstrumentController {

    @Autowired
    private InstrumentService instrumentService;

    @Operation(
            summary = "Retrieve all instruments",
            description = "Get a list of all financial instruments. This endpoint uses Redis caching for improved performance.",
            tags = { "instruments", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Instrument.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping
    public List<Instrument> getAllInstruments() {
        return instrumentService.getAllInstruments();
    }

    @Operation(
            summary = "Retrieve an instrument by ID",
            description = "Get a specific instrument by its ID. This endpoint uses Redis caching for improved performance.",
            tags = { "instruments", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Instrument.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/{id}")
    public ResponseEntity<Instrument> getInstrumentById(
            @Parameter(description = "ID of the instrument to be retrieved", required = true)
            @PathVariable Long id) {
        Optional<Instrument> instrument = instrumentService.getInstrumentById(id);
        return instrument.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create a new instrument",
            description = "Create a new financial instrument. The created instrument will be cached in Redis.",
            tags = { "instruments", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = Instrument.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping
    public ResponseEntity<Instrument> createInstrument(
            @Parameter(description = "Instrument object to be created", required = true)
            @RequestBody Instrument instrument) {
        Instrument savedInstrument = instrumentService.createInstrument(instrument);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedInstrument);
    }

    @Operation(
            summary = "Update an existing instrument",
            description = "Update an existing financial instrument. The updated instrument will be cached in Redis.",
            tags = { "instruments", "put" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Instrument.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PutMapping("/{id}")
    public ResponseEntity<Instrument> updateInstrument(
            @Parameter(description = "ID of the instrument to be updated", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated instrument object", required = true)
            @RequestBody Instrument instrument) {
        if (!instrumentService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Instrument updatedInstrument = instrumentService.updateInstrument(id, instrument);
        return ResponseEntity.ok(updatedInstrument);
    }

    @Operation(
            summary = "Delete an instrument",
            description = "Delete a financial instrument by ID. The instrument will be removed from Redis cache.",
            tags = { "instruments", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstrument(
            @Parameter(description = "ID of the instrument to be deleted", required = true)
            @PathVariable Long id) {
        if (!instrumentService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        instrumentService.deleteInstrument(id);
        return ResponseEntity.noContent().build();
    }
}
