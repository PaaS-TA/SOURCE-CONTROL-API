package com.paasta.scapi.repository;

import com.paasta.scapi.entity.ScUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ScUserRepository extends JpaRepository<ScUser, String> {
//    Page<ScUser> findAllByUserNameOrUserDescOrUserId(String userName, String userDesc, String userId, Pageable pageable);
//    Page<ScUser> findByUserNameContaining(String userName, Pageable pageRequest);
    List<ScUser> findAllByUserIdContaining(String userName);
}
