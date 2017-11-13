package com.paasta.scapi.service;


import com.paasta.scapi.common.exception.CustomLoginException;
import com.paasta.scapi.common.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sonia.scm.client.ScmClient;
import sonia.scm.client.ScmClientSession;
import sonia.scm.client.ScmUnauthorizedException;
import sonia.scm.user.User;


@Service
public class ScLoginService extends CommonService{

    @Autowired
    private PropertiesUtil propertiesUtil;

    public ScLoginService() {
        super();
    }


    @SuppressWarnings("unchecked")
    public ResponseEntity login(User user, PropertiesUtil propertiesUtil) {
        try {
            logger.info("login Start : ");
            ScmClientSession scmClientSession = ScmClient.createSession(propertiesUtil.getBaseUrl(),user.getName(), user.getPassword());
            User rtnUser = scmClientSession.getUserHandler().get(user.getName());
            return new ResponseEntity(rtnUser, HttpStatus.OK);
        } catch (ScmUnauthorizedException e) {
            return new CustomLoginException().CustomLoginException(e);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new CustomLoginException().CustomLoginException(e);
        }
    }
}