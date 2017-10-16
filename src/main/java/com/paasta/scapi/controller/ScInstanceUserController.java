package com.paasta.scapi.controller;

import com.paasta.scapi.common.Common;
import com.paasta.scapi.common.exception.RestException;
import com.paasta.scapi.entity.ScInstanceUser;
import com.paasta.scapi.entity.ScUser;
import com.paasta.scapi.service.ScInstanceUserService;
import com.paasta.scapi.service.ScUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
@Api(description = "Instance 권한 관리를 위한 Api")
public class ScInstanceUserController extends Common {

	@Autowired
	private ScInstanceUserService scInstanceUserService;

	@Autowired
	private ScUserService scUserService;

	/**
	 * 인스턴스별 사용자 조회
	 * @param instanceId
	 * @param userId
	 * @return
	 * @throws RestException
	 * @author 이인정
	 * @version 1.0
	 * @since 2017.8.16 최초작성
	 */
	@ApiOperation(value = "모든 권한 조회", response = ResponseEntity.class)
	@GetMapping
	@ApiImplicitParam
    @ApiResponses(@ApiResponse(code = 200, message = "success", response = ResponseEntity.class))
	public ResponseEntity getUsers(@RequestParam(value = "instanceId", required = false) String instanceId
			, @RequestParam(value = "userId", required = false) String userId) throws RestException {
		List<ScInstanceUser> lstInstanceUsers = new ArrayList<>();
		if(Common.empty(instanceId)){
			lstInstanceUsers = scInstanceUserService.getAll();
		}else{
			if(Common.empty(userId)){
				lstInstanceUsers = scInstanceUserService.getByInstanceId(instanceId);
			}else{
				lstInstanceUsers = scInstanceUserService.getByInstanceIdAndUserId(instanceId,userId);
			}
		}
		return new ResponseEntity(lstInstanceUsers, HttpStatus.OK);
	}


	@GetMapping("/synch")
	public ResponseEntity synch() throws Exception{

		List<ScUser> lst = scUserService.getAll();
		lst.forEach(scUser -> {

			System.out.println("scUser.getUserId():::"+scUser.getUserId());
			System.out.println("scUserService.getScmUser(scUser.getUserId()).getId():::"+(scUserService.getScmUser(scUser.getUserId()).getId()));
			if(Common.empty(scUserService.getScmUser(scUser.getUserId()).getId())){
				scUserService.delete(scUser.getUserId());
			}
		});

		return new ResponseEntity(HttpStatus.OK);
	}

}
