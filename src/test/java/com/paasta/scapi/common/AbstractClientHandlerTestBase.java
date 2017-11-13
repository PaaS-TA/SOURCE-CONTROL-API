package com.paasta.scapi.common;

//~--- non-JDK imports --------------------------------------------------------

import org.junit.Test;
import sonia.scm.ModelObject;
import sonia.scm.client.ClientHandler;
import sonia.scm.client.JerseyClientSession;
import sonia.scm.client.ScmForbiddenException;
import sonia.scm.client.ScmUnauthorizedException;

import static com.paasta.scapi.common.ClientTestUtil.*;
import static org.junit.Assert.*;

/**
 *
 * @author Sebastian Sdorra
 *
 * @param <T>
 */
public abstract class AbstractClientHandlerTestBase<T extends ModelObject>
{

    /**
     * Method description
     *
     *
     * @param session
     *
     * @return
     */
    protected abstract ClientHandler<T> createHandler(
            JerseyClientSession session);

    /**
     * Method description
     *
     *
     * @return
     */
    protected abstract ModifyTest<T> createModifyTest();

    /**
     * Method description
     *
     *
     * @param number
     *
     * @return
     */
    protected abstract T createTestData(int number);

    /**
     * Method description
     *
     *
     */
    @Test
    public void testCreate()
    {
        JerseyClientSession session = createAdminSession();
        T item = createTestData(1);
        ClientHandler<T> handler = createHandler(session);

        handler.create(item);
        assertIsValid(item);

        String id = item.getId();
        T o = handler.get(id);

        assertNotNull(o);
        assertEquals(item.getId(), o.getId());
        session.close();
    }

    /**
     * Method description
     *
     *
     */
    @Test
    public void testDelete()
    {
        JerseyClientSession session = createAdminSession();
        T item = createTestData(2);
        ClientHandler<T> handler = createHandler(session);

        handler.create(item);
        assertIsValid(item);

        String id = item.getId();

        handler.delete(item);

        T o = handler.get(id);

        assertNull(o);
    }

    /**
     * Method description
     *
     *
     */
    @Test(expected = ScmUnauthorizedException.class)
    public void testDisabledCreateAnonymous()
    {
        JerseyClientSession session = createAnonymousSession();
        T item = createTestData(3);

        createHandler(session).create(item);
        session.close();
    }

    /**
     * Method description
     *
     *
     */
    @Test
    public void testEnabledCreateAnonymous()
    {
        setAnonymousAccess(true);

        JerseyClientSession session = createAnonymousSession();
        T item = createTestData(3);
        boolean forbidden = false;

        try
        {
            createHandler(session).create(item);
        }
        catch (ScmForbiddenException ex)
        {
            forbidden = true;
        }

        assertTrue(forbidden);
        session.close();
        setAnonymousAccess(false);
    }

    /**
     * Method description
     *
     */
    @Test
    public void testEnabledModifyAnonymous()
    {
        setAnonymousAccess(true);

        JerseyClientSession session = createAdminSession();
        T item = createTestData(4);

        createHandler(session).create(item);
        assertIsValid(item);
        session.close();
        session = createAnonymousSession();

        ModifyTest<T> mt = createModifyTest();

        mt.modify(item);

        boolean notfound = false;

        try
        {
            createHandler(session).modify(item);
        }
        catch (ScmForbiddenException ex)
        {
            notfound = true;
        }

        setAnonymousAccess(false);
        session.close();
        session = createAdminSession();
        createHandler(session).delete(item);
        session.close();
        assertTrue(notfound);
    }

    /**
     * Method description
     *
     */
    @Test
    public void testModify()
    {
        long start = System.currentTimeMillis();
        JerseyClientSession session = createAdminSession();
        T item = createTestData(4);
        ClientHandler<T> handler = createHandler(session);

        handler.create(item);
        assertIsValid(item);
        item = handler.get(item.getId());

        ModifyTest<T> mt = createModifyTest();

        mt.modify(item);
        handler.modify(item);
        item = handler.get(item.getId());
        assertIsValid(item);
        assertTrue(mt.isCorrectModified(item));
        assertNotNull(item.getLastModified());
        assertTrue(item.getLastModified() > start);
        assertTrue(item.getLastModified() < System.currentTimeMillis());
        handler.delete(item);
        session.close();
    }

    /**
     * Method description
     *
     *
     * @param item
     */
    protected void assertIsValid(T item)
    {
        assertNotNull(item);
        assertNotNull(item.getId());
        assertTrue(item.getId().length() > 0);
    }

    //~--- inner interfaces -----------------------------------------------------

    /**
     * Interface description
     *
     *
     * @param <T>
     *
     * @version        Enter version here..., 11/05/13
     * @author         Enter your name here...
     */
    protected interface ModifyTest<T>
    {

        /**
         * Method description
         *
         *
         * @param item
         */
        public void modify(T item);

        //~--- get methods --------------------------------------------------------

        /**
         * Method description
         *
         *
         * @param item
         *
         * @return
         */
        public boolean isCorrectModified(T item);
    }
}