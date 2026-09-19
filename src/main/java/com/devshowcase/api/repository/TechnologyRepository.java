package com.devshowcase.api.repository;

import com.devshowcase.api.model.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    Optional<Technology> findByName(String name);
}
