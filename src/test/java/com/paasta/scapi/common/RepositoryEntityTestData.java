package com.paasta.scapi.common;

import com.paasta.scapi.entity.ScRepository;
import sonia.scm.repository.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class RepositoryEntityTestData
{

    private RepositoryEntityTestData() {}

    public static ScRepository createScRepoitory()
    {
        ScRepository scRepository = new ScRepository(MockUtil.repoScmId, MockUtil.repoName,MockUtil.repoDesc,MockUtil.instanceId , MockUtil.ownerUserId , MockUtil.createUserId);
        scRepository.setCreateTime("");
        return scRepository;
    }
    public static ScRepository getScRepoitory()
    {
        ScRepository scRepository = new ScRepository(MockUtil.repoScmId, MockUtil.repoName,MockUtil.repoDesc,MockUtil.instanceId , MockUtil.ownerUserId , MockUtil.createUserId);
        scRepository.setRepoNo(MockUtil.repoNo);
        scRepository.setCreateTime("");
        return scRepository;
    }/**/

    public static List<ScRepository> getLstScRepository()
    {
        List<ScRepository> rtnList = new ArrayList();
        rtnList.add(getScRepoitory());
        return rtnList;
    }

    public static Repository createRepoitory()
    {

        Repository repository = new Repository();
        repository.setId(MockUtil.repoScmId);
        repository.setName(MockUtil.repoName);
        repository.setDescription(MockUtil.repoDesc);
//        repository.setProperties(getLstRepositoryProperties());
        repository.setProperties(getMapRepositoryProperties());
        repository.setPublicReadable(false);
        repository.setType(MockUtil.repoType);
        repository.setPermissions(RepoPermissionEntityTestData.getLstPermission());
        return repository;
    }

    public static Repository getRepoitory()
    {
        Repository repository = createRepoitory();
        return repository;
    }

    public static List<Map<String, String>> getLstRepositoryProperties()
    {
        List<Map<String, String>> getLstRepositoryProperties = new ArrayList<>();
        Map mapInstanceId = new HashMap();
        mapInstanceId.put(Constants.PROPERTIES_KEY,Constants.REPO_PROPERTIES_INSTANCE_ID);
        mapInstanceId.put(Constants.PROPERTIES_VALUE,MockUtil.instanceId);
        getLstRepositoryProperties.add(mapInstanceId);
        Map mapCreateUserId = new HashMap();
        mapCreateUserId.put(Constants.PROPERTIES_KEY,Constants.REPO_PROPERTIES_CREATE_USER);
        mapCreateUserId.put(Constants.PROPERTIES_VALUE,MockUtil.createUserId);
       getLstRepositoryProperties.add(mapCreateUserId);

        return getLstRepositoryProperties;
    }
    public static Map<String, String> getMapRepositoryProperties()
    {
        Map<String, String> getMapRepositoryProperties = new HashMap();
        getMapRepositoryProperties.put(Constants.REPO_PROPERTIES_INSTANCE_ID, MockUtil.instanceId);
        getMapRepositoryProperties.put(Constants.REPO_PROPERTIES_CREATE_USER,MockUtil.createUserId);
        return getMapRepositoryProperties;
    }


}