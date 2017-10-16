package com.paasta.scapi.model;

import com.paasta.scapi.entity.ScRepository;

import java.util.List;

/**
 * Created by SEJI on 2017-07-26.
 */
public class RepositoryList {

    int total;
    int start;
    int display;
    List<ScRepository> scRepositories;
    int page;
    int size;
    int totalPages;
    long totalElements;
    boolean isLast;

    public RepositoryList(){
    }

    public RepositoryList(int total, int start, int display, List<ScRepository>scRepositories) {
        this.total = total;
        this.start = start;
        this.display = display;
        this.scRepositories = scRepositories;
    }


    public List<ScRepository> getScRepositories() {
        return this.scRepositories;
    }

    public void setScRepositories(List<ScRepository> repositories){
        scRepositories =repositories;
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
