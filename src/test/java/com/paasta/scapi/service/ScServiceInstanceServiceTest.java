package com.paasta.scapi.service;

import com.paasta.scapi.common.ScServiceInstanceEntityTestData;
import com.paasta.scapi.entity.ScServiceInstance;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * create ScServiceInstanceServiceTest
 * @author ijlee
 * @since 1.0
 * lee on 2017-08-02.
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ScServiceInstanceServiceTest  extends CommonServiceTest{

    @Before
    public void setUp() {
        setUpMockUtil();
    }

    @Test
    public void getServiceInstanceList() throws Exception {

        List<ScServiceInstance>  list = this.scServiceInstanceRepository.findByCreateUserId(userId);
        Assert.assertEquals(ScServiceInstanceEntityTestData.getLstScServiceInstance(),list);
    }


//    @Test
//    public void geServiceInstanceListPage() throws Exception {
//        ServiceInstanceList resultList;
//        Page<ScServiceInstance> serviceInstanceListPage;
//
//        if (organizationName == null || "".equals(organizationName)) {
//            serviceInstanceListPage = scServiceInstanceRepository.findAll(pageRequest);
//        } else {
//            serviceInstanceListPage = scServiceInstanceRepository.findAll(ScServiceInstancesSpecifications.hasOrganizationName(organizationName), pageRequest);
//        }
//
////        resultList = (ServiceInstanceList) setPageInfo(serviceInstanceListPage, new ServiceInstanceList());
////        resultList.setServiceInstances(serviceInstanceListPage.getContent());
//        return resultList;;
//    }


}