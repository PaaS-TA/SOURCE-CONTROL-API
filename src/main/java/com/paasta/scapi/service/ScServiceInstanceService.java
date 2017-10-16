package com.paasta.scapi.service;

import com.paasta.scapi.common.Common;
import com.paasta.scapi.entity.ScServiceInstance;
import com.paasta.scapi.model.ServiceInstanceList;
import com.paasta.scapi.repository.ScServiceInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScServiceInstanceService extends CommonService{

    @Autowired
    ScServiceInstanceRepository scServiceInstanceRepository;

    /**
     * 형상관리 서비스 인스턴스 전체 목록을 확인한다.
     * @param
     * @return List<ScServiceInstance>
     * @throws
     * @author 최세지
     * @version 1.0
     * @since 2017.09.15 최초작성
     */
    
    public List<ScServiceInstance> getAll() {
        return this.scServiceInstanceRepository.findAll();
    }

    
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
            serviceInstanceListPage = this.scServiceInstanceRepository.findAll(pageable);
        } else {
            serviceInstanceListPage = this.scServiceInstanceRepository.findAll(ScServiceInstancesSpecifications.hasOrganizationName(organizationName), pageable);
        }

        resultList = (ServiceInstanceList) this.setPageInfo(serviceInstanceListPage, new ServiceInstanceList());
        resultList.setServiceInstances(serviceInstanceListPage.getContent());
        return resultList;
    }
    
    public List getServiceInstanceList(String creqteUserId) {

        this.logger.info(":::::::::::::::::::::::::::::::::::::::::::::");
        this.logger.info("  - creqteUserId :: {}", creqteUserId);
        this.logger.info(":::::::::::::::::::::::::::::::::::::::::::::");

        List list = new ArrayList();
        if (Common.empty(creqteUserId) ){
            list = this.scServiceInstanceRepository.findAll();
        } else {
            list = this.scServiceInstanceRepository.findByCreateUserId(creqteUserId);
        }

        return list;
    }
}

