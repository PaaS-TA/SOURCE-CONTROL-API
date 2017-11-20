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
import sonia.scm.client.ScmUnauthorizedException;
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

        User user = UserEntityTestData.getAdminUser();
        ResponseEntity rtnResponseEntity = scLoginService.login(user, null);
        assertEquals(rtnResponseEntity.getStatusCode(),HttpStatus.OK);
    }

    @Test(expected = ScmUnauthorizedException.class)
    public void login_401() throws Exception{
        User user = UserEntityTestData.getUser();
        scLoginService.login(user, null);
    }

    @Test(expected = RuntimeException.class)
    public void login_500() {
        User user = null;
        scLoginService.login(user, null);
    }
}