package com.paasta.scapi.common;

import com.paasta.scapi.entity.*;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.Subject.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;
import sonia.scm.SCMContextProvider;
import sonia.scm.security.Role;
import sonia.scm.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public static final  String seryiceId = "seryiceId";
    public static final  String spaceGuid = "spaceGuid";
    public static final  String sCreatedDate = "sCreatedDate";

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

    @SuppressWarnings("unchecked")
    public static Subject createAdminSubject()
    {
        Subject subject = mock(Subject.class);

        when(subject.isAuthenticated()).thenReturn(Boolean.TRUE);
        when(subject.isPermitted(anyListOf(Permission.class))).then(
                invocation -> {
                    List<Permission> permissions =
                            (List<Permission>) invocation.getArguments()[0];
                    Boolean[] returnArray = new Boolean[permissions.size()];

                    Arrays.fill(returnArray, Boolean.TRUE);

                    return returnArray;
                });
        when(subject.isPermitted(any(Permission.class))).thenReturn(Boolean.TRUE);
        when(subject.isPermitted(any(String.class))).thenReturn(Boolean.TRUE);
        when(subject.isPermittedAll(anyCollectionOf(Permission.class))).thenReturn(
                Boolean.TRUE);
        when(subject.isPermittedAll()).thenReturn(Boolean.TRUE);
        when(subject.hasRole(Role.ADMIN)).thenReturn(Boolean.TRUE);
        when(subject.hasRole(Role.USER)).thenReturn(Boolean.TRUE);

        PrincipalCollection collection = mock(PrincipalCollection.class);

        when(collection.getPrimaryPrincipal()).thenReturn(ADMIN.getId());
        when(collection.oneByType(User.class)).thenReturn(ADMIN);

        when(subject.getPrincipal()).thenReturn(ADMIN.getId());
        when(subject.getPrincipals()).thenReturn(collection);

        return subject;
    }

    public static Subject createUserSubject()
    {
        return createUserSubject(null);
    }


    public static Subject createUserSubject(
            org.apache.shiro.mgt.SecurityManager securityManager)
    {
        SimplePrincipalCollection collection = new SimplePrincipalCollection();
        User user = UserTestData.createTrillian();

        collection.add(user.getName(), "junit");
        collection.add(user, "junit");

        Builder builder;

        if (securityManager != null)
        {
            builder = new Subject.Builder(securityManager);
        }
        else
        {
            builder = new Subject.Builder();
        }

        return builder.principals(collection).authenticated(true).buildSubject();
    }


    public static HttpServletRequest getHttpServletRequest()
    {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getContextPath()).thenReturn("/scm-webapp");

        return request;
    }

    public static HttpServletResponse getHttpServletResponse()
    {
        return mock(HttpServletResponse.class);
    }


    public static SCMContextProvider getSCMContextProvider(File directory)
    {
        SCMContextProvider provider = mock(SCMContextProvider.class);

        when(provider.getBaseDirectory()).thenReturn(directory);

        return provider;
    }
}