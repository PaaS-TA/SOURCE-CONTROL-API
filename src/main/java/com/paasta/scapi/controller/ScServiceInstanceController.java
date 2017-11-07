package com.paasta.scapi.controller;

import com.paasta.scapi.common.Common;
import com.paasta.scapi.common.exception.RestException;
import com.paasta.scapi.entity.ScServiceInstance;
import com.paasta.scapi.entity.ScUser;
import com.paasta.scapi.model.ServiceInstanceList;
import com.paasta.scapi.service.ScInstanceUserService;
import com.paasta.scapi.service.ScServiceInstanceService;
import com.paasta.scapi.service.ScUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sonia.scm.user.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/serviceInstances")
public class ScServiceInstanceController {
    @Autowired
    ScServiceInstanceService scServiceInstanceService;

    @Autowired
    ScInstanceUserService scInstanceUserServicer;

    @Autowired
    ScUserService scUserService;
    /*
    *형상관리 amdin 신청 목록
    * private static final int PAGE_SIZE = 3; => PageRequest pageable 로 변경
    */
    @SuppressWarnings("unchecked")
    @GetMapping("")
    public ResponseEntity<ServiceInstanceList> getServiceInstances(@RequestParam(value = "organizationName", required = false) String organizationName,
                                                                    @RequestParam(value = "size", required = false) int size,
                                                                    @RequestParam(value = "page", required = false) int page){
        //만약 Pageable을 사용하지 않고 PageRequest를 사용한다면 파라미터를 넘겨서 다음과 같이 처리하면 된다.
        PageRequest pageable = new PageRequest(page, size, new Sort(Direction.ASC,"organizationName")); // 현재페이지, 조회할 페이지수, 정렬정보
        ServiceInstanceList serviceInstances = scServiceInstanceService.getServiceInstanceList(organizationName, pageable);
        return new ResponseEntity(serviceInstances, HttpStatus.OK);
    }

    /*
     *형상관리 user 신청 목록
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/user")
    public ResponseEntity< List<ScServiceInstance>> getUserServiceInstances(@RequestParam(value = "createUserId", required = false) String createUserId){
        List serviceInstances = this.scServiceInstanceService.getServiceInstanceList(createUserId);
        return new ResponseEntity(serviceInstances, HttpStatus.OK);
    }

    /**
     * 인스턴스별 사용자 추가
     * @param linkMapScInstanceUser {instanceId, userId,repoRole, createrYn, userId, displayName,mail, desc,password}
     * @return
     * @throws RestException
     * @version 1.0
     */
    @SuppressWarnings("unchecked")
    @PostMapping
    public ResponseEntity createInstanceUser(@RequestBody LinkedHashMap linkMapScInstanceUser) throws SQLException {
        Map<String, String> scInstanceUser = Common.convertMapByLinkedHashMap(linkMapScInstanceUser);

        ResponseEntity rssCreateInstance = scInstanceUserServicer.createInstanceUser(scInstanceUser);
        Map map = new HashMap();

        if(rssCreateInstance.getStatusCodeValue()==200) {
        /* 상용자 정보조회 (DB) & sourcecontrol service*/
            String userId = scInstanceUser.getOrDefault("userId", "");
            String displayName = scInstanceUser.getOrDefault("displayName", "");
            String mail = scInstanceUser.getOrDefault("mail", "");
            String desc = scInstanceUser.getOrDefault("desc", "");
            //사용자 정보 : DB정보
            ScUser scUser = scUserService.findOne(userId);
            User rtnUser = scUserService.getScmUser(userId);

            if (Common.empty(scUser)) {
                scUser = scUserService.save(new ScUser(userId, displayName, mail, desc));
            }

            linkMapScInstanceUser.put("name",userId);
            if (Common.empty(rtnUser)) {
                scUserService.apiCreateUser(linkMapScInstanceUser);
                rtnUser = scUserService.getScmUser(userId);
            }
            map.put("scUser", scUser);
            map.put("rtnUser", rtnUser);
            map.put("rssCreateInstance", rssCreateInstance);
            map.put("status", HttpStatus.OK);

        }else{
            return new ResponseEntity(map, HttpStatus.EXPECTATION_FAILED);
        }


        return new ResponseEntity(map, HttpStatus.OK);


    }
}
