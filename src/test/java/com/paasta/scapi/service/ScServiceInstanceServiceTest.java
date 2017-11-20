package com.paasta.scapi.service;

import com.paasta.scapi.entity.ScServiceInstance;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.Mockito.mock;

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
    public void getAll() throws Exception {
        List<ScServiceInstance> list = mock(List.class);
        scServiceInstanceRepository.findAll();
    }

    @Test
    public void getServiceInstanceListC() throws Exception {
        //String organizationName, Pageable pageable
        Page<ScServiceInstance> serviceInstanceListPage = mock(Page.class);
        scServiceInstanceService.getServiceInstanceList("");

        //String creqteUserId
        scServiceInstanceRepository.findAll();
    }

}