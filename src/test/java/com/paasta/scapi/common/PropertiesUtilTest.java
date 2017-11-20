package com.paasta.scapi.common;

import com.paasta.scapi.common.util.PropertiesUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class, classes = { PropertiesUtil.class })
@ActiveProfiles("test")
public class PropertiesUtilTest extends MockUtil{

    @Autowired
    PropertiesUtil propertiesUtil;

    @Test
    public void getBasicAuth() throws Exception {
        String apiRepo = propertiesUtil.getApiRepo();
        Assert.assertEquals(propertiesUtilTest.getApiRepo(), apiRepo);
    }

    @Test
    public void setBaseUrl() throws Exception {
        propertiesUtil.setBaseUrl(propertiesUtilTest.getBaseUrl());
    }

    @Test
    public void setAdminId() throws Exception {
        propertiesUtil.setAdminId(propertiesUtilTest.getAdminId());
    }

    @Test
    public void setAdminPwd() throws Exception {
        propertiesUtil.setAdminPwd(propertiesUtilTest.getAdminPwd());
    }

    @Test
    public void setApiAuth() throws Exception {
        propertiesUtil.setApiAuth(propertiesUtilTest.getApiAuth());
    }

    @Test
    public void setApiAuthLogin() throws Exception {
        propertiesUtil.setApiAuthLogin(propertiesUtilTest.getApiAuthLogin());
    }

    @Test
    public void setApiRepo() throws Exception {
        propertiesUtil.setApiRepo(propertiesUtilTest.getApiRepo());
    }

    @Test
    public void setApiRepoId() throws Exception {
        propertiesUtil.setApiRepoId(propertiesUtilTest.getApiRepoId());
    }

    @Test
    public void setApiRepoBlame() throws Exception {
        propertiesUtil.setApiRepoBlame(propertiesUtilTest.getApiRepoBlame());
    }

    @Test
    public void setApiRepoBranches() throws Exception {
        propertiesUtil.setApiRepoBranches(propertiesUtilTest.getApiRepoBranches());
    }

    @Test
    public void setApiRepoBrowse() throws Exception {
        propertiesUtil.setApiRepoBrowse(propertiesUtilTest.getApiRepoBrowse());
    }

    @Test
    public void setApiRepoChangesets() throws Exception {
        propertiesUtil.setApiRepoChangesets(propertiesUtilTest.getApiRepoChangesets());
    }

    @Test
    public void setApiRepoContent() throws Exception {
        propertiesUtil.setApiRepoContent(propertiesUtilTest.getApiRepoContent());
    }

    @Test
    public void setApiRepoDiff() throws Exception {
        propertiesUtil.setApiRepoDiff(propertiesUtilTest.getApiRepoDiff());
    }

    @Test
    public void setApiRepoHealthcheck() throws Exception {
        propertiesUtil.setApiRepoHealthcheck(propertiesUtilTest.getApiRepoHealthcheck());
    }

    @Test
    public void setApiRepoTags() throws Exception {
        propertiesUtil.setApiRepoTags(propertiesUtilTest.getApiRepoTags());
    }

    @Test
    public void setApiRepoTypeName() throws Exception {
        propertiesUtil.setApiRepoTypeName(propertiesUtilTest.getApiRepoTypeName());
    }

    @Test
    public void setApiRepoRevision() throws Exception {
        propertiesUtil.setApiRepoRevision(propertiesUtilTest.getApiRepoRevision());
    }

    @Test
    public void setApiRepositoryIdContentPathRevision() throws Exception {
        propertiesUtil.setApiRepositoryIdContentPathRevision(propertiesUtilTest.getApiRepositoryIdContentPathRevision());
    }

    @Test
    public void setApiUsers() throws Exception {
        propertiesUtil.setApiUsers(propertiesUtilTest.getApiUsers());
    }

    @Test
    public void getBaseUrl() throws Exception {
        String getBaseUrl = propertiesUtil.getBaseUrl();
        Assert.assertEquals(propertiesUtilTest.getBaseUrl(), getBaseUrl);
    }

    @Test
    public void getAdminId() throws Exception {
        String getAdminId = propertiesUtil.getAdminId();
        Assert.assertEquals(propertiesUtilTest.getAdminId(), getAdminId);
    }

    @Test
    public void getAdminPwd() throws Exception {
        String getAdminPwd = propertiesUtil.getAdminPwd();
        Assert.assertEquals(propertiesUtilTest.getAdminPwd(), getAdminPwd);
    }

    @Test
    public void getApiAuth() throws Exception {
        String getApiAuth = propertiesUtil.getApiAuth();
        Assert.assertEquals(propertiesUtilTest.getApiAuth(), getApiAuth);
    }

    @Test
    public void getApiAuthLogin() throws Exception {
        String getApiAuthLogin = propertiesUtil.getApiAuthLogin();
        Assert.assertEquals(propertiesUtilTest.getApiAuthLogin(), getApiAuthLogin);
    }

    @Test
    public void getApiRepo() throws Exception {
        String getApiRepo = propertiesUtil.getApiRepo();
        Assert.assertEquals(propertiesUtilTest.getApiRepo(), getApiRepo);
    }

    @Test
    public void getApiRepoId() throws Exception {
        String getApiRepoId = propertiesUtil.getApiRepoId();
        Assert.assertEquals(propertiesUtilTest.getApiRepoId(), getApiRepoId);
    }

    @Test
    public void getApiRepoBlame() throws Exception {
        String getApiRepoBlame = propertiesUtil.getApiRepoBlame();
        Assert.assertEquals(propertiesUtilTest.getApiRepoBlame(), getApiRepoBlame);
    }

    @Test
    public void getApiRepoBranches() throws Exception {
        String getApiRepoBranches = propertiesUtil.getApiRepoBranches();
        Assert.assertEquals(propertiesUtilTest.getApiRepoBranches(), getApiRepoBranches);
    }

    @Test
    public void getApiRepoBrowse() throws Exception {
        String getApiRepoBrowse = propertiesUtil.getApiRepoBrowse();
        Assert.assertEquals(propertiesUtilTest.getApiRepoBrowse(), getApiRepoBrowse);
    }


    @Test
    public void getApiRepoChangesets() throws Exception {
        String getApiRepoChangesets = propertiesUtil.getApiRepoChangesets();
        Assert.assertEquals(propertiesUtilTest.getApiRepoChangesets(), getApiRepoChangesets);
    }

    @Test
    public void getApiRepoContent() throws Exception {
        String getApiRepoContent = propertiesUtil.getApiRepoContent();
        Assert.assertEquals(propertiesUtilTest.getApiRepoContent(), getApiRepoContent);
    }

    @Test
    public void getApiRepoDiff() throws Exception {
        String getApiRepoTags = propertiesUtil.getApiRepoTags();
        Assert.assertEquals(propertiesUtilTest.getApiRepoTags(), getApiRepoTags);
    }

    @Test
    public void getApiRepoHealthcheck() throws Exception {
        String getApiRepoTags = propertiesUtil.getApiRepoTags();
        Assert.assertEquals(propertiesUtilTest.getApiRepoTags(), getApiRepoTags);
    }

    @Test
    public void getApiRepoTags() throws Exception {
        String getApiRepoTags = propertiesUtil.getApiRepoTags();
        Assert.assertEquals(propertiesUtilTest.getApiRepoTags(), getApiRepoTags);
    }

    @Test
    public void getApiRepoTypeName() throws Exception {
        String getApiRepoTypeName = propertiesUtil.getApiRepoTypeName();
        Assert.assertEquals(propertiesUtilTest.getApiRepoTypeName(), getApiRepoTypeName);
    }


    @Test
    public void getApiRepoRevision() throws Exception {
        String getApiRepoRevision = propertiesUtil.getApiRepoRevision();
        Assert.assertEquals(propertiesUtilTest.getApiRepoRevision(), getApiRepoRevision);
    }

    @Test
    public void getApiRepositoryIdContentPathRevision() throws Exception {
        String getApiRepositoryIdContentPathRevision = propertiesUtil.getApiRepositoryIdContentPathRevision();
        Assert.assertEquals(propertiesUtilTest.getApiRepositoryIdContentPathRevision(), getApiRepositoryIdContentPathRevision);
    }


    @Test
    public void getApiUsers() throws Exception {
        String getApiUsers = propertiesUtil.getApiUsers();
        Assert.assertEquals(propertiesUtilTest.getApiUsers(), getApiUsers);
    }


    @Test
    public void toString_util() throws Exception {
        String toString_util = propertiesUtil.toString();
        Assert.assertEquals(propertiesUtilTest.toString(), toString_util);
    }

}