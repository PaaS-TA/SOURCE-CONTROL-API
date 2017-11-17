package com.paasta.scapi.service;

import com.paasta.scapi.common.UserEntityTestData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
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
        String userId = "userId";
        scUserService.restDeleteUser(userId);
//      Mockito.doNothing().when(scUserService).restUpdateUser(userId,modifty);
        Assert.assertNotNull(userId);
    }

    @Test
    public void restUpdateUser() throws Exception {
        LinkedHashMap modifyUser = UserEntityTestData.createModifyUser();
        scUserService.apiCreateUser(modifyUser);
        Assert.assertNotNull(modifyUser);
    }
    @Test
    public void getNotExistUser() throws Exception {
        Map getEmptyUser = UserEntityTestData.getEmptyUser();
        Map rtnUser = scUserService.getUser(emptyId);
        Assert.assertEquals(getEmptyUser, rtnUser);
    }
    @Test
    public void getExistUser() throws Exception {
        Map getUser = UserEntityTestData.getMapUser();
        Map rtnUser = scUserService.getUser(userId);
        Assert.assertEquals(getUser, rtnUser);
    }


    @Test
    public void getUsers() throws Exception {
        Map getMapUser = UserEntityTestData.getMapAllUsers();
        Map resultMap = scUserService.getUsers();
        Assert.assertEquals(getMapUser, resultMap);
    }

    @Test
    public void apiCreateUser() throws Exception {
        LinkedHashMap jsonUser = UserEntityTestData.createModifyUser();
        ResponseEntity actualResponseEntity = new ResponseEntity(HttpStatus.OK);
        ResponseEntity rtn = scUserService.apiCreateUser(jsonUser);
        Assert.assertEquals(actualResponseEntity, rtn);
    }

    @Test
    public void getUsersByrepositoryId() throws Exception {
        ResponseEntity actRss = UserEntityTestData.getUsersByrepositoryId();
        ResponseEntity<Map> rtnrss = scUserService.getUsersByrepositoryId(repoScmId, searchUserId, sRepoPermission, sActive, pageRequest);
        Assert.assertEquals(actRss, rtnrss);
    }

}