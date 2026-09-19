package com.devshowcase.api.controller;

import com.devshowcase.api.model.Comment;
import com.devshowcase.api.model.Project;
import com.devshowcase.api.model.Technology;
import com.devshowcase.api.repository.CommentRepository;
import com.devshowcase.api.repository.DeveloperRepository;
import com.devshowcase.api.repository.ProjectRepository;
import com.devshowcase.api.repository.TechnologyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final DeveloperRepository developerRepository;
    private final TechnologyRepository technologyRepository;
    private final CommentRepository commentRepository;

    public ProjectController(ProjectRepository projectRepository,
                             DeveloperRepository developerRepository,
                             TechnologyRepository technologyRepository,
                             CommentRepository commentRepository) {
        this.projectRepository = projectRepository;
        this.developerRepository = developerRepository;
        this.technologyRepository = technologyRepository;
        this.commentRepository = commentRepository;
    }

    @PostMapping("/developers/{developerId}/projects")
    public ResponseEntity<?> createProject(@PathVariable Long developerId, @RequestBody Project project) {
        if (project.getTitle() == null || project.getTitle().isBlank()) {
            return ResponseEntity.badRequest().body("Título é obrigatório");
        }
        return developerRepository.findById(developerId).map(dev -> {
            project.setDeveloper(dev);
            return ResponseEntity.status(201).body(projectRepository.save(project));
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/projects")
    public List<Project> listProjects() {
        return projectRepository.findAll();
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        return projectRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody Project dados) {
        return projectRepository.findById(id).map(project -> {
            if (dados.getTitle() != null) project.setTitle(dados.getTitle());
            if (dados.getDescription() != null) project.setDescription(dados.getDescription());
            if (dados.getRepositoryUrl() != null) project.setRepositoryUrl(dados.getRepositoryUrl());
            return ResponseEntity.ok(projectRepository.save(project));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        if (!projectRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        projectRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/projects/{id}/technologies")
    public ResponseEntity<?> addTechnology(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String name = body.get("name");
        if (name == null || name.isBlank()) {
            return ResponseEntity.badRequest().body("Nome da tecnologia é obrigatório");
        }
        return projectRepository.findById(id).map(project -> {
            Technology tech = technologyRepository.findByName(name)
                    .orElseGet(() -> technologyRepository.save(new Technology()));
            if (tech.getName() == null) tech.setName(name);
            if (!project.getTechnologies().contains(tech)) {
                project.getTechnologies().add(tech);
            }
            return ResponseEntity.status(201).body(projectRepository.save(project));
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/technologies")
    public List<Technology> listTechnologies() {
        return technologyRepository.findAll();
    }

    @PostMapping("/projects/{id}/comments")
    public ResponseEntity<?> addComment(@PathVariable Long id, @RequestBody Comment comment) {
        if (comment.getContent() == null || comment.getContent().isBlank()) {
            return ResponseEntity.badRequest().body("Conteúdo é obrigatório");
        }
        return projectRepository.findById(id).map(project -> {
            comment.setProject(project);
            return ResponseEntity.status(201).body(commentRepository.save(comment));
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/projects/{id}/comments")
    public ResponseEntity<List<Comment>> listComments(@PathVariable Long id) {
        if (!projectRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(commentRepository.findByProjectId(id));
    }
}
