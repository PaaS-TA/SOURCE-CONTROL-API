package com.paasta.scapi.controller;

import com.paasta.scapi.service.ScLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(description = "Super Admin 을 위한 login Api")
public class ScLoginController {
    @Autowired
    ScLoginService scLoginService;

    @PostMapping("/login")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = Map.class)
            , @ApiResponse(code = 401, message = "You are not authorized to view the resource")
            , @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<Map> login(@RequestBody User user) {
        return scLoginService.login(user);

    }
}
