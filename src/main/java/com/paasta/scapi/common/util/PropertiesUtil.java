package com.paasta.scapi.common.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * Created by lena on 2017-06-23.
 */
@Component
@Setter
@Getter
public class PropertiesUtil {

    /**
     * The Base url.
     */
    @Value("${api.base.url}") String baseUrl;

    /**
     * The Admin id.
     */
    @Value("${admin.id}") String adminId;

    /**
     * The Admin pwd.
     */
    @Value("${admin.pwd}") String adminPwd;

    //====== [Scm Server Authentication Resource Api]

    /**
     * The Api Repository branches.
     */
    @Value("${api.repository.id.branches}") String apiRepoBranches;
    /**
     * The Api Repository Browse.
     */
    @Value("${api.repository.id.browse}") String apiRepoBrowse;

    /**
     * The Api Repository revision.
     */
    @Value("${api.repository.id.content.path.revision}") String apiRepositoryIdContentPathRevision;

    @Value("${api.clone.url}") String  apiCloneUrl;
    //====== [Scm Server User Resource Api]

    /**
     * Gets basic auth.
     *
     * @return the basic auth
     */
    public String getBasicAuth() {

        return "Basic " + (Base64.getEncoder().encodeToString((adminId + ":" + adminPwd).getBytes()));
    }

    @Override
    public String toString() {
        return "PropertiesUtil{" +
                "baseUrl='" + baseUrl + '\'' +
                ", adminId='" + adminId + '\'' +
                ", adminPwd='" + adminPwd + '\'' +
                ", apiRepoBranches='" + apiRepoBranches + '\'' +
                ", apiRepoBrowse='" + apiRepoBrowse + '\'' +
                ", apiCloneUrl='" + apiCloneUrl + '\'' +
                ", apiRepositoryIdContentPathRevision='" + apiRepositoryIdContentPathRevision + '\'' +
                '}';
    }
}
