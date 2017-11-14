package com.paasta.scapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paasta.scapi.common.Common;
import com.paasta.scapi.common.Constants;
import com.paasta.scapi.common.exception.RestException;
import com.paasta.scapi.common.util.DateUtil;
import com.paasta.scapi.common.util.PropertiesUtil;
import com.paasta.scapi.common.util.RestClientUtil;
import com.paasta.scapi.entity.RepoPermission;
import com.paasta.scapi.entity.ScInstanceUser;
import com.paasta.scapi.entity.ScRepository;
import com.paasta.scapi.model.Permission;
import com.paasta.scapi.model.Repository;
import com.paasta.scapi.repository.RepoPermissionRepository;
import com.paasta.scapi.repository.ScInstanceUserRepository;
import com.paasta.scapi.repository.ScRepositoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sonia.scm.client.UserClientHandler;
import sonia.scm.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Created by lena on 2017-06-14.
 */
@Service
public class RepoPermissionApiService extends CommonService{

    /**
     * The Rest client util.
     */
    @Autowired
    private
    RestClientUtil restClientUtil;

    @Autowired
    private
    PropertiesUtil propertiesUtil;

    @Autowired
    private
    ScRepositoryApiService scRepositoryApiService;

    @Autowired
    private
    ScRepositoryRepository scRepositoryRepository;

    @Autowired
    private
    RepoPermissionRepository repoPermissionRepository;

    @Autowired
    private
    ScInstanceUserRepository scInstanceUserRepository;

    /**
     * Reposity 사용자별 권한 추가  git 툴로 사용자 권한을 추가할 수있으로 개별로 추가함.
     * 사용자가 없는 경우 사용자 추가후 권한추가(사용자가 없을 경우에도 추가가 가능함.
     * @param repositoryId the String
     * @param permission  the permission
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public ResponseEntity insertRepositoryForUserAuth(String repositoryId, RepoPermission permission) {
        ResponseEntity entity;

        try {
            UserClientHandler userClientHandler = scmAdminSession().getUserHandler();
            User user = scmAdminSession().getUserHandler().get(permission.getUserId());
            if(Common.empty(user)){
                userClientHandler.create(new User(permission.getUserId()));
            }
            Repository repository = scRepositoryApiService.getRepositoryByIdApi(repositoryId);
            List<Permission> lstPermission = repository.getPermissions();
            lstPermission.add(new Permission(permission.getUserId(), permission.getPermission()));
            repository.setPermissions(lstPermission);
            ObjectMapper jackson = new ObjectMapper();
            logger.info("repositoryForUserAuth Start : ");

            String param = null;
            try {
                param = jackson.writeValueAsString(repository);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            logger.info("repositoryForUserAuth Start :param:: "+param);

            HttpEntity<Object> httEntity = restClientUtil.restCommonHeader(repository);
            entity = restClientUtil.callRestApi(HttpMethod.PUT, this.propertiesUtil.getApiRepo() + "/" + repositoryId, httEntity, String.class);
            if (entity.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
                List<ScRepository> scRepository = scRepositoryRepository.findAllByRepoScmId(repositoryId);
                permission.setRepoNo(scRepository.get(0).getRepoNo());
                repoPermissionRepository.save(permission);
                return new ResponseEntity(entity.getHeaders(), HttpStatus.OK);
            }
        } catch (RestException restException) {
            restException.printStackTrace();
            return new ResponseEntity(restException.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        return entity;
    }


    @SuppressWarnings("unchecked")
    @Transactional
    public ResponseEntity deleteRepositoryForUserAuth(Integer permisionNo) {
        ResponseEntity entity;
        try {
            RepoPermission repoPermission = repoPermissionRepository.getOne(permisionNo);

            ScRepository scRepository = scRepositoryRepository.findOne(repoPermission.getRepoNo());
            String repositoryId = scRepository.getRepoScmId();
            Repository repository = scRepositoryApiService.getRepositoryByIdApi(scRepository.getRepoScmId());

            List<Permission> lstPermission = repository.getPermissions();
            List rtnLstPermission = new ArrayList();

            for (Permission vo : lstPermission) {
                if (!(repoPermission.getUserId().equals(vo.getName()) && repoPermission.getPermission().equals(vo.getType()))) {
                    rtnLstPermission.add(vo);
                }
            }
            repository.setPermissions(rtnLstPermission);

            repoPermissionRepository.delete(permisionNo);
            ObjectMapper jackson = new ObjectMapper();
            logger.info("repositoryForUserAuth Start : ");
            String param;
            try {
                param = jackson.writeValueAsString(repository);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return new ResponseEntity(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
            }
            HttpEntity<Object> httEntity = this.restClientUtil.restCommonHeaders(param);
            entity = this.restClientUtil.callRestApi(HttpMethod.PUT, this.propertiesUtil.getApiRepo() + "/" + repositoryId, httEntity, String.class);
            if (entity.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
                return new ResponseEntity(entity.getHeaders(), HttpStatus.OK);
            }
        } catch (RestException restException) {
            restException.printStackTrace();
            return new ResponseEntity(restException.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }

        return entity;

    }


    
    @SuppressWarnings("unchecked")
    @Transactional
    public ResponseEntity getListPermitionByInstanceId(String instanceId, int page, int size, String username) {
        try {
            logger.info("getListPermitionByInstanceId start : ");
            PageRequest pageRequest = new PageRequest(page, size);
            UserClientHandler userClientHandler = scmAdminSession().getUserHandler();
            int start = page * size + 1;
            int end = (page + 1) * size + 1;
            List<Map> repositories = new ArrayList<>();

            // 서비스 인스턴스별 repository
            List<Repository> instanceRepositories = scRepositoryApiService.getRepositoryByInstanceId(instanceId,"");

            List<User> lstUser = userClientHandler.getAll();
            List<User> relstUser = new ArrayList<>();
            //사용자 정보중에서 username을 필터한다.(perfomance 를 위한 작업)
            lstUser.forEach((User user) -> {
                if (!Common.empty(username)) {
                    if (user.getName().contains(username) || user.getDisplayName().contains(username))
                        relstUser.add(user);
                } else {
                    relstUser.add(user);
                }
            });

            //해당 인스턴스 Repository 와 permission 아이디에 사용자 정보를 조합한다. permission {i} --> user {i}
            instanceRepositories.forEach((Repository repository) -> {
                List lst = new ArrayList();
                repository.getPermissions().forEach((Permission permission) -> relstUser.forEach((User User) -> {
                    if (User.getName().equals(permission.getName())) {
                        lst.add(User);
                    }
                }));
                //return 할 Repositories 객체에 Permsission정보를 넣어준다.
                if (lst.size() > 0) {
                    Map addMap = new HashMap();
                    addMap.put("user", lst);
                    addMap.put("repositories", repository);
                    repositories.add(addMap);
                }
            });

            Page pageImpl = new PageImpl(repositories.stream().skip(start - 1).limit(size).collect(toList()), pageRequest, repositories.size());

            logger.info("getListPermitionByInstanceId end : ");
            return new ResponseEntity(pageImpl, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e.toString(), HttpStatus.FORBIDDEN);
        }

    }

    @SuppressWarnings("unchecked")
    public Map permisionByInstanceIdAndParam(String instanceId, String searchUserId, String searchCreateYn, String searchActive, String sPage, String sSize){
        try {
            //검색 아이디가 있을 경우 없을 경우
            PageRequest pageRequest;

            List<ScInstanceUser> lstScInstanceUsers = scInstanceUserRepository.findByInstanceIdAndUserIdIsContainingAndCreaterYnContaining(instanceId, searchUserId, searchCreateYn);

            UserClientHandler userClientHandler = scmAdminSession().getUserHandler();
            List<User> lstUser = userClientHandler.getAll();
            List<Map> rtnList = new ArrayList();

            for (ScInstanceUser lstScInstanceUser : lstScInstanceUsers) {
                Map map = new HashMap();
                map.put("no", lstScInstanceUser.getNo());
                map.put("userId", Common.notNullrtnByobj(lstScInstanceUser.getUserId(), ""));
                map.put("userName", "");
                map.put("userEmail", "");
                map.put("userActive", false);
                map.replace("userAdmin", false);
                map.put("userRepoRole", lstScInstanceUser.getRepoRole());
                map.put("userCreateYn", lstScInstanceUser.getCreaterYn());
                map.put("userCreatedDate", DateUtil.rtnFormatString(Constants.DATE_FORMAT_1, lstScInstanceUser.getCreatedDate()));
                map.put("userModifiedDate", DateUtil.rtnFormatString(Constants.DATE_FORMAT_1, lstScInstanceUser.getModifiedDate()));

                for (User user : lstUser) {
                    if (lstScInstanceUser.getUserId().equals(user.getName())) {
                        String creationDate = user.getCreationDate() == null ? "" : String.valueOf(user.getCreationDate());
                        String lastModified = user.getLastModified() == null ? "" : String.valueOf(user.getLastModified());
                        map.replace("userName", Common.notNullrtnByobj(user.getDisplayName(), ""));
                        map.replace("userEmail", Common.notNullrtnByobj(user.getMail(), ""));
                        map.replace("userActive", Common.notNullrtnByobj(user.isActive(), false));
                        map.replace("userAdmin", Common.notNullrtnByobj(user.isAdmin(), false));
                        map.put("userCreatedDate", DateUtil.parseStringDatebyInt(Constants.DATE_FORMAT_1, creationDate));
                        map.put("userModifiedDate", DateUtil.parseStringDatebyInt(Constants.DATE_FORMAT_1, creationDate));
                    }
                }
                rtnList.add(map);
            }
            List<Map> rstRtnList = new ArrayList<>();
            if (Common.notEmpty(searchActive)) {
                List<Map> finalRstRtnList = rstRtnList;
                rtnList.forEach(e -> {
                    boolean bActive = Boolean.parseBoolean(searchActive);
                    if (e.get("userActive").equals(bActive)) {
                        finalRstRtnList.add(e);
                    }
                });
                rstRtnList = finalRstRtnList;
            } else {
                rstRtnList = rtnList;
            }
            if (Common.empty(sSize)) {
                pageRequest = new PageRequest(0, rstRtnList.size());//, sort);
            } else {
                int page = Integer.parseInt(sPage);
                int size = Integer.parseInt(sSize);
                pageRequest = new PageRequest(page, size);//, sort);
            }
            int start = pageRequest.getPageNumber()*Integer.parseInt(sSize);
            long lSize = Long.parseLong(sSize);
            long totalSize =  rstRtnList.size();
            rstRtnList = rstRtnList.stream().skip(start).limit(lSize).collect(toList());
            Page pageImpl = new PageImpl(rstRtnList, pageRequest, totalSize);
            Map rtnMap = new HashMap();
            rtnMap.put("rtnMap", pageImpl);
            return rtnMap;
        }catch (Exception e){
            e.printStackTrace();
            return new HashMap();
        }
    }
}
