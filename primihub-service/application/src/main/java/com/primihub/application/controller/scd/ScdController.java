package com.primihub.application.controller.scd;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.primihub.biz.entity.base.BaseResultEntity;
import com.primihub.biz.entity.base.BaseResultEnum;
import com.primihub.biz.entity.data.req.*;
import com.primihub.biz.service.data.ScdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("scd")
@RestController
@Slf4j
public class ScdController {
    @Autowired
    private ScdService scdService;

    // 创建模版
    @PostMapping("createTemplate")
    public BaseResultEntity createTemplate(@RequestHeader("userId") Long userId,
                                           @RequestBody ScdCreateTemplateReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
        if (StringUtils.isBlank(req.getAttrs()))
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return scdService.createTemplate(req, userId);
    }

    @GetMapping("queryTemplate/{tempId}")
    public BaseResultEntity queryTemplate(@RequestHeader("userId") Long userId,
                                          @PathVariable Long tempId) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"attrs");
//
//        if (userId == null || userId == 0L) {
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "userId");
//        }
        if (tempId == null || tempId == 0L) {
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "tempId");
        }
        return scdService.queryTemplate(tempId, userId);
    }

    @GetMapping("listTemplates")
    public BaseResultEntity listTemplates(@RequestHeader("userId") Long userId,
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
        return scdService.listTemplates(num, limit, userId);
    }

    @PostMapping("updateTemplate")
    public BaseResultEntity updateTemplate(@RequestHeader("userId") Long userId,
                                           @RequestBody ScdUpdateTemplateReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
        if (StringUtils.isBlank(req.getCertificate()))
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "certificate");
        if (StringUtils.isBlank(req.getPriKey()))
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "priKey");
        if (req.getId() == null || req.getId() == 0L) {
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "tempId");
        }
        return scdService.updateTemplate(req, userId);
    }

    // 签发证书
    @PostMapping("createCertificate")
    public BaseResultEntity createCertificate(@RequestHeader("userId") Long userId,
                                              @RequestBody ScdCreateCertificateReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"name");
        if (StringUtils.isBlank(req.getAttrs()))
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        if (req.getTempId() == null || req.getTempId() == 0L)
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "tempId");
        return scdService.createCertificate(req, userId);
    }

    @GetMapping("queryCertificate/{certId}")
    public BaseResultEntity queryCertificate(@RequestHeader("userId") Long userId,
                                             @PathVariable Long certId) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"attrs");
//
//        if (userId == null || userId == 0L) {
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "userId");
//        }
        if (certId == null || certId == 0L) {
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "certId");
        }
        return scdService.queryCertificate(certId, userId);
    }

    @GetMapping("queryAttrs/{certId}")
    public BaseResultEntity queryAttrs(@RequestHeader("userId") Long userId,
                                       @PathVariable Long certId) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"attrs");
//
//        if (userId == null || userId == 0L) {
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "userId");
//        }
        if (certId == null || certId == 0L) {
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "certId");
        }
        return scdService.queryAttributes(certId);
    }

    @GetMapping("listCertificates")
    public BaseResultEntity listCertificates(@RequestHeader("userId") Long userId,
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
        return scdService.listCertificates(num, limit, userId);
    }

    /**
     * 创建规则
     */
    @PostMapping("createRule")
    public BaseResultEntity createRule(@RequestHeader("userId") Long userId,
                                       @RequestBody ScdCreateRuleReq req) {
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
        return scdService.createRule(req, userId);
    }

    /**
     * 创建规则
     */
    @PostMapping("listRulesArray")
    public BaseResultEntity listRulesArray(@RequestHeader("userId") Long userId,
                                           @RequestParam(value = "ids") List<Long> ids) {
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
        return scdService.listRulesArray(ids);
    }

    /**
     * 验证
     */
    @PostMapping("verify")
    public BaseResultEntity verify(@RequestHeader("userId") Long userId,
                                   @RequestBody ScdVerifyReq req) {
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
        return scdService.verify(req, userId);
    }

    @GetMapping("queryRule/{id}")
    public BaseResultEntity queryRule(@RequestHeader("userId") Long userId,
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
            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "ruleId");
        }
        return scdService.queryRule(id);
    }

    @GetMapping("listRules")
    public BaseResultEntity listRules(@RequestHeader("userId") Long userId,
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
        return scdService.listRules(num, limit, userId);
    }

    /**
     * 创建规则
     */
    @PostMapping("updateRule")
    public BaseResultEntity updateRule(@RequestHeader("userId") Long userId,
                                       @RequestBody ScdUpdateRuleReq scdUpdateRuleReq) {
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
        return scdService.updateRule(scdUpdateRuleReq, userId);
    }

}
