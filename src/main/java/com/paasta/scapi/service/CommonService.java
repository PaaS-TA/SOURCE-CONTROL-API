package com.paasta.scapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import sonia.scm.client.ScmClient;
import sonia.scm.client.ScmClientSession;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ijlee on 2017-07-16.
 */

public class CommonService {

    /**
     * SCM Server connection Session Information
     */
    @Value("${api.base.url}")  private String url;
    @Value("${admin.id}")  private String username;
    @Value("${admin.pwd}")  private String password;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    ScmClientSession scmClientSession(){
        return ScmClient.createSession(url, username, password);
    }
    Object setPageInfo(Page reqPage, Object reqObject) {
        try {
            Class<?> aClass = reqObject.getClass();

            Method methodSetPage = aClass.getMethod("setPage", Integer.TYPE);
            Method methodSetSize = aClass.getMethod("setSize", Integer.TYPE);
            Method methodSetTotalPages = aClass.getMethod("setTotalPages", Integer.TYPE);
            Method methodSetTotalElements = aClass.getMethod("setTotalElements", Long.TYPE);
            Method methodSetLast = aClass.getMethod("setLast", Boolean.TYPE);

            methodSetPage.invoke(reqObject, reqPage.getNumber());
            methodSetSize.invoke(reqObject, reqPage.getSize());
            methodSetTotalPages.invoke(reqObject, reqPage.getTotalPages());
            methodSetTotalElements.invoke(reqObject, reqPage.getTotalElements());
            methodSetLast.invoke(reqObject, reqPage.isLast());

        } catch (NoSuchMethodException e) {
            this.logger.error("NoSuchMethodException :: {}", e);
        } catch (IllegalAccessException e1) {
            this.logger.error("IllegalAccessException :: {}", e1);
        } catch (InvocationTargetException e2) {
            this.logger.error("InvocationTargetException :: {}", e2);
        }
        return reqObject;
    }

  /*  public ScmState getScmState(){
        javax.ws.rs.client.Client client = javax.ws.rs.client.ClientBuilder.newClient();

        ScmState result = client.target(baseUrl + "/authentication")
                .get(ScmState.class);
        return result;

    }
*/

}
