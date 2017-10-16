package com.paasta.scapi.common.util;

import com.paasta.scapi.common.exception.RestException;
import com.paasta.scapi.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lena on 2017-06-15.
 */
@Component
public class RestClientUtil extends CommonService{

    /**
     * The Properties util.
     */
    @Autowired
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
        ResponseEntity<T> response = null;

        this.logger.info("Type : {}, URL : {}, ResponseType : {}", httpMethod, this.propertiesUtil.base_url+url, responseType);

        try {

            response = restTemplate.exchange(this.propertiesUtil.base_url+url, httpMethod, entity, responseType);

        } catch (HttpServerErrorException he) {
            //TODO exception 처리
            //JsonNode error = JsonUtils.convertStringToJson(e.getResponseBodyAsString());
            he.printStackTrace();
            throw new RestException(he.getStatusCode()+he.getMessage());
        } catch (Exception e) {
            throw new RestException(e.getMessage());
        }

        return response;
    }

    /**
     * Rest common headers http model.
     *
     * @param param the param
     * @return the http model
     */
    public HttpEntity<Object> restCommonHeaders(Object param) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", this.propertiesUtil.getBasicAuth());
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        List lst =new ArrayList();
       lst.add(Charset.forName("euc-kr"));
//        lst.add(Charset.forName("UTF-8"));
        headers.setAcceptCharset(lst);
        HttpEntity<Object> entity = new HttpEntity<Object>(param, headers);

        return entity;
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

        HttpEntity<Object> entity = new HttpEntity<Object>(param, headers);

        return entity;
    }

    /**
     * Call rest api return obj list response entity.
     *
     * responseType is..
     *  : ParameterizedTypeReference<List<T>> responseType = new ParameterizedTypeReference<List<T>>() {};
     *
     * @param <T>          the type parameter
     * @param httpMethod   the http method
     * @param url          the url
     * @param entity       the entity
     * @param responseType the response type
     * @return the response entity
     * @throws Exception the exception
     */
    public <T> ResponseEntity<List<T>> callRestApiReturnObjList(HttpMethod httpMethod, String url, HttpEntity<Object> entity, ParameterizedTypeReference<List<T>> responseType) throws Exception{

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<T>> response = null;

        this.logger.debug("Type : {}, URL : {}, ResponseType : {}", httpMethod, this.propertiesUtil.base_url+url, responseType);

        response = restTemplate.exchange(this.propertiesUtil.base_url+url, httpMethod, entity, responseType);

        //TODO Exception 처리

        return response;

    }

}
