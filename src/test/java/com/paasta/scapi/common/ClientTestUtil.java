package com.paasta.scapi.common;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.multipart.impl.MultiPartWriter;
import sonia.scm.ScmState;
import sonia.scm.client.ClientUtil;
import sonia.scm.client.JerseyClientProvider;
import sonia.scm.client.JerseyClientSession;
import sonia.scm.config.ScmConfiguration;
import sonia.scm.url.UrlProvider;
import sonia.scm.url.UrlProviderFactory;
import sonia.scm.user.User;

import static org.mockito.Mockito.mock;

//~--- JDK imports ------------------------------------------------------------
/**
 *
 * @author Sebastian Sdorra
 */
public final class ClientTestUtil
{

    /** Field description */
    public static final String ADMIN_PASSWORD = "scmadmin";

    /** Field description */
    public static final String ADMIN_USERNAME = "scmadmin";

    /** Field description */
    public static final String REPOSITORY_TYPE = "git";

    /** Field description */
    public static final String URL_BASE = "http://localhost:8081/scm";

    /** Field description */
    public static final boolean REQUEST_LOGGING = false;

    private ClientTestUtil()
    {
    }




    //~--- methods --------------------------------------------------------------

    /**
     * Method description
     *
     *
     * @return
     *
     */
    public static JerseyClientSession createAdminSession()
    {
        return createSession(ADMIN_USERNAME, ADMIN_PASSWORD);
    }

    /**
     * Method description
     *
     *
     * @return
     *
     */
    public static JerseyClientSession createAnonymousSession()
    {
        return createSession(null, null);
    }

    /**
     * Method description
     *
     *
     * @param username
     * @param password
     *
     * @return
     *
     */
    public static JerseyClientSession createSession(String username,
                                                    String password)
    {
        JerseyClientProvider provider = new JerseyClientProvider(REQUEST_LOGGING);
        UrlProvider urlProvider = UrlProviderFactory.createUrlProvider(URL_BASE,
                UrlProviderFactory.TYPE_RESTAPI_XML);
        Client client =Client.create(new DefaultClientConfig(MultiPartWriter.class));
        User admin = new User(username);
        admin.setPassword(password);
        ScmState state = createAdminScmState();
        state.setUser(admin);
        JerseyClientSession jerseyClientSession = new JerseyClientSession(client, urlProvider,state);

        return jerseyClientSession;
    }

    //~--- set methods ----------------------------------------------------------

    /**
     * Method description
     *
     *
     * @param access
     *
     */
    public static void setAnonymousAccess(boolean access)
    {
        JerseyClientSession adminSession = createAdminSession();
        UrlProvider up = adminSession.getUrlProvider();
        Client client = adminSession.getClient();
        WebResource resource = ClientUtil.createResource(client, up.getConfigUrl(),
                REQUEST_LOGGING);
        ScmConfiguration config = resource.get(ScmConfiguration.class);

        config.setAnonymousAccessEnabled(access);
        resource.post(config);
        adminSession.close();
    }


    public static ScmState createAdminScmState()
    {
        /*
        SCMContextProvider provider = mock(SCMContextProvider.class);
        User user = mock(User.class);
        Collection<String> groups = mock(Collection.class);
        Collection<Type> repositoryTypes= mock(Collection.class);
        String defaultUserType = mock(String.class);
        ScmClientConfig clientConfig = mock(ScmClientConfig.class);
        List<String> assignedPermission = mock(List.class);
        List<PermissionDescriptor> availablePermissions = mock(List.class);

        return provider,user,groups, groups, repositoryTypes, defaultUserType, clientConfig, assignedPermission, availablePermissions);
 */
        ScmState rtnScmState = mock(ScmState.class);
        return rtnScmState;
    }
}