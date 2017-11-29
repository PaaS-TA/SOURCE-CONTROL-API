package com.paasta.scapi.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paasta.scapi.common.util.DateUtil;
import com.paasta.scapi.entity.RepoPermission;
import com.paasta.scapi.entity.ScUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sonia.scm.user.User;

import java.util.*;

public final class UserEntityTestData
{

    private static String modifyName = "modifyName";
    private static String modifyMail = "modifyMail@aaa.aa.aa";
    private static boolean modifAdmin = true;
    private static boolean modifyActive = true;
    private static String modifyPassword = "modifyPassword";
    private static String modifyPasswordSet = "modifyPassword";
    private static String modifyDesc = "modifyDesc";


    private UserEntityTestData() {}

    public static ScUser createScUser()
    {
        return new ScUser(MockUtil.userId, MockUtil.userName, MockUtil.userMail, MockUtil.userDesc);
    }
    public static ScUser getScUser()
    {
        return new ScUser(MockUtil.userId, MockUtil.userName, MockUtil.userMail, MockUtil.userDesc);
    }

    public static User getAdminUser()
    {
        User user = new User(MockUtil.scmAdminId, MockUtil.userName, MockUtil.userMail);
        user.setPassword(MockUtil.scmAdminPassword);
        return user;
    }
    public static ScUser getEmptyScUser()
    {
        return null;
    }
    public static User createUser()
    {
        return new User(MockUtil.userId, MockUtil.userName, MockUtil.userMail);
    }
    public static User getUser()
    {
        return new User(MockUtil.userId, MockUtil.userName, MockUtil.userMail);
    }
    public static User modifyeUser()
    {
        return new User(MockUtil.userId, MockUtil.userName, MockUtil.userMail);
    }
    public static List<ScUser> getLstScUser()
    {
        List<ScUser> rtnList = new ArrayList();
        rtnList.add(createScUser());
        return rtnList;
    }

    public static List<User> getLstUser()
    {
        List<User> rtnList = new ArrayList();
        rtnList.add(createUser());
        return rtnList;
    }

    public static LinkedHashMap createModifyUser()
    {
        LinkedHashMap createModifyUser = new LinkedHashMap();

        createModifyUser.put("displayName",modifyName);
        createModifyUser.put("mail",modifyMail);
        createModifyUser.put("admin",modifAdmin);
        createModifyUser.put("active",modifyActive);
        createModifyUser.put("password",modifyPassword);
        createModifyUser.put("PasswordSet",modifyPasswordSet);
        createModifyUser.put("desc",modifyDesc);
        return createModifyUser;
    }

    public static Map getMapUser(){
        Map rspApp = new HashMap();
        rspApp.put("ScUser", getScUser());
        rspApp.put("rtnUser",getUser());
        rspApp.put("status", HttpStatus.OK.value());
        rspApp.put("message", "사용자 정보 조회에 성공하였습니다.");
        return rspApp;
    }
    public static Map getEmptyUser(){
        Map rspApp = new HashMap();
        rspApp.put("ScUser", null);
        rspApp.put("rtnUser",null);
        rspApp.put("status",HttpStatus.NOT_FOUND.value());
        rspApp.put("message","사용자 존재하지 않습니다.");
        return rspApp;
    }

    public static Map getMapAllUsers(){
        Map rspApp = new HashMap();
        List rtnList = new ArrayList();
        List<User> lstUser = getLstUser();
        List<ScUser> lstScUser = getLstScUser();
        for (ScUser testScUser : lstScUser) {
            for (User testUser : lstUser) {
                if (testScUser.getUserId().equals(testUser.getName())) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map rtnMap = objectMapper.convertValue(testUser, LinkedHashMap.class);
                    String creationDate = Objects.equals(String.valueOf(rtnMap.get("creationDate")), "null") ? "" : String.valueOf(rtnMap.get("creationDate"));
                    String lastModified = Objects.equals(String.valueOf(rtnMap.get("lastModified")), "null") ? "" : String.valueOf(rtnMap.get("lastModified"));
                    rtnMap.replace("creationDate", DateUtil.parseStringDatebyInt("yyyy-MM-dd HH:mm:ss", creationDate));
                    rtnMap.replace("lastModified", DateUtil.parseStringDatebyInt("yyyy-MM-dd HH:mm:ss", lastModified));
                    rtnMap.put("desc", testScUser.getUserDesc());
                    rtnList.add(rtnMap);
                }
            }
        }
        rspApp.put("rtnUser",rtnList);
        return rspApp;
    }

    public static ResponseEntity getUsersByrepositoryId(){
        List pageList = new ArrayList();
        PageRequest pageRequest =  new PageRequest(0, 1);
        ObjectMapper objectMapper = new ObjectMapper();
        User user = getUser();
        RepoPermission permission = RepoPermissionEntityTestData.createRepoPermission();
        Map rtnMap =  objectMapper.convertValue(user,Map.class);
        String creationDate = user.getCreationDate() == null ? "" : String.valueOf(user.getCreationDate());
        String lastModified = user.getLastModified() == null ? "" : String.valueOf(user.getLastModified());
        rtnMap.put("creationDate", DateUtil.parseStringDatebyInt("yyyy-MM-dd HH:mm:ss", creationDate));
        rtnMap.put("lastModified", DateUtil.parseStringDatebyInt("yyyy-MM-dd HH:mm:ss", lastModified));
        rtnMap.put("permission", permission);

        pageList.add(rtnMap);
        Map rtnrss = new HashMap<>();
        Page pageImpl = new PageImpl(pageList, pageRequest, pageList.size());
        rtnrss.put("rtnList", pageImpl);
        return new ResponseEntity<>(rtnrss, null, HttpStatus.OK);
    }
}