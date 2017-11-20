package com.paasta.scapi.service;

import com.paasta.scapi.common.UserEntityTestData;
import com.paasta.scapi.common.util.PropertiesUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sonia.scm.user.User;

import static org.junit.Assert.assertEquals;
/**
 * Created by ijlee on 2017-09-04.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class, classes = { PropertiesUtil.class })
@ActiveProfiles("test")
public class ScLoginServiceTest extends CommonServiceTest{

    @Autowired
    private PropertiesUtil propertiesUtil;

    @Before
    public void setUp() {
        setUpMockUtil();
    }

    @Test
    public void login_200() {

//        User user = UserEntityTestData.getUser();
        User user = UserEntityTestData.getAdminUser();
        ResponseEntity rtnResponseEntity = scLoginService.login(user, null);
        assertEquals(rtnResponseEntity.getStatusCode(),HttpStatus.OK);
    }

    @Test
    public void login_403() {

        User user = UserEntityTestData.getUser();
        ResponseEntity rtnResponseEntity = scLoginService.login(user, null);
        assertEquals(rtnResponseEntity.getStatusCode(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}