package com.paasta.scapi.controller;

import com.paasta.scapi.common.Common;
import com.paasta.scapi.common.exception.RestException;
import com.paasta.scapi.entity.ScInstanceUser;
import com.paasta.scapi.repository.ScInstanceUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class ScInstanceUserController extends Common {

	@Autowired
	private ScInstanceUserRepository scInstanceUserRepository;

	/**
	 * 인스턴스별 사용자 조회
	 * @param instanceId
	 * @param userId
	 * @return
	 * @author 이인정
	 * @version 1.0
	 * @since 2017.8.16 최초작성
	 */
	@SuppressWarnings("unchecked")
	@GetMapping
	public ResponseEntity getUsers(@RequestParam(value = "instanceId", required = false) String instanceId
			, @RequestParam(value = "userId", required = false) String userId) throws RestException {
		List<ScInstanceUser> lstInstanceUsers = scInstanceUserRepository.findByInstanceIdContainingAndUserIdContaining(instanceId,userId);
		return new ResponseEntity(lstInstanceUsers, HttpStatus.OK);
	}

}
