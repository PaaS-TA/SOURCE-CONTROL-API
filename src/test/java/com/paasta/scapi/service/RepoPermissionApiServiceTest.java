package com.paasta.scapi.service;

import com.paasta.scapi.common.util.PropertiesUtil;
import com.paasta.scapi.common.util.RestClientUtil;
import com.paasta.scapi.entity.RepoPermission;
import com.paasta.scapi.model.Repository;
import com.paasta.scapi.repository.RepoPermissionRepository;
import com.paasta.scapi.repository.ScInstanceUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

/**
 * Created by ij
 * lee on 2017-08-02.
 */
@RunWith(MockitoJUnitRunner.class)
@Configuration
public class RepoPermissionApiServiceTest {

    @InjectMocks
    RestClientUtil restClientUtil;

    @InjectMocks
    PropertiesUtil propertiesUtil;

    @Mock
    ScRepositoryApiService scRepositoryApiService;

    @Mock
    RepoPermissionRepository repoPermissionRepository;

    @Mock
    RepoPermissionDBService repoPermissionDBService;

    @InjectMocks
    RepoPermissionApiService repoPermissionApiService;

    @Mock
    ScInstanceUserRepository scInstanceUserRepository;

    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void insertRepositoryForUserAuth() throws Exception {
        Repository repository = mock(Repository.class);
        String repositoryId="";

        RepoPermission permission = mock(RepoPermission.class);
        permission.getUserId();
        permission.getPermission();

        scRepositoryApiService.getRepositoryByIdApi("");
}

    @Test
    public void deleteRepositoryForUserAuth() throws Exception {
        RepoPermission repoPermission = mock(RepoPermission.class);
        repoPermissionRepository.delete(repoPermission.getNo());
    }

    @Test
    public void getListPermitionByInstanceId() throws Exception {
        repoPermissionApiService.getListPermitionByInstanceId("",1,10,"");
    }

    @Test
    public void permisionByInstanceIdAndParam() throws Exception {
        repoPermissionApiService.permisionByInstanceIdAndParam("","","Y","","","");
    }

}