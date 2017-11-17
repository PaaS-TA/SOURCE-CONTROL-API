package com.paasta.scapi.common;

import com.paasta.scapi.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import sonia.scm.SCMContextProvider;
import sonia.scm.user.User;

import java.io.File;
import java.util.*;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class MockUtil
{
    //TODO confing로 바꿀 부분
    /**
     * static final validation
     * then service excute then  not changed
     */

    private static final String clientSessionId = "clientSessionId";
    private static final String clientSessionPassword = "clientSessionPassword";
    public static final String scmUrl = "scmUrl";
    public static final String scmAdminId = "scmAdminId";
    public static final String scmAdminPassword = "scmAdminPassword";
    public static final int iSuccess = 1;
    public static final int iFail = 0;
    public static final int repoNo = 0;
    public static final  String repoScmId = "repoScmId";
    public static final  String repoName = "repoName";
    public static final  String repoDesc = "repoDesc";
    public static final  String instanceId = "instanceId";
    public static final  String ownerUserId = "ownerUserId";
    public static final  String createUserId = "createUserId";
    public static final  String searchUserId = "userId";
    public static final  String adminId = "adminId";
    public static final  String adminPassword = "adminPassword";
    public static final  String userId = "userId";
    public static final  String emptyId = "emptyId";
    public static final  String userName = "userName";
    public static final  String userMail = "userMail";
    public static final  String userDesc = "userDesc";
    public static final  int iRepoNo= 0;
    public static final  int getInstanceNo = 0;
    public static final  int getInstanceId = 0;
    public static final  int getPermissionNo = 0;
    public static final  String sRepoPermission = "repoPermission";
    public static final  String userRepoRole = "userRepoRole";
    public static final  String userCreateYn = "userCreateYn";
    public static final  String userSearchCreateYn = "";
    public static final  Date userCreatedDate = new Date();
    public static final  Date userModifiedDate = new Date();
    public static final  String organizationGuid = "organizationGuid";
    public static final  String organizationName = "organizationName";
    public static final  String planId = "planId";
    public static final  String serviceId = "serviceId";
    public static final  String spaceGuid = "spaceGuid";
    public static final  String sCreatedDate = "sCreatedDate";
    public static final  String sActive = "true";
    public static final  PageRequest pageRequest =  new PageRequest(0, 1) ;

    /**
     * service excute end then
     * return List
     */

    public static List<ScRepository> lstScRepository = new ArrayList();
    public static List<ScUser> lstScUserBefore  = new ArrayList();
    public static List<ScUser> lstScUser = new ArrayList();
    public static List<sonia.scm.user.User> lstUser = new ArrayList();
    public static List<RepoPermission> lstRepoPermission = new ArrayList();
    public static List<ScInstanceUser> lstScInstanceUsers = new ArrayList();
    public static List<ScServiceInstance> lstScServiceInstance = new ArrayList();
    public static Page<ScServiceInstance> pageScServiceInstance = new PageImpl<ScServiceInstance>(lstScServiceInstance);
    public static List<Map> rtnList = new ArrayList();


    /** Field description */
    private static final User ADMIN = new User("scmadmin", "SCM Admin",
            "scmadmin@scm.org");

    public static SCMContextProvider getSCMContextProvider(File directory)
    {
        SCMContextProvider provider = mock(SCMContextProvider.class);

        when(provider.getBaseDirectory()).thenReturn(directory);

        return provider;
    }
}