package com.simple.accounts.inventory.repository;

import com.simple.accounts.inventory.model.UnitOfMeasure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitOfMeasureRepository extends JpaRepository<UnitOfMeasure, Long> {
    Optional<UnitOfMeasure> findByName(String name);
    Optional<UnitOfMeasure> findByAbbreviation(String abbreviation);
}
