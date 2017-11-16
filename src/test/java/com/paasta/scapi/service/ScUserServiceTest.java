package com.paasta.scapi.service;

import com.paasta.scapi.common.util.RestClientUtil;
import com.paasta.scapi.entity.ScUser;
import com.paasta.scapi.repository.RepoPermissionRepository;
import com.paasta.scapi.repository.ScRepositoryRepository;
import com.paasta.scapi.repository.ScUserRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;

/**
 * create ScUserService
 * @author ijlee
 * @since 1.0
 * lee on 2017-08-02.
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ScUserServiceTest extends CommonServiceTest{

    @Before
    public void setUp() {
        setUpMockUtil();
    }

    @Test
    public void restDeleteUser() throws Exception {
        Mockito.doNothing().when(scUserService).restDeleteUser(userId);
    }
    @Test
    public void beforeSaveDBrestCreateUser() throws Exception {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("name","name");
        linkedHashMap.put("displayName","displayName");
        linkedHashMap.put("mail","mail");
//        Mockito.when(scUserService.beforeSaveDBrestCreateUser(linkedHashMap)doNothing().when(scUserService).restDeleteUser(userId);
    }

    @Test
    public void restCreateUser() throws Exception {
    }

    @Test
    public void restUpdateUser() throws Exception {
    }

    @Test
    public void getUser() throws Exception {
    }

    @Test
    public void getScmUser() throws Exception {
    }

    @Test
    public void restGetAllUsers() throws Exception {
    }

    @Test
    public void getUsers() throws Exception {
    }

    @Test
    public void apiCreateUser() throws Exception {
    }

    @Test
    public void apiGetUsers() throws Exception {
    }

    @Test
    public void apiGetUsersByName() throws Exception {
    }

    @Test
    public void getUsersByrepositoryId() throws Exception {
    }

}