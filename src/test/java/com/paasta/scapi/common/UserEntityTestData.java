package com.paasta.scapi.common;
import com.paasta.scapi.entity.ScUser;
import sonia.scm.user.User;

import java.util.ArrayList;
import java.util.List;

public final class UserEntityTestData
{

    private UserEntityTestData() {}

    public static ScUser createScUser()
    {
        return new ScUser(MockUtil.userId, MockUtil.userName, MockUtil.userMail, MockUtil.userDesc);
    }

    public static User createUser()
    {
        return new User(MockUtil.userId, MockUtil.userName, MockUtil.userMail);
    }

    public static List<ScUser> getLstScUser()
    {
        List<ScUser> rtnList = new ArrayList();
        rtnList.add(createScUser());
        return rtnList;
    }

    public static List<User> getLstUser()
    {
        List<User> rtnList = new ArrayList();
        rtnList.add(createUser());
        return rtnList;
    }
}