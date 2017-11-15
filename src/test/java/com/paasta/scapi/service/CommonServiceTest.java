package com.paasta.scapi.service;

import com.paasta.scapi.common.ScmClientTest;
import com.paasta.scapi.common.util.PropertiesUtil;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sonia.scm.client.ScmClientSession;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class, classes = { PropertiesUtil.class })
@ActiveProfiles("test")
public class CommonServiceTest extends CommonService{

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


    //    private static final String clientSessionPassword = "clientSessionPassword";private static final String clientSessionId = "clientSessionId";
//    private static final String clientSessionPassword = "clientSessionPassword";private static final String clientSessionId = "clientSessionId";
//    private static final String clientSessionPassword = "clientSessionPassword";private static final String clientSessionId = "clientSessionId";
//    private static final String clientSessionPassword = "clientSessionPassword";private static final String clientSessionId = "clientSessionId";
//    private static final String clientSessionPassword = "clientSessionPassword";private static final String clientSessionId = "clientSessionId";
//    private static final String clientSessionPassword = "clientSessionPassword";private static final String clientSessionId = "clientSessionId";
//    private static final String clientSessionPassword = "clientSessionPassword";private static final String clientSessionId = "clientSessionId";
//    private static final String clientSessionPassword = "clientSessionPassword";private static final String clientSessionId = "clientSessionId";
//    private static final String clientSessionPassword = "clientSessionPassword";private static final String clientSessionId = "clientSessionId";
//    private static final String clientSessionPassword = "clientSessionPassword";

    @Mock CommonService commonService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        commonService = new CommonService();
    }

    @Autowired
    private PropertiesUtil propertiesUtil;

    @Override
    ScmClientSession scmAdminSession(){
        return new ScmClientTest().createSession(propertiesUtil.getBaseUrl(),clientSessionId, clientSessionPassword);
    }
}