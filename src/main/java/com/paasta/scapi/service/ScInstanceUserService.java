package com.paasta.scapi.service;

import com.paasta.scapi.entity.ScInstanceUser;
import com.paasta.scapi.repository.ScInstanceUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	
	public ScInstanceUser update(ScInstanceUser scInstanceUser){
		return this.scInstanceUserRepository.save(scInstanceUser);
	}

	
	public void delete(int no){
        scInstanceUserRepository.delete(no);
	}


}
