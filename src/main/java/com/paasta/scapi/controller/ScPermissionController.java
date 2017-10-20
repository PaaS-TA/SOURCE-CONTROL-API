package com.paasta.scapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.paasta.scapi.common.Common;
import com.paasta.scapi.entity.RepoPermission;
import com.paasta.scapi.service.RepoPermissionApiService;
import com.paasta.scapi.service.RepoPermissionDBService;
import com.paasta.scapi.service.ScUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * Created by lena on 2017-06-14.
 */
@RestController
@RequestMapping("/permissions")
public class ScPermissionController {

    @Autowired
    private RepoPermissionApiService repoPermissionApiService;

    @Autowired
    private RepoPermissionDBService repoPermissionDBService;

    @Autowired
    private
    ScUserService scUserService;

    /**
     * Repository 참여자 권한 추가
     * 참여자 권한 추가. ID가 없을 경우 사용자 추가후 참여자 추가.
     * 사전작업 : Repository 유무
     *
     * @param repositoryId string
     * @param jsonMap
     * @return
     * @throws JsonProcessingException
     */
    @SuppressWarnings("unchecked")
    @ApiOperation("레파지토리 참여자 권한 추가")
    @ApiResponses(@ApiResponse(response = Map.class, code = 201, message = "success"))
    @PutMapping("/{repositoryId}")
    @ResponseBody
    public ResponseEntity<String> updatePermissionByRepository(@PathVariable String repositoryId,  @RequestBody LinkedHashMap<?, Object> jsonMap) throws JsonProcessingException {
        ResponseEntity rssEntity;
        try {
            Map map =Common.convertMapByLinkedHashMap(jsonMap);
            RepoPermission permission = new RepoPermission((String)map.getOrDefault("userId",""), (String)map.getOrDefault("permission",""));
            rssEntity = repoPermissionApiService.insertRepositoryForUserAuth(repositoryId, permission);

        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return rssEntity;
    }

    /**
     * "레파지토리 참여자 권한 삭제"
     * 사전작업 : Repository 유무
     *
     * @param permissionIds permissionIds
     * @return
     * @throws JsonProcessingException
     */
    @SuppressWarnings("unchecked")
    @ApiOperation("레파지토리 참여자 권한 삭제")
    @ApiResponses(@ApiResponse(response = Map.class, code = 201, message = "success"))
    @DeleteMapping("/{ids}")
    public ResponseEntity<String> deletePermissionByRepository(@PathVariable("ids") String permissionIds) throws JsonProcessingException {
        if (Common.notEmpty(permissionIds)) {
            try {
                int id = Integer.parseInt(permissionIds);
                repoPermissionApiService.deleteRepositoryForUserAuth(id);
            } catch (Exception exception) {
                exception.printStackTrace();
                return new ResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 레파지 토리 참여자 조회
     * @param repoId
     * @return
     * @throws JsonProcessingException
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/{repoId}")
    public ResponseEntity getPermitionByRepoId(@PathVariable int repoId) throws JsonProcessingException {

        List<Map> lstMap = new ArrayList<>();
        List<RepoPermission> lst = repoPermissionDBService.selectByRepoId(repoId);
        Map mapUser = scUserService.getUsers("");
        List lstUser = (List) mapUser.get("rtnUser");
        for (RepoPermission repoPermission : lst) {
            String srepoUserId = repoPermission.getUserId();
            for (Object aLstUser : lstUser) {
                HashMap hashMap = (HashMap) aLstUser;
                if (hashMap.get("name").equals(srepoUserId)) {
                    hashMap.put("permission", repoPermission.getPermission());
                    lstMap.add(hashMap);
                }
            }
        }
        return new ResponseEntity(lstMap, HttpStatus.OK);

    }

    /**
     * 인스턴스별 레파지 토리 조회
     * @param instanceId
     * @param username
     * @param type
     * @param reposort
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    @GetMapping("/admin/{instanceId}")
    public ResponseEntity getPermitionByInstanced(@PathVariable String instanceId,
                                                      @RequestParam(value = "username", required = false) String username,
                                                      @RequestParam(value = "type", required = false) String type,
                                                      @RequestParam(value = "reposort", required = false) String reposort,
                                                      @RequestParam(value = "page", required = false, defaultValue = "0") int start,
                                                      @RequestParam(value = "size", required = false, defaultValue = "10") int end
                                                    ) throws Exception {
        return this.repoPermissionApiService.getListPermitionByInstanceId(instanceId, start, end, username);

    }

    /**
     * 레파지토리 아이디로 사용자 참여 권한 검색
     * @param searchUserId
     * @param repositoryId
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/search/repositoryId/")
    public ResponseEntity searchPermisionByUserIdAndRepositoryId(@RequestParam(value = "searchUserId") String searchUserId,@RequestParam(value = "repositoryId") String repositoryId) throws Exception {

        List lst = repoPermissionDBService.searchPermisionByUserIdAndRepositoryId(searchUserId, repositoryId);
        Map map = new HashMap();
        map.put("rtnList", lst);
        return new ResponseEntity(map, HttpStatus.OK);

    }
    /**
     * 인스턴스 아이디로 사용자 참여 권한 검색
     * @param searchUserId
     * @param instanceId
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/search/instanceId/{searchUserId}")
    public ResponseEntity searchPermisionByUserIdAndInstanceId(@PathVariable("searchUserId") String searchUserId
            ,@RequestParam(value = "instanceId") String instanceId
            ,@RequestParam(value = "repositoryId") String repositoryId) throws Exception {

        List lst = repoPermissionDBService.searchPermisionByUserIdAndInstanceId(searchUserId, instanceId, repositoryId);
        Map map = new HashMap();
        map.put("rtnList", lst);
        return new ResponseEntity(map, HttpStatus.OK);
    }
    /**
     * 인스턴스 아이디로 사용자 참여 권한 검색 //
     * http://localhost:9091/permissions/user/84383047-218e-4388-b6b4-93fd8599d8f5?searchUserId=&createYn=&active=&page=&size=
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/user/{instanceId}")
    @ResponseBody
    public ResponseEntity searchPermisionByInstanceId(@PathVariable("instanceId") String instanceId,
                                                      @RequestParam(value = "searchUserId", required = false ,defaultValue = "") String searchUserId,
                                                      @RequestParam(value = "createYn", required = false, defaultValue = "") String createYn,
                                                      @RequestParam(value = "active", required = false, defaultValue = "") String active,
                                                      @RequestParam(value = "page", required = false, defaultValue = "") String page,
                                                      @RequestParam(value = "size", required = false, defaultValue = "") String size
                                                    ) throws Exception {

        Map lst = repoPermissionApiService.permisionByInstanceIdAndParam(instanceId, searchUserId, createYn, active, page, size);
        return new ResponseEntity(lst, HttpStatus.OK);
    }
}

