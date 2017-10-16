package com.paasta.scapi.service;

import com.paasta.scapi.common.PropertiesUtilTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import sonia.scm.user.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Created by ijlee on 2017-09-04.
 */
@RunWith(MockitoJUnitRunner.class)

public class ScLoginServiceTest {

    @InjectMocks
    private ScLoginService scLoginService;
    @Autowired
    private PropertiesUtilTest propertiesUtilTest;
    @Value("${admin.id}") String admin_id;
    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void login_500() throws Exception {
        User user = mock(User.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,scLoginService.login(user).getStatusCode());
    }

    @Test
    public void login_200() throws Exception {

        User user = new User("a");
        user.setPassword("a");

        assertNotNull(scLoginService.login(user).getStatusCode());
    }

}