package com.iot.repository;

import com.iot.TmallApplicationTests;
import com.iot.domain.Module;
import com.iot.mapper.ModuleMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ModuleTest extends TmallApplicationTests {
    @Autowired
    private ModuleMapper moduleMapper;
    //@Ignore("not ready yet")

    @Before
    public void testInsert() {
        Module module = new Module();
        module.setName("123");
        moduleMapper.insert(module);
    }

    @Test
    public void testQueryList(){
        List<Module> list = moduleMapper.queryList("123");
        Assert.assertEquals(1,list.size());
    }

}
