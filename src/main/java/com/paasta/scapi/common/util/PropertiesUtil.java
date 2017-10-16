package com.paasta.scapi.common.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * Created by lena on 2017-06-23.
 */
@Component
@Setter
@Getter
@PropertySource("classpath:scapi.properties")
public class PropertiesUtil {

    /**
     * The Base url.
     */
    @Value("${api.base.url}") String base_url;

    /**
     * The Admin id.
     */
    @Value("${admin.id}") String admin_id;

    /**
     * The Admin pwd.
     */
    @Value("${admin.pwd}") String admin_pwd;

    //====== [Scm Server Authentication Resource Api]
    /**
     * The Api auth.
     */
    @Value("${api.authentication}") String api_auth;
    /**
     * The Api auth login.
     */
    @Value("${api.authentication.login}") String api_auth_login;

    //====== [Scm Server Repository Resource Api]
    /**
     * The Api Repository.
     */
    @Value("${api.repository}") String api_repo;
    /**
     * The Api Repository id.
     */
    @Value("${api.repository.id}") String api_repo_id;
    /**
     * The Api Repository blame.
     */
    @Value("${api.repository.id.blame}") String api_repo_blame;
    /**
     * The Api Repository branches.
     */
    @Value("${api.repository.id.branches}") String api_repo_branches;
    /**
     * The Api Repository Browse.
     */
    @Value("${api.repository.id.browse}") String api_repo_browse;
    /**
     * The Api Repository changesets.
     */
    @Value("${api.repository.id.changesets}") String api_repo_changesets;
    /**
     * The Api Repository content.
     */
    @Value("${api.repository.id.content}") String api_repo_content;
    /**
     * The Api Repository diff.
     */
    @Value("${api.repository.id.diff}") String api_repo_diff;
    /**
     * The Api Repository healthcheck.
     */
    @Value("${api.repository.id.healthcheck}") String api_repo_healthcheck;
    /**
     * The Api Repository tags.
     */
    @Value("${api.repository.id.tags}") String api_repo_tags;
    /**
     * The Api Repository name.
     */
    @Value("${api.repository.type.name}") String api_repo_type_name;
    /**
     * The Api Repository revision.
     */
    @Value("${api.repository.id.changeset.revision}") String api_repo_revision;
    /**
     * The Api Repository revision.
     */
    @Value("${api.repository.id.content.path.revision}") String api_repository_id_content_path_revision;

    //====== [Scm Server User Resource Api]
    /**
     * The Api users.
     */
    @Value("${api.users}") String api_users;

    /**
     * Gets basic auth.
     *
     * @return the basic auth
     */
    public String getBasicAuth() {
        String basicAuth = "Basic " + (Base64.getEncoder().encodeToString((admin_id + ":" + admin_pwd).getBytes()));

        return basicAuth;
    }

}
