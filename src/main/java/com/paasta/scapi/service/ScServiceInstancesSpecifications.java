package com.paasta.scapi.service;
import com.paasta.scapi.entity.ScServiceInstance;
import org.springframework.data.jpa.domain.Specification;



public class ScServiceInstancesSpecifications extends CommonService {
    private ScServiceInstancesSpecifications(){}
    /**
     * DB의 그룹명[organizationName]에 해당하는 리스트를 조회한다.
     * @param organizationName
     * @return Specification<ScServiceInstance>
     * @author 최세지
     * @version 1.0
     * @since 2017.07.05 최초작성
     */
    static Specification<ScServiceInstance> hasOrganizationName(String organizationName){

        return (root, query, cb) -> cb.like(root.get("organizationName"), "%" + organizationName + "%");
    }
}
