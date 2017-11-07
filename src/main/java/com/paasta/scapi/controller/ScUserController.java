package com.paasta.scapi.controller;

import com.paasta.scapi.common.Common;
import com.paasta.scapi.common.exception.RestException;
import com.paasta.scapi.entity.ScUser;
import com.paasta.scapi.service.ScInstanceUserService;
import com.paasta.scapi.service.ScUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sonia.scm.user.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/users")
public class ScUserController extends CommonController {

	@Autowired
	private ScUserService scUserService;

	@Autowired
	private ScInstanceUserService scInstanceUserService;

	// 사용자 조회
	@SuppressWarnings("unchecked")
	@GetMapping
	public ResponseEntity getUsers() throws RestException {
		Map users = scUserService.getUsers();
		return new ResponseEntity(users, HttpStatus.OK);
	}

	/**
	 * 사용자 생성
	 * GIT 툴로 사용자를 생성했을경우 source control dash board 사용자로 추가하여야 멤버로 추가할수 있으므로 사용자  생성을 할 수 있도록 한다.
	 * {"name":"name_repo1"  ,"displayName":"displayName2"	 ,"mail":"mail3@aaa.co.kr" ,"password":"password"	}
	 * 메일의 경우 메일 형식이 아닐경우 scmmanager backend service에서 생성이 불가하다.
	 * @param jsonUser Json  형식의 사용자 정보
	 * @return
	 * @throws SQLException
	 */

	@SuppressWarnings("unchecked")
	@PostMapping
	public ResponseEntity createUser(@RequestBody LinkedHashMap<?, Object> jsonUser) throws SQLException {
		Map<String, Object> map = new HashMap<>();

		/* 상용자 정보조회 (DB) & sourcecontrol service*/
		Map mapScUserMap = scUserService.getUser((String) jsonUser.getOrDefault("name", ""));
		ScUser scUser = (ScUser)mapScUserMap.get("ScUser");
		User rtnUser = scUserService.getScmUser((String) jsonUser.getOrDefault("name", ""));

		if(Common.empty(scUser) ){
			scUser = new ScUser((String) jsonUser.getOrDefault("name", ""),
					(String) jsonUser.getOrDefault("displayName", ""), (String) jsonUser.getOrDefault("mail", ""), (String) jsonUser.getOrDefault("desc", ""));
			scUser = scUserService.save(scUser);
		}
		if(Common.empty(rtnUser)){
            scUserService.apiCreateUser(jsonUser).getBody();
			rtnUser = scUserService.getScmUser((String) jsonUser.getOrDefault("name", ""));
		}

		map.put("scUser", scUser);
		map.put("rtnUser", rtnUser);
		map.put("status", HttpStatus.OK);

		return new ResponseEntity(map, HttpStatus.OK);

	}

	// 사용자 삭제
    @DeleteMapping("/{name}")
	public ResponseEntity deleteUser(@PathVariable("name") String name) throws SQLException {
        this.scUserService.restDeleteUser(name);
		//scUserService.delete(name);
		return new ResponseEntity(HttpStatus.OK);

	}

	// 사용자 수정
	@SuppressWarnings("unchecked")
    @PutMapping("/{name}")
    public ResponseEntity updateUser(@PathVariable("name") String id, @RequestBody LinkedHashMap<?, Object> jsonUser) throws SQLException {
        ScUser scUser = scUserService.restUpdateUser((String)jsonUser.get("name"), jsonUser);
        return  new ResponseEntity(scUser,HttpStatus.OK);

    }


	// 사용자 상세정보조회
    @GetMapping("/user/{name}")
	@ResponseBody
	public ResponseEntity<Map> getDetailUser(@PathVariable("name") String name) throws Exception {
		try {
			Map map = scUserService.getUser(name);
			return new ResponseEntity<>(map, HttpStatus.OK);
		}catch (Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	/**
	 * desc query sort direction desc or aesc false boolean limit query the
	 * limit value for paging -1 int sortby query sort parameter start query the
	 * start value for paging 0 int
	 *
	 * @param instanceId  사용자 포함될 이름
	 * @return
	 * @throws RestException
	 */

	// 인스턴스별 사용자 조회 (query)
	@SuppressWarnings("unchecked")
    @GetMapping("/admin/{instanceId}")
	public ResponseEntity getListUsersByName(@PathVariable("instanceId") String instanceId, HttpServletRequest request) throws RestException {

		Map map = Common.rtnMapByRequestParam(request);

		String name = request.getParameter("name");
		int page = (int) map.getOrDefault("page", 0);
		int size = (int) map.getOrDefault("size", 10);
		String sdirection = (String) map.getOrDefault("direction", "ASC");
		String[] properties = ((List<String>) map.get("proierties")).toArray(new String[0]);
		Direction direction = Direction.fromString(sdirection);
		Sort sort = new Sort(direction, properties);
		PageRequest pageRequest =  new PageRequest(page, size, sort);
		return this.scUserService.apiGetUsersByName(instanceId, name, pageRequest);

	}
	/**
	 * desc query sort direction desc or aesc false boolean limit query the
	 * limit value for paging -1 int sortby query sort parameter start query the
	 * start value for paging 0 int
	 *
	 * @param repositoryId  사용자 포함될 이름
	 * @return
	 * @throws RestException
	 */
	// 레파지토리별 사용자 조회 (query)
	@SuppressWarnings("unchecked")
    @GetMapping("/repository/{repositoryId}")
	public ResponseEntity getListUsersByRepositoryId(@PathVariable("repositoryId") String repositoryId, HttpServletRequest request) throws Exception {
		logger.debug("getListUsersByRepositoryId::repositoryId"+"::"+repositoryId);
		Map map = Common.rtnMapByRequestParam(request);
		int page = Integer.parseInt((String) map.getOrDefault("page", "0"));
		int size = Integer.parseInt((String) map.getOrDefault("size", "10"));
		String permission = (String) map.getOrDefault("type1", "");
		String active = (String)map.getOrDefault("type2", "");
		String sdirection = (String) map.getOrDefault("direction", "DESC");
		String searchUserName = (String) map.getOrDefault("searchUserName", "");
		List lstProperties = (List) map.getOrDefault("proierties", new ArrayList<>());
		if(lstProperties.size()==0){
			lstProperties.add("no");
		}
		PageRequest pageRequest =  new PageRequest(page, size);//, sort);
		return scUserService.getUsersByrepositoryId(repositoryId, searchUserName, permission, active, pageRequest);

	}

	// 인스턴스 사용자 삭제
	@DeleteMapping("/{name}/{instance}")
	public ResponseEntity deleteUser(@PathVariable("name") String name, @PathVariable("instance") String instanceid) throws Exception {
		scInstanceUserService.restInstanceDeleteUser(instanceid, name);
		return new ResponseEntity(HttpStatus.OK);

	}
}
