package com.paasta.scapi.repository;

import com.paasta.scapi.entity.ScUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ScUserRepository extends JpaRepository<ScUser, String> {
    List<ScUser> findAllByUserIdContaining(String userName);
}
