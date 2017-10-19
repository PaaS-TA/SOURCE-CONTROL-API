package com.paasta.scapi.service;

import com.paasta.scapi.entity.RepoPermission;
import com.paasta.scapi.entity.ScRepository;
import com.paasta.scapi.model.Repository;
import com.paasta.scapi.repository.ScRepositoryRepository;
import com.sun.org.apache.regexp.internal.RE;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        RepoPermission repoPermission = mock(RepoPermission.class);
        scRepositoryDBService.createRepositoryDB(repository);
        repoPermissionDBService.save(repoPermission);
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