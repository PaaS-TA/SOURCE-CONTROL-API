package com.paasta.scapi.service;

import com.paasta.scapi.entity.RepoPermission;
import com.paasta.scapi.entity.ScRepository;
import com.paasta.scapi.entity.ScUser;
import com.paasta.scapi.model.User;
import com.paasta.scapi.repository.RepoPermissionRepository;
import com.paasta.scapi.repository.ScRepositoryRepository;
import com.paasta.scapi.repository.ScUserRepository;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import sonia.scm.client.ScmClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SEJI on 2017-10-16.
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RepoPermissionDBServiceTest extends CommonServiceTest{


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @InjectMocks
    private
    RepoPermissionDBService repoPermissionDBService;

    @Mock
    private
    ScRepositoryRepository scRepositoryRepository;

    @Mock
    private
    ScUserRepository scUserRepository;

    @Mock
    private
    RepoPermissionRepository repoPermissionRepository;

    @Mock
    private
    ScUserService scUserService;
    @Mock
    private
    CommonService commonService;
/*    @Mock
    private
    ScmClient scmClient;*/

    List<Map> rtnList = new ArrayList();
    List<ScRepository> lstScRepository = new ArrayList();
    List<ScUser> lstScUserBefore  = new ArrayList();
    List<ScUser> lstScUser = new ArrayList();
    List<sonia.scm.user.User> lstUser = new ArrayList();
    List<RepoPermission> lstRepoPermission = new ArrayList();

    @MockBean
    ResponseEntity responseEntity;


    @Override
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ScRepository scRepository = new ScRepository();
        scRepository.setRepoNo(repoNo);
        scRepository.setRepoScmId(repoScmId);
        scRepository.setRepoName(repoName);
        scRepository.setCreateUserId(createUserId);
        scRepository.setInstanceId(instanceId);
        scRepository.setOwnerUserId(ownerUserId);
        lstScRepository.add(scRepository);

        ScUser scUser= new ScUser(userId, userName, userMail, userDesc);
        lstScUserBefore.add(scUser);

        sonia.scm.user.User user = new sonia.scm.user.User(userId, userName,userMail);
        lstUser.add(user);

        RepoPermission repoPermission = new RepoPermission(repoNo, userId);
        repoPermission.setPermission(sRepoPermission);
        repoPermission.setNo(iRepoNo);
        lstRepoPermission.add(repoPermission);

        Mockito.when(scRepositoryRepository.findAllByRepoScmId(repoScmId)).thenReturn(lstScRepository);
        Mockito.when(scUserRepository.findAllByUserIdContaining(userId)).thenReturn(lstScUserBefore);

        Mockito.when(repoPermissionRepository.findAllByRepoNo(repoNo)).thenReturn(lstRepoPermission);
        Mockito.when(commonService.scmAdminSession()).thenReturn(scmClientSession);
        Mockito.when(scmClientSession.getUserHandler()).thenReturn(userClientHandler);
        Mockito.when(userClientHandler.getAll()).thenReturn(lstUser);
        Mockito.when(scUserService.apiGetUsers()).thenReturn(responseEntity);
        Mockito.when(responseEntity.getBody()).thenReturn(lstUser);

        Map map = new HashMap<>();
        map.put("userId",userId);
        map.put("userName",userName);
        map.put("userEmail",userMail);
        map.put("userPermission",sRepoPermission);
        map.put("userPermissionNo",iRepoNo);
        rtnList.add(map);

    }

    @Test
    public void searchPermisionByUserIdAndRepositoryId() throws Exception {
        List<Map> mathRtnList = repoPermissionDBService.searchPermisionByUserIdAndRepositoryId(userId,repoScmId);
        Assert.assertEquals(mathRtnList,rtnList);

    }
/*
    @Test
    public void searchPermisionByUserIdAndInstanceId() throws Exception {
        String searchUserId = "";
        String instanceId = "";
        String repositoryId = "";
        repoPermissionDBService.searchPermisionByUserIdAndInstanceId(searchUserId,instanceId,repositoryId);
    }

    @Test
    public void scmAdminSession() throws Exception {
        Assert.assertNotNull(scmAdminSession());
    }
    */

}
