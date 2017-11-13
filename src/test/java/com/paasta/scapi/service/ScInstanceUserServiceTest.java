package com.paasta.scapi.service;

import com.paasta.scapi.entity.ScInstanceUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
