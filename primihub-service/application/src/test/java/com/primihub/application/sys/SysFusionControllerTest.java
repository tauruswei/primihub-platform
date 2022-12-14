package com.primihub.application.sys;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
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
public class SysFusionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHealthConnection() throws Exception {
        this.mockMvc.perform(get("/fusion/healthConnection")
                .param("serverAddress","http://localhost:8099"))
                .andExpect(status().isOk())
                .andDo(document("healthConnection",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                        ),
                        requestParameters(
                                parameterWithName("serverAddress").description("??????????????????")
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
    public void testRegisterConnection() throws Exception {
        this.mockMvc.perform(get("/fusion/registerConnection")
                .param("serverAddress","http://localhost:8099"))
                .andExpect(status().isOk())
                .andDo(document("registerConnection",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                        ),
                        requestParameters(
                                parameterWithName("serverAddress").description("??????????????????")
                        ),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
                                fieldWithPath("result.isRegistered").description("????????????"),
                                fieldWithPath("result.fusionMsg").description("????????????"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }

    @Test
    public void testDeleteConnection() throws Exception {
        this.mockMvc.perform(get("/fusion/deleteConnection")
                .param("serverAddress","http://localhost:8096"))
                .andExpect(status().isOk())
                .andDo(document("deleteConnection",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                        ),
                        requestParameters(
                                parameterWithName("serverAddress").description("??????????????????")
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
    public void testCreateGroup() throws Exception {
        this.mockMvc.perform(get("/fusion/createGroup")
                .param("serverAddress","http://localhost:8099")
                .param("groupName","???????????????"))
                .andExpect(status().isOk())
                .andDo(document("createGroup",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                        ),
                        requestParameters(
                                parameterWithName("serverAddress").description("??????????????????"),
                                parameterWithName("groupName").description("????????????")
                        ),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
                                fieldWithPath("result.fusionMsg").description("??????"),
                                fieldWithPath("result.groupData.group.id").description("??????id"),
                                fieldWithPath("result.groupData.group.groupName").description("????????????"),
                                fieldWithPath("result.groupData.group.groupOrganId").description("????????????"),
                                fieldWithPath("result.groupData.group.isDel").description("????????????"),
                                fieldWithPath("result.groupData.group.ctime").optional().description("????????????"),
                                fieldWithPath("result.groupData.group.utime").optional().description("????????????"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }
    @Test
    public void testFindAllGroup() throws Exception {
        this.mockMvc.perform(get("/fusion/findAllGroup")
                .param("serverAddress","http://localhost:8099"))
                .andExpect(status().isOk())
                .andDo(document("findAllGroup",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                        ),
                        requestParameters(
                                parameterWithName("serverAddress").description("??????????????????")
                        ),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
                                fieldWithPath("result.fusionMsg").description("????????????"),
                                fieldWithPath("result.organList.groupList[]").description("????????????"),
                                fieldWithPath("result.organList.groupList[].id").description("??????ID"),
                                fieldWithPath("result.organList.groupList[].groupName").description("????????????"),
                                fieldWithPath("result.organList.groupList[].groupOrganId").description("??????id"),
                                fieldWithPath("result.organList.groupList[].isDel").description("????????????"),
                                fieldWithPath("result.organList.groupList[].in").description("??????????????????"),
                                fieldWithPath("result.organList.groupList[].utime").description("????????????"),
                                fieldWithPath("result.organList.groupList[].ctime").description("????????????"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }

    @Test
    public void testJoinGroup() throws Exception {
        this.mockMvc.perform(get("/fusion/joinGroup")
                .param("serverAddress","http%3A%2F%2Flocalhost%3A8099")
                .param("groupId","1"))
                .andExpect(status().isOk())
                .andDo(document("joinGroup",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                        ),
                        requestParameters(
                                parameterWithName("serverAddress").description("??????????????????"),
                                parameterWithName("groupId").description("??????ID")
                        ),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
                                fieldWithPath("result.fusionMsg").description("??????????????????"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }

    @Test
    public void testExitGroup() throws Exception {
        this.mockMvc.perform(get("/fusion/exitGroup")
                .param("serverAddress","http://localhost:8099")
                .param("groupId","1"))
                .andExpect(status().isOk())
                .andDo(document("exitGroup",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                        ),
                        requestParameters(
                                parameterWithName("serverAddress").description("??????????????????"),
                                parameterWithName("groupId").description("??????ID")
                        ),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
                                fieldWithPath("result.fusionMsg").description("??????????????????"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }
    @Test
    public void testFindOrganInGroup() throws Exception {
        this.mockMvc.perform(get("/fusion/findOrganInGroup")
                .param("serverAddress","http://localhost:8099")
                .param("groupId","32"))
                .andExpect(status().isOk())
                .andDo(document("findOrganInGroup",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                        ),
                        requestParameters(
                                parameterWithName("serverAddress").description("??????????????????"),
                                parameterWithName("groupId").description("??????ID")
                        ),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
                                fieldWithPath("result.dataList.organList[]").description("????????????????????????"),
                                fieldWithPath("result.dataList.organList[].id").optional().description("id"),
                                fieldWithPath("result.dataList.organList[].globalId").optional().description("??????id"),
                                fieldWithPath("result.dataList.organList[].globalName").optional().description("????????????"),
                                fieldWithPath("result.dataList.organList[].pinCodeMd").optional().description("code"),
                                fieldWithPath("result.dataList.organList[].gatewayAddress").optional().description("????????????"),
                                fieldWithPath("result.dataList.organList[].registerTime").optional().description("????????????"),
                                fieldWithPath("result.dataList.organList[].isDel").optional().description("????????????"),
                                fieldWithPath("result.dataList.organList[].ctime").optional().description("????????????"),
                                fieldWithPath("result.dataList.organList[].utime").optional().description("????????????"),
                                fieldWithPath("result.fusionMsg").description("??????????????????"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }


}
