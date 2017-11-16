package com.paasta.scapi.service;

import com.paasta.scapi.common.Constants;
import com.paasta.scapi.common.util.DateUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SEJI on 2017-10-16.
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RepoPermissionDBServiceTest extends CommonServiceTest{

    @Before
    public void setUp() {
        setUpMockUtil();
    }

    @Test
    public void searchPermisionByUserIdAndRepositoryId() throws Exception {

        rtnList = new ArrayList();
        Map map = new HashMap();
        map.put("userId",searchUserId);
        map.put("userName",userName);
        map.put("userEmail",userMail);
        map.put("userPermission",sRepoPermission);
        map.put("userPermissionNo",iRepoNo);
        rtnList.add(map);

       List<Map> mathRtnList = repoPermissionDBService.searchPermisionByUserIdAndRepositoryId(searchUserId,repoScmId);

       Assert.assertEquals(mathRtnList,rtnList);

    }
    @Test
    public void searchPermisionByUserIdAndInstanceId() throws Exception {
        rtnList = new ArrayList<>();
        Map map = new HashMap();
        map.put("no", getInstanceNo);
        map.put("userId", userId);
        map.put("userName", userName);
        map.put("userEmail", userMail);
        map.put("userRepoRole", userRepoRole );
        map.put("userCreateYn", userSearchCreateYn);
        map.put("userPermission", sRepoPermission);
        map.put("userPermissionNo", getPermissionNo);
        map.put("userModifiedDate",  DateUtil.rtnFormatString(Constants.DATE_FORMAT_1, userModifiedDate));
        map.put("userCreatedDate",  DateUtil.rtnFormatString(Constants.DATE_FORMAT_1, userCreatedDate));
        rtnList.add(map);
        List<Map> mathRtnList = repoPermissionDBService.searchPermisionByUserIdAndInstanceId(searchUserId,instanceId,repoScmId);
        Assert.assertEquals(mathRtnList,rtnList);
    }

}
