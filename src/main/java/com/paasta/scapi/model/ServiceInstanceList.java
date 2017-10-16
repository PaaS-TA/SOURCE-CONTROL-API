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

    public ServiceInstanceList(List<ScServiceInstance> scServiceInstances){
        this.scServiceInstances = scServiceInstances;
    }

    public ServiceInstanceList(int total, int start, int display, List<ScServiceInstance>scServiceInstances) {
        this.total = total;
        this.start = start;
        this.display = display;
        this.scServiceInstances = scServiceInstances;
    }

    public List<ScServiceInstance> getServiceInstances(){
        return this.scServiceInstances;
    }

    public void setServiceInstances(List<ScServiceInstance> serviceInstances) {
        scServiceInstances =serviceInstances;
    }

    //Generate Getter&Setter

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getDisplay() {
        return this.display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return this.totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isLast() {
        return this.isLast;
    }

    public void setLast(boolean last) {
        this.isLast = last;
    }

}
