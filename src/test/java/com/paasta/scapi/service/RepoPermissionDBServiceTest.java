package com.paasta.scapi.service;

import com.paasta.scapi.entity.RepoPermission;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;

/**
 * Created by SEJI on 2017-10-16.
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RepoPermissionDBServiceTest {
    @Mock
    private
    RepoPermissionDBService repoPermissionDBService;

    @Test
    public void delete() throws Exception {
        RepoPermission repoPermission = mock(RepoPermission.class);
    }

    @Test
    public void searchPermisionByUserIdAndRepositoryId() throws Exception {
        String searchUserId ="";
        String repoId="" ;
        repoPermissionDBService.searchPermisionByUserIdAndRepositoryId(searchUserId, repoId);
    }

    @Test
    public void searchPermisionByUserIdAndInstanceId() throws Exception {
        String searchUserId = "";
        String instanceId = "";
        String repositoryId = "";
        repoPermissionDBService.searchPermisionByUserIdAndInstanceId(searchUserId,instanceId,repositoryId);
    }

}