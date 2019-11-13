package com.iot.mapper;

import com.iot.domain.Attribute;
import com.iot.domain.Module;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 属性管理mapper
 *
 * @author Nian Guowei
 */
@Mapper
@Repository
public interface AttributeMapper {

    /**
     * 插入属性
     * @param attribute
     */
    void insert(Attribute attribute);

    /**
     * 更新属性信息
     * @param attribute
     */
    void update(Attribute attribute);

    /**
     * 查询属性列表
     * @return
     */
    List<Attribute> queryListByModId(@Param("modId") Long modId);

    /**
     * 查询属性列表
     * @return
     */
    List<Attribute> queryListByModIdWithVerify(@Param("modId") Long modId);

    /**
     * 删除信息
     */
    void delete(@Param("id") Long id);

}
