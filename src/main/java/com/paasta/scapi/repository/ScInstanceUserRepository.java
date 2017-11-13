package com.paasta.scapi.repository;

import com.paasta.scapi.entity.ScInstanceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ScInstanceUserRepository extends JpaRepository<ScInstanceUser, Integer> {

    List<ScInstanceUser> findByInstanceIdAndUserId(String instanceId, String userId);
    List<ScInstanceUser> findByInstanceIdContainingAndUserIdContaining(String instanceId, String userId);
    List<ScInstanceUser> findByUserId(String userId);
    List<ScInstanceUser> findByInstanceIdAndUserIdIsContainingAndCreaterYnContaining(String instanceId, String userId, String createYn);

    @Override
    void delete(Iterable<? extends ScInstanceUser> entities);
}
