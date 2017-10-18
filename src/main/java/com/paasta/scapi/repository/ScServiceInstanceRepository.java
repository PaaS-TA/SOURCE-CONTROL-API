package com.paasta.scapi.repository;

import com.paasta.scapi.entity.ScServiceInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ScServiceInstanceRepository extends JpaRepository<ScServiceInstance, String>{

    List<ScServiceInstance> findByCreateUserId(String createUserId);

    Page<ScServiceInstance> findAll(Specification<ScServiceInstance> scRepositorySpecification, Pageable pageable);
}
