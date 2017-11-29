package com.paasta.scapi.model;

import com.paasta.scapi.entity.ScServiceInstance;

import java.util.List;

/**
 * Created by User on 2017-07-10.
 */

public class ServiceInstanceList {
    int total;
    int start;
    int display;
    List<ScServiceInstance> scServiceInstances;

    int page;
    int size;
    int totalPages;
    long totalElements;
    boolean isLast;

    public ServiceInstanceList(){
    }

    public void setServiceInstances(List<ScServiceInstance> serviceInstances) {
        scServiceInstances =serviceInstances;
    }

}
