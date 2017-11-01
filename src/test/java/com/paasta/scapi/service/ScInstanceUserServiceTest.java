package com.paasta.scapi.service;

import com.paasta.scapi.entity.ScInstanceUser;

import com.paasta.scapi.repository.ScInstanceUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * Created by SEJI on 2017-10-17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ScInstanceUserServiceTest {

    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @InjectMocks
    ScInstanceUserService scInstanceUserService;

    @Test
    public void getAll() throws Exception {
        Mockito.when(this.scInstanceUserService.getAll()).thenReturn(null);
    }

    @Test
    public void getByInstanceId() throws Exception {
        String InstanceId = "";
        scInstanceUserService.getByInstanceId(InstanceId);

    }

    @Test
    public void getByInstanceIdAndUserId() throws Exception{
        String instanceId="";
        String userId="";
        scInstanceUserService.getByInstanceIdAndUserId(instanceId,userId);

    }

    @Test
    public void createInstanceUser() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void update() throws Exception{
        ScInstanceUser scInstanceUser = new ScInstanceUser();
//        Mockito.when(this.scInstanceUserService.update(scInstanceUser)).thenReturn(scInstanceUser);
    }

    @Test
    public void delete() throws Exception{
//        scInstanceUserService.delete(1);
    }

}
