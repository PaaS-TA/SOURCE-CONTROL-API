package com.paasta.scapi.repository;

import com.paasta.scapi.entity.RepoPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoPermissionRepository extends JpaRepository<RepoPermission, Integer> {

    int deleteAllByRepoNo(int repo_no);
    void delete(int no);
    List<RepoPermission>  findAllByRepoNo(int repo_no);
    List<RepoPermission>  findAllByRepoNoAndPermission(int repo_no, String permission);
    List<RepoPermission>  findAllByRepoNoAndUserId(int repo_no, String userId);

}
