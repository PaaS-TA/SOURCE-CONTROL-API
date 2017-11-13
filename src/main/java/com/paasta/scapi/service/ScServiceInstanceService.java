package com.paasta.scapi.service;

import com.paasta.scapi.entity.ScServiceInstance;
import com.paasta.scapi.model.ServiceInstanceList;
import com.paasta.scapi.repository.ScServiceInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class ScServiceInstanceService extends CommonService{

    private final
    ScServiceInstanceRepository scServiceInstanceRepository;

    @Autowired
    public ScServiceInstanceService(ScServiceInstanceRepository scServiceInstanceRepository) {
        this.scServiceInstanceRepository = scServiceInstanceRepository;
    }

    /**
     * 형상관리 서비스 인스턴스 전체 목록을 확인한다.
     * @param
     * @return List<ScServiceInstance>
     * @throws
     * @author 최세지
     * @version 1.0
     * @since 2017.09.15 최초작성
     */
    
    public ServiceInstanceList getServiceInstanceList(String organizationName, Pageable pageable) {

        this.logger.info(":::::::::::::::::::::::::::::::::::::::::::::");
        this.logger.info("  - PageNumber :: {}", pageable.getPageNumber());
        this.logger.info("  - PageSize :: {}", pageable.getPageSize());
        this.logger.info("  - Sort :: {}", pageable.getSort());
        this.logger.info("  - Offset :: {}", pageable.getOffset());
        this.logger.info("  - HasPrevious :: {}", pageable.hasPrevious());
        this.logger.info(":::::::::::::::::::::::::::::::::::::::::::::");

        ServiceInstanceList resultList;
        Page<ScServiceInstance> serviceInstanceListPage;

        if (organizationName == null || "".equals(organizationName)) {
            serviceInstanceListPage = scServiceInstanceRepository.findAll(pageable);
        } else {
            serviceInstanceListPage = scServiceInstanceRepository.findAll(ScServiceInstancesSpecifications.hasOrganizationName(organizationName), pageable);
        }

        resultList = (ServiceInstanceList) setPageInfo(serviceInstanceListPage, new ServiceInstanceList());
        resultList.setServiceInstances(serviceInstanceListPage.getContent());
        return resultList;
    }

    /**
     * 서비스인스턴스 생성자별 서비스 인스턴스 목록 조회
     *
     * @param creqteUserId
     * @return List
     *  if() createUserId  삭제함.2017.11.06 by ijlee
     */
    public  List<ScServiceInstance> getServiceInstanceList(String creqteUserId) {

        this.logger.info(":::::::::::::::::::::::::::::::::::::::::::::");
        this.logger.info("  - creqteUserId :: {}", creqteUserId);
        this.logger.info(":::::::::::::::::::::::::::::::::::::::::::::");

        List<ScServiceInstance>  list = this.scServiceInstanceRepository.findByCreateUserId(creqteUserId);

        return list;
    }

    private Object setPageInfo(Page reqPage, Object reqObject) {
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
}

