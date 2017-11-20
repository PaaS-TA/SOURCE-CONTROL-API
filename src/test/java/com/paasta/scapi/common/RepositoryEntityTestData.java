package com.paasta.scapi.common;
import com.paasta.scapi.entity.ScRepository;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.List;

@Ignore
public final class RepositoryEntityTestData
{

    private RepositoryEntityTestData() {}

    public static ScRepository createRepoitory()
    {

        ScRepository scRepository = new ScRepository();
        scRepository.setRepoNo(MockUtil.repoNo);
        scRepository.setRepoScmId(MockUtil.repoScmId);
        scRepository.setRepoName(MockUtil.repoName);
        scRepository.setRepoDesc(MockUtil.repoDesc);
        scRepository.setCreateUserId(MockUtil.createUserId);
        scRepository.setInstanceId(MockUtil.instanceId);
        scRepository.setOwnerUserId(MockUtil.ownerUserId);
        return scRepository;
    }

    public static List<ScRepository> getLstScRepository()
    {
        List<ScRepository> rtnList = new ArrayList();
        rtnList.add(createRepoitory());
        return rtnList;
    }

}