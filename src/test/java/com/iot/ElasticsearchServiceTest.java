//package com.iot;
//
//import com.alibaba.fastjson.JSONObject;
//import com.github.pagehelper.PageInfo;
//import com.iot.service.ElasticsearchService;
//import org.junit.Assert;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//public class ElasticsearchServiceTest extends TmallApplicationTests{
//
//    @Autowired
//    private ElasticsearchService elasticsearchService;
//    /**
//     * 创建索引
//     */
//    @Test
//    public void createIndexTest() throws IOException {
//        elasticsearchService.createIndex("index_test", null);
//    }
//
//
//    /**
//     * 数据添加
//     */
//    @Test
//    public void addDataTest() throws IOException {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("message", "撒地方");
//        jsonObject.put("name", "钱钱钱");
//
//        elasticsearchService.addData(jsonObject, "index_test", "1");
//
//        Map<String, Object>  map = elasticsearchService.searchDataById("index_test", "1");
//        Assert.assertEquals("钱钱钱", String.valueOf(map.get("name")));
//    }
//
//    @Test
//    public void updateDataByIdTest() throws IOException {
//        Map<String, Object> mapUpdate = new HashMap<String, Object>();
//        mapUpdate.put("name", "鹏磊1");
//        elasticsearchService.updateDataById(mapUpdate, "index_test", "1");
//
//        Map<String, Object>  map = elasticsearchService.searchDataById("index_test", "1");
//        Assert.assertEquals("鹏磊1", String.valueOf(map.get("name")));
//    }
//
//
//    /**
//     * 通过ID删除数据
//     */
//    @Test
//    public void deleteDataByIdTest() throws IOException {
//        elasticsearchService.deleteDataById("index_test", "1");
//
//        Map<String, Object>  map = elasticsearchService.searchDataById("index_test", "1");
//        Assert.assertEquals(null, map);
//    }
//
//
//    @Test
//    public void searchDataPage() throws IOException {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("message", "方");
//        PageInfo esPage = elasticsearchService.searchDataPage("index_test", 1, 10, jsonObject);
//
//        Assert.assertEquals(1, esPage.getTotal());
//    }
//
//
//    /**
//     * 删除索引
//     */
//    @Test
//    public void deleteIndexTest() throws IOException {
//        elasticsearchService.deleteIndex("index_test");
//    }
//
//}
