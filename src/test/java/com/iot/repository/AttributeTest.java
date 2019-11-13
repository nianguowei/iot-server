package com.iot.repository;

import com.iot.TmallApplicationTests;
import com.iot.domain.Attribute;
import com.iot.mapper.AttributeMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AttributeTest extends TmallApplicationTests {
    @Autowired
    private AttributeMapper attributeMapper;
    //@Ignore("not ready yet")

    @Before
    public void testInsert() {
        Attribute attribute = new Attribute();
        attribute.setModId(1L);
        attribute.setName("123");
        attribute.setType("string");
        attributeMapper.insert(attribute);
    }

    @Test
    public void testQueryList(){
        List<Attribute> list = attributeMapper.queryListByModId(1L);
        Assert.assertEquals(1,list.size());
    }

}
