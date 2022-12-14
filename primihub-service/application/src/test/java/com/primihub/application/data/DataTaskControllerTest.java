package com.primihub.application.data;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataTaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetModelTaskList() throws Exception{
        this.mockMvc.perform(get("/task/getModelTaskList")
                .param("pageNo","1")
                .param("pageSize","5")
                .param("modelId","3"))
                .andExpect(status().isOk())
                .andDo(document("getModelTaskList",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                        ),
                        requestParameters(
                                parameterWithName("pageNo").description("?????????"),
                                parameterWithName("pageSize").description("????????????"),
                                parameterWithName("modelId").description("??????id")
                        ),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
                                fieldWithPath("result.total").description("??????????????????"),
                                fieldWithPath("result.pageSize").description("?????????????????????"),
                                fieldWithPath("result.totalPage").description("???????????????"),
                                fieldWithPath("result.index").description("??????????????????"),
                                fieldWithPath("result.data[]").optional().description("??????"),
                                fieldWithPath("result.data[].taskId").optional().description("??????ID"),
                                fieldWithPath("result.data[].taskIdName").optional().description("????????????"),
                                fieldWithPath("result.data[].taskState").optional().description("????????????(0????????? 1?????? 2????????? 3?????? 4??????)"),
                                fieldWithPath("result.data[].taskType").optional().description("???????????? 1????????? 2???PSI 3???PIR"),
                                fieldWithPath("result.data[].taskStartDate").optional().description("??????????????????"),
                                fieldWithPath("result.data[].taskEndDate").optional().description("??????????????????"),
                                fieldWithPath("result.data[].taskErrorMsg").optional().description("???????????? ????????????"),
                                fieldWithPath("result.data[].timeConsuming").optional().description("?????? ?????????"),
                                fieldWithPath("result.data[].taskName").optional().description("???????????? ???????????????"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }


    @Test
    public void testGetTaskData() throws Exception{
        this.mockMvc.perform(get("/task/getTaskData")
                .param("taskId","1"))
                .andExpect(status().isOk())
                .andDo(document("getTaskData",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                        ),
                        requestParameters(
                                parameterWithName("taskId").description("??????ID")
                        ),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
                                fieldWithPath("result.taskId").optional().description("??????ID"),
                                fieldWithPath("result.taskIdName").optional().description("????????????"),
                                fieldWithPath("result.taskState").optional().description("????????????(0????????? 1?????? 2????????? 3?????? 4??????)"),
                                fieldWithPath("result.taskType").optional().description("???????????? 1????????? 2???PSI 3???PIR"),
                                fieldWithPath("result.taskStartDate").optional().description("??????????????????"),
                                fieldWithPath("result.taskEndDate").optional().description("??????????????????"),
                                fieldWithPath("result.taskErrorMsg").optional().description("???????????? ????????????"),
                                fieldWithPath("result.timeConsuming").optional().description("?????? ?????????"),
                                fieldWithPath("result.taskName").optional().description("???????????? ???????????????"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }

    @Test
    public void testDeleteTask() throws Exception{
        this.mockMvc.perform(get("/task/deleteTask")
                .param("taskId","1"))
                .andExpect(status().isOk())
                .andDo(document("deleteTask",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                        ),
                        requestParameters(
                                parameterWithName("taskId").description("??????ID")
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
    public void testGetPirTaskList() throws Exception{
        this.mockMvc.perform(get("/pir/getPirTaskList")
                .param("pageNo","1")
                .param("pageSize","5")
                .param("organName","")
                .param("resourceName","")
                .param("retrievalId","")
                .param("taskState","")
                .param("serverAddress",""))
                .andExpect(status().isOk())
                .andDo(document("getPirTaskList",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                        ),
                        requestParameters(
                                parameterWithName("pageNo").description("?????????"),
                                parameterWithName("pageSize").description("????????????"),
                                parameterWithName("organName").description("????????????"),
                                parameterWithName("resourceName").description("????????????"),
                                parameterWithName("retrievalId").description("??????ID"),
                                parameterWithName("taskState").description("????????????(0????????? 1?????? 2????????? 3??????)"),
                                parameterWithName("serverAddress").description("????????????")
                        ),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
                                fieldWithPath("result.total").description("??????????????????"),
                                fieldWithPath("result.pageSize").description("?????????????????????"),
                                fieldWithPath("result.totalPage").description("???????????????"),
                                fieldWithPath("result.index").description("??????????????????"),
                                fieldWithPath("result.data[]").optional().description("??????"),
                                fieldWithPath("result.data[].taskId").optional().description("??????ID"),
                                fieldWithPath("result.data[].taskState").optional().description("????????????(0????????? 1?????? 2????????? 3?????? 4??????)"),
                                fieldWithPath("result.data[].serverAddress").optional().description("????????????"),
                                fieldWithPath("result.data[].organId").optional().description("??????ID"),
                                fieldWithPath("result.data[].organName").optional().description("????????????"),
                                fieldWithPath("result.data[].resourceId").optional().description("??????ID"),
                                fieldWithPath("result.data[].resourceName").optional().description("????????????"),
                                fieldWithPath("result.data[].resourceRowsCount").description("????????????"),
                                fieldWithPath("result.data[].resourceColumnCount").description("????????????"),
                                fieldWithPath("result.data[].resourceContainsY").optional().description("???????????????????????????y?????? 0??? 1???"),
                                fieldWithPath("result.data[].resourceYRowsCount").optional().description("??????y??????????????????"),
                                fieldWithPath("result.data[].resourceYRatio").optional().description("??????y???????????????????????????????????????"),
                                fieldWithPath("result.data[].retrievalId").optional().description("??????ID"),
                                fieldWithPath("result.data[].createDate").optional().description("????????????"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }
}
