package com.paasta.scapi.common;
import com.paasta.scapi.entity.ScInstanceUser;
import com.paasta.scapi.entity.ScServiceInstance;

import java.util.ArrayList;
import java.util.List;

public final class ScServiceInstanceEntityTestData
{

    private ScServiceInstanceEntityTestData() {}

    public static ScServiceInstance createScInstanceUser()
    {
        ScServiceInstance scServiceInstance = new ScServiceInstance(MockUtil.instanceId, MockUtil.organizationGuid, MockUtil.organizationName, MockUtil.planId, MockUtil.seryiceId, MockUtil.spaceGuid, MockUtil.createUserId);
        scServiceInstance.setCreatedTime(MockUtil.sCreatedDate);
        return scServiceInstance;
    }

    public static List<ScServiceInstance> getLstScServiceInstance()
    {
        List<ScServiceInstance> rtnList = new ArrayList();
        rtnList.add(createScInstanceUser());
        return rtnList;
    }

}