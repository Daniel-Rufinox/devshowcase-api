package com.devshowcase.api.controller;

import com.devshowcase.api.model.Developer;
import com.devshowcase.api.repository.DeveloperRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/developers")
public class DeveloperController {

    private final DeveloperRepository repository;

    public DeveloperController(DeveloperRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Developer developer) {
        if (developer.getName() == null || developer.getName().isBlank()
                || developer.getEmail() == null || developer.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("Nome e email são obrigatórios");
        }
        if (repository.existsByEmail(developer.getEmail())) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }
        return ResponseEntity.status(201).body(repository.save(developer));
    }

    @GetMapping
    public List<Developer> list() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Developer> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
