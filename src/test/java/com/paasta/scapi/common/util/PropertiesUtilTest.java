//package com.paasta.scapi.common.util;
//
//import org.junit.Before;
//import org.junit.Test;
////import org.mockserver.socket.PortFactory;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
//import org.springframework.core.env.MutablePropertySources;
//import org.springframework.core.env.StandardEnvironment;
//import org.springframework.core.io.ClassPathResource;
////import org.springframework.mock.env.MockEnvironment;
//import org.springframework.mock.env.MockPropertySource;
//@SpringBootTest
//public class PropertiesUtilTest {
//
//    @Before
//    public void setUp() throws Exception {
//        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
//        ClassPathResource classPathResource = new ClassPathResource("application-test.properties.properties",getClass());
////        MockEnvironment mockEnvironment = new MockEnvironment();
//    }
//    @Override
//    public void initialize(ConfigurableApplicationContext applicationContext) {
//        MutablePropertySources propertySources = applicationContext.getEnvironment().getPropertySources();
//        MockPropertySource mockEnvVars = new MockPropertySource().withProperty("spring.port", PortFactory.findFreePort());
//        propertySources.replace(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, mockEnvVars);
//    }
//
//    @Test
//    public void getBasicAuth() throws Exception {
//    }
//
//    @Test
//    public void toString() throws Exception {
//    }
//
//    @Test
//    public void getBaseUrl() throws Exception {
//    }
//
//    @Test
//    public void getAdminId() throws Exception {
//    }
//
//    @Test
//    public void getAdminPwd() throws Exception {
//    }
//
//    @Test
//    public void getApiAuth() throws Exception {
//    }
//
//    @Test
//    public void getApiAuthLogin() throws Exception {
//    }
//
//    @Test
//    public void getApiRepo() throws Exception {
//    }
//
//    @Test
//    public void getApiRepoId() throws Exception {
//    }
//
//    @Test
//    public void getApiRepoBlame() throws Exception {
//    }
//
//    @Test
//    public void getApiRepoBranches() throws Exception {
//    }
//
//    @Test
//    public void getApiRepoBrowse() throws Exception {
//    }
//
//    @Test
//    public void getApiRepoChangesets() throws Exception {
//    }
//
//    @Test
//    public void getApiRepoContent() throws Exception {
//    }
//
//    @Test
//    public void getApiRepoDiff() throws Exception {
//    }
//
//    @Test
//    public void getApiRepoHealthcheck() throws Exception {
//    }
//
//    @Test
//    public void getApiRepoTags() throws Exception {
//    }
//
//    @Test
//    public void setApiAuth() throws Exception {
//    }
//
//    @Test
//    public void setApiAuthLogin() throws Exception {
//    }
//
//    @Test
//    public void setApiRepo() throws Exception {
//    }
//
//    @Test
//    public void setApiRepoId() throws Exception {
//    }
//
//    @Test
//    public void setApiRepoBlame() throws Exception {
//    }
//
//    @Test
//    public void setApiRepoBrowse() throws Exception {
//    }
//
//    @Test
//    public void setApiRepoChangesets() throws Exception {
//    }
//
//    @Test
//    public void setApiRepoContent() throws Exception {
//    }
//
//    @Test
//    public void setApiRepoDiff() throws Exception {
//    }
//
//    @Test
//    public void setApiRepoHealthcheck() throws Exception {
//    }
//
//    @Test
//    public void setApiRepoTags() throws Exception {
//    }
//
//    @Test
//    public void setApiRepoTypeName() throws Exception {
//    }
//
//    @Test
//    public void setApiRepoRevision() throws Exception {
//    }
//
//    @Test
//    public void setApiRepositoryIdContentPathRevision() throws Exception {
//    }
//
//    @Test
//    public void setApiUsers() throws Exception {
//    }
//
//}