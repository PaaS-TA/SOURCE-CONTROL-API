package com.paasta.scapi.repository;

import com.paasta.scapi.entity.ScRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lena on 2017-06-15.
 */
@Repository
public interface ScRepositoryRepository extends JpaRepository<ScRepository, Integer> {

    List<ScRepository> findAllByRepoScmId(String repoScmId);

    List<ScRepository> findAllByInstanceId(String instanceId);

}
