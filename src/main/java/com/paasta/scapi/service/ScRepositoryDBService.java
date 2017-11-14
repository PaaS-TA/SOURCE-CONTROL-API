package com.paasta.scapi.service;

import com.paasta.scapi.common.Constants;
import com.paasta.scapi.entity.RepoPermission;
import com.paasta.scapi.entity.ScRepository;
import com.paasta.scapi.model.Repository;
import com.paasta.scapi.repository.RepoPermissionRepository;
import com.paasta.scapi.repository.ScRepositoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lena on 2017-06-14.
 */
@Service
public class ScRepositoryDBService extends CommonService{

    /**
     * The Sc repository repository.
     */
    @Autowired
    private
    ScRepositoryRepository scRepositoryRepository;

    /**
     * The Repo permition repository.
     */
    @Autowired
    private
    RepoPermissionRepository repoPermissionRepository;


    @SuppressWarnings("ConstantConditions")
    @Transactional
    public ScRepository createRepositoryDB(Repository repository) {

        // 1-1. ScRepository insert
        // 1-2. RepoPermission insert

        String instanceId = repository.getProperties().stream().filter(e -> e.get(Constants.PROPERTIES_KEY).equals(Constants.REPO_PROPERTIES_INSTANCE_ID)).findFirst().get().get(Constants.PROPERTIES_VALUE);
        String create_user = repository.getProperties().stream().filter(e -> e.get(Constants.PROPERTIES_KEY).equals(Constants.REPO_PROPERTIES_CREATE_USER)).findFirst().get().get(Constants.PROPERTIES_VALUE);

        ScRepository scRepository = new ScRepository(repository.getId(), repository.getName(),repository.getDescription(),instanceId , create_user , create_user);
        ScRepository result = this.scRepositoryRepository.save(scRepository);
        RepoPermission repoPermission = new RepoPermission(result.getRepoNo(), result.getCreateUserId());
        this.repoPermissionRepository.save(repoPermission);

        return scRepository;
    }

    
    @Transactional
    public void updateRepositoryDB(Repository repository) {

        // step1. Update DB repository Info
        // 1-1. ScRepository Update ( 현재 Description 항목 수정 가능)
        // 1-2. RepoPermission Update - 생략 (permission 수정 항목 없음)
        List<ScRepository> scRepository = scRepositoryRepository.findAllByRepoScmId(repository.getId());
        // repository 수정 항목 적용
        scRepository.get(0).setRepoDesc(repository.getDescription());
        scRepositoryRepository.save(scRepository);
    }

    
    @Transactional
    public void deleteRepositoryDB(String id) {

        // step1. Delete DB repository Info
        // 1-1. ScRepository Delete
        // 1-2. RepoPermission Delete
        List<ScRepository> scRepository = scRepositoryRepository.findAllByRepoScmId(id);
        int repoNo = scRepository.get(0).getRepoNo();
    try {
        repoPermissionRepository.deleteAllByRepoNo(repoNo);
        scRepositoryRepository.delete(scRepository);
    }catch (Exception e){
        e.printStackTrace();
    }

    }

}
