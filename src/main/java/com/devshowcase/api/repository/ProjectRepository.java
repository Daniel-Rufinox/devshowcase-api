package com.devshowcase.api.repository;

import com.devshowcase.api.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByDeveloperId(Long developerId);
}
