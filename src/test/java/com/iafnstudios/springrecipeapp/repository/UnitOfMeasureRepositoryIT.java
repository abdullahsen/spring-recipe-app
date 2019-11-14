package com.iafnstudios.springrecipeapp.repository;

import com.iafnstudios.springrecipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testFindByDescription() {
        Optional<UnitOfMeasure> uomOptional =
                unitOfMeasureRepository.findByDescription("Teaspoon");

        assertEquals("Teaspoon",uomOptional.get().getDescription());
    }

    @Test
    void testFindByDescriptionCup() {
        Optional<UnitOfMeasure> uomOptional =
                unitOfMeasureRepository.findByDescription("Cup");

        assertEquals("Cup",uomOptional.get().getDescription());
    }
}