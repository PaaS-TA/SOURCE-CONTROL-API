package com.paasta.scapi.service;

import com.paasta.scapi.common.RepositoryEntityTestData;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.junit4.SpringRunner;
import sonia.scm.repository.Repository;


/**
 * Created by SEJI on 2017-10-16.
 */

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ScRepositoryDBServiceTest extends CommonServiceTest{

    @Test
    public void createRepositoryDB() throws  Exception {
//        Repository reqRepository = RepositoryEntityTestData.createRepoitory();
//        ScRepository expectedScRepository = RepositoryEntityTestData.createScRepoitory();
//        ScRepository rtnRepository = scRepositoryDBService.createRepositoryDB(reqRepository);
//        expectedScRepository.setCreateTime(rtnRepository.getCreateTime());
//        Assert.assertEquals(expectedScRepository, rtnRepository);
    }

    @Test
    public void updateRepositoryDB() throws Exception {
        Repository reqRepository = RepositoryEntityTestData.getRepoitory();
        scRepositoryDBService.updateRepositoryDB(reqRepository);
    }

    @Test
    public void deleteRepositoryDB() throws Exception {
        scRepositoryDBService.deleteRepositoryDB(repoScmId);
    }
}