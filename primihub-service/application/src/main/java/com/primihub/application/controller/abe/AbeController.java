package com.primihub.application.controller.abe;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.primihub.biz.entity.base.BaseResultEntity;
import com.primihub.biz.entity.base.BaseResultEnum;
import com.primihub.biz.entity.data.req.*;
import com.primihub.biz.service.abe.AbeService;
import com.primihub.biz.service.scd.ScdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("abe")
@RestController
@Slf4j
public class AbeController {
    @Autowired
    private AbeService abeService;
    @Autowired
    private ScdService scdService;

    // 创建模版
    @PostMapping("createProject")
    public BaseResultEntity createProject(@RequestHeader("userId") Long userId,
                                           @RequestBody ScdCreateTemplateReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return abeService.saveProject(req, userId);
    }

    @GetMapping("queryProject/{id}")
    public BaseResultEntity queryTemplate(@RequestHeader("userId") Long userId,
                                          @PathVariable Long id) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"attrs");
//
//        if (userId == null || userId == 0L) {
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "userId");
//        }
        if (id == null || id == 0L) {
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "id");
        }
        return abeService.queryProject(id, userId);
    }

    @GetMapping("listProjects")
    public BaseResultEntity listProjects(@RequestHeader("userId") Long userId,
                                          @RequestParam("num") Integer num,
                                          @RequestParam("limit") Integer limit) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"attrs");
//
//        if (userId == null || userId == 0L) {
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "userId");
//        }
        return abeService.listProjects(num, limit, userId);
    }

    @GetMapping("listProjectsByName")
    public BaseResultEntity listProjectsByName(@RequestHeader("userId") Long userId,
                                         @RequestParam("name") String name,
                                         @RequestParam("num") Integer num,
                                         @RequestParam("limit") Integer limit) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"attrs");
//
//        if (userId == null || userId == 0L) {
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "userId");
//        }
        return abeService.listProjectsByName(num, limit, userId,name);
    }

    @PostMapping("updateProject")
    public BaseResultEntity updateTemplate(@RequestHeader("userId") Long userId,
                                           @RequestBody AbeUpdateProjectReq req) {
        // 参数校验
        if (StringUtils.isBlank(req.getPath()))
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"path");
        if (StringUtils.isBlank(req.getPk()))
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "pk");
        if (StringUtils.isBlank(req.getMsk()))
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "msk");
        if (req.getId() == null || req.getId() == 0L) {
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "id");
        }
        return abeService.updateProject(req, userId);
    }

    // 加密
    @PostMapping("encrypt")
    public BaseResultEntity encrypt(@RequestHeader("userId") Long userId,
                                              @RequestBody AbeEncryptReq req) {
//         参数校验
        if (StringUtils.isBlank(req.getPk()))
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"pk");
        if (StringUtils.isBlank(req.getPolicy()))
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"policy");
        if (StringUtils.isBlank(req.getPlainText()))
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "plaintext");
        if (req.getProjectId() == null || req.getProjectId() == 0L)
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "projectId");
        return abeService.encrypt(req, userId);
    }

    @GetMapping("queryCipher/{id}")
    public BaseResultEntity queryCipher(@RequestHeader("userId") Long userId,
                                             @PathVariable Long id) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"attrs");
//
//        if (userId == null || userId == 0L) {
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "userId");
//        }
        if (id == null || id == 0L) {
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "id");
        }
        return abeService.queryCipher(id, userId);
    }

//    @GetMapping("queryAttrs/{certId}")
//    public BaseResultEntity queryAttrs(@RequestHeader("userId") Long userId,
//                                       @PathVariable Long certId) {
//        // 参数校验
////        if (StringUtils.isBlank(req.getName()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
////        if (StringUtils.isBlank(req.getAttrs()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"attrs");
////
////        if (userId == null || userId == 0L) {
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "userId");
////        }
//        if (certId == null || certId == 0L) {
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "certId");
//        }
//        return scdService.queryAttributes(certId);
//    }

    @GetMapping("listCiphers")
    public BaseResultEntity listCiphers(@RequestHeader("userId") Long userId,
                                             @RequestParam("num") Integer num,
                                             @RequestParam("limit") Integer limit) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"attrs");
//
//        if (userId == null || userId == 0L) {
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "userId");
//        }
//        if (certId == null || certId == 0L) {
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "certId");
//        }
        return abeService.listCiphers(num, limit, userId);
    }


    @PostMapping("updateCipher")
    public BaseResultEntity updateCipher(@RequestHeader("userId") Long userId,
                                           @RequestBody AbeUpdateCipherReq req) {
        // 参数校验
        if (StringUtils.isBlank(req.getPath()))
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"path");
        if (StringUtils.isBlank(req.getCipherText()))
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "cipher text");
        if (req.getId() == null || req.getId() == 0L) {
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "id");
        }
        return abeService.updateCipher(req, userId);
    }

    // 密钥生成
    @PostMapping("createUserKey")
    public BaseResultEntity createUserKey(@RequestHeader("userId") Long userId,
                                              @RequestBody AbeCreateUserKeyReq req) {
//         参数校验
        if (req.getAttrs().isEmpty())
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"attrs");
        if (req.getProjectId() == null || req.getProjectId() == 0L)
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "projectId");
        return abeService.saveUserKey(req, userId);
    }

    @GetMapping("queryUserKey/{id}")
    public BaseResultEntity queryUserKey(@RequestHeader("userId") Long userId,
                                        @PathVariable Long id) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"attrs");
//
//        if (userId == null || userId == 0L) {
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "userId");
//        }
        if (id == null || id == 0L) {
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "id");
        }
        return abeService.queryUserKey(id, userId);
    }

//    @GetMapping("queryAttrs/{certId}")
//    public BaseResultEntity queryAttrs(@RequestHeader("userId") Long userId,
//                                       @PathVariable Long certId) {
//        // 参数校验
////        if (StringUtils.isBlank(req.getName()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
////        if (StringUtils.isBlank(req.getAttrs()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"attrs");
////
////        if (userId == null || userId == 0L) {
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "userId");
////        }
//        if (certId == null || certId == 0L) {
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "certId");
//        }
//        return scdService.queryAttributes(certId);
//    }

    @GetMapping("listUserKeys")
    public BaseResultEntity listUserKeys(@RequestHeader("userId") Long userId,
                                        @RequestParam("num") Integer num,
                                        @RequestParam("limit") Integer limit) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"attrs");
//
//        if (userId == null || userId == 0L) {
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "userId");
//        }
//        if (certId == null || certId == 0L) {
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "certId");
//        }
        return abeService.listUserKeys(num, limit, userId);
    }

    @PostMapping("updateUserKey")
    public BaseResultEntity updateUserKey(@RequestHeader("userId") Long userId,
                                          @RequestParam("id") Long id,
                                          @RequestParam("path") String path,
                                          @RequestParam("sk") String sk) {
        return abeService.updateUserKey(sk,path,id, userId);
    }
    @PostMapping("decrypt")
    public BaseResultEntity decrypt(@RequestHeader("userId") Long userId,
                                    @RequestBody AbeDecryptReq req) {
        return abeService.decrypt(req, userId);
    }

}
