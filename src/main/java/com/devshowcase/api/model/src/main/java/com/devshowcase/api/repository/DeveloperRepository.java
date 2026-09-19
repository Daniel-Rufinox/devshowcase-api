package com.devshowcase.api.repository;

import com.devshowcase.api.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    boolean existsByEmail(String email);
}
