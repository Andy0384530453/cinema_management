package com.example.demo.service;

import com.example.demo.dto.ProjectionDetail;
import com.example.demo.dto.ProjectionInput;
import com.example.demo.entity.JProjection;
import com.example.demo.mapper.JProjectionMapper;
import com.example.demo.mapper.ProjectionMapper;
import com.example.demo.model.Projection;
import com.example.demo.repository.ProjectionRepository;
import com.example.demo.validator.ProjectionValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectionService {

  private final ProjectionRepository projectionRepository;
  private final ProjectionMapper projectionMapper;
  private final JProjectionMapper jProjectionMapper;
  private final ProjectionValidator projectionValidator;

  public ProjectionDetail save(ProjectionInput input) {
    ProjectionValidator.ProjectionDeps deps = projectionValidator.validate(input);
    Projection projection = projectionMapper.toDomain(input);
    JProjection jProjection = jProjectionMapper.toJpa(projection);
    jProjection.setMovie(deps.movie());
    jProjection.setRoom(deps.room());
    return projectionMapper.toDetail(
        jProjectionMapper.toDomain(projectionRepository.save(jProjection)));
  }

  public List<ProjectionDetail> findAll() {
    return projectionRepository.findAll().stream()
        .map(jProjectionMapper::toDomain)
        .map(projectionMapper::toDetail)
        .toList();
  }
}
