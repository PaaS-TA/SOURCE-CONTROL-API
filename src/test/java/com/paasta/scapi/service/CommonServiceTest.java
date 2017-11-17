package com.paasta.scapi.service;

import com.paasta.scapi.common.*;
import com.paasta.scapi.common.util.PropertiesUtil;
import com.paasta.scapi.common.util.RestClientUtil;
import com.paasta.scapi.repository.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sonia.scm.client.*;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class, classes = { PropertiesUtil.class })
@ActiveProfiles("test")
public class CommonServiceTest extends MockUtil{

    @InjectMocks
    public
    RepoPermissionDBService repoPermissionDBService;

    @InjectMocks
    public
    RepoPermissionApiService repoPermissionApiService;

    @InjectMocks
    public
    RepoPermissionDBService getRepoPermissionDBService;

    @InjectMocks
    public
    ScInstanceUserService scInstanceUserService;

    @InjectMocks
    public
    ScLoginService scLoginService;

    @InjectMocks
    public
    ScRepositoryApiService scRepositoryApiService;

    @InjectMocks
    public ScRepositoryDBService scRepositoryDBService;

    @InjectMocks
    public ScServiceInstanceService scServiceInstanceService;

    @InjectMocks
    public ScServiceInstancesSpecifications scServiceInstancesSpecifications;

    @InjectMocks
    public ScUserService injectionScUserService;

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
    ScServiceInstanceRepository scServiceInstanceRepository;

    @Mock
    public
    ScUserService scUserService;

    @MockBean
    ResponseEntity responseEntity;

    @MockBean
    ScmClientSession scmClientSession;

    @MockBean
    UserClientHandler userClientHandler;

    @MockBean
    RepositoryClientHandler repositoryClientHandler;

    @Mock
    CommonService commonService;

    @Mock
    RestClientUtil restClientUtil;

    /**
     * It is required to perform the service.
     * To prevent errors when changing service.
     */
    public void setUpMockUtil() {
        MockitoAnnotations.initMocks(this);

        /**
         * List initalize
         */
        lstScRepository = new ArrayList();
        lstScUserBefore  = new ArrayList();
        lstScUser = new ArrayList();
        lstUser = new ArrayList();
        lstRepoPermission = new ArrayList();
        lstScInstanceUsers = new ArrayList();
        rtnList = new ArrayList();

        /**
         * entity initialize
         */

        lstUser = UserEntityTestData.getLstUser();
        lstScUserBefore = UserEntityTestData.getLstScUser();
        lstScRepository = RepositoryEntityTestData.getLstScRepository();
        lstRepoPermission = RepoPermissionEntityTestData.getLstRepoPermissiony();
        lstScInstanceUsers = ScInstanceUserEntityTestData.getSearchScInstanceUser();
        lstScServiceInstance = ScServiceInstanceEntityTestData.getLstScServiceInstance();
        pageScServiceInstance = ScServiceInstanceEntityTestData.getPageScServiceInstance();

        /**
         * scUserRepository mockito init
         */
        Mockito.when(scUserRepository.findOne(userId)).thenReturn(UserEntityTestData.getScUser());
        Mockito.when(scUserRepository.findOne(emptyId)).thenReturn(UserEntityTestData.getEmptyScUser());
        Mockito.when(scUserRepository.findAll()).thenReturn(lstScUser);
        Mockito.when(scUserRepository.save(UserEntityTestData.createScUser())).thenReturn(UserEntityTestData.createScUser());
        Mockito.doNothing().when(scUserRepository).delete(userId);

        /**
         * repoPermissionRepository mockito init
         */
        Mockito.when(repoPermissionRepository.deleteAllByRepoNo(repoNo)).thenReturn(iSuccess);
        Mockito.doNothing().when(repoPermissionRepository).delete(repoNo);
        Mockito.when(repoPermissionRepository.findAllByRepoNo(repoNo)).thenReturn(lstRepoPermission);
        Mockito.when(repoPermissionRepository.findAllByRepoNoAndPermission(repoNo, sRepoPermission)).thenReturn(lstRepoPermission);
        Mockito.when(repoPermissionRepository.findAllByRepoNoAndUserId(repoNo, userId)).thenReturn(lstRepoPermission);

        /**
         * scInstanceUserRepository mockito init
         */
        Mockito.when(scInstanceUserRepository.findByInstanceIdAndUserId(instanceId, userId)).thenReturn(lstScInstanceUsers);
        Mockito.when(scInstanceUserRepository.findByInstanceIdContainingAndUserIdContaining(instanceId, userId)).thenReturn(lstScInstanceUsers);
        Mockito.when(scInstanceUserRepository.findByUserId(userId)).thenReturn(lstScInstanceUsers);
        Mockito.when(scInstanceUserRepository.findByInstanceIdAndUserIdIsContainingAndCreaterYnContaining(instanceId,searchUserId,userSearchCreateYn)).thenReturn(lstScInstanceUsers);
        Mockito.doNothing().when(scInstanceUserRepository).delete(getInstanceId);

        /**
         * scUserRepository mockito init
         */
        Mockito.when(scRepositoryRepository.findAllByRepoScmId(repoScmId)).thenReturn(lstScRepository);
        Mockito.when(scRepositoryRepository.findAllByInstanceId(instanceId)).thenReturn(lstScRepository);
        Mockito.when(scRepositoryRepository.save(RepositoryEntityTestData.createRepoitory())).thenReturn(RepositoryEntityTestData.createRepoitory());
        Mockito.doNothing().when(scRepositoryRepository).delete(RepositoryEntityTestData.createRepoitory());

        /**
         * scServiceInstanceRepository mockito init
         */

        Mockito.when(scServiceInstanceRepository.findByCreateUserId(userId)).thenReturn(lstScServiceInstance);
//        Mockito.when(scServiceInstanceRepository.findAll(ScServiceInstanceEntityTestData.geSpecScServiceInstance()).thenReturn(ScServiceInstanceEntityTestData.geSpecScServiceInstance());

        /**
         * scUserRepository mockito init
         */
        Mockito.when(scUserRepository.findOne(userId)).thenReturn(UserEntityTestData.createScUser());
        Mockito.when(scUserRepository.findAll()).thenReturn(lstScUser);
        Mockito.when(scUserRepository.save(UserEntityTestData.createScUser())).thenReturn(UserEntityTestData.createScUser());
        Mockito.doNothing().when(scUserRepository).delete(userId);
        Mockito.when(scUserRepository.findAllByUserIdContaining(userId)).thenReturn(lstScUserBefore);

        Mockito.when(commonService.scmAdminSession()).thenReturn(scmClientSession);
        Mockito.when(scmClientSession.getUserHandler()).thenReturn(userClientHandler);

        Mockito.when(userClientHandler.getAll()).thenReturn(lstUser);
        Mockito.when(userClientHandler.get(userId)).thenReturn(UserEntityTestData.getUser());
        Mockito.doNothing().when(userClientHandler).delete(userId);
        Mockito.doNothing().when(userClientHandler).modify(UserEntityTestData.modifyeUser());

        Mockito.when(scUserService.apiGetUsers()).thenReturn(responseEntity);
        Mockito.doNothing().when(scUserService).restDeleteUser(userId);
        Mockito.doThrow(new RuntimeException()).when(scUserService).restDeleteUser(userId);


        Mockito.when(responseEntity.getBody()).thenReturn(lstUser);
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