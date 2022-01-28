package com.paasta.scapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paasta.scapi.common.Common;
import com.paasta.scapi.common.util.DateUtil;
import com.paasta.scapi.entity.RepoPermission;
import com.paasta.scapi.entity.ScRepository;
import com.paasta.scapi.entity.ScUser;
import com.paasta.scapi.repository.RepoPermissionRepository;
import com.paasta.scapi.repository.ScRepositoryRepository;
import com.paasta.scapi.repository.ScUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sonia.scm.user.User;
import sonia.scm.util.ValidationUtil;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by lena on 2017-06-14.
 */

/**
 * @author ijlee
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

    @Autowired
    RepoPermissionApiService repoPermissionApiService;

    @Autowired
    ScUserApiService scUserApiService;

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
     *
     * @param name
     * @return
     * @author 최세지
     * @version 1.0
     * @since 2017.09.15 최초작성
     */

    @SuppressWarnings("unchecked")
    @Transactional
    public void restDeleteUser(String name) {
        this.logger.info("restDeleteUser Start : ");
        scUserRepository.delete(name);
        scUserApiService.delete(name);
    }

    /**
     * Scm API의 기능으로 사용자 수정을 구현한다.
     *
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
            String displayName = (String) jsonUser.getOrDefault("displayName", "");
            String mail = (String) jsonUser.getOrDefault("mail", "");
            boolean admin = Boolean.parseBoolean((String) jsonUser.getOrDefault("admin", false));
            boolean active = Boolean.parseBoolean((String) jsonUser.getOrDefault("active", false));
            String password = (String) jsonUser.getOrDefault("password", "\"__dummypassword__\"");
            String passwordSet = (String) jsonUser.getOrDefault("PasswordSet", "false");
            String description = (String) jsonUser.getOrDefault("desc", "");
            ScUser scUser = new ScUser(name, displayName, mail, description);
            rtnScUser = scUserRepository.save(scUser);
            /**
             * syntax check 를 두어 smc 수정오류를 방지함.
             */
            if (!ValidationUtil.isMailAddressValid(mail)) {
                mail = "";
            }
            if (Common.empty(displayName)) {
                displayName = name;
            }
            User user = scUserApiService.getScmUser(name);
            user.setDisplayName(displayName);
            user.setPassword(password);
            user.setActive(active);
            user.setAdmin(admin);
            user.setMail(mail);
            if (!Common.empty(passwordSet)) {
                user.setProperty("PasswordSet", passwordSet);
            }
            scUserApiService.modify(user);
            this.logger.info("restUpdateUser End " + rspApp);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnScUser;
    }


    /**
     * Scm APT의 기능으로 사용자 생성을 구현한다.
     * 사용자 정보 조회 사용자가 DB 와 SourceControl Service 에 모두 존재하는지 확인 후 DB에 없을경우, "사용자가 존재 하지 않습니다."를 return 해 준다.
     *
     * @param name
     * @return Map key : "ScUser" value : DB 사용자 정보
     * key : "rtnUser" value : SourceControl 사용자 정보
     * @author 최세지
     * @version 1.0
     * @since 2017.09.15 최초작성
     */

    @SuppressWarnings("unchecked")
    public Map getUser(String name) {

        Map rspApp = new HashMap();
        this.logger.debug("getUser Start : " + name);
        ScUser scUser = scUserRepository.findOne(name);
        if (Common.empty(scUser)) {
            rspApp.put("ScUser", null);
            rspApp.put("rtnUser", null);
            rspApp.put("status", HttpStatus.NOT_FOUND.value());
            rspApp.put("message", "User not found.");
            return rspApp;
        } else {
            User user = scUserApiService.getScmUser(name);
            rspApp.put("ScUser", scUser);
            rspApp.put("rtnUser", user);
            rspApp.put("status", HttpStatus.OK.value());
            rspApp.put("message", "User information inquiry was successful.");
            return rspApp;
        }
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
        List<User> responseEntity = scUserApiService.restGetAllUsers();
		List<ScUser> users = scUserRepository.findAll();
        for (ScUser scUser : users) {
            for (User linkedHashMap : responseEntity) {
                if (scUser.getUserId().equals(linkedHashMap.getName())) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map rtnMap = objectMapper.convertValue(linkedHashMap, LinkedHashMap.class);
                    String creationDate = Objects.equals(String.valueOf(rtnMap.get("creationDate")), "null") ? "" : String.valueOf(rtnMap.get("creationDate"));
                    String lastModified = Objects.equals(String.valueOf(rtnMap.get("lastModified")), "null") ? "" : String.valueOf(rtnMap.get("lastModified"));
                    rtnMap.replace("creationDate", DateUtil.parseStringDatebyInt("yyyy-MM-dd HH:mm:ss", creationDate));
                    rtnMap.replace("lastModified", DateUtil.parseStringDatebyInt("yyyy-MM-dd HH:mm:ss", lastModified));
                    rtnMap.put("desc", scUser.getUserDesc());
                    rtnList.add(rtnMap);
                }
            }
        }
		rspApp.put("rtnUser",rtnList);
        logger.debug("getUser End "+ rspApp);
		return rspApp;
	}

    /**
     * DB와 Scm APT 사용자 생성 내역을 가져온다.
     *
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
            Map jsonUser2 = Common.convertMapByLinkedHashMap(jsonUser);
            String name = (String) jsonUser2.getOrDefault("name", "");
            String displayName = (String) jsonUser2.getOrDefault("displayName", null);
            String mail = (String) jsonUser2.getOrDefault("mail", "");
            String type = (String) jsonUser2.getOrDefault("type", "xml");
            String password = (String) jsonUser2.getOrDefault("password", "");
            String passwordSet = (String) jsonUser2.getOrDefault("PasswordSet", "false");
            User user = new User(name);//, displayName, mail);
            if (!Common.empty(displayName)) {
                user.setDisplayName(displayName);
            }
            if (!Common.empty(mail)) {
                user.setMail(mail);
            }
            user.setType(type);

            if (!Common.empty(password)) {
                user.setPassword(password);
            }

            user.setProperty("PasswordSet", passwordSet);
            scUserApiService.create(user);
            logger.info(getClass().getName() + " : apiCreateUser end");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Scm APT의 기능으로 레파지토리별 부분사용자 조회를 한다.
     *
     * @param repositoryId
     * @param searchUserName
     * @param searchPermission
     * @param active
     * @param pageRequest
     * @return * @author 최세지
     * @version 1.0
     * @since 2017.09.15 최초작성
     */
    @SuppressWarnings("unchecked")
    public ResponseEntity<Map> getUsersByrepositoryId(String repositoryId, String searchUserName, String searchPermission, String active, PageRequest pageRequest) {
        try {
            logger.info(getClass().getName() + " : getUsersByrepositoryId start");
            List<ScRepository> scRepository = scRepositoryRepository.findAllByRepoScmId(repositoryId);
            // 전체 사용자 가져오기
            List<User> lstUser = scUserApiService.restGetAllUsers();

            Map rssMap = new HashMap();

            List rtnList = new ArrayList();
            //참여자 정보 에 사용자 정보를 병합한다.
            List<RepoPermission> lstPermission;
            if(scRepository.size()!=0) {
                if (Common.empty(searchPermission)) {
                    lstPermission = repoPermissionRepository.findAllByRepoNo(scRepository.get(0).getRepoNo());
                } else {
                    lstPermission = repoPermissionRepository.findAllByRepoNoAndPermission(scRepository.get(0).getRepoNo(), searchPermission);
                }
                lstPermission.forEach(permission -> lstUser.forEach(user -> {
                    if (permission.getUserId().equals(user.getName())) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        Map rtnMap = objectMapper.convertValue(user, Map.class);
                        String creationDate = user.getCreationDate() == null ? "" : String.valueOf(user.getCreationDate());
                        String lastModified = user.getLastModified() == null ? "" : String.valueOf(user.getLastModified());
                        rtnMap.put("creationDate", DateUtil.parseStringDatebyInt("yyyy-MM-dd HH:mm:ss", creationDate));
                        rtnMap.put("lastModified", DateUtil.parseStringDatebyInt("yyyy-MM-dd HH:mm:ss", lastModified));
                        rtnMap.put("permission", permission);
                        rtnList.add(rtnMap);
                    }
                }));

            }
            List rssList = new ArrayList();
            //참여자 정보 중에서 이름과 활성정보를 필터링한다.
            if (Common.notEmpty(searchUserName) || Common.notEmpty(active)) {
                rtnList.forEach(hashMap -> {
                    boolean bAdd = false;
                    ObjectMapper objectMapper = new ObjectMapper();
                    HashMap map = objectMapper.convertValue(hashMap, HashMap.class);
                    //searchUserName이 있을경우 필터한다.
                    if (map.getOrDefault("displayName", "").toString().contains(searchUserName) || map.getOrDefault("name", "").toString().contains(searchUserName)) {
                        bAdd = true;
                    }
                    boolean bActive = Boolean.parseBoolean(active);
                    if (map.get("active").equals(bActive)) {
                        bAdd = true;
                    }
                    if(bAdd){
                        rssList.add(hashMap);
                    }
                });
                rssMap.put("rtnList", rssList);
            } else {
                rssMap.put("rtnList", rtnList);
            }
            logger.info(getClass().getName() + " : getUsersByrepositoryId end");
            List pageList = (List) rssMap.get("rtnList");
            //page,size
            int start = pageRequest.getPageNumber() * pageList.size();
            pageList = (List) pageList.stream().skip(start).limit(start+pageRequest.getPageSize()).collect(toList());
            Page pageImpl = new PageImpl(pageList, pageRequest, pageList.size());
            rssMap.replace("rtnList", pageImpl);
            return new ResponseEntity<>(rssMap, null, HttpStatus.OK);

        } catch (Exception exception) {
            exception.printStackTrace();

            return new ResponseEntity<>(new HashMap(), null, HttpStatus.FORBIDDEN);
        }
    }


}
