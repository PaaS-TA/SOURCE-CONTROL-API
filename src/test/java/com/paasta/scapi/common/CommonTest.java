package com.paasta.scapi.common;

import org.junit.Test;
import sonia.scm.user.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by ijlee on 2017-08-02.
 */
public class CommonTest {
    @Test
    public void diffDay() throws Exception {
        Date date = mock(Date.class);
        Date accessDate = mock(Date.class);
        assertTrue(Common.diffDay(date, accessDate)>=0);
    }

    @Test
    public void stringNullCheck() throws Exception {
        String string = null;
        assertFalse(Common.stringNullCheck(string));
    }

    @Test
    public void empty() throws Exception {
        Object object = null;
        assertTrue(Common.empty(object));
    }

    @Test
    public void notEmpty() throws Exception {
        Object object = mock(Object.class);
        assertTrue(Common.notEmpty(object));
    }

    @Test
    public void notNullrtnByobj() throws Exception {
        Object object = null;
        assertTrue(Common.notNullrtnByobj(object, "true").equals("true"));
    }

    @Test
    public void rtnMapByRequestParam() throws Exception {
        HttpServletRequest object = mock(HttpServletRequest.class);

        assert(Common.rtnMapByRequestParam(object)!=null);
    }

    @Test
    public void getUserByUserName() throws Exception {
        String username = "username";
        List<User> lstUser = new ArrayList();
        lstUser.add(new User(username));
        assertEquals(Common.getUserByUserName(username,lstUser), new User(username));
    }

}