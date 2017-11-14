package com.paasta.scapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paasta.scapi.common.Common;
import com.paasta.scapi.common.util.DateUtil;
import com.paasta.scapi.common.util.RestClientUtil;
import com.paasta.scapi.entity.RepoPermission;
import com.paasta.scapi.entity.ScRepository;
import com.paasta.scapi.entity.ScUser;
import com.paasta.scapi.repository.RepoPermissionRepository;
import com.paasta.scapi.repository.ScRepositoryRepository;
import com.paasta.scapi.repository.ScUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import sonia.scm.util.ValidationUtil;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by lena on 2017-06-14.
 */
/**
 * @author ijlee
 *
 */
@Service
public class ScUserService extends CommonService {

	@Autowired
	private
	ScUserRepository scUserRepository;

	@Autowired
	private
	ScRepositoryRepository scRepositoryRepository;

	@Autowired
	private
	RepoPermissionRepository repoPermissionRepository;

	/*@Autowired
	private
	ScInstanceUserRepository scInstanceUserRepository;

	@Autowired
	private
	ScRepositoryApiService scRepositoryApiService;*/
	@Autowired
	RepoPermissionApiService repoPermissionApiService;
	@Autowired
	private
	RestClientUtil restClientUtil;

	@Value("${api.users}")
    private String userUrl;

	/**
	 * DB에서 사용자 전체 목록을 확인한다.
	 * @param
	 * @return List<ScUser>
	 * @throws
	 * @author 최세지
	 * @version 1.0
	 * @since 2017.09.15 최초작성
	 */
	
	public List<ScUser> getAll() {
		return this.scUserRepository.findAll();
	}


	/**
	 * DB에서 사용자 변경 내역을 저장한다.
	 * @param scUser
	 * @return
	 * @throws
	 * @author 최세지
	 * @version 1.0
	 * @since 2017.09.15 최초작성
	 */
	
	public ScUser save(ScUser scUser) {
		return scUserRepository.save(scUser);
	}


	/**
	 * DB에서 사용자 변경 내역을 수정한다.
	 * @param scUser
	 * @return
	 * @throws
	 * @author 최세지
	 * @version 1.0
	 * @since 2017.09.15 최초작성
	 */

	/**
	 * SCM API의 기능으로 사용자 삭제를 구현한다.
	 * @param name
	 * @return
	 * @author 최세지
	 * @version 1.0
	 * @since 2017.09.15 최초작성
	 */
	
	@SuppressWarnings("unchecked")
	@Transactional
	public void restDeleteUser(String name) {
//		String rspApp = "";
        this.logger.info("restDeleteUser Start : ");
		try {
//			HttpEntity httEntity = this.restClientUtil.restCommonHeaders(null);
			scUserRepository.delete(name);
			scmAdminSession().getUserHandler().delete(name);
//			rspApp = this.restClientUtil.callRestApi(HttpMethod.DELETE, this.userUrl +"/"+name, httEntity, Object.class).getBody().toString();
		}catch (Exception exception){
			exception.printStackTrace();
		}
//        this.logger.info("restDeleteUser End "+ rspApp);
	}

	/**
	 * Scm API의 기능으로 사용자 수정을 구현한다.
	 * @param name
	 * @param jsonUser
	 * @return
	 * @author 최세지
	 * @version 1.0
	 * @since 2017.09.15 최초작성
	 */
	
	@SuppressWarnings("unchecked")
	@Transactional
		public ScUser restUpdateUser(String name, LinkedHashMap jsonUser) {
		ScUser rtnScUser = new ScUser();
		try {
			String rspApp = "";
			logger.info("restUpdateUser Start : ");
//			JSONObject jsonObject = new JSONObject(jsonUser);
//			String request = jsonObject.toString();

			String displayName = (String) jsonUser.getOrDefault("displayName", "");
			String mail = (String) jsonUser.getOrDefault("mail", "");
			boolean admin = Boolean.parseBoolean((String) jsonUser.getOrDefault("admin", false));
			boolean active = Boolean.parseBoolean((String) jsonUser.getOrDefault("active", false));
			String password = (String) jsonUser.getOrDefault("password", "\"__dummypassword__\"");
			String passwordSet = (String) jsonUser.getOrDefault("PasswordSet", "false");
			String description = (String) jsonUser.getOrDefault("desc", "");
			ScUser scUser = new ScUser(name, displayName, mail,description);
			rtnScUser = scUserRepository.save(scUser);
			/**
			 * syntax check 를 두어 smc 수정오류를 방지함.
			 */
			if(!ValidationUtil.isMailAddressValid(mail)){
				mail="";
			}
			if(Common.empty(displayName)){
				displayName=name;
			}
			UserClientHandler userClientHandler = scmAdminSession().getUserHandler();
			User user = userClientHandler.get(name);
			user.setDisplayName(displayName);
			user.setPassword(password);
			user.setActive(active);
			user.setAdmin(admin);
			user.setMail(mail);
			if(!Common.empty(passwordSet)){
//				Map map =user.getProperties();
				user.setProperty("PasswordSet",passwordSet);
			}

			scmAdminSession().getUserHandler().modify(user);

//        this.restClientUtil.callRestApi(HttpMethod.PUT, this.userUrl +"/"+name , httEntity, String.class);
			this.logger.info("restUpdateUser End " + rspApp);
		}catch (Exception e){
			e.printStackTrace();
		}
        return rtnScUser;
	}


	/**
	 * Scm APT의 기능으로 사용자 생성을 구현한다.
	 * 사용자 정보 조회 사용자가 DB 와 SourceControl Service 에 모두 존재하는지 확인 후 DB에 없을경우, "사용자가 존재 하지 않습니다."를 return 해 준다.
	 * @param name
	 * @return Map key : "ScUser" value : DB 사용자 정보
	 * 				key : "rtnUser" value : SourceControl 사용자 정보
	 * @author 최세지
	 * @version 1.0
	 * @since 2017.09.15 최초작성
	 */
	
	@SuppressWarnings("unchecked")
	public Map getUser(String name){

		Map rspApp = new HashMap();
        this.logger.debug("getUser Start : " + name);
		ScUser scUser = scUserRepository.findOne(name);
		if(Common.empty(scUser)) {
			rspApp.put("ScUser", null);
			rspApp.put("rtnUser",null);
			rspApp.put("status",HttpStatus.NOT_FOUND.value());
			rspApp.put("message","사용자 존재하지 않습니다.");
			return rspApp;
		}else {
			User user = getScmUser(name);
			rspApp.put("ScUser", scUser);
			rspApp.put("rtnUser", user);
			rspApp.put("status", HttpStatus.OK.value());
			rspApp.put("message", "사용자 정보 조회에 성공하였습니다.");
			return rspApp;
		}
	}

	/**
	 * DB와 Scm APT 사용자 정보 내역을 가져온다.
	 * @param name
	 * @return
	 * @author 최세지
	 * @version 1.0
	 * @since 2017.09.15 최초작성
	 */
	
	public User getScmUser(String name) {
        this.logger.debug("getScmUser Start : " + name);
		return scmAdminSession().getUserHandler().get(name);
	}

	/**
	 * Scm APT의 기능으로 사용자의 상세정보를 확인한다.
	 * @param
	 * @return
	 * @author 최세지
	 * @version 1.0
	 * @since 2017.09.15 최초작성
	 */

	@SuppressWarnings("unchecked")
	private ResponseEntity restGetAllUsers(){
        this.logger.debug("getUser Start : ");
		HttpEntity httEntity = this.restClientUtil.restCommonHeaders(null);
		return this.restClientUtil.callRestApi(HttpMethod.GET, this.userUrl, httEntity, List.class);
	}


	/**
	 * DB에 입력된 사용자의 상세정보를 확인한다.
	 * @return
	 * @author 최세지
	 * @version 1.0
	 * @since 2017.09.15 최초작성
	 */
	
	@SuppressWarnings("unchecked")
	public Map getUsers() {

		Map rspApp = new HashMap();
        this.logger.debug("getUser Start : ");
		List rtnList = new ArrayList();
		ResponseEntity responseEntity = restGetAllUsers();
		List<ScUser> users = scUserRepository.findAll();
		if (null != responseEntity.getBody()){
			for (ScUser scUser : users) {
				List<LinkedHashMap> list = (List<LinkedHashMap>) responseEntity.getBody();
				for (LinkedHashMap linkedHashMap : list) {
					if (scUser.getUserId().equals(linkedHashMap.get("name"))) {
						Map rtnMap = new HashMap();
						String creationDate = Objects.equals(String.valueOf(linkedHashMap.get("creationDate")), "null") ? "" : String.valueOf(linkedHashMap.get("creationDate"));
						String lastModified = Objects.equals(String.valueOf(linkedHashMap.get("lastModified")), "null") ? "" : String.valueOf(linkedHashMap.get("lastModified"));
						linkedHashMap.replace("creationDate", DateUtil.parseStringDatebyInt("yyyy-MM-dd HH:mm:ss", creationDate));
						linkedHashMap.replace("lastModified", DateUtil.parseStringDatebyInt("yyyy-MM-dd HH:mm:ss", lastModified));
						rtnMap.putAll(linkedHashMap);
						rtnMap.put("desc", scUser.getUserDesc());
						rtnList.add(rtnMap);
					}
				}
			}
		}
		rspApp.put("rtnUser",rtnList);
        logger.debug("getUser End "+ rspApp);
		return rspApp;
	}


	/**
	 * DB와 Scm APT 사용자 생성 내역을 가져온다.
	 * @param jsonUser
	 * @return
	 * @author 최세지
	 * @version 1.0
	 * @since 2017.09.15 최초작성
	 */
	
	@SuppressWarnings("unchecked")
	public ResponseEntity apiCreateUser(LinkedHashMap jsonUser) {
		try {
            logger.info(getClass().getName() + " : apiCreateUser start");
			Map jsonUser2 =Common.convertMapByLinkedHashMap(jsonUser);
			String name = (String) jsonUser2.getOrDefault("name", "");
			String displayName = (String) jsonUser2.getOrDefault("displayName", null);
			String mail = (String) jsonUser2.getOrDefault("mail", "");
			String type = (String) jsonUser2.getOrDefault("type", "xml");
			String password = (String)jsonUser2.getOrDefault("password", "");
			String passwordSet = (String)jsonUser2.getOrDefault("PasswordSet", "false");
			User user = new User(name);//, displayName, mail);
			if(!Common.empty(displayName)){
				user.setDisplayName(displayName);
			}
			if(!Common.empty(mail)){
				user.setMail(mail);
			}
			user.setType(type);

			if( !Common.empty(password)){
				user.setPassword(password);
			}

			user.setProperty("PasswordSet",passwordSet);
			scmAdminSession().getUserHandler().create( user );
            logger.info(getClass().getName() + " : apiCreateUser end");

		}catch (Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity(HttpStatus.OK);
	}


	/**
	 *  Scm APT의 기능으로 검색기능을 통해 사용자를 확인 후, 사용자 정보를 가져온다.
	 * @param
	 * @param
	 * @return
	 * @author 최세지
	 * @version 1.0
	 * @since 2017.09.15 최초작성
	 */
	
	public ResponseEntity<List> apiGetUsers() {
		try {
            this.logger.info(getClass().getName() + " : apiGetUsers start");
			List userList = this.scmAdminSession().getUserHandler().getAll();
            this.logger.info(getClass().getName() + " : apiGetUsers end");
			return new ResponseEntity<>(userList, null, HttpStatus.OK);

		}catch (Exception e){
			e.printStackTrace();
			return new ResponseEntity<>(null, null, HttpStatus.FORBIDDEN);
		}
	}


	/**
	 * Scm APT의 기능으로 인스턴스별 사용자리스트 조회를 한다.
	 * @param instanceId
	 * @param userName
	 * @param pageRequest
	 * @return
	 * @author 최세지
	 * @version 1.0
	 * @since 2017.09.15 최초작성
	 */
	
/*	public ResponseEntity<Page> apiGetUsersByName(String instanceId, String userName, PageRequest pageRequest) {
		try {
            this.logger.info(getClass().getName() + " : apiGetUsers start::instanceId::"+instanceId);

			*//** 인스턴스별 레파지 토리 정보가져오기 *//*

			List<Repository> list = this.scmAdminSession().getRepositoryHandler().getAll();
			List rtnList = new ArrayList();


			Page<ScUser> page = this.scUserRepository.findByUserNameContaining(userName, pageRequest);
			List userList = this.scmAdminSession().getUserHandler().getAll();
//			List list = page.getContent();

            this.logger.info(getClass().getName() + " : apiGetUsers end");
			return new ResponseEntity<>(page, null, HttpStatus.OK);

		}catch (Exception e){
			e.printStackTrace();
			return new ResponseEntity<>(null, null, HttpStatus.FORBIDDEN);
		}
	}*/


	/**
	 * Scm APT의 기능으로 레파지토리별 부분사용자 조회를 한다.
	 * @param repositoryId
	 * @param searchUserName
	 * @param searchPermission
	 * @param active
	 * @param pageRequest
	 * @return
	 * * @author 최세지
	 * @version 1.0
	 * @since 2017.09.15 최초작성
	 */
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<Map> getUsersByrepositoryId(String repositoryId, String searchUserName, String searchPermission, String active, PageRequest pageRequest) {
		try {
            logger.info(getClass().getName() + " : apiGetUsers start");
			List<ScRepository> scRepository = scRepositoryRepository.findAllByRepoScmId(repositoryId);

			// 전체 사용자 가져오기
			UserClientHandler userClientHandler = scmAdminSession().getUserHandler();
			List<User> lstUser = userClientHandler.getAll();
			Map rssMap = new HashMap();

			List<RepoPermission> lstPermission ;
			if(Common.empty(searchPermission)){
				lstPermission = repoPermissionRepository.findAllByRepoNo(scRepository.get(0).getRepoNo());
			}else{
				lstPermission = repoPermissionRepository.findAllByRepoNoAndPermission(scRepository.get(0).getRepoNo(), searchPermission);
			}

			List rtnList = new ArrayList();
			lstPermission.forEach(permission -> lstUser.forEach(user -> {
                if (permission.getUserId().equals(user.getName())) {
                    Map rtnMap = new HashMap();
                    String creationDate = user.getCreationDate() == null ? "" : String.valueOf(user.getCreationDate());
                    String lastModified = user.getLastModified() == null ? "" : String.valueOf(user.getLastModified());
                    rtnMap.put("creationDate", DateUtil.parseStringDatebyInt("yyyy-MM-dd HH:mm:ss", creationDate));
                    rtnMap.put("lastModified", DateUtil.parseStringDatebyInt("yyyy-MM-dd HH:mm:ss", lastModified));
                    rtnMap.put("name", user.getName());
                    rtnMap.put("active", user.isActive());
                    rtnMap.put("admin", user.isAdmin());
					rtnMap.put("email", user.getMail());
                    rtnMap.put("displayName", user.getDisplayName());
                    rtnMap.put("permission", permission);
                    rtnList.add(rtnMap);
                }
            }));

			if(Common.notEmpty(searchUserName) || Common.notEmpty(active)) {
				List rssList = new ArrayList();

				rtnList.forEach(hashMap -> {

					ObjectMapper objectMapper = new ObjectMapper();
					HashMap map = objectMapper.convertValue(hashMap, HashMap.class);
					//searchUserName이 있을경우 필터한다.
					if(Common.notEmpty(searchUserName)) {
						if (map.getOrDefault("displayName", "").toString().contains(searchUserName) || map.getOrDefault("name", "").toString().contains(searchUserName)) {
							rssList.add(map);
						}
					}
					//active가 있을경우 필터한다.
					if(Common.notEmpty(active)) {
						boolean bActive = Boolean.parseBoolean(active);
						if (map.get("active").equals(bActive)) {
							rssList.add(map);
						}
					}
				});
				rssMap.put("rtnList",rssList);
			}else{
				rssMap.put("rtnList",rtnList);
			}

            logger.info(getClass().getName() + " : apiGetUsers end");
			List pageList = (List)rssMap.getOrDefault("rtnList", new ArrayList());
			int start = pageRequest.getPageNumber()*pageList.size();
			pageList = (List) pageList.stream().skip(start).limit(pageList.size()).collect(toList());
			Page pageImpl = new PageImpl(pageList, pageRequest, pageList.size());
			rssMap.replace("rtnList",pageImpl);
			return new ResponseEntity<>(rssMap, null, HttpStatus.OK);

		}catch (Exception exception){
			exception.printStackTrace();

			return new ResponseEntity<>(new HashMap(), null, HttpStatus.FORBIDDEN);
		}
	}


}
