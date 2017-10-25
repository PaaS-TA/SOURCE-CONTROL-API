package com.paasta.scapi.service;

import com.paasta.scapi.entity.RepoPermission;
import com.paasta.scapi.entity.ScInstanceUser;
import com.paasta.scapi.entity.ScRepository;
import com.paasta.scapi.repository.RepoPermissionRepository;
import com.paasta.scapi.repository.ScInstanceUserRepository;
import com.paasta.scapi.repository.ScRepositoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author ijlee
 *
 */
@Service
public class ScInstanceUserService{

	@Autowired
	private
	ScInstanceUserRepository scInstanceUserRepository;

	@Autowired
	private ScRepositoryRepository scRepositoryRepository;

	@Autowired
	private ScUserService scUserService;

	@Autowired
	private RepoPermissionRepository repoPermissionRepository;

	@Autowired
	private RepoPermissionApiService repoPermissionApiService;

	public List<ScInstanceUser> getAll(){
		return scInstanceUserRepository.findAll();
	}
	
	public List<ScInstanceUser> getByInstanceId(String instanceId){
		return scInstanceUserRepository.findByInstanceId(instanceId);
	}

	
	public List<ScInstanceUser> getByInstanceIdAndUserId(String instanceId,String userId){
		return scInstanceUserRepository.findByInstanceIdAndUserId(instanceId,userId);
	}

	
	@SuppressWarnings("unchecked")
    @Transactional
	public ResponseEntity createInstanceUser(Map<String, String> map){
		ResponseEntity responseEntity ;
		Map rtnMap = new HashMap();

		//instance에 사용자 추가
		ScInstanceUser scMap = new ScInstanceUser(
				map.getOrDefault("instanceId","")
				, map.getOrDefault("userId","")
				, map.getOrDefault("repoRole","owner")
				, map.getOrDefault("createrYn","N")
		);
		ScInstanceUser scInstanceUser = scInstanceUserRepository.save(scMap);
		rtnMap.put("scInstanceUser", scInstanceUser);
		responseEntity = new ResponseEntity(rtnMap, HttpStatus.OK);
		return responseEntity;
	}


	@SuppressWarnings("unchecked")
	@Transactional
	public void restInstanceDeleteUser(String instance, String name) throws Exception{

		// 1. 사용자의 instance 정보 조회

		List<ScInstanceUser> lst = scInstanceUserRepository.findByUserId(name);
		List<ScInstanceUser> instanceUsers = scInstanceUserRepository.findByInstanceIdAndUserId(instance, name);
		//인스턴스에 해당하는 레파지토리 정보조회
		List<ScRepository> lstScrepository = scRepositoryRepository.findAllByInstanceId(instance);

		//다른 인스턴스에서 사용중인 사용자인지 확인한다.
		boolean userDeleteYn = true;
		for (ScInstanceUser scInstanceUser : lst) {
			if (!instance.equals(scInstanceUser.getInstanceId())) {
				userDeleteYn = false;
			}
		}
		//다른 인스턴스에서 사용중이지 않은 사용자 (사용자 정보삭제)
		if(userDeleteYn){
			scUserService.restDeleteUser(name);
		}

		// DB 인스턴스 사용자 정보삭제
		instanceUsers.forEach(scInstanceUser -> scInstanceUserRepository.delete(scInstanceUser.getNo()));

		// DB 인스턴스 레파지토리 사용자 Permission 정보 삭제
		lstScrepository.forEach(scRepository -> {
			//레파지토리 넘버에 해당하는 사용자 퍼미션 삭제
			int repoNo = scRepository.getRepoNo();
			List<RepoPermission> lstRepoPermission = repoPermissionRepository.findAllByRepoNoAndUserId(repoNo,name);
			lstRepoPermission.forEach(repoPermission -> repoPermissionApiService.deleteRepositoryForUserAuth(repoPermission.getNo()));
		});

		// API 인스턴스 레파지토리 사용자 Permission 정보 삭제


	}
}
