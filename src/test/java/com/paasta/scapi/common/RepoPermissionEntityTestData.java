package com.paasta.scapi.common;
import com.paasta.scapi.entity.RepoPermission;
import com.paasta.scapi.entity.ScRepository;

import java.util.ArrayList;
import java.util.List;

public final class RepoPermissionEntityTestData
{

    private RepoPermissionEntityTestData() {}

    public static RepoPermission createRepoPermission()
    {

        RepoPermission repoPermission = new RepoPermission(MockUtil.repoNo, MockUtil.userId);
        repoPermission.setPermission(MockUtil.sRepoPermission);
        repoPermission.setNo(MockUtil.getPermissionNo);
        repoPermission.setRepoNo(MockUtil.repoNo);

        return repoPermission;
    }

    public static List<RepoPermission> getLstRepoPermissiony()
    {
        List<RepoPermission> rtnList = new ArrayList();
        rtnList.add(createRepoPermission());
        return rtnList;
    }

}