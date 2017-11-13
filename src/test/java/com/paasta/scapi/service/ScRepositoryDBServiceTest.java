package com.paasta.scapi.service;

import com.paasta.scapi.model.Repository;
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
public class ScRepositoryDBServiceTest {

    @Mock
    ScRepositoryDBService scRepositoryDBService;

    @Mock
    RepoPermissionDBService repoPermissionDBService;


    @Test
    public void createRepositoryDB() throws  Exception {
        Repository repository = mock(Repository.class);
//        RepoPermission repoPermission = mock(RepoPermission.class);
        scRepositoryDBService.createRepositoryDB(repository);
//        repoPermissionDBService.save(repoPermission);
    }

    @Test
    public void updateRepositoryDB() throws Exception {
        Repository repository = mock(Repository.class);
        scRepositoryDBService.updateRepositoryDB(repository);
    }

    @Test
    public void deleteRepositoryDB() throws Exception {
        Repository repository = mock(Repository.class);
        String id = "";
        scRepositoryDBService.deleteRepositoryDB(id);
    }
}