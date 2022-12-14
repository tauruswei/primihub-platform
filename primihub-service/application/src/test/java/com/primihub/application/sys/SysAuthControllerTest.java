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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysAuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAuthTree() throws Exception{
        this.mockMvc.perform(get("/auth/getAuthTree"))
                .andExpect(status().isOk())
                .andDo(document("getAuthTree",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                        ),
                        requestParameters(
                        ),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
                                fieldWithPath("result.sysAuthRootList").description("???????????????"),
                                fieldWithPath("result.sysAuthRootList[].authId").description("??????id"),
                                fieldWithPath("result.sysAuthRootList[].authName").description("????????????"),
                                fieldWithPath("result.sysAuthRootList[].authType").description("???????????????1.?????? 2.?????? 3.?????? 4.?????????"),
                                fieldWithPath("result.sysAuthRootList[].authCode").description("????????????"),
                                fieldWithPath("result.sysAuthRootList[].dataAuthCode").description("??????????????????"),
                                fieldWithPath("result.sysAuthRootList[].authIndex").description("??????"),
                                fieldWithPath("result.sysAuthRootList[].authDepth").description("??????"),
                                fieldWithPath("result.sysAuthRootList[].isShow").description("????????????"),
                                fieldWithPath("result.sysAuthRootList[].isEditable").description("???????????????"),
                                fieldWithPath("result.sysAuthRootList[].isDel").description("????????????"),
                                fieldWithPath("result.sysAuthRootList[].pauthId").description("???id"),
                                fieldWithPath("result.sysAuthRootList[].rauthId").description("???id"),
                                fieldWithPath("result.sysAuthRootList[].fullPath").description("?????????"),
                                fieldWithPath("result.sysAuthRootList[].authUrl").description("??????url"),
                                fieldWithPath("result.sysAuthRootList[].ctime").description("????????????"),
                                fieldWithPath("result.sysAuthRootList[].utime").description("????????????"),
                                fieldWithPath("result.sysAuthRootList[].children").optional().description("?????????"),
                                fieldWithPath("result.sysAuthRootList[].isGrant").description("????????????"),
                                fieldWithPath("result.sysAuthRootList[].children[].*").optional().description("????????????"),
                                fieldWithPath("result.sysAuthRootList[].children[].children[].*").optional().description("????????????"),
                                fieldWithPath("result.sysAuthRootList[].children[].children[].children[].*").optional().description("????????????"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }

    @Test
    public void testAuthCreateAuthNode() throws Exception{
        this.mockMvc.perform(post("/auth/createAuthNode")
                .param("authName","????????????1")
                .param("authType","1")
                .param("authCode","project_code")
                .param("pAuthId","0")
                .param("dataAuthCode","own")
                .param("authIndex","1")
                .param("isShow","1")
                .param("authUrl",""))
                .andExpect(status().isOk())
                .andDo(document("createAuthNode",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(

                        ),
                        requestParameters(
                                parameterWithName("authName").description("????????????"),
                                parameterWithName("authType").description("???????????????1.?????? 2.?????? 3.?????????"),
                                parameterWithName("authCode").description("????????????(????????????????????????)"),
                                parameterWithName("pAuthId").description("???????????????id ??????????????????0"),
                                parameterWithName("authIndex").description("??????"),
                                parameterWithName("dataAuthCode").description("?????????????????? ????????? ?????????????????????"),
                                parameterWithName("isShow").description("???????????? ????????? ?????????1 ??????"),
                                parameterWithName("authUrl").description("??????url ????????? ??????????????????")
                        ),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
                                fieldWithPath("result.sysAuth.authId").description("??????id"),
                                fieldWithPath("result.sysAuth.authName").description("????????????"),
                                fieldWithPath("result.sysAuth.authType").description("???????????????1.?????? 2.?????? 3.?????? 4.?????????"),
                                fieldWithPath("result.sysAuth.authCode").description("????????????"),
                                fieldWithPath("result.sysAuth.dataAuthCode").description("??????????????????"),
                                fieldWithPath("result.sysAuth.authIndex").description("??????"),
                                fieldWithPath("result.sysAuth.authDepth").description("??????"),
                                fieldWithPath("result.sysAuth.isShow").description("????????????"),
                                fieldWithPath("result.sysAuth.isEditable").description("???????????????"),
                                fieldWithPath("result.sysAuth.isDel").description("????????????"),
                                fieldWithPath("result.sysAuth.pauthId").description("???id"),
                                fieldWithPath("result.sysAuth.rauthId").description("???id"),
                                fieldWithPath("result.sysAuth.fullPath").description("?????????"),
                                fieldWithPath("result.sysAuth.authUrl").description("??????url"),
                                fieldWithPath("result.sysAuth.ctime").description("????????????"),
                                fieldWithPath("result.sysAuth.utime").description("????????????"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }


    @Test
    public void testAlterAuthNodeStatus() throws Exception{
        this.mockMvc.perform(get("/auth/alterAuthNodeStatus")
                .param("authId","1")
                .param("authName","????????????")
                .param("authType","1")
                .param("authCode","Project")
                .param("dataAuthCode","own")
                .param("isShow","1"))
                .andExpect(status().isOk())
                .andDo(document("alterAuthNodeStatus",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(

                        ),
                        requestParameters(
                                parameterWithName("authId").description("??????id"),
                                parameterWithName("authName").description("????????????"),
                                parameterWithName("authType").description("???????????????1.?????? 2.?????? 3.?????? 4.?????????"),
                                parameterWithName("authCode").description("????????????(????????????????????????)"),
                                parameterWithName("dataAuthCode").description("?????????????????? ????????? ?????????????????????"),
                                parameterWithName("isShow").description("???????????? ????????? ?????????1 ??????")
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
    public void testDeleteAuthNode() throws Exception {
        this.mockMvc.perform(get("/auth/deleteAuthNode")
                .param("authId","999"))
                .andExpect(status().isOk())
                .andDo(document("deleteAuthNode",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(

                        ),
                        requestParameters(
                                parameterWithName("authId").description("??????id")
                        ),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }
}
