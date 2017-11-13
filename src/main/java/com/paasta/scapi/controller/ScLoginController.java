package com.paasta.scapi.controller;

import com.paasta.scapi.common.util.PropertiesUtil;
import com.paasta.scapi.service.ScLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sonia.scm.user.User;

import java.util.Map;

@RestController
@RequestMapping
public class ScLoginController {
    @Autowired
    private
    ScLoginService scLoginService;
    @Autowired
    private
    PropertiesUtil propertiesUtil;

    @SuppressWarnings("unchecked")
    @PostMapping("/login")
    public ResponseEntity<Map> login(@RequestBody User user) {
        return scLoginService.login(user, propertiesUtil);

    }
}
