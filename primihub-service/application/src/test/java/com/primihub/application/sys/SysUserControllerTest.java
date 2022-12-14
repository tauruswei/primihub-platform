package com.primihub.application.sys;

import com.alibaba.fastjson.JSON;
import com.primihub.biz.entity.base.BaseResultEntity;
import com.primihub.biz.util.crypt.CryptUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
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
public class SysUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetValidatePublicKey() throws Exception {
        this.mockMvc.perform(get("/common/getValidatePublicKey"))
                .andExpect(status().isOk())
                .andDo(document("getValidatePublicKey",
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
                                fieldWithPath("result.publicKey").description("??????"),
                                fieldWithPath("result.publicKeyName").description("??????key"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }


    @Test
    public void testLogin() throws Exception {
        MvcResult mvcResult=this.mockMvc.perform(get("/common/getValidatePublicKey")).andReturn();
        MockHttpServletResponse response =mvcResult.getResponse();
        String result=response.getContentAsString();
        BaseResultEntity<Map<String,String>> baseResultEntity=JSON.parseObject(result, BaseResultEntity.class);

        String rsaPassword= CryptUtil.encryptRsaWithPublicKey( "123456",baseResultEntity.getResult().get("publicKey"));
        String publicKeyName=baseResultEntity.getResult().get("publicKeyName");
        String userAccount="admin";

        MvcResult loginMvcResult=this.mockMvc.perform(post("/user/login")
                .param("userAccount",userAccount)
                .param("userPassword",rsaPassword)
                .param("validateKeyName",publicKeyName))
                .andExpect(status().isOk())
                .andDo(document("login",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                        ),
                        requestParameters(
                                parameterWithName("userAccount").description("????????????"),
                                parameterWithName("userPassword").description("???????????? ???rsa??????????????????"),
                                parameterWithName("validateKeyName").description("??????key?????????")
                        ),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
//                                fieldWithPath("result.roleAuthRootList").description("???????????????"),
//                                fieldWithPath("result.roleAuthRootList[].authId").description("??????id"),
//                                fieldWithPath("result.roleAuthRootList[].authName").description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].authType").description("???????????????1.?????? 2.?????? 3.?????????"),
//                                fieldWithPath("result.roleAuthRootList[].authCode").description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].dataAuthCode").description("??????????????????"),
//                                fieldWithPath("result.roleAuthRootList[].authIndex").description("??????"),
//                                fieldWithPath("result.roleAuthRootList[].authDepth").description("??????"),
//                                fieldWithPath("result.roleAuthRootList[].isShow").description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].isEditable").description("???????????????"),
//                                fieldWithPath("result.roleAuthRootList[].isDel").description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].pauthId").description("???id"),
//                                fieldWithPath("result.roleAuthRootList[].rauthId").description("???id"),
//                                fieldWithPath("result.roleAuthRootList[].fullPath").description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].authUrl").description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].ctime").description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].utime").description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].children").optional().description("?????????"),
//                                fieldWithPath("result.roleAuthRootList[].isGrant").description("???????????????"),
//
//                                fieldWithPath("result.roleAuthRootList[].children[].authId").optional().description("??????id"),
//                                fieldWithPath("result.roleAuthRootList[].children[].authName").optional().description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].authType").optional().description("???????????????1.?????? 2.?????? 3.?????? 4.?????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].authCode").optional().description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].dataAuthCode").optional().description("??????????????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].authIndex").optional().description("??????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].authDepth").optional().description("??????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].isShow").optional().description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].isEditable").optional().description("???????????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].isDel").optional().description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].pauthId").optional().description("???id"),
//                                fieldWithPath("result.roleAuthRootList[].children[].rauthId").optional().description("???id"),
//                                fieldWithPath("result.roleAuthRootList[].children[].fullPath").description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].authUrl").description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].ctime").optional().description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].utime").optional().description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].children").optional().description("?????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].isGrant").optional().description("???????????????"),
//
//                                fieldWithPath("result.roleAuthRootList[].children[].children[].authId").optional().description("??????id"),
//                                fieldWithPath("result.roleAuthRootList[].children[].children[].authName").optional().description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].children[].authType").optional().description("???????????????1.?????? 2.?????? 3.?????? 4.?????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].children[].authCode").optional().description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].children[].dataAuthCode").optional().description("??????????????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].children[].authIndex").optional().description("??????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].children[].authDepth").optional().description("??????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].children[].isShow").optional().description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].children[].isEditable").optional().description("???????????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].children[].isDel").optional().description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].children[].pauthId").optional().description("???id"),
//                                fieldWithPath("result.roleAuthRootList[].children[].children[].rauthId").optional().description("???id"),
//                                fieldWithPath("result.roleAuthRootList[].children[].children[].fullPath").description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].children[].authUrl").description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].children[].ctime").optional().description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].children[].utime").optional().description("????????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].children[].children").optional().description("?????????"),
//                                fieldWithPath("result.roleAuthRootList[].children[].children[].isGrant").optional().description("????????????"),
                                fieldWithPath("result.grantAuthRootList").description("?????????????????????"),
                                fieldWithPath("result.grantAuthRootList[].authId").description("??????id"),
                                fieldWithPath("result.grantAuthRootList[].authName").description("????????????"),
                                fieldWithPath("result.grantAuthRootList[].authType").description("???????????????1.?????? 2.?????? 3.?????????"),
                                fieldWithPath("result.grantAuthRootList[].authCode").description("????????????"),
                                fieldWithPath("result.grantAuthRootList[].dataAuthCode").description("??????????????????"),
                                fieldWithPath("result.grantAuthRootList[].authIndex").description("??????"),
                                fieldWithPath("result.grantAuthRootList[].authDepth").description("??????"),
                                fieldWithPath("result.grantAuthRootList[].isShow").description("????????????"),
                                fieldWithPath("result.grantAuthRootList[].isEditable").description("???????????????"),
                                fieldWithPath("result.grantAuthRootList[].isDel").description("????????????"),
                                fieldWithPath("result.grantAuthRootList[].pauthId").description("???id"),
                                fieldWithPath("result.grantAuthRootList[].rauthId").description("???id"),
                                fieldWithPath("result.grantAuthRootList[].fullPath").description("????????????"),
                                fieldWithPath("result.grantAuthRootList[].authUrl").description("????????????"),
                                fieldWithPath("result.grantAuthRootList[].ctime").description("????????????"),
                                fieldWithPath("result.grantAuthRootList[].utime").description("????????????"),
                                fieldWithPath("result.grantAuthRootList[].children").optional().description("?????????"),
                                fieldWithPath("result.grantAuthRootList[].isGrant").description("???????????????"),
                                fieldWithPath("result.sysUser").description("????????????"),
                                fieldWithPath("result.sysUser.userId").description("??????id"),
                                fieldWithPath("result.sysUser.userAccount").description("????????????"),
                                fieldWithPath("result.sysUser.registerType").description("????????????1?????????????????? 2????????? 3?????????"),
                                fieldWithPath("result.sysUser.userName").description("????????????"),
                                fieldWithPath("result.sysUser.roleIdList").description("??????id??????"),
                                fieldWithPath("result.sysUser.roleIdListDesc").description("??????id????????????"),
                                fieldWithPath("result.sysUser.organIdList").description("??????id??????"),
                                fieldWithPath("result.sysUser.organIdListDesc").description("??????id????????????"),
//                                fieldWithPath("result.sysUser.rorganIdList").description("?????????id??????"),
//                                fieldWithPath("result.sysUser.rorganIdListDesc").description("?????????id????????????"),
                                fieldWithPath("result.sysUser.authIdList").description("??????id??????"),
                                fieldWithPath("result.sysUser.isForbid").description("????????????"),
                                fieldWithPath("result.sysUser.isEditable").description("???????????????"),
                                fieldWithPath("result.sysUser.ctime").description("????????????"),
                                fieldWithPath("result.sysUser.cTime").description("????????????"),

                                fieldWithPath("result.token").description("????????????"),

                                fieldWithPath("extra").description("????????????")
                        )
                )).andReturn();
        System.out.println(loginMvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testLogout() throws Exception {
        this.mockMvc.perform(get("/user/logout"))
                .andExpect(status().isOk())
                .andDo(document("logout",
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
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }

    @Test
    public void testSaveOrUpdateUser() throws Exception {
        this.mockMvc.perform(post("/user/saveOrUpdateUser")
                .param("userId","")
                .param("userName","??????????????????")
                .param("userAccount","account"+System.currentTimeMillis())
                .param("registerType","1")
                .param("roleIdList","")
                .param("organIdList","")
                .param("rOrganIdList","")
                .param("isForbid","0"))
                .andExpect(status().isOk())
                .andDo(document("saveOrUpdateUser",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                        ),
                        requestParameters(
                                parameterWithName("userId").description("??????id(?????????????????????????????????)"),
                                parameterWithName("userName").description("????????????"),
                                parameterWithName("userAccount").description("???????????? ????????????????????????"),
                                parameterWithName("registerType").description("????????????1?????????????????? 2????????? 3?????????"),
                                parameterWithName("roleIdList").description("???????????????id?????? ???????????? ?????????"),
                                parameterWithName("organIdList").description("???????????????id?????? ???????????? ?????????"),
                                parameterWithName("rOrganIdList").description("???organIdList???????????????????????????id?????? ???????????? ?????????"),
                                parameterWithName("isForbid").description("????????????")
                        ),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
                                fieldWithPath("result.sysUser").description("????????????"),
                                fieldWithPath("result.sysUser.userId").description("??????id"),
                                fieldWithPath("result.sysUser.userAccount").description("????????????"),
                                fieldWithPath("result.sysUser.userPassword").description(""),
                                fieldWithPath("result.sysUser.userName").description("????????????"),
                                fieldWithPath("result.sysUser.roleIdList").description("??????id??????"),
                                fieldWithPath("result.sysUser.registerType").description("????????????1?????????????????? 2????????? 3?????????"),
//                                fieldWithPath("result.sysUser.organIdList").description("??????id??????"),
//                                fieldWithPath("result.sysUser.rorganIdList").description("?????????id??????"),
                                fieldWithPath("result.sysUser.isForbid").description("????????????"),
                                fieldWithPath("result.sysUser.isEditable").description("???????????????"),
                                fieldWithPath("result.sysUser.isDel").description("????????????"),
                                fieldWithPath("result.sysUser.ctime").description("????????????"),
                                fieldWithPath("result.sysUser.utime").description("????????????"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }

    @Test
    public void testDeleteSysUser() throws Exception {
        this.mockMvc.perform(get("/user/deleteSysUser")
                .param("userId","999"))
                .andExpect(status().isOk())
                .andDo(document("deleteSysUser",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                        ),
                        requestParameters(
                                parameterWithName("userId").description("??????id")
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
    public void testFindUserPage() throws Exception {
        this.mockMvc.perform(get("/user/findUserPage")
                .param("userName","")
                .param("roleId","")
                .param("organId","")
                .param("rOrganId","")
                .param("pageNum","1")
                .param("pageSize","10"))
                .andExpect(status().isOk())
                .andDo(document("findUserPage",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                        ),
                        requestParameters(
                                parameterWithName("userName").description("???????????? ?????????"),
                                parameterWithName("roleId").description("??????id ?????????"),
                                parameterWithName("organId").description("??????id ?????????"),
                                parameterWithName("rOrganId").description("?????????id ?????????"),
                                parameterWithName("pageNum").description("?????? ????????? ?????????1"),
                                parameterWithName("pageSize").description("???????????? ????????? ?????????10")
                        ),
                        responseFields(
                                fieldWithPath("code").description("?????????"),
                                fieldWithPath("msg").description("???????????????"),
                                fieldWithPath("result").description("???????????????"),
                                fieldWithPath("result.sysUserList").description("????????????"),
                                fieldWithPath("result.sysUserList[].userId").description("??????id"),
                                fieldWithPath("result.sysUserList[].userAccount").description("????????????"),
                                fieldWithPath("result.sysUserList[].registerType").description("????????????1?????????????????? 2????????? 3?????????"),
                                fieldWithPath("result.sysUserList[].userName").description("????????????"),
                                fieldWithPath("result.sysUserList[].roleIdList").description("??????id??????"),
                                fieldWithPath("result.sysUserList[].roleIdListDesc").description("??????id????????????"),
                                fieldWithPath("result.sysUserList[].organIdList").description("??????id??????"),
                                fieldWithPath("result.sysUserList[].organIdListDesc").description("??????id????????????"),
//                                fieldWithPath("result.sysUserList[].rorganIdList").description("?????????id??????"),
//                                fieldWithPath("result.sysUserList[].rorganIdListDesc").description("?????????id????????????"),
                                fieldWithPath("result.sysUserList[].authIdList").description("??????id??????"),
                                fieldWithPath("result.sysUserList[].isForbid").description("????????????"),
                                fieldWithPath("result.sysUserList[].isEditable").description("???????????????"),
                                fieldWithPath("result.sysUserList[].ctime").description("????????????"),
                                fieldWithPath("result.sysUserList[].cTime").description("????????????"),
                                fieldWithPath("result.pageParam").description("????????????"),
                                fieldWithPath("result.pageParam.pageNum").description("??????"),
                                fieldWithPath("result.pageParam.pageSize").description("????????????"),
                                fieldWithPath("result.pageParam.pageIndex").description("????????????"),
                                fieldWithPath("result.pageParam.itemTotalCount").description("??????"),
                                fieldWithPath("result.pageParam.pageCount").description("?????????"),
                                fieldWithPath("result.pageParam.isLoadMore").description("?????????????????????"),
                                fieldWithPath("extra").description("????????????")
                        )
                ));
    }

    @Test
    public void testInitPassword() throws Exception {
        this.mockMvc.perform(get("/user/initPassword")
                .param("userId","1"))
                .andExpect(status().isOk())
                .andDo(document("initPassword",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                        ),
                        requestParameters(
                                parameterWithName("userId").description("??????id")
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
    public void testUpdatePassword() throws Exception {
        MvcResult mvcResult=this.mockMvc.perform(get("/common/getValidatePublicKey")).andReturn();
        MockHttpServletResponse response =mvcResult.getResponse();
        String result=response.getContentAsString();
        BaseResultEntity<Map<String,String>> baseResultEntity=JSON.parseObject(result, BaseResultEntity.class);
        String password = "123456,liweihua";
        String rsaPassword= CryptUtil.encryptRsaWithPublicKey( password,baseResultEntity.getResult().get("publicKey"));
        String publicKeyName=baseResultEntity.getResult().get("publicKeyName");
        this.mockMvc.perform(post("/user/updatePassword")
                .param("validateKeyName",publicKeyName)
                .param("password",rsaPassword)
                .header("userId","1"))
                .andExpect(status().isOk())
                .andDo(document("updatePassword",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("userId").description("??????id (??????????????????)")
                        ),
                        requestParameters(
                                parameterWithName("validateKeyName").description("??????key?????????"),
                                parameterWithName("password").description("???????????? ??????????????????,?????????")
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
