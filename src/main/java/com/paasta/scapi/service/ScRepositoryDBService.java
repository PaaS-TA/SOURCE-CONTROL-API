package com.paasta.scapi.service;

import com.paasta.scapi.common.Constants;
import com.paasta.scapi.entity.RepoPermission;
import com.paasta.scapi.entity.ScRepository;
import com.paasta.scapi.repository.RepoPermissionRepository;
import com.paasta.scapi.repository.ScRepositoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sonia.scm.repository.Repository;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private  ScRepositoryApiService scRepositoryApiService;

    @SuppressWarnings("ConstantConditions")
    @Transactional
    public sonia.scm.repository.Repository createRepositoryDB(sonia.scm.repository.Repository repository) {
            scRepositoryApiService.createRepositoryApi(repository);
            repository = scRepositoryApiService.scmRepositoryByNameType(repository.getType(),repository.getName());
            // 1-1. ScRepository insert
            // 1-2. RepoPermission insert
            Map mapReposiotry = repository.getProperties();
            String instanceId = (String)mapReposiotry.getOrDefault(Constants.REPO_PROPERTIES_INSTANCE_ID,"");
            String createUser = (String)mapReposiotry.getOrDefault(Constants.REPO_PROPERTIES_CREATE_USER,"");
            ScRepository scRepository = new ScRepository(repository.getId(), repository.getName(),repository.getDescription(),instanceId , createUser , createUser);
            scRepositoryRepository.save(scRepository);
            List<ScRepository> lstScRepository = scRepositoryRepository.findAllByRepoScmId(scRepository.getRepoScmId());
            RepoPermission repoPermission = new RepoPermission(lstScRepository.get(0).getRepoNo(), createUser);
            repoPermissionRepository.save(repoPermission);

        return repository;
    }

    
    @Transactional
    public Repository updateRepositoryDB(Repository repository) {

        // step1. Update DB repository Info
        // 1-1. ScRepository Update ( 현재 Description 항목 수정 가능)
        // 1-2. RepoPermission Update - 생략 (permission 수정 항목 없음)
        List<ScRepository> scRepository = scRepositoryRepository.findAllByRepoScmId(repository.getId());
        scRepositoryApiService.updateRepository(repository.getId(), repository);
        // repository 수정 항목 적용
        scRepository.get(0).setRepoDesc(repository.getDescription());
        scRepositoryRepository.save(scRepository);
        return scRepositoryApiService.getRepositoryByIdApi(repository.getId());
    }

    
    @Transactional
    public void deleteRepositoryDB(String id) {

        //scm delete transaction 으로인해 오류일경우 rollback 됨.
        // step1. Delete DB repository Info
        // 1-1. ScRepository Delete
        // 1-2. RepoPermission Delete
        scRepositoryApiService.deleteRepositoryApi(id);
        List<ScRepository> scRepository = scRepositoryRepository.findAllByRepoScmId(id);
        if(scRepository.size()>0) {
            int repoNo = scRepository.get(0).getRepoNo();
            repoPermissionRepository.deleteAllByRepoNo(repoNo);
            scRepositoryRepository.delete(scRepository);
        }
    }

}
