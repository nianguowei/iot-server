package com.iot.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iot.domain.Attribute;
import com.iot.domain.Role;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * elasticsearch服务接口
 *
 * @author Nian Guowei
 */

public interface ElasticsearchService {

    /**
     * 创建索引
     * @param index
     * @param attributeList
     * @return
     * @throws IOException
     */
    boolean createIndex(String index, List<Attribute> attributeList) throws IOException;

    /**
     * 新增数据
     * @param jsonObject
     * @param index
     * @param id
     * @return
     * @throws IOException
     */
    String addData(JSONObject jsonObject, String index, String id) throws IOException;

    /**
     * 根据ID搜索
     * @param index
     * @param id
     * @return
     * @throws IOException
     */
    Map<String, Object> searchDataById(String index, String id) throws IOException;

    /**
     * 更新索引
     * @param objectMap
     * @param index
     * @param id
     * @throws IOException
     */
    void updateDataById(Map<String, Object> objectMap, String index, String id) throws IOException;

    /**
     * 删除
     * @param index
     * @param id
     * @throws IOException
     */
    void deleteDataById(String index, String id) throws IOException;

    /**
     * 搜索
     * @param index
     * @param currentPage
     * @param pageSize
     * @param jsonObject
     * @return
     * @throws IOException
     */
    PageInfo searchDataPage(String index, int currentPage, int pageSize, JSONObject jsonObject) throws IOException;

    /**
     * 删除索引
     * @param index
     * @return
     * @throws IOException
     */
    boolean deleteIndex(String index) throws IOException;
}
