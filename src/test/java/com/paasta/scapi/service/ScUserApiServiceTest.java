package com.paasta.scapi.service;

import com.paasta.scapi.common.UserEntityTestData;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.junit4.SpringRunner;
import sonia.scm.user.User;

import java.util.List;

/**
 * create ScUserService
 * @author ijlee
 * @since 1.0
 * lee on 2017-08-02.
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ScUserApiServiceTest extends CommonServiceTest {

    @Test
    public void getScmUser() throws Exception {
        User getUser = UserEntityTestData.getUser();
        User rtnUser = scUserApiService.getScmUser(userId);
        Assert.assertEquals(getUser, rtnUser);
    }

    @Test
    public void create() throws Exception {
       User createUser = UserEntityTestData.createUser();
       scUserApiService.create(createUser);
       Assert.assertNotNull(createUser);
    }

    @Test
    public void modify() throws Exception {
        User modifyUser = UserEntityTestData.modifyeUser();
        scUserApiService.modify(modifyUser);
        Assert.assertNotNull(modifyUser);
    }

    @Test
    public void delete() throws Exception {
        scUserApiService.delete(userId);
        Assert.assertNotNull(userId);
    }

    @Test
    public void restGetAllUsers() throws Exception {
        List<User> lst =  UserEntityTestData.getLstUser();
        List<User> rtnUser = scUserApiService.restGetAllUsers();
        Assert.assertEquals(lst, rtnUser);
    }

}