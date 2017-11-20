package com.paasta.scapi.common;

import org.junit.Ignore;
import sonia.scm.client.ScmClientException;
import sonia.scm.client.ScmClientProvider;
import sonia.scm.client.ScmClientSession;
import sonia.scm.util.ServiceUtil;
@Ignore
public final class ScmClientTest {

    /** Field description */
    private static volatile ScmClientProvider provider = null;

    //~--- constructors ---------------------------------------------------------

    /**
     * Constructs ...
     *
     */
    public ScmClientTest() {}

    //~--- methods --------------------------------------------------------------

    /**
     * Creates an ScmClientSession for the given user
     *
     *
     * @param url
     * @param username
     * @param password
     *
     * @return
     *
     * @throws ScmClientException
     */
    public static ScmClientSession createSession(String url, String username,
                                                 String password)
            throws ScmClientException
    {
        return getProvider().createSession(url, username, password);
    }

    /**
     * Creates an anonymous ScmClientSession
     *
     *
     * @param url
     *
     * @return
     *
     * @throws ScmClientException
     */
    public static ScmClientSession createSession(String url)
            throws ScmClientException
    {
        return getProvider().createSession(url, null, null);
    }

    //~--- get methods ----------------------------------------------------------

    /**
     * Method description
     *
     *
     * @return
     *
     */
    private static ScmClientProvider getProvider()
    {
        if (provider == null)
        {
            synchronized (ScmClientProvider.class)
            {
                if (provider == null)
                {
                    provider = ServiceUtil.getService(ScmClientProvider.class);
                }
            }
        }

        if (provider == null)
        {
            throw new ScmClientException("could not find a ScmClientProvider");
        }
        return provider;
    }
}
