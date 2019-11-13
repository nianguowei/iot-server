package com.iot.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iot.domain.Attribute;
import com.iot.service.ElasticsearchService;
import com.sun.deploy.association.utility.AppConstants;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;


/**
 * 描述: Elasticsearch
 *
 * @author Nian Guowei
 * @create 2018-11-01 11:20
 **/
@Component
public class ElasticsearchServiceImpl implements ElasticsearchService{

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchServiceImpl.class);

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引
     *
     * @param index
     * @return
     */
    public boolean createIndex(String index, List<Attribute> attributeList) throws IOException {
        if (isIndexExist(index)) {
            logger.debug("Index is exits!");
            return false;
        }

        // 1、创建 创建索引request 参数：索引名mess
        CreateIndexRequest request = new CreateIndexRequest(index);

        // 2、设置索引的settings
        request.settings(Settings.builder().put("index.number_of_shards", 3) // 分片数
                .put("index.number_of_replicas", 2) // 副本数
                .put("analysis.analyzer.my_analyzer.tokenizer", "my_tokenizer")
                .put("analysis.tokenizer.my_tokenizer.type", "ik_smart")
                .put("analysis.tokenizer.my_tokenizer.max_token_length", "5")
        );

        //todo 设置mapping

        // 3、设置索引的mappings
        request.mapping(
                "  {\n" +
                        "      \"properties\": {\n" +
                        "        \"message\": {\n" +
                        "          \"type\": \"text\"\n" +
                        "        }\n" +
                        "      }\n" +
                        "  }",
                XContentType.JSON);

        // 4、 设置索引的别名
//        request.alias(new Alias("alias_" + index));

        // 5、 发送请求
        // 5.1 同步方式发送请求
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);

        // 6、处理响应
        boolean acknowledged = createIndexResponse.isAcknowledged();
        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
        System.out.println("acknowledged = " + acknowledged);
        System.out.println("shardsAcknowledged = " + shardsAcknowledged);

        return acknowledged;
    }

    /**
     * 判断索引是否存在
     *
     * @param index
     * @return
     */
    public boolean isIndexExist(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 删除索引
     *
     * @param index
     * @return
     */
    public boolean deleteIndex(String index) throws IOException {
        if (!isIndexExist(index)) {
            logger.debug("Index is not exits!");
            return false;
        }
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        return acknowledgedResponse.isAcknowledged();
    }


    /**
     * 数据添加，正定ID
     * @param jsonObject
     * @param index
     * @param id
     * @return
     * @throws IOException
     */
    public String addData(JSONObject jsonObject, String index, String id) throws IOException {
        // 1、创建索引请求
        IndexRequest request = new IndexRequest(index).id(id);
        request.source(jsonObject, XContentType.JSON);
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        logger.debug("新增数据 status:{},id:{}", response.status().getStatus(), response.getId());
        return response.getId();
    }

    /**
     * 数据添加，正定ID
     * @param jsonMap
     * @param index
     * @param id
     * @return
     * @throws IOException
     */
    public String addData(Map<String, Object> jsonMap, String index, String id) throws IOException {
        // 1、创建索引请求
        IndexRequest request = new IndexRequest(index);
        request.source(jsonMap);
        //4、发送请求
        IndexResponse response =  restHighLevelClient.index(request, RequestOptions.DEFAULT);
        logger.debug("新增数据 status:{},id:{}", response.status().getStatus(), response.getId());
        return response.getId();
    }

    /**
     * 通过ID删除数据
     * @param index
     * @param id
     * @throws IOException
     */
    public void deleteDataById(String index, String id) throws IOException {
        DeleteRequest request = new DeleteRequest(index, String.valueOf(id));
        DeleteResponse deleteResponse = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        if (deleteResponse.status() == RestStatus.OK) {
            logger.info("删除成功！id: {}", id);
        }
    }
//
//    /**
//     * 通过属性删除数据
//     *
//     * @param index 索引，类似数据库
//     * @param key   属性
//     * @param value 值
//     */
//    public void deleteByQuery(String index, String key, String value) {
//        BulkByScrollResponse response =
//                new DeleteByQueryRequestBuilder((ElasticsearchClient) restHighLevelClient, DeleteByQueryAction.INSTANCE)
//                        .filter(QueryBuilders.matchPhraseQuery(key, value))
//                        .source(index)
//                        .get();
////        long deleted = response.getDeleted();
//        logger.debug("deleteDataByQuery response status:{},id:{}", response.getStatus(), response.getDeleted());
//    }
//

    /**
     * 更新数据
     * @param objectMap
     * @param index
     * @param id
     * @throws IOException
     */
    public void updateDataById(Map<String, Object> objectMap, String index, String id) throws IOException {
        UpdateRequest request = new UpdateRequest(index, id);
        request.doc(objectMap);
        UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        if (updateResponse.status() == RestStatus.OK) {
            logger.info("修改成功！id: {}", id);
        }
    }

    /**
     * 通过ID获取数据
     * @param index
     * @param id
     * @return
     * @throws IOException
     */
    public Map<String, Object> searchDataById(String index, String id) throws IOException {
        return searchDataById(index, id, null);
    }

    /**
     * 通过ID获取数据
     * @param index
     * @param id
     * @param fields
     * @return
     * @throws IOException
     */
    public Map<String, Object> searchDataById(String index, String id, String fields) throws IOException {
        GetRequest getRequest = new GetRequest(index, id);
        if (StringUtils.isNotEmpty(fields)) {
            getRequest.storedFields(fields.split(","));
        }
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        if (getResponse.isExists()) {
            return getResponse.getSource();
        }
        return null;
    }

    /**
     * 通过ID获取数据
     * @param index
     * @param ids
     * @param fields
     * @param pageSize
     * @return
     * @throws IOException
     */
    public List<Map<String, Object>> searchDataByIds(String index, String[] ids, String fields, Integer pageSize) throws IOException {
        return searchDataByIds(index, ids, fields, null, null, pageSize);
    }

    /**
     * 通过IDS 查询List
     * @param index
     * @param ids
     * @param fields
     * @param sortField
     * @param sortOrder
     * @param pageSize
     * @return
     * @throws IOException
     */
    public List<Map<String, Object>> searchDataByIds(String index, String[] ids, String fields, String sortField, String sortOrder, Integer pageSize) throws IOException {

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        if (pageSize!=null && pageSize > 0) {
            sourceBuilder.from(0);
            sourceBuilder.size(pageSize);
        }

        // 需要显示的字段，逗号分隔（缺省为全部字段）
        if (StringUtils.isNotEmpty(fields)) {
            sourceBuilder.fetchSource(fields.split(","), null);
        }

        //排序字段
        if (StringUtils.isNotEmpty(sortField) && StringUtils.isNotEmpty(sortOrder)) {
            if (sortOrder.equalsIgnoreCase("desc")) {
                sourceBuilder.sort(new FieldSortBuilder(sortField).order(SortOrder.DESC));
            } else {
                sourceBuilder.sort(new FieldSortBuilder(sortField).order(SortOrder.ASC));
            }
        }

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.idsQuery().addIds(ids));

//        sourceBuilder.query(QueryBuilders.matchAllQuery());
        sourceBuilder.query(boolQueryBuilder);

        // 执行搜索,返回搜索响应信息
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        RestStatus restStatus = searchResponse.status();
        if (restStatus != RestStatus.OK) {
            return null;
        }
        // 解析对象
        return setSearchResponse(searchResponse);
    }

    /**
     * 使用分词查询,并分页
     * @param index
     * @param currentPage
     * @param pageSize
     * @param jsonObject
     * @return
     */
    public PageInfo searchDataPage(String index, int currentPage, int pageSize, JSONObject jsonObject) throws IOException {
        return searchDataPage(index, currentPage, pageSize, null, null, null, jsonObject);
    }
    /**
     * 使用分词查询,并分页
     * @param index
     * @param currentPage
     * @param pageSize
     * @param fields
     * @param jsonObject
     * @return
     */
    public PageInfo searchDataPage(String index, int currentPage, int pageSize, String fields, JSONObject jsonObject) throws IOException {
        return searchDataPage(index, currentPage, pageSize, fields, null, null, jsonObject);
    }

    /**
     * 使用分词查询,并分页
     * @param index
     * @param currentPage
     * @param pageSize
     * @param fields
     * @param sortField
     * @param sortOrder
     * @param jsonObject
     * @return
     */
    public PageInfo searchDataPage(String index, int currentPage, int pageSize, String fields, String sortField, String sortOrder, JSONObject jsonObject) throws IOException {
        BoolQueryBuilder boolQuery = createBoolQueryBuilder(jsonObject);
        return searchDataPage(index, currentPage, pageSize, fields, sortField, sortOrder, boolQuery);
    }

    /**
     * 生成bool查询器
     * @param jsonObject
     * @return
     */
    private BoolQueryBuilder createBoolQueryBuilder(JSONObject jsonObject){
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (jsonObject != null) {
            Set<String> keySet = jsonObject.keySet();
            for (String key : keySet) {
               boolQuery.must(QueryBuilders.matchPhraseQuery(key, jsonObject.getString(key)));
               //boolQuery.must(QueryBuilders.matchQuery(key, jsonObject.getString(key)).analyzer("ik_max_word"));
            }
        }
        return boolQuery;
    }

//    /**
//     * 根据条件查询数量
//     * @param index
//     * @param type
//     * @param jsonObject
//     * @return
//     */
//    public long searchDataCount(String index, String type, JSONObject jsonObject) {
//        BoolQueryBuilder boolQuery = createBoolQueryBuilder(jsonObject);
//        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(index);
//        if (StringUtils.isNotEmpty(type)) {
//            searchRequestBuilder.setTypes(type.split(","));
//        }
//
//        searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH);
//        searchRequestBuilder.setQuery(QueryBuilders.matchAllQuery());
//        searchRequestBuilder.setQuery(boolQuery);
//        // 设置返回结果为0
//        searchRequestBuilder.setSize(0);
//
//        // 设置是否按查询匹配度排序
//        searchRequestBuilder.setExplain(true);
//
//        //打印的内容 可以在 Elasticsearch head 和 Kibana  上执行查询
//        logger.debug("\n{}", searchRequestBuilder);
//
//        // 执行搜索,返回搜索响应信息
//        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
//        long length = searchResponse.getHits().totalHits;
//        logger.debug("共查询数据条数[{}]", length);
//
//        return length;
//    }

    /**
     * 定义好boolQuery 调用ES查询
     * @param index
     * @param currentPage
     * @param pageSize
     * @param fields
     * @param sortField
     * @param sortOrder
     * @param boolQueryBuilder
     * @return
     * @throws IOException
     */
    private PageInfo searchDataPage(String index, int currentPage, int pageSize, String fields, String sortField, String sortOrder, BoolQueryBuilder boolQueryBuilder) throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 需要显示的字段，逗号分隔（缺省为全部字段）
        if (StringUtils.isNotEmpty(fields)) {
            sourceBuilder.fetchSource(fields.split(","), null);
        }
        // 分页应用
        if (currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize > 0) {
            sourceBuilder.from((currentPage - 1));
            sourceBuilder.size(pageSize);
        }

        //排序字段
        if (StringUtils.isNotEmpty(sortField) && StringUtils.isNotEmpty(sortOrder)) {
            if (sortOrder.equalsIgnoreCase("desc")) {
                sourceBuilder.sort(new FieldSortBuilder(sortField).order(SortOrder.DESC));
            } else {
                sourceBuilder.sort(new FieldSortBuilder(sortField).order(SortOrder.ASC));
            }
        }

        //sourceBuilder.query(QueryBuilders.matchAllQuery());
        sourceBuilder.query(boolQueryBuilder);

        // 设置是否按查询匹配度排序
        sourceBuilder.explain(true);

        //打印的内容 可以在 Elasticsearch head 和 Kibana  上执行查询
        logger.debug("\n{}", sourceBuilder);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        searchRequest.source(sourceBuilder);

        return returnPageInfo(searchRequest);

    }

    /**
     * 拼装返回值
     * @param searchRequest
     * @return
     * @throws IOException
     */
    private PageInfo returnPageInfo(SearchRequest searchRequest) throws IOException {
        // 执行搜索,返回搜索响应信息
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        RestStatus restStatus = searchResponse.status();
        if (restStatus != RestStatus.OK) {
            return null;
        }

        long totalHits = searchResponse.getHits().getTotalHits().value;
        logger.info("共查询到[{}]条数据", totalHits);

        // 解析对象
        List<Map<String, Object>> sourceList = setSearchResponse(searchResponse);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setTotal(totalHits);
        pageInfo.setList(sourceList);
        return pageInfo;
    }


    /**
     * 高亮结果集 特殊处理
     *
     * @param searchResponse
     */
    private static List<Map<String, Object>> setSearchResponse(SearchResponse searchResponse) {
        List<Map<String, Object>> sourceList = new ArrayList<>();
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            searchHit.getSourceAsMap().put("id", searchHit.getId());
            sourceList.add(searchHit.getSourceAsMap());
        }
        return sourceList;
    }
}
