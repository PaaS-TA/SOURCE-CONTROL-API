package com.paasta.scapi.service;

import com.paasta.scapi.entity.RepoPermission;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Created by SEJI on 2017-10-16.
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RepoPermissionDBServiceTest {
    @Mock
    RepoPermissionDBService repoPermissionDBService;

    @Test
    public void save() throws Exception {
        RepoPermission repoPermission = mock(RepoPermission.class);
        repoPermissionDBService.save(repoPermission);
    }

    @Test
    public void save1() throws Exception {
        List<RepoPermission> repoPermission = mock(ArrayList.class);
        repoPermissionDBService.save(repoPermission);
    }

    @Test
    public void delete() throws Exception {
        RepoPermission repoPermission = mock(RepoPermission.class);
        repoPermissionDBService.delete(1);
    }

    @Test
    public void selectByRepoId() throws Exception {
        List<RepoPermission> repoPermission = mock(ArrayList.class);
        repoPermission.get(1);
        repoPermissionDBService.selectByRepoId(1);
    }

    @Test
    public void searchPermisionByUserIdAndRepositoryId() throws Exception {
//        List<User> lstUser = new ArrayList<>();
//        User mockUser1 = Mockito.mock(User.class);
//        lstUser.add(mockUser1);
        String searchUserId ="";
        String repoId="" ;

        repoPermissionDBService.searchPermisionByUserIdAndRepositoryId(searchUserId, repoId);

    }

    @Test
    public void searchPermisionByUserIdAndInstanceId() throws Exception {
    }

}