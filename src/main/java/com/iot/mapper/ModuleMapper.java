package com.iot.mapper;

import com.iot.domain.Module;
import com.iot.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 模块管理mapper
 *
 * @author Nian Guowei
 */
@Mapper
@Repository
public interface ModuleMapper {

    /**
     * 插入模块
     * @param module
     */
    void insert(Module module);

    /**
     * 更新模块信息
     * @param module
     */
    void update(Module module);

    /**
     * 查询模块列表
     * @return
     */
    List<Module> queryList(@Param("name") String name);

    /**
     * 查询模块列表
     * @return
     */
    List<Module> queryListByPid(@Param("pId") Long pId, @Param("name") String name);

    /**
     * 删除信息
     */
    void delete(@Param("id") Long id);

}
