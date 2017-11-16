package com.paasta.scapi.service;

import com.paasta.scapi.common.ClientTestUtil;
import com.paasta.scapi.common.util.PropertiesUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sonia.scm.client.JerseyClientSession;
import sonia.scm.client.ScmClientSession;
import sonia.scm.client.ScmUnauthorizedException;
import sonia.scm.client.UserClientHandler;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class, classes = { PropertiesUtil.class })
@ActiveProfiles("test")
public class CommonServiceTest{

    private static final String clientSessionId = "clientSessionId";
    private static final String clientSessionPassword = "clientSessionPassword";
    public static final String scmUrl = "scmUrl";
    public static final String scmAdminId = "scmAdminId";
    public static final String scmAdminPassword = "scmAdminPassword";
    public static final int repoNo = 0;
    public static final  String repoScmId = "repoScmId";
    public static final  String repoName = "repoName";
    public static final  String repoDesc = "repoDesc";
    public static final  String instanceId = "instanceId";
    public static final  String ownerUserId = "ownerUserId";
    public static final  String createUserId = "createUserId";
    public static final  String userId = "userId";
    public static final  String userName = "userName";
    public static final  String userMail = "userMail";
    public static final  String userDesc = "userDesc";
    public static final  int iRepoNo= 0;
    public static final  String sRepoPermission = "repoPermission";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @MockBean
    ScmClientSession scmClientSession;

    @MockBean
    UserClientHandler userClientHandler;

   /* @Mock
    JerseyClientSession jerseyClientSession;
*/
   /* @Mock
    Client client;*/

 /*   @Mock
    WebResource webResource;

    @Mock
    ClientResponse clientResponse;*/

    @InjectMocks
    private
    CommonService commonService;

    @Before
    public void setUp() {
//        MockitoAnnotations.initMocks(this);
/*        client = new Client();
        webResource = client.resource(ClientTestUtil.resourceUrl);
        webResource.accept("application/json");
        webResource.post();
        clientResponse = webResource.post(ClientResponse.class);
        clientResponse.setStatus(ClientResponse.Status.OK);*/

//        scmClientSession = ClientTestUtil.createAdminSession();
//        jerseyClientSession =  ClientTestUtil.createAdminSession();
//        scmClientSession = jerseyClientSession;
//        Mockito.when(ScmClient.createSession(scmUrl, scmAdminId, scmAdminPassword)).thenReturn(jerseyClientSession);
        Mockito.when(commonService.scmAdminSession()).thenReturn(scmClientSession);
    }

    /*@Test(expected = ScmUnauthorizedException.class)
    public void createSessionAnonymousFailedTest()
    {
        ClientTestUtil.createAnonymousSession().close();
    }*/
    @Test
    public void scmAdminSession() throws Exception {
        ScmClientSession rtnScmClientSession = commonService.scmAdminSession();
//        scmClientSession = ClientTestUtil.createAdminSession();
        Assert.assertEquals(commonService.scmAdminSession(),scmClientSession);
    }


}