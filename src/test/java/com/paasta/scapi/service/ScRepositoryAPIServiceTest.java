package com.paasta.scapi.service;

import com.paasta.scapi.common.util.PropertiesUtil;
import com.paasta.scapi.common.util.RestClientUtil;
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

@RunWith(MockitoJUnitRunner.class)
public class ScRepositoryAPIServiceTest {

    /**
     * SCM Server connection Session Information
     */

    @InjectMocks
    RestClientUtil restClientUtil;

    @InjectMocks
    PropertiesUtil propertiesUtil;

    @Mock
    ScRepositoryDBService scRepositoryDBService;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void scmRepositoryByNameTypePrivate() throws Exception {
        List<ScServiceInstance> list = mock(List.class);
}


    @Test
    public void getUserRepositories() throws Exception {
    }

    @Test
    public void getRepositoryByIdApi() throws Exception {
    }

    @Test
    public void createRepositoryApi() throws Exception {
    }

    @Test
    public void deleteRepositoryApi() throws Exception {
    }

    @Test
    public void getAdminRepositories() throws Exception {
    }

    @Test
    public void scmRepositoryByNameType() throws Exception {
    }

    @Test
    public void getRepositoryByInstanceId() throws Exception {
    }

    @Test
    public void scmCreateRepository() throws Exception {
    }

    @Test
    public void getBranches() throws Exception {
    }

    @Test
    public void getTags() throws Exception {
    }

    @Test
    public void getBrowseByParam() throws Exception {
    }

    @Test
    public void getChangesets() throws Exception {
    }

    @Test
    public void updateRepository() throws Exception {
    }

    @Test
    public void getContent() throws Exception {
    }
}