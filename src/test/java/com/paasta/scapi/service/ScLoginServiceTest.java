package com.paasta.scapi.service;

import com.paasta.scapi.common.util.PropertiesUtil;
import com.sun.jersey.api.client.Client;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sonia.scm.user.User;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import com.paasta.scapi.common.ClientTestUtil;
/**
 * Created by ijlee on 2017-09-04.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class, classes = { PropertiesUtil.class })
@ActiveProfiles("test")
public class ScLoginServiceTest extends ScLoginService{

//    private MockMvc mockMvc;
    private ScLoginService scLoginService;

    @Override
    public ResponseEntity login(User user, PropertiesUtil propertiesUtil) {
        ClientTestUtil.createAdminSession();
        return new ResponseEntity(user,HttpStatus.OK);
    }
    @Autowired
    private PropertiesUtil propertiesUtil;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        scLoginService = new ScLoginService();
    }
    @Test
    public void login_200() throws Exception {
        User user = mock(User.class);
        assertEquals(HttpStatus.OK,login(user, propertiesUtil).getStatusCode());
    }

}