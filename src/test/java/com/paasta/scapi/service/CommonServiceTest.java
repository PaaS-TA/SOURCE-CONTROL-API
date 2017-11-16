package com.paasta.scapi.service;

import com.paasta.scapi.common.ClientTestUtil;
import com.paasta.scapi.common.MockUtil;
import com.paasta.scapi.common.util.PropertiesUtil;
import com.paasta.scapi.entity.RepoPermission;
import com.paasta.scapi.entity.ScInstanceUser;
import com.paasta.scapi.entity.ScRepository;
import com.paasta.scapi.entity.ScUser;
import com.paasta.scapi.repository.RepoPermissionRepository;
import com.paasta.scapi.repository.ScInstanceUserRepository;
import com.paasta.scapi.repository.ScRepositoryRepository;
import com.paasta.scapi.repository.ScUserRepository;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sonia.scm.client.JerseyClientSession;
import sonia.scm.client.ScmClientSession;
import sonia.scm.client.ScmUnauthorizedException;
import sonia.scm.client.UserClientHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class, classes = { PropertiesUtil.class })
@ActiveProfiles("test")
public class CommonServiceTest extends MockUtil{

    @InjectMocks
    public
    RepoPermissionDBService repoPermissionDBService;

    @Mock
    public
    ScRepositoryRepository scRepositoryRepository;

    @Mock
    public
    ScUserRepository scUserRepository;

    @Mock
    public
    RepoPermissionRepository repoPermissionRepository;

    @Mock
    public
    ScInstanceUserRepository scInstanceUserRepository;

    @Mock
    public
    ScUserService scUserService;
/*

    static final List<ScRepository> lstScRepository = new ArrayList();
    static final List<ScUser> lstScUserBefore  = new ArrayList();
    static final List<ScUser> lstScUser = new ArrayList();
    static final List<sonia.scm.user.User> lstUser = new ArrayList();
    static final List<RepoPermission> lstRepoPermission = new ArrayList();
    static final List<ScInstanceUser> lstScInstanceUsers = new ArrayList();
    static List<Map> rtnList = new ArrayList();
*/


    static List<ScRepository> lstScRepository = new ArrayList();
    static List<ScUser> lstScUserBefore  = new ArrayList();
    static List<ScUser> lstScUser = new ArrayList();
    static List<sonia.scm.user.User> lstUser = new ArrayList();
    static List<RepoPermission> lstRepoPermission = new ArrayList();
    static List<ScInstanceUser> lstScInstanceUsers = new ArrayList();
    static List<Map> rtnList = new ArrayList();

    @MockBean
    ResponseEntity responseEntity;

    @MockBean
    ScmClientSession scmClientSession;

    @MockBean
    UserClientHandler userClientHandler;

    @Mock
    CommonService commonService;

    public void setUpMockUtil() {
        MockitoAnnotations.initMocks(this);

        lstScRepository = new ArrayList();
        lstScUserBefore  = new ArrayList();
        lstScUser = new ArrayList();
        lstUser = new ArrayList();
        lstRepoPermission = new ArrayList();
        lstScInstanceUsers = new ArrayList();
        rtnList = new ArrayList();

        ScRepository scRepository = new ScRepository();
        scRepository.setRepoNo(repoNo);
        scRepository.setRepoScmId(repoScmId);
        scRepository.setRepoName(repoName);
        scRepository.setRepoDesc(repoDesc);
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
        repoPermission.setNo(getPermissionNo);
        repoPermission.setRepoNo(repoNo);
        lstRepoPermission.add(repoPermission);

        ScInstanceUser scInstanceUser = new ScInstanceUser(instanceId, userId, userRepoRole, userCreateYn);
        scInstanceUser.setNo(getPermissionNo);
        scInstanceUser.setCreatedDate(userCreatedDate);
        scInstanceUser.setModifiedDate(userModifiedDate);
        lstScInstanceUsers.add(scInstanceUser);

        Mockito.when(scRepositoryRepository.findAllByRepoScmId(repoScmId)).thenReturn(lstScRepository);
        Mockito.when(scUserRepository.findAllByUserIdContaining(userId)).thenReturn(lstScUserBefore);

        Mockito.when(repoPermissionRepository.findAllByRepoNo(repoNo)).thenReturn(lstRepoPermission);
        Mockito.when(commonService.scmAdminSession()).thenReturn(scmClientSession);
        Mockito.when(scmClientSession.getUserHandler()).thenReturn(userClientHandler);
        Mockito.when(userClientHandler.getAll()).thenReturn(lstUser);
        Mockito.when(scUserService.apiGetUsers()).thenReturn(responseEntity);
        Mockito.when(responseEntity.getBody()).thenReturn(lstUser);

        Mockito.when(scInstanceUserRepository.findByInstanceIdAndUserIdIsContainingAndCreaterYnContaining(instanceId,searchUserId,"")).thenReturn(lstScInstanceUsers);

        Mockito.when(commonService.scmAdminSession()).thenReturn(scmClientSession);

    }

    /*@Test(expected = ScmUnauthorizedException.class)
    public void createSessionAnonymousFailedTest()
    {
        ClientTestUtil.createAnonymousSession().close();
    }*/
    @Ignore
    @Test
    public void scmAdminSession() throws Exception {
        ScmClientSession rtnScmClientSession = commonService.scmAdminSession();
//        scmClientSession = ClientTestUtil.createAdminSession();
        Assert.assertEquals(commonService.scmAdminSession(),scmClientSession);
    }
}