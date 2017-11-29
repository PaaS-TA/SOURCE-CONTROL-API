package com.paasta.scapi.common.util;

import com.paasta.scapi.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by lena on 2017-06-15.
 */
@Component
public class RestClientUtil extends CommonService{

    /**
     * The Properties util.
     */
    @Autowired
    private
    PropertiesUtil propertiesUtil;
    /**
     * Call rest api response model.
     *
     * @param <T>          the type parameter
     * @param httpMethod   the http method
     * @param url          the url
     * @param entity       the model
     * @param responseType the response type
     * @return the response model
     */
    public <T> ResponseEntity<T> callRestApi(HttpMethod httpMethod, String url, HttpEntity<Object> entity, Class<T> responseType) {

        RestTemplate restTemplate = new RestTemplate();
        logger.info("Type : {}, URL : {}, ResponseType : {}", httpMethod, this.propertiesUtil.baseUrl +url, responseType);
        return restTemplate.exchange(this.propertiesUtil.baseUrl +url, httpMethod, entity, responseType);

    }

    /**
     * Header setting to result for sonia.scm.entity response Body
     * sonia.scm.에서 제공되는 response를 받기위 한 header.추가
     * @
     * @param param the param
     * @return the http T
     */
    public HttpEntity<Object> restCommonHeaderJson(Object param) {

        HttpEntity httpEntity = restCommonHeader(param);
        HttpHeaders headers =httpEntity.getHeaders();

        /*headers.add(HttpHeaders.CONTENT_TYPE, ContentType.JSON+ Changeset);
        headers.add(HttpHeaders.CONTENT_TYPE, ContentType.);*/
        return new HttpEntity<>(param, headers);
    }


    /**
     * Header setting to result for sonia.scm.entity response Body
     * sonia.scm.에서 제공되는 response를 받기위 한 header.추가
     * @
     * @param param the param
     * @return the http T
     */
    public HttpEntity<Object> restCommonHeader(Object param) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", propertiesUtil.getBasicAuth());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Object>(param, headers);
    }
}
