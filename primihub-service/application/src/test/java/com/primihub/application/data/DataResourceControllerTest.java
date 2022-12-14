package com.primihub.application.data;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.primihub.biz.entity.data.req.DataResourceFieldReq;
import com.primihub.biz.entity.data.req.DataResourceReq;
import com.primihub.biz.entity.data.req.DataSourceOrganReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataResourceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSaveResource() throws Exception{
        DataResourceReq dataResourceReq=new DataResourceReq();
//        dataResourceReq.setResourceId(87L);
        dataResourceReq.setResourceName("????????????");
        dataResourceReq.setResourceDesc("?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
        dataResourceReq.setResourceAuthType(3);
        List<DataSourceOrganReq> organList=new ArrayList(){{
            add(new DataSourceOrganReq("A111111","??????A","http://localhost:8099"));
            add(new DataSourceOrganReq("B222222","??????B","http://localhost:8099"));
            add(new DataSourceOrganReq("C333333","??????C","http://localhost:8099"));;}};
        dataResourceReq.setFusionOrganList(organList);
        dataResourceReq.setResourceSource(1);
        dataResourceReq.setFileId(1000L);
        dataResourceReq.setTags(new ArrayList(){{add("??????");add("?????????");add("????????????");}});
        DataResourceFieldReq dataResourceFieldReq=new DataResourceFieldReq();
        dataResourceFieldReq.setFieldName("id");
        dataResourceFieldReq.setFieldType("String");
        dataResourceReq.setFieldList(new ArrayList(){{add(dataResourceFieldReq);}});
        dataResourceReq.setPageNo(null);
        dataResourceReq.setPageSize(null);
        dataResourceReq.setSelectTag(null);
        String tojson = JSONObject.toJSONString(dataResourceReq,true);
        System.out.println(tojson);
        this.mockMvc.perform(post("/resource/saveorupdateresource")
                .contentType(MediaType.APPLICATION_JSON)
                .content(tojson)
                .header("userId","1"))
                .andExpect(status().isOk())
                .andDo(document("dataResource",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("userId").description("??????id (??????????????????)")
                        ),
                        requestParameters(
                        ),
                        requestFields(
                                fieldWithPath("resourceId").optional().type(Long.class).description("??????id ????????????"),
                                fieldWithPath("resourceName").optional().description("????????????"),
                                fieldWithPath("resourceDesc").optional().description("????????????"),
                                fieldWithPath("resourceAuthType").optional().description("???????????? 1.?????? 2.?????? 3.??????"),
                                fieldWithPath("resourceSource").optional().description("???????????? 1.???????????? 2.???????????????"),
                                fieldWithPath("fileId").optional().description("??????id"),
                                fieldWithPath("tags").optional().description("??????name??????"),
                                fieldWithPath("fieldList[].fieldName").optional().description("??????????????????"),
                                fieldWithPath("fieldList[].fieldType").optional().description("??????????????????"),
                                fieldWithPath("fieldList[].fieldDesc").optional().type(String.class).description("??????????????????"),
                                fieldWithPath("fieldList[].grouping").optional().description("????????? 0??? 1???"),
                                fieldWithPath("fieldList[].protectionStatus").optional().description("?????? 0??? 1???"),
                                fieldWithPath("fieldList[].relevance").optional().description("???????????? 0?????? 1??????"),
                                fieldWithPath("fusionOrganList[].organGlobalId").optional().description("??????id"),
                                fieldWithPath("fusionOrganList[].organName").optional().description("????????????"),
                                fieldWithPath("fusionOrganList[].organServerAddress").optional().description("??????????????????")
                        ),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
                                fieldWithPath("result.resourceId").description("??????id"),
                                fieldWithPath("result.resourceName").description("????????????"),
                                fieldWithPath("result.resourceDesc").description("????????????"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }



    @Test
    public void testGetdataResourceList() throws Exception{
        // ????????????????????????
        this.mockMvc.perform(get("/resource/getdataresourcelist")
                .param("pageNo","1")
                .param("pageSize","5")
                .param("resourceName","")
                .param("resourceAuthType","")
                .param("tag","")
                .param("selectTag","0")
                .header("userId","1"))
                .andExpect(status().isOk())
                .andDo(document("getdataresourcelist",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("userId").description("??????id (??????????????????)")
                        ),
                        requestParameters(
                                parameterWithName("pageNo").description("?????????"),
                                parameterWithName("pageSize").description("????????????"),
                                parameterWithName("resourceName").description("????????????"),
                                parameterWithName("resourceAuthType").description("???????????? 1.?????? 2.??????"),
                                parameterWithName("tag").description("????????????"),
                                parameterWithName("selectTag").description("??????????????????  0:?????? 1:?????? ??????0??????")
                        ),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
                                fieldWithPath("result.total").description("??????????????????"),
                                fieldWithPath("result.pageSize").description("?????????????????????"),
                                fieldWithPath("result.totalPage").description("???????????????"),
                                fieldWithPath("result.index").description("??????????????????"),
                                fieldWithPath("result.data").description("??????"),
                                fieldWithPath("result.data[].resourceId").description("??????id"),
                                fieldWithPath("result.data[].resourceName").description("????????????"),
                                fieldWithPath("result.data[].resourceDesc").description("????????????"),
                                fieldWithPath("result.data[].resourceAuthType").description("???????????? 1.?????? 2.?????? 2.??????"),
                                fieldWithPath("result.data[].resourceSource").description("???????????? ???????????? ???????????????"),
                                fieldWithPath("result.data[].resourceNum").description("?????????"),
                                fieldWithPath("result.data[].fileId").description("??????id"),
                                fieldWithPath("result.data[].fileSize").description("????????????"),
                                fieldWithPath("result.data[].fileSuffix").description("????????????"),
                                fieldWithPath("result.data[].fileRows").description("????????????"),
                                fieldWithPath("result.data[].fileColumns").description("????????????"),
                                fieldWithPath("result.data[].fileHandleField").description("??????????????????"),
                                fieldWithPath("result.data[].fileHandleStatus").description("?????????????????? 0 ????????? 1????????? 2????????????"),
                                fieldWithPath("result.data[].fileContainsY").optional().description("???????????????????????????y?????? 0??? 1???"),
                                fieldWithPath("result.data[].fileYRows").optional().description("??????y??????????????????"),
                                fieldWithPath("result.data[].fileYRatio").optional().description("??????y???????????????????????????????????????"),
                                fieldWithPath("result.data[].dbId").description("?????????id"),
//                                fieldWithPath("result.data[].url").description("??????url"),
                                fieldWithPath("result.data[].userId").description("??????id"),
                                fieldWithPath("result.data[].userName").description("????????????"),
                                fieldWithPath("result.data[].organId").description("??????id"),
                                fieldWithPath("result.data[].organName").description("????????????"),
                                fieldWithPath("result.data[].createDate").description("????????????"),
                                fieldWithPath("result.data[].tags[]").description("????????????"),
                                fieldWithPath("result.data[].tags[].tagId").description("??????id"),
                                fieldWithPath("result.data[].tags[].tagName").description("????????????"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }

    @Test
    public void testGetDataResource() throws Exception{
        // ????????????????????????
        this.mockMvc.perform(get("/resource/getdataresource")
                .param("resourceId","1")
                .header("userId","1"))
                .andExpect(status().isOk())
                .andDo(document("getdataresource",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("userId").description("??????id (??????????????????)")
                        ),
                        requestParameters(
                                parameterWithName("resourceId").description("??????id")
                        ),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
                                fieldWithPath("result.resource").description("????????????"),
                                fieldWithPath("result.resource.resourceId").description("??????id"),
                                fieldWithPath("result.resource.resourceName").description("????????????"),
                                fieldWithPath("result.resource.resourceDesc").description("????????????"),
                                fieldWithPath("result.resource.resourceAuthType").description("???????????? 1.?????? 2.?????? 3.??????"),
                                fieldWithPath("result.resource.resourceSource").description("???????????? ???????????? ???????????????"),
                                fieldWithPath("result.resource.resourceNum").description("?????????"),
                                fieldWithPath("result.resource.fileId").description("??????id"),
                                fieldWithPath("result.resource.fileSize").description("????????????"),
                                fieldWithPath("result.resource.fileSuffix").description("????????????"),
                                fieldWithPath("result.resource.fileRows").description("????????????"),
                                fieldWithPath("result.resource.fileColumns").description("????????????"),
                                fieldWithPath("result.resource.fileHandleField").description("??????????????????"),
                                fieldWithPath("result.resource.fileHandleStatus").description("?????????????????? 0 ????????? 1????????? 2????????????"),
                                fieldWithPath("result.resource.fileContainsY").optional().description("???????????????????????????y?????? 0??? 1???"),
                                fieldWithPath("result.resource.fileYRows").optional().description("??????y??????????????????"),
                                fieldWithPath("result.resource.fileYRatio").optional().description("??????y???????????????????????????????????????"),
                                fieldWithPath("result.resource.userId").description("??????id"),
                                fieldWithPath("result.resource.userName").description("????????????"),
                                fieldWithPath("result.resource.dbId").description("?????????id"),
//                                fieldWithPath("result.resource.url").description("??????url"),
                                fieldWithPath("result.resource.organId").description("??????id"),
                                fieldWithPath("result.resource.organName").description("????????????"),
                                fieldWithPath("result.resource.createDate").description("????????????"),
                                fieldWithPath("result.resource.tags").description("??????????????????"),
                                fieldWithPath("result.resource.tags[].tagId").description("??????id"),
                                fieldWithPath("result.resource.tags[].tagName").description("????????????"),
                                fieldWithPath("result.dataList[].*").ignored().optional().description("??????????????????"),
                                fieldWithPath("result.fieldList[].*").ignored().optional().description("??????????????????"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }

    @Test
    public void testDelDataResource() throws Exception{
        // ??????????????????
        this.mockMvc.perform(get("/resource/deldataresource")
                .param("resourceId","1")
                .header("userId","1")
                .header("organId","1"))
                .andExpect(status().isOk())
                .andDo(document("deldataresource",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("userId").description("??????id (??????????????????)"),
                                headerWithName("organId").description("??????id (??????????????????)")
                        ),
                        requestParameters(
                                parameterWithName("resourceId").description("??????id")
                        ),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }

    @Test
    public void testGetDataResourceFieldPage() throws Exception{
        this.mockMvc.perform(get("/resource/getDataResourceFieldPage")
                .param("resourceId","1")
                .param("pageSize","5")
                .param("pageNo","1")
                .header("userId","1"))
                .andExpect(status().isOk())
                .andDo(document("getDataResourceFieldPage",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("userId").description("??????id (??????????????????)")
                        ),
                        requestParameters(
                                parameterWithName("resourceId").description("??????id"),
                                parameterWithName("pageSize").description("???????????????"),
                                parameterWithName("pageNo").description("??????")
                        ),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
                                fieldWithPath("result.isUpdate").description("???????????????"),
                                fieldWithPath("result.pageData.total").description("??????????????????"),
                                fieldWithPath("result.pageData.pageSize").description("?????????????????????"),
                                fieldWithPath("result.pageData.totalPage").description("???????????????"),
                                fieldWithPath("result.pageData.index").description("??????????????????"),
                                fieldWithPath("result.pageData.data").description("??????"),
                                fieldWithPath("result.pageData.data[].fieldId").description("??????id"),
                                fieldWithPath("result.pageData.data[].fieldName").description("????????????"),
                                fieldWithPath("result.pageData.data[].fieldAs").description("????????????"),
                                fieldWithPath("result.pageData.data[].fieldType").description("????????????"),
                                fieldWithPath("result.pageData.data[].fieldDesc").description("????????????"),
                                fieldWithPath("result.pageData.data[].relevance").description("????????? 0??? 1???"),
                                fieldWithPath("result.pageData.data[].grouping").description("?????? 0??? 1???"),
                                fieldWithPath("result.pageData.data[].protectionStatus").description("???????????? 0?????? 1??????"),
                                fieldWithPath("result.pageData.data[].createDate").description("????????????"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }

    @Test
    public void testUpdateDataResourceField() throws Exception{
        this.mockMvc.perform(get("/resource/updateDataResourceField")
                .param("fieldId","659")
                .param("fieldAs","liweihua")
                .param("fieldType","string")
                .param("fieldDesc","????????????")
                .param("relevance","1")
                .param("grouping","0")
                .param("protectionStatus","1"))
                .andExpect(status().isOk())
                .andDo(document("updateDataResourceField",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("fieldId").description("??????id"),
                                parameterWithName("fieldAs").description("????????????"),
                                parameterWithName("fieldType").description("????????????"),
                                parameterWithName("fieldDesc").description("????????????"),
                                parameterWithName("relevance").description("????????? 0??? 1???"),
                                parameterWithName("grouping").description("?????? 0??? 1???"),
                                parameterWithName("protectionStatus").description("???????????? 0?????? 1??????")
                        ),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }
    @Test
    public void testGetResourceTags() throws Exception{
        this.mockMvc.perform(get("/resource/getResourceTags"))
                .andExpect(status().isOk())
                .andDo(document("getResourceTags",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
                                fieldWithPath("result[]").description("????????????"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }
}
