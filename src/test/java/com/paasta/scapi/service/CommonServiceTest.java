package com.paasta.scapi.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import sonia.scm.client.ScmClient;

import javax.servlet.http.HttpSession;

/**
 * Created by ij
 * lee on 2017-08-02.
 */

@RunWith(MockitoJUnitRunner.class)
public class CommonServiceTest {

    /**
     * SCM Server connection Session Information
     */

    @InjectMocks
    CommonService CommonService;

    @Mock
    private HttpSession session;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Value("${api.base.url}")  private String url;
    @Value("${admin.id}")  private String username;
    @Value("${admin.pwd}")  private String password;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void scmClientSession() throws Exception {
        Mockito.doReturn("ok").when(session).getAttribute("");
       // Mockito.doReturn(ScmClient.createSession("","",""));
        /*
        MockHttpServletRequest request = new MockHttpServletRequest();
         RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         */
    }

    @Test
    public void setPageInfo() throws Exception {
        //Page reqPage, Object reqObject
    }
}