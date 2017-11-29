package com.paasta.scapi.common;

import com.paasta.scapi.entity.RepoPermission;
import sonia.scm.repository.Permission;
import sonia.scm.repository.PermissionType;

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

    public static List<RepoPermission> getLstRepoPermission()
    {
        List<RepoPermission> rtnList = new ArrayList();
        rtnList.add(createRepoPermission());
        return rtnList;
    }

    public static Permission createPermission()
    {
        Permission permission = new Permission(MockUtil.repoScmId, PermissionType.OWNER);
        return permission;
    }
    public static List<Permission> getLstPermission()
    {
        List<Permission> rtnList = new ArrayList();
        rtnList.add(createPermission());
        return rtnList;
    }

}