package com.primihub.biz.service.scd;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.primihub.biz.constant.ScdConstant;
import com.primihub.biz.entity.base.BaseResultEntity;
import com.primihub.biz.entity.data.req.*;
import com.primihub.biz.entity.scd.po.ScdCertificate;
import com.primihub.biz.entity.scd.po.ScdPredicate;
import com.primihub.biz.entity.scd.po.ScdRule;
import com.primihub.biz.entity.scd.po.ScdTemplate;
import com.primihub.biz.repository.primarydb.scd.ScdCertificateRepository;
import com.primihub.biz.repository.primarydb.scd.ScdRuleRepository;
import com.primihub.biz.repository.primarydb.scd.ScdTemplateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScdService {
    @Autowired
    private ScdAsyncService dataAsyncService;
    @Autowired
    private ScdTemplateRepository scdTemplateRepository;
    @Autowired
    private ScdCertificateRepository scdCertificateRepository;
    @Autowired
    private ScdRuleRepository scdRuleRepository;

    public BaseResultEntity createTemplate(ScdCreateTemplateReq req, Long userId) {
        ScdTemplate template = new ScdTemplate();
        template.setName(req.getName());
        template.setAttrs(req.getAttrs().toString());
        template.setStatus(ScdConstant.INACTIVE);
        scdTemplateRepository.saveTemplate(template);
        dataAsyncService.createTemplate(template,req.getAttrs());
        //todo
        return BaseResultEntity.success(template);
    }

    public BaseResultEntity queryTemplate(Long tempId, Long userId) {
        ScdTemplate template = new ScdTemplate();
        ScdTemplate scdTemplate = scdTemplateRepository.queryTemplate(tempId);
        return BaseResultEntity.success(scdTemplate);
    }
    public BaseResultEntity listTemplates( Integer num, Integer limit,Long userId) {
        if((null!=num)&&(null!=limit)){
            PageHelper.startPage(num,limit);
        }else{
            PageHelper.startPage(0,0);
        }
        List<ScdTemplate> scdTemplates = scdTemplateRepository.listTemplates();
        PageInfo<ScdTemplate> pageInfo = new PageInfo<>(scdTemplates);
//        return ResultLayui.success(applications,pageInfo.getTotal());
        return BaseResultEntity.success(pageInfo);

    }

    public BaseResultEntity updateTemplate(ScdUpdateTemplateReq req, Long userId) {
        ScdTemplate template = new ScdTemplate();
        template.setId(req.getId());
        template.setCertificate(req.getCertificate());
        template.setPriKey(req.getPriKey());
        template.setStatus(ScdConstant.ACTIVE);
        scdTemplateRepository.updateTemplate(template);
        return BaseResultEntity.success(template);
    }


    public BaseResultEntity createCertificate(ScdCreateCertificateReq createCertificateReq, Long userId) {
        ScdCertificate scdCertificate = new ScdCertificate();
        scdCertificate.setName(createCertificateReq.getName());
        scdCertificate.setTempId(createCertificateReq.getTempId());
        scdCertificate.setUserId(userId);
        scdCertificate.setStatus(ScdConstant.INACTIVE);
        scdCertificate.setAttrs(createCertificateReq.getAttrs().toString());
        scdCertificateRepository.saveCertificate(scdCertificate);
        dataAsyncService.createCertificate(scdCertificate,createCertificateReq.getAttrs());
        return BaseResultEntity.success(scdCertificate);
    }

    public BaseResultEntity queryCertificate(Long certId, Long userId) {
        ScdCertificate scdCertificate = new ScdCertificate();
        ScdCertificate scdCertificate1 = scdCertificateRepository.queryCertificate(certId);
        return BaseResultEntity.success(scdCertificate1);
    }
    public BaseResultEntity listCertificates( Integer num, Integer limit,Long userId) {
        if((null!=num)&&(null!=limit)){
            PageHelper.startPage(num,limit);
        }else{
            PageHelper.startPage(0,0);
        }
        List<ScdCertificate> scdCertificates = scdCertificateRepository.listCertificates();
        PageInfo<ScdCertificate> pageInfo = new PageInfo<>(scdCertificates);
        return BaseResultEntity.success(pageInfo);
    }

    public BaseResultEntity queryAttributes(Long certId) {
        ScdCertificate scdCertificate = new ScdCertificate();
        ScdCertificate scdCertificate1 = scdCertificateRepository.queryCertificate(certId);
        return BaseResultEntity.success(scdCertificate1);
    }

    public BaseResultEntity createRule(ScdCreateRuleReq req, Long userId) {
        ScdRule rule = new ScdRule();
        rule.setName(req.getName());
        rule.setAttrName(req.getAttrName());
        rule.setType(req.getType());
        rule.setValue(req.getValue());
        scdRuleRepository.saveRule(rule);
//        dataAsyncService.createTemplate(template);
        //todo
        return BaseResultEntity.success(rule);
    }
    public BaseResultEntity listRules( Integer num, Integer limit,Long userId) {
        if((null!=num)&&(null!=limit)){
            PageHelper.startPage(num,limit);
        }else{
            PageHelper.startPage(0,0);
        }
        List<ScdRule> scdRules = scdRuleRepository.listRules();
        PageInfo<ScdRule> pageInfo = new PageInfo<>(scdRules);

//        dataAsyncService.createTemplate(template);
        //todo
        return BaseResultEntity.success(pageInfo);
    }
    public BaseResultEntity queryRule(Long id) {
        ScdRule scdRule = scdRuleRepository.queryRule(id);
//        dataAsyncService.createTemplate(template);
        //todo
        return BaseResultEntity.success(scdRule);
    }
    public BaseResultEntity updateRule(ScdUpdateRuleReq req, Long userId) {
        ScdRule scdRule = new ScdRule();
        scdRule.setId(req.getId());
        scdRule.setName(req.getName());
        scdRule.setAttrName(req.getAttrName());
        scdRule.setType(req.getType());
        scdRule.setValue(req.getValue());
        Long row = scdRuleRepository.updateRule(scdRule);
        // todo 加入 if 语句的判断
        return BaseResultEntity.success(scdRule);
    }
    public BaseResultEntity listRulesArray(List<Long> ids) {
        List<ScdRule> scdRules = scdRuleRepository.listRulesArray(ids);
//        dataAsyncService.createTemplate(template);
        //todo
        return BaseResultEntity.success(scdRules);
    }
    public BaseResultEntity verify(ScdVerifyReq req, Long userId) {
//        List<ScdRule> scdRules = scdRuleRepository.listRulesArray(ids);
//        dataAsyncService.createTemplate(template);
        List<ScdRule> scdRules = scdRuleRepository.listRulesArray(req.getIds());
        List<String> rules = scdRules.stream().map(scdRule -> {
            ScdPredicate scdPredicate = new ScdPredicate();
            scdPredicate.setAttrName(scdRule.getAttrName());
            scdPredicate.setType(scdRule.getType());
            scdPredicate.setValue(scdRule.getValue());
            return JSON.toJSONString(scdPredicate);
        }).collect(Collectors.toList());
        ScdTemplate scdTemplate = scdTemplateRepository.queryTemplate(req.getTempId());
        ScdCertificate scdCertificate = scdCertificateRepository.queryCertificate(req.getCertId());
        dataAsyncService.verify(scdTemplate.getCertificate(),scdCertificate.getCertificate(),rules);

        //todo
        return BaseResultEntity.success(scdRules);
    }
}
