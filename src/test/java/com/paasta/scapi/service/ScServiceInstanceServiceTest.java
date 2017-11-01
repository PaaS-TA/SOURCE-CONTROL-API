package com.paasta.scapi.service;

import com.paasta.scapi.entity.ScServiceInstance;
import com.paasta.scapi.repository.ScServiceInstanceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;


import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScServiceInstanceServiceTest {

    /**
     * SCM Server connection Session Information
     */

    @Mock
    ScServiceInstanceRepository scServiceInstanceRepository;

    @Mock
    ScServiceInstanceService scServiceInstanceService;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAll() throws Exception {
        List<ScServiceInstance> list = mock(List.class);
        scServiceInstanceRepository.findAll();
    }

    @Test
    public void getServiceInstanceList() throws Exception {
        //String organizationName, Pageable pageable
        Page<ScServiceInstance> serviceInstanceListPage = mock(Page.class);
        scServiceInstanceService.getServiceInstanceList("");

        //String creqteUserId
        scServiceInstanceRepository.findAll();
    }

}