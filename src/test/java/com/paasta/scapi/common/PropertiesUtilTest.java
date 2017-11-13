package com.paasta.scapi.common;

import com.paasta.scapi.common.util.PropertiesUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class, classes = { PropertiesUtil.class })
@ActiveProfiles("test")
public class PropertiesUtilTest {

    /**
     * The Api Repository.
     */
    @Value("${api.repository}") String apiRepo;
    @Autowired
    PropertiesUtil propertiesUtil;
    @Test
    public void testConfig(){
        System.out.println("apiRepo:::::::::"+apiRepo);
        Assert.assertEquals("/api/rest/repositories",apiRepo);
        propertiesUtil.getApiRepo();
    }


  /*  @Test
    public void toString() throws Exception {
    }

    @Test
    public void getBaseUrl() throws Exception {
    }

    @Test
    public void getAdminId() throws Exception {
    }

    @Test
    public void getAdminPwd() throws Exception {
    }

    @Test
    public void getApiAuth() throws Exception {
    }

    @Test
    public void getApiAuthLogin() throws Exception {
    }

    @Test
    public void getApiRepo() throws Exception {
    }

    @Test
    public void getApiRepoId() throws Exception {
    }

    @Test
    public void getApiRepoBlame() throws Exception {
    }

    @Test
    public void getApiRepoBranches() throws Exception {
    }

    @Test
    public void getApiRepoBrowse() throws Exception {
    }

    @Test
    public void getApiRepoChangesets() throws Exception {
    }

    @Test
    public void getApiRepoContent() throws Exception {
    }

    @Test
    public void getApiRepoDiff() throws Exception {
    }

    @Test
    public void getApiRepoHealthcheck() throws Exception {
    }

    @Test
    public void getApiRepoTags() throws Exception {
    }

    @Test
    public void setApiAuth() throws Exception {
    }

    @Test
    public void setApiAuthLogin() throws Exception {
    }

    @Test
    public void setApiRepo() throws Exception {
    }

    @Test
    public void setApiRepoId() throws Exception {
    }

    @Test
    public void setApiRepoBlame() throws Exception {
    }

    @Test
    public void setApiRepoBrowse() throws Exception {
    }

    @Test
    public void setApiRepoChangesets() throws Exception {
    }

    @Test
    public void setApiRepoContent() throws Exception {
    }

    @Test
    public void setApiRepoDiff() throws Exception {
    }

    @Test
    public void setApiRepoHealthcheck() throws Exception {
    }

    @Test
    public void setApiRepoTags() throws Exception {
    }

    @Test
    public void setApiRepoTypeName() throws Exception {
    }

    @Test
    public void setApiRepoRevision() throws Exception {
    }

    @Test
    public void setApiRepositoryIdContentPathRevision() throws Exception {
    }

    @Test
    public void setApiUsers() throws Exception {
    }
*/
}