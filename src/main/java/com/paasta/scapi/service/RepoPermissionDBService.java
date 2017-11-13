package com.paasta.scapi.service;

import com.paasta.scapi.common.Common;
import com.paasta.scapi.common.Constants;
import com.paasta.scapi.common.util.DateUtil;
import com.paasta.scapi.entity.RepoPermission;
import com.paasta.scapi.entity.ScInstanceUser;
import com.paasta.scapi.entity.ScRepository;
import com.paasta.scapi.entity.ScUser;
import com.paasta.scapi.repository.RepoPermissionRepository;
import com.paasta.scapi.repository.ScInstanceUserRepository;
import com.paasta.scapi.repository.ScRepositoryRepository;
import com.paasta.scapi.repository.ScUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sonia.scm.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lena on 2017-06-14.
 */
@Service
public class RepoPermissionDBService extends CommonService{

    @Autowired
    private
    RepoPermissionRepository repoPermissionRepository;

    @Autowired
    private
    ScUserRepository scUserRepository;

    @Autowired
    private
    ScRepositoryRepository scRepositoryRepository;

    @Autowired
    private
    ScInstanceUserRepository scInstanceUserRepository;

    @Autowired
    private
    ScUserService scUserService;

    @SuppressWarnings("unchecked")
    @Transactional
    public List searchPermisionByUserIdAndRepositoryId(String searchUserId, String repoId){
        //아이디 검색된 사용자로 사용자를 가져온다.
        List<ScRepository> scRepository = scRepositoryRepository.findAllByRepoScmId(repoId);
        List<ScUser> lstScUserBefore = scUserRepository.findAllByUserIdContaining(searchUserId);
        List<ScUser> lstScUser = new ArrayList<>();
        List<User> lstUser = (List<User>) scUserService.apiGetUsers().getBody();
        for (ScUser scUser : lstScUserBefore) {
            for (User User : lstUser) {
                if(scUser.getUserId().equals(User.getName())){
                    lstScUser.add(scUser);
                }
            }
        }
        List<RepoPermission> lstRepoPermission = repoPermissionRepository.findAllByRepoNo(scRepository.get(0).getRepoNo());
        List<Map> rtnList = new ArrayList();

        for (ScUser aLstScUser : lstScUser) {
            Map map = new HashMap();
            map.put("userId", Common.notNullrtnByobj(aLstScUser.getUserId(), ""));
            map.put("userName", Common.notNullrtnByobj(aLstScUser.getUserName(), ""));
            map.put("userEmail", Common.notNullrtnByobj(aLstScUser.getUserMail(), ""));
            map.put("userPermission", "");
            map.put("userPermissionNo", "");
            rtnList.add(map);
        }
        for (RepoPermission aLstRepoPermission : lstRepoPermission) {
            for (Map map : rtnList) {
                if (map.get("userId").equals(aLstRepoPermission.getUserId())) {
                    map.replace("userPermission", aLstRepoPermission.getPermission());
                    map.replace("userPermissionNo", aLstRepoPermission.getNo());
                }
            }
        }
        return rtnList;
    }

    /**
     * 추가 : scm에 없는 사용자 제외/
     * 2017.09.14 인스턴스 사용자만 조회
     * @param searchUserId
     * @param instanceId
     * @return
     */

    @SuppressWarnings("unchecked")
    @Transactional
    public List searchPermisionByUserIdAndInstanceId(String searchUserId, String instanceId, String repositoryId){
        List rtnList = new ArrayList();
        try{
            //아이디 검색된 사용자로 사용자를 가져온다.
            List<ScRepository> scRepository = scRepositoryRepository.findAllByRepoScmId(repositoryId);
            List<ScInstanceUser> lstScInstanceUsers = scInstanceUserRepository.findByInstanceIdAndUserIdIsContainingAndCreaterYnContaining(instanceId,searchUserId,"");
            List<ScUser> lstScUser = scUserRepository.findAllByUserIdContaining(searchUserId);
            List<RepoPermission> lstRepoPermission = repoPermissionRepository.findAllByRepoNo(scRepository.get(0).getRepoNo());


            for (ScInstanceUser lstScInstanceUser : lstScInstanceUsers) {
                Map map = new HashMap();
                for (ScUser scUser : lstScUser) {
                    if (scUser.getUserId().equals(lstScInstanceUser.getUserId())) {
                        map.put("no", lstScInstanceUser.getNo());
                        map.put("userId", Common.notNullrtnByobj(scUser.getUserId(), ""));
                        map.put("userName", Common.notNullrtnByobj(scUser.getUserName(), ""));
                        map.put("userEmail", Common.notNullrtnByobj(scUser.getUserMail(), ""));
                        map.put("userRepoRole", lstScInstanceUser.getRepoRole());
                        map.put("userCreateYn", lstScInstanceUser.getCreaterYn());
                        map.put("userCreatedDate", DateUtil.rtnFormatString(Constants.DATE_FORMAT_1, lstScInstanceUser.getCreatedDate()));
                        map.put("userModifiedDate", DateUtil.rtnFormatString(Constants.DATE_FORMAT_1, lstScInstanceUser.getModifiedDate()));
                        map.put("userPermission", "");
                        map.put("userPermissionNo", "");
                        for (RepoPermission permission : lstRepoPermission) {
                            if (scUser.getUserId().equals(permission.getUserId())) {
                                map.replace("userPermission", permission.getPermission());
                                map.replace("userPermissionNo", permission.getNo());
                            }
                        }
                    }
                }
                rtnList.add(map);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return rtnList;
    }

}
