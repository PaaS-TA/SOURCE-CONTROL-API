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
    RepoPermissionRepository repoPermissionRepository;

    @Autowired
    ScUserRepository scUserRepository;

    @Autowired
    ScRepositoryRepository scRepositoryRepository;

    @Autowired
    ScInstanceUserRepository scInstanceUserRepository;

    @Autowired
    ScUserService scUserService;

    
    @Transactional
    public RepoPermission save(RepoPermission repoPermission){
        return repoPermissionRepository.save(repoPermission);
    }

    
    @Transactional
    public List<RepoPermission> save(List<RepoPermission> lstRepoPermission){

        return this.repoPermissionRepository.save(lstRepoPermission);
    }

      
    @Transactional
    public void delete(int permissionId){
          this.repoPermissionRepository.delete(permissionId);
    }

    
    @Transactional
    public List<RepoPermission> selectByRepoId(int repoId){
        return this.repoPermissionRepository.findAllByRepoNo(repoId);
    }

    
    @Transactional
    public List searchPermisionByUserIdAndRepositoryId(String searchUserId, String repoId){
        //아이디 검색된 사용자로 사용자를 가져온다.
        List<ScRepository> scRepository = scRepositoryRepository.findAllByRepoScmId(repoId);
        List<ScUser> lstScUserBefore = scUserRepository.findAllByUserIdContaining(searchUserId);
        List<ScUser> lstScUser = new ArrayList<ScUser>();
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

        for(int i=0; i < lstScUser.size(); i++){
            Map map = new HashMap();
            ScUser scUser = lstScUser.get(i);
            map.put("userId",Common.notNullrtnByobj(scUser.getUserId(),""));
            map.put("userName", Common.notNullrtnByobj(scUser.getUserName(),""));
            map.put("userEmail",Common.notNullrtnByobj(scUser.getUserMail(),""));
            map.put("userPermission","");
            map.put("userPermissionNo","");
            rtnList.add(map);
        }
        for(int j=0; j < lstRepoPermission.size(); j++){
            for(int i=0; i < rtnList.size(); i++){
                Map map = rtnList.get(i);
                if(map.get("userId").equals(lstRepoPermission.get(j).getUserId())){
                    map.replace("userPermission",lstRepoPermission.get(j).getPermission());
                    map.replace("userPermissionNo",lstRepoPermission.get(j).getNo());
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
    
    @Transactional
    public List searchPermisionByUserIdAndInstanceId(String searchUserId, String instanceId, String repositoryId){
        List rtnList = new ArrayList();
        try{
            //아이디 검색된 사용자로 사용자를 가져온다.
            List<ScRepository> scRepository = scRepositoryRepository.findAllByRepoScmId(repositoryId);
            List<ScInstanceUser> lstScInstanceUsers = scInstanceUserRepository.findByInstanceIdAndUserIdIsContainingAndCreaterYnContaining(instanceId,searchUserId,"");
            List<ScUser> lstScUser = scUserRepository.findAllByUserIdContaining(searchUserId);
            List<RepoPermission> lstRepoPermission = repoPermissionRepository.findAllByRepoNo(scRepository.get(0).getRepoNo());


            for(int j=0; j < lstScInstanceUsers.size(); j++) {
                Map map = new HashMap();
                ScInstanceUser scInstanceUser = lstScInstanceUsers.get(j);
                for (int i = 0; i < lstScUser.size(); i++) {
                    ScUser scUser = lstScUser.get(i);
                    if(scUser.getUserId().equals(scInstanceUser.getUserId())) {
                        map.put("no",lstScInstanceUsers.get(j).getNo());
                        map.put("userId", Common.notNullrtnByobj(scUser.getUserId(), ""));
                        map.put("userName", Common.notNullrtnByobj(scUser.getUserName(), ""));
                        map.put("userEmail", Common.notNullrtnByobj(scUser.getUserMail(), ""));
                        map.put("userRepoRole",lstScInstanceUsers.get(j).getRepoRole());
                        map.put("userCreateYn",lstScInstanceUsers.get(j).getCreaterYn());
                        map.put("userCreatedDate", DateUtil.rtnFormatString(Constants.DATE_FORMAT_1,scInstanceUser.getCreatedDate()));
                        map.put("userModifiedDate",DateUtil.rtnFormatString(Constants.DATE_FORMAT_1,scInstanceUser.getModifiedDate()));
                        map.put("userPermission","");
                        map.put("userPermissionNo","");
                        for(int k=0; k < lstRepoPermission.size(); k++){
                            RepoPermission permission = lstRepoPermission.get(k);
                            if(scUser.getUserId().equals(lstRepoPermission.get(k).getUserId())){
                                map.replace("userPermission",permission.getPermission());
                                map.replace("userPermissionNo",permission.getNo());
                            }
                        }
                    }
                }
                rtnList.add(map);
            }}catch(Exception e){
            e.printStackTrace();
        }
        return rtnList;
    }

}
