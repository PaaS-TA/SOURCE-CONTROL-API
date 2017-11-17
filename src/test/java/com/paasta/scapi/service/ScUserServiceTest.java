package com.paasta.scapi.service;

import com.paasta.scapi.common.UserEntityTestData;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;
import java.util.Map;

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
    public void restUpdateUser() throws Exception {
        //TODO 수정할것.
        LinkedHashMap modifty = UserEntityTestData.createModifyUser();
//      Mockito.doNothing().when(scUserService).restUpdateUser(userId,modifty);
        Mockito.doThrow(new RuntimeException()).when(scUserService).restUpdateUser(userId,modifty);
    }
    @Test
    public void getExistUser() throws Exception {
        Map getUser = UserEntityTestData.getMapUser();
        Mockito.when(scUserService.getUser(userId)).thenReturn(getUser);
    }
    @Test
    public void getEmptyUser() throws Exception {
        Map getEmptyUser = UserEntityTestData.getEmptyUser();
        Mockito.when(scUserService.getUser(emptyId)).thenReturn(getEmptyUser);
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