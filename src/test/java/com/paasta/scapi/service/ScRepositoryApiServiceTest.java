package com.paasta.scapi.service;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ScRepositoryApiServiceTest extends CommonServiceTest{

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
        scRepositoryApiService.deleteRepositoryApi(repoScmId);
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