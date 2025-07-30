package com.myapp.desk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myapp.desk.domain.Instrument;

public interface InstrumentRepository extends JpaRepository<Instrument, Long> {

}