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
     * The Api auth.
     */
    @Value("${api.authentication}") String apiAuth;
    /**
     * The Api auth login.
     */
    @Value("${api.authentication.login}") String apiAuthLogin;

    //====== [Scm Server Repository Resource Api]
    /**
     * The Api Repository.
     */
    @Value("${api.repository}") String apiRepo;
    /**
     * The Api Repository id.
     */
    @Value("${api.repository.id}") String apiRepoId;
    /**
     * The Api Repository blame.
     */
    @Value("${api.repository.id.blame}") String apiRepoBlame;
    /**
     * The Api Repository branches.
     */
    @Value("${api.repository.id.branches}") String apiRepoBranches;
    /**
     * The Api Repository Browse.
     */
    @Value("${api.repository.id.browse}") String apiRepoBrowse;
    /**
     * The Api Repository changesets.
     */
    @Value("${api.repository.id.changesets}") String apiRepoChangesets;
    /**
     * The Api Repository content.
     */
    @Value("${api.repository.id.content}") String apiRepoContent;
    /**
     * The Api Repository diff.
     */
    @Value("${api.repository.id.diff}") String apiRepoDiff;
    /**
     * The Api Repository healthcheck.
     */
    @Value("${api.repository.id.healthcheck}") String apiRepoHealthcheck;
    /**
     * The Api Repository tags.
     */
    @Value("${api.repository.id.tags}") String apiRepoTags;
    /**
     * The Api Repository name.
     */
    @Value("${api.repository.type.name}") String apiRepoTypeName;
    /**
     * The Api Repository revision.
     */
    @Value("${api.repository.id.changeset.revision}") String apiRepoRevision;
    /**
     * The Api Repository revision.
     */
    @Value("${api.repository.id.content.path.revision}") String apiRepositoryIdContentPathRevision;

    //====== [Scm Server User Resource Api]
    /**
     * The Api users.
     */
    @Value("${api.users}") String apiUsers;

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
                ", apiAuth='" + apiAuth + '\'' +
                ", apiAuthLogin='" + apiAuthLogin + '\'' +
                ", apiRepo='" + apiRepo + '\'' +
                ", apiRepoId='" + apiRepoId + '\'' +
                ", apiRepoBlame='" + apiRepoBlame + '\'' +
                ", apiRepoBranches='" + apiRepoBranches + '\'' +
                ", apiRepoBrowse='" + apiRepoBrowse + '\'' +
                ", apiRepoChangesets='" + apiRepoChangesets + '\'' +
                ", apiRepoContent='" + apiRepoContent + '\'' +
                ", apiRepoDiff='" + apiRepoDiff + '\'' +
                ", apiRepoHealthcheck='" + apiRepoHealthcheck + '\'' +
                ", apiRepoTags='" + apiRepoTags + '\'' +
                ", apiRepoTypeName='" + apiRepoTypeName + '\'' +
                ", apiRepoRevision='" + apiRepoRevision + '\'' +
                ", apiRepositoryIdContentPathRevision='" + apiRepositoryIdContentPathRevision + '\'' +
                ", apiUsers='" + apiUsers + '\'' +
                '}';
    }
}
