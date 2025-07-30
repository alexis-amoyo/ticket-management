package com.myapp.desk.service;

import com.myapp.desk.domain.Instrument;
import com.myapp.desk.repository.InstrumentRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Service
public class InstrumentService {
    private final InstrumentRepository instrumentRepository;

    public InstrumentService(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    // Get all instruments - cacheable
    @Cacheable(value = "instruments", key = "'all'")
    public List<Instrument> getAllInstruments() {
        return instrumentRepository.findAll();
    }

    // Get instrument by ID - cacheable
    @Cacheable(value = "instruments", key = "#id")
    public Optional<Instrument> getInstrumentById(Long id) {
        return instrumentRepository.findById(id);
    }

    // Create new instrument - cache the result
    @CachePut(value = "instruments", key = "#result.id")
    public Instrument createInstrument(Instrument instrument) {
        Instrument savedInstrument = instrumentRepository.save(instrument);
        // Evict the 'all' cache since we added a new instrument
        evictAllInstrumentsCache();
        return savedInstrument;
    }

    // Update instrument - update cache
    @CachePut(value = "instruments", key = "#id")
    public Instrument updateInstrument(Long id, Instrument instrument) {
        instrument.setId(id);
        Instrument updatedInstrument = instrumentRepository.save(instrument);
        // Evict the 'all' cache since we updated an instrument
        evictAllInstrumentsCache();
        return updatedInstrument;
    }

    // Delete instrument - evict from cache
    @CacheEvict(value = "instruments", key = "#id")
    public void deleteInstrument(Long id) {
        instrumentRepository.deleteById(id);
        // Evict the 'all' cache since we removed an instrument
        evictAllInstrumentsCache();
    }

    // Check if instrument exists
    public boolean existsById(Long id) {
        return instrumentRepository.existsById(id);
    }

    // Helper method to evict all instruments cache
    @CacheEvict(value = "instruments", key = "'all'")
    public void evictAllInstrumentsCache() {
        // This method will evict the cache for all instruments
    }

    // Business logic methods for Instrument
}