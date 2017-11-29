package com.paasta.scapi.service;

import com.paasta.scapi.common.Common;
import com.paasta.scapi.common.Constants;
import com.paasta.scapi.common.util.PropertiesUtil;
import com.paasta.scapi.common.util.RestClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sonia.scm.NotSupportedFeatuerException;
import sonia.scm.client.ClientChangesetHandler;
import sonia.scm.client.RepositoryClientHandler;
import sonia.scm.client.ScmClientException;
import sonia.scm.client.ScmClientSession;
import sonia.scm.repository.BrowserResult;
import sonia.scm.repository.ChangesetPagingResult;
import sonia.scm.repository.Repository;
import sonia.scm.repository.Tags;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

/**
 * Created by lena on 2017-06-14.
 */
@Service
public class ScRepositoryApiService extends  CommonService{

    @Autowired
    private
    RestClientUtil restClientUtil;

    @Autowired
    private
    ScRepositoryDBService scRepositoryDBService;

    @Autowired
    private
    PropertiesUtil propertiesUtil;

    private ScmClientSession session;

    @SuppressWarnings("unchecked")
    public Map<String, Object> getUserRepositories(String instanceid, String userid, int page, int size, String repoName, String type1, String type2, String reposort) {
        Map<String, Object> resultMap = new HashMap();
            // 서비스 인스턴스별 repository
        List<Repository> repositories = fillterRepositories(instanceid, userid, reposort,  repoName,  type1,  type2);

        int start = page* size;
        size  = (0==size) ? repositories.size():size;
        PageRequest pageRequest =  new PageRequest(page, size);
        int totalSize = repositories.size();
        repositories = repositories.stream().skip(start).limit(start+size).collect(toList());
        Page pageImpl = new PageImpl(repositories, pageRequest, totalSize);
        resultMap.put("rtnList", pageImpl);
        return resultMap;
    }


    private  Map rtnPageInfo(int start, int end, int totCnt, Map resultMap, List<Repository> repositories){
        if (start >= 1 && end > 0 && start <= end && start <= totCnt) {
            resultMap.put("repositories", repositories.stream().skip(start - 1).limit(end).collect(toList()));
        } else {
            resultMap.put("repositories", repositories);
        }
        Map<String, Object> pageInfo = new HashMap<>();
        pageInfo.put("totalCnt", totCnt);
        pageInfo.put("startCnt", start);
        pageInfo.put("endCnt", end >= totCnt ? totCnt : end);

        resultMap.put("pageInfo", pageInfo);
        return resultMap;
    }

    public Repository getRepositoryByIdApi(String id){
        // scm-manager search repository
        Repository repository = createRepositoryClientHandler().get(id);
        return repository;
    }

    public void createRepositoryApi(Repository request) throws ScmClientException {

        // step1. scm-manager create repository
        // 1-1. create repository
//        scmCreateRepository(request);
        createRepositoryClientHandler().create(request);
    }

    @Transactional
    public void deleteRepositoryApi(String id) throws ScmClientException {

        // scm-manager delete repository
        createRepositoryClientHandler().delete(id);

    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getAdminRepositories(String instanceid, String userid, int start, int end, String repoName, String type, String reposort) throws ScmClientException{
        Map<String, Object> resultMap = new HashMap();
        logger.info("getAdminRepositories::::userid" + userid+":::::::::type :::"+type);
        // 서비스 인스턴스별 repository
        List<Repository> repositories = fillterRepositories(instanceid, "" , reposort, repoName, "", "");
        int totCnt = repositories.size();
        resultMap = rtnPageInfo(start, end, totCnt, resultMap, repositories);
        return resultMap;
    }



    /**
     *
     * @param instanceid
     * @param reposprt
     * @return
     */
    public  List<Repository> getRepositoryByInstanceId(String instanceid, String reposprt) throws ScmClientException {

//        List<Repository> repositories = scmAllRepositories(sSort);
        List<Repository> repositories = scmAllRepositories(reposprt);
        // 인스턴스 아이디 존재하지 않을 경우, 모든 레파지토리 반환
        List<Repository> repositoryList = new ArrayList<>();

        // instance ID 가 존재하지 않는 경우, 모든 레파지토리 목록 반환
        if (StringUtils.isEmpty(instanceid)) {
            return repositories;
        }
        repositories.forEach(e -> {
                    if (e.getProperties() != null && !e.getProperties().isEmpty()) {
                        if (e.getProperties().getOrDefault(Constants.REPO_PROPERTIES_INSTANCE_ID, "").equals(instanceid)) {
                            repositoryList.add(e);
                        }
                    }
                });
        return repositoryList;
    }


    public sonia.scm.repository.Branches getBranches(String id) throws ScmClientException {

        HttpEntity<Object> entity = restClientUtil.restCommonHeader(null);
        String url = propertiesUtil.getApiRepoBranches().replace("{id}", id);
        ResponseEntity<sonia.scm.repository.Branches> response = restClientUtil.callRestApi(HttpMethod.GET, url, entity, sonia.scm.repository.Branches.class);
        return response.getBody();
    }


    public Tags getTags(String id) throws ScmClientException{
        Repository repository = scmAdminSession().getRepositoryHandler().get(id);
        return scmAdminSession().getRepositoryHandler().getTags(repository);
    }


    public BrowserResult getBrowseByParam(String id, boolean disableLastCommit, boolean disableSubRepositoryDetection, String path, boolean recursive, String revision) throws ScmClientException {
        HttpEntity<Object> entity = restClientUtil.restCommonHeaderJson(null);
        String url = propertiesUtil.getApiRepoBrowse().replace("{id}", id);
        String param = "?disableLastCommit=" + disableLastCommit + "&disableSubRepositoryDetection=" + disableSubRepositoryDetection + "&path=" + path + "&recursive=" + recursive + "&revision=" + revision;
        url = url + param;
        logger.debug("url:::" + url);
        ResponseEntity<BrowserResult> response = restClientUtil.callRestApi(HttpMethod.GET, url, entity, BrowserResult.class);
        return response.getBody();
    }


    
    public ChangesetPagingResult getChangesets(String id) throws NotSupportedFeatuerException,ScmClientException {
        RepositoryClientHandler repositoryHandler = scmAdminSession().getRepositoryHandler();
        Repository repository = repositoryHandler.get(id);
        ClientChangesetHandler changesetHandler = repositoryHandler.getChangesetHandler(repository);
        // get 20 changesets started by 0
        return changesetHandler.getChangesets(0, 20);
    }

    @Transactional
    public Repository updateRepository(String id, Repository repository) throws ScmClientException{
        try {
            RepositoryClientHandler repositoryClientHandler = scmAdminSession().getRepositoryHandler();
            //조회
            Repository mofiyRepository = repositoryClientHandler.get(id);

            //변경사항 저장
            mofiyRepository.setDescription(repository.getDescription());
            mofiyRepository.setPublicReadable(repository.isPublicReadable());
            repositoryClientHandler.modify(mofiyRepository);
            scRepositoryDBService.updateRepositoryDB(repository);
            return mofiyRepository;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<byte[]> getContent(String id, String revision, String path, String dc) throws ScmClientException, NotSupportedFeatuerException, IOException {
        String url = propertiesUtil.getApiRepositoryIdContentPathRevision().replace("{id}", id).replace("{path}", path);
        url = url.replace("{revision}", (String) Common.notNullrtnByobj(revision, ""));
        url = url.replace("{dc}", (String) Common.notNullrtnByobj(dc, ""));
        logger.debug("getContent:::url" + url);
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        HttpEntity<Object> entity = new HttpEntity<>(null, headers);
        return restClientUtil.callRestApi(HttpMethod.GET, url, entity, byte[].class);

    }

    private List<Repository> fillterRepositories(String instanceid, String userid, String reposort, String repoName, String type1, String type2) throws ScmClientException{

        List<Repository> instanceRepositories = getRepositoryByInstanceId(instanceid, reposort);
        List<Repository> repositories = new ArrayList<>();
        if (!StringUtils.isEmpty(instanceid)) {
            // Repository <> User 권한 Check : 참여되어있는 Repository 조회
            instanceRepositories.forEach((Repository e) -> {
                boolean bRtn = true;
                boolean bType2 = true;
                //사용자 아이디 검색 하지 않음
                //레파지토리 이름 검색
                if (Common.notEmpty(repoName)) {
                    if (!e.getName().contains(repoName)) {
                        bRtn = false;
                    }
                }
                //레파지 토리 타입 검색(git, svn)
                if (Common.notEmpty(type1)) {
                    if (!e.getType().equals(type1)) {
                        bRtn = false;
                    }
                }
                //레파지토리 권한 검색 OWNER, READ, WRITE
                if (Common.notEmpty(type2)) {
                    String[] lstType2 = type2.split("_");
                    bType2 = false;
                    List<sonia.scm.repository.Permission> permissions = e.getPermissions();
                    for (sonia.scm.repository.Permission permission : permissions) {
                        for (String sType2 : lstType2) {
                            if (permission.getName().equals(userid) && ((permission.getType()).name()).equals(sType2)) {
                                bType2 = true;
                            }
                        }
                    }
                }
                if (bRtn && bType2) {
                    repositories.add(e);
                }
            });
        } else {
            repositories.forEach(e -> {
                repositories.add(e);
            });
        }
        return repositories;
    }

    // [ private Method ]=================================================================================================

    /**
     * Scm Server에서 Sort 정보를 통해 데이터를 가져온다.
     * @param reposprt sSort = "?sortby=" + sort[0] + "&desc=" + sort[1];//lastModified_true, lastModified_false/creationDate_true/creationDate_false
     * @return
     */
    private List<Repository> scmAllRepositories(String reposprt) throws ScmClientException{
        // 모든 Repository 조회
        List<Repository> lstScmAllRepositories = scmAllRepositories();
        //처음조회시 lastmodify 가 무조건 true로 넘어옴.
        //sort
        if (Common.notEmpty(reposprt)) {
            String sSort[] =  reposprt.split("_");
            if (sSort.length != 0) {
                boolean bSort = Boolean.parseBoolean(sSort[1]);
                //LastModify true````
                if("lastModified".equals(sSort[0])){
                    if(bSort) {
                        Collections.reverse(lstScmAllRepositories);
                    }
                }
                if("creationDate".equals(sSort[0])){
                    if(bSort) {
                        lstScmAllRepositories.sort(Comparator.comparing(Repository::getCreationDate).reversed());
                    }
                }
                if("creationDate".equals(sSort[0])){
                    if(!bSort) {
                        lstScmAllRepositories.sort(Comparator.comparing(Repository::getCreationDate));
                    }
                }
            }
        }
        return lstScmAllRepositories;
    }

    /**
     * Scm Server에서 Sort 정보를 통해 데이터를 가져온다.
     * Scm Server에서 모든 레파지토리리스트를 가져온다.
     * @return
     */
    private List<Repository> scmAllRepositories() throws ScmClientException{
        // 모든 Repository 조회
        //기본 last modify true임.
        return createRepositoryClientHandler().getAll();
    }
    public Repository scmRepositoryByNameType(String type, String name) throws ScmClientException {

        RepositoryClientHandler repositoryClientHandler = scmAdminSession().getRepositoryHandler();
        return repositoryClientHandler.get(type, name);

    }
}
