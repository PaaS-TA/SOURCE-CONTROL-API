package com.paasta.scapi.service;

import com.paasta.scapi.entity.RepoPermission;
import com.paasta.scapi.model.Repository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;

/**
 * Created by ijlee
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RepoPermissionApiServiceTest extends CommonServiceTest {

    @Before
    public void setUp() {
        setUpMockUtil();
    }

    @Test
    public void insertRepositoryForUserAuth() throws Exception {
        Repository repository = mock(Repository.class);
        String repositoryId="";

        RepoPermission permission = mock(RepoPermission.class);
        permission.getUserId();
        permission.getPermission();

        scRepositoryApiService.getRepositoryByIdApi("");
}

    @Test
    public void deleteRepositoryForUserAuth() throws Exception {
        RepoPermission repoPermission = mock(RepoPermission.class);
        repoPermissionRepository.delete(repoPermission.getNo());
    }

    @Test
    public void getListPermitionByInstanceId() throws Exception {
        repoPermissionApiService.getListPermitionByInstanceId("",1,10,"");
    }

    @Test
    public void permisionByInstanceIdAndParam() throws Exception {
        repoPermissionApiService.permisionByInstanceIdAndParam("","","Y","","","");
    }

}