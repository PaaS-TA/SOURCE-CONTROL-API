package com.paasta.scapi.service;

import com.paasta.scapi.common.*;
import com.paasta.scapi.common.util.RestClientUtil;
import com.paasta.scapi.repository.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import sonia.scm.client.RepositoryClientHandler;
import sonia.scm.client.ScmClientSession;
import sonia.scm.client.ScmUnauthorizedException;
import sonia.scm.client.UserClientHandler;

import java.util.ArrayList;
@RunWith(SpringRunner.class)
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

    @Mock
    public ScRepositoryApiService scRepositoryApiService;

    @InjectMocks
    public ScRepositoryDBService scRepositoryDBService;

    @InjectMocks
    public ScServiceInstanceService scServiceInstanceService;

    @InjectMocks
    public
    ScUserService scUserService;

    @InjectMocks
    public ScServiceInstancesSpecifications scServiceInstancesSpecifications;

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
    ScUserApiService scUserApiService;

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
    public
    ScLoginService scLoginService;

    @Mock
    RestClientUtil restClientUtil;

    /**
     * It is required to perform the service.
     * To prevent errors when changing service.
     */
    @Before
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
        lstScUser = UserEntityTestData.getLstScUser();
        lstUser = UserEntityTestData.getLstUser();
        lstScUserBefore = UserEntityTestData.getLstScUser();
        lstScRepository = RepositoryEntityTestData.getLstScRepository();
        lstRepoPermission = RepoPermissionEntityTestData.getLstRepoPermission();
        lstScInstanceUsers = ScInstanceUserEntityTestData.getSearchScInstanceUser();
        lstScServiceInstance = ScServiceInstanceEntityTestData.getLstScServiceInstance();
        pageScServiceInstance = ScServiceInstanceEntityTestData.getPageScServiceInstance();

//        scmAdminSession();
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
        Mockito.when(repoPermissionRepository.save(RepoPermissionEntityTestData.createRepoPermission())).thenReturn(RepoPermissionEntityTestData.createRepoPermission());


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
        Mockito.when(scRepositoryRepository.save(RepositoryEntityTestData.createScRepoitory())).thenReturn(RepositoryEntityTestData.createScRepoitory());
        Mockito.doNothing().when(scRepositoryRepository).delete(RepositoryEntityTestData.createScRepoitory());

        /**
         * scServiceInstanceRepository mockito init
         */

        Mockito.when(scServiceInstanceRepository.findByCreateUserId(userId)).thenReturn(lstScServiceInstance);
        Mockito.when(scServiceInstanceRepository.findAll(new PageRequest(0,1, new Sort(Sort.Direction.ASC,"organizationName")))).thenReturn(ScServiceInstanceEntityTestData.getPageScServiceInstance());

        /**
         * scUserRepository mockito init
         */
        Mockito.when(scUserRepository.findOne(userId)).thenReturn(UserEntityTestData.createScUser());
        Mockito.when(scUserRepository.findAll()).thenReturn(lstScUser);
        Mockito.when(scUserRepository.save(UserEntityTestData.createScUser())).thenReturn(UserEntityTestData.createScUser());
        Mockito.doNothing().when(scUserRepository).delete(userId);
        Mockito.when(scUserRepository.findAllByUserIdContaining(userId)).thenReturn(lstScUserBefore);

        Mockito.doNothing().when(repositoryClientHandler).delete(repoScmId);


        Mockito.when(commonService.scmAdminSession()).thenReturn(scmClientSession);
        Mockito.when(scmClientSession.getUserHandler()).thenReturn(userClientHandler);
        Mockito.when(scmClientSession.getUserHandler().get(userId)).thenReturn(UserEntityTestData.getUser());

        Mockito.when(commonService.createRepositoryClientHandler()).thenReturn(repositoryClientHandler);

        Mockito.when(scLoginService.login(UserEntityTestData.getAdminUser(), null)).thenReturn(new ResponseEntity(UserEntityTestData.getUser(), HttpStatus.OK));
        Mockito.when(scLoginService.login(UserEntityTestData.getUser(), null)).thenThrow(new ScmUnauthorizedException());
        Mockito.when(scLoginService.login(null, null)).thenThrow(new RuntimeException("serverError"));

        Mockito.when(userClientHandler.getAll()).thenReturn(lstUser);
        Mockito.when(userClientHandler.get(userId)).thenReturn(UserEntityTestData.getUser());
        Mockito.doNothing().when(userClientHandler).delete(userId);
        Mockito.doNothing().when(userClientHandler).modify(UserEntityTestData.modifyeUser());

        Mockito.when(scUserApiService.getScmUser(userId)).thenReturn(UserEntityTestData.getUser());
        Mockito.when(scUserApiService.restGetAllUsers()).thenReturn(UserEntityTestData.getLstUser());
        Mockito.doNothing().when(scUserApiService).create(UserEntityTestData.createUser());
        Mockito.doNothing().when(scUserApiService).modify(UserEntityTestData.modifyeUser());
        Mockito.doNothing().when(scUserApiService).delete(userId);
        /** scServiceInstancesSpecifications

//        Mockito.when(scServiceInstancesSpecifications.findAll(organizationName, userId)).thenReturn(UserEntityTestData.getMapUser());
        Mockito.when(scUserService.getUser(emptyId)).thenReturn(UserEntityTestData.getEmptyUser());

        Mockito.when(scUserService.apiCreateUser(UserEntityTestData.createModifyUser())).thenReturn(responseEntity);

        Mockito.doNothing().when(scUserService).restDeleteUser(userId);
        Mockito.doThrow(new RuntimeException()).doCallRealMethod().doNothing().when(scUserService).restDeleteUser(userId);
        Mockito.doNothing().when(scUserService).restDeleteUser(userId);
        Mockito.doThrow(new RuntimeException()).when(scUserService).restUpdateUser("", UserEntityTestData.createModifyUser());*/

        Mockito.when(responseEntity.getBody()).thenReturn(lstUser);
    }

    @Test
    public void scmAdminSession() throws Exception {
        ScmClientSession rtnScmClientSession = commonService.scmAdminSession();
        Assert.assertEquals(scmClientSession, rtnScmClientSession);
    }

    @Test
    public void createRepositoryClientHandler() throws Exception {
        RepositoryClientHandler rtnRepositoryClientHandler = commonService.createRepositoryClientHandler();
        Assert.assertEquals(repositoryClientHandler, rtnRepositoryClientHandler);
    }
}