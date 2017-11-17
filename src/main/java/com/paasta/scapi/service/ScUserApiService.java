package com.paasta.scapi.service;

import com.paasta.scapi.common.util.RestClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sonia.scm.user.User;

import java.util.List;

/**
 * Created by lena on 2017-06-14.
 */

/**
 * @author ijlee
 */
@Service
public class ScUserApiService extends CommonService {

    @Autowired
    private
    RestClientUtil restClientUtil;

    @Value("${api.users}")
    private String userUrl;

    /**
     * DB와 Scm APT 사용자 정보 내역을 가져온다.
     *
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
     * DB와 Scm APT 사용자 정보 생성을 한다.
     *
     * @param user
     * @return
     * @author 최세지
     * @version 1.0
     * @since 2017.09.15 최초작성
     */

    public void create(User user) {
        this.logger.debug("getScmUser Start : " + user.toString());
        scmAdminSession().getUserHandler().create(user);
    }


    /**
     * DB와 Scm APT 사용자 정보 수정
     *
     * @param user
     * @return
     * @author 최세지
     * @version 1.0
     * @since 2017.09.15 최초작성
     */

    public void modify(User user) {
        this.logger.debug("getScmUser Start : " + user.toString());
        scmAdminSession().getUserHandler().modify(user);
    }
    /**
     * DB와 Scm APT 사용자 정보 삭제을 가져온다.
     *
     * @param name
     * @return
     * @author 최세지
     * @version 1.0
     * @since 2017.09.15 최초작성
     */

    public void delete(String name) {
        this.logger.debug("getScmUser Start : " + name);
        scmAdminSession().getUserHandler().delete(name);
    }

    /**
     * Scm APT의 기능으로 사용자의 상세정보를 확인한다.
     *
     * @param
     * @return
     * @author 최세지
     * @version 1.0
     * @since 2017.09.15 최초작성
     */
    @SuppressWarnings("unchecked")
    public List<User> restGetAllUsers() {
        this.logger.debug("getUser Start : ");
        return scmAdminSession().getUserHandler().getAll();
	}

}
