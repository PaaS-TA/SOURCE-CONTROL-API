package com.paasta.scapi.service;

import com.paasta.scapi.common.util.RestClientUtil;
import com.paasta.scapi.entity.ScUser;
import com.paasta.scapi.repository.RepoPermissionRepository;
import com.paasta.scapi.repository.ScRepositoryRepository;
import com.paasta.scapi.repository.ScUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by ij
 * lee on 2017-08-02.
 */
//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringRunner.class)
public class ScUserServiceImplTest {

    @InjectMocks
    ScUserService scUserService;

    @Mock
    ScUserRepository scUserRepository;
    @Mock
    ScRepositoryRepository scRepositoryRepository;

    @Mock
    RepoPermissionRepository repoPermissionRepository;

    @InjectMocks
    RestClientUtil restClientUtil;

    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAll() throws Exception {
        Mockito.when(this.scUserService.getAll()).thenReturn(null);
    }

    @Test
    public void save() throws Exception {
        ScUser scUser = new ScUser("useId","uerName", "userEmail");
        Mockito.when(this.scUserService.save(scUser)).thenReturn(scUser);
    }

/**
    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(scUserService).delete("userId");
    }
/**
    @Test
    public void restDeleteUser() throws Exception {
        assertEquals(scUserService.restDeleteUser("name"),"");
    }
    @Test
    public void beforeSaveDBrestCreateUser() throws Exception {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("name","name");
        linkedHashMap.put("displayName","displayName");
        linkedHashMap.put("mail","mail");
        assertEquals(scUserService.beforeSaveDBrestCreateUser(linkedHashMap),"");
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
    */

}