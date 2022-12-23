//package com.primihub.biz.service.data;
//
//
//import com.alibaba.fastjson.JSON;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.primihub.biz.config.base.BaseConfiguration;
//import com.primihub.biz.constant.ScdConstant;
//import com.primihub.biz.entity.base.BaseResultEntity;
//import com.primihub.biz.entity.data.req.*;
//import com.primihub.biz.entity.scd.po.ScdCertificate;
//import com.primihub.biz.entity.scd.po.ScdPredicate;
//import com.primihub.biz.entity.scd.po.ScdRule;
//import com.primihub.biz.entity.scd.po.ScdTemplate;
//import com.primihub.biz.repository.primarydb.data.DataTaskPrRepository;
//import com.primihub.biz.repository.primarydb.scd.ScdCertificateRepository;
//import com.primihub.biz.repository.primarydb.scd.ScdRuleRepository;
//import com.primihub.biz.repository.primarydb.scd.ScdTemplateRepository;
//import com.primihub.biz.repository.secondarydb.data.DataTaskRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//public class ScdService {
//    @Autowired
//    private BaseConfiguration baseConfiguration;
//    @Autowired
//    private FusionResourceService fusionResourceService;
//    @Autowired
//    private DataTaskPrRepository dataTaskPrRepository;
//    @Autowired
//    private DataTaskRepository dataTaskRepository;
//    @Autowired
//    private DataAsyncService dataAsyncService;
//    @Autowired
//    private ScdTemplateRepository scdTemplateRepository;
//    @Autowired
//    private ScdCertificateRepository scdCertificateRepository;
//    @Autowired
//    private ScdRuleRepository scdRuleRepository;
//
//    public String getResultFilePath(String taskId, String taskDate) {
//        return new StringBuilder().append(baseConfiguration.getResultUrlDirPrefix()).append(taskDate).append("/").append(taskId).append(".csv").toString();
//    }
////    public BaseResultEntity pirSubmitTask(String serverAddress,String resourceId, String pirParam) {
////        BaseResultEntity dataResource = fusionResourceService.getDataResource(serverAddress, resourceId);
////        if (dataResource.getCode()!=0)
////            return BaseResultEntity.failure(BaseResultEnum.DATA_RUN_TASK_FAIL,"资源查询失败");
////        Map<String, Object> pirDataResource = (LinkedHashMap)dataResource.getResult();
////        int available = Integer.parseInt(pirDataResource.getOrDefault("available","1").toString());
////        if (available == 1)
////            return BaseResultEntity.failure(BaseResultEnum.DATA_RUN_TASK_FAIL,"资源不可用");
////        DataTask dataTask = new DataTask();
//////        dataTask.setTaskIdName(UUID.randomUUID().toString());
////        dataTask.setTaskIdName(Long.toString(SnowflakeId.getInstance().nextId()));
////        dataTask.setTaskName(pirDataResource.get("resourceName").toString());
////        dataTask.setTaskState(TaskStateEnum.IN_OPERATION.getStateType());
////        dataTask.setTaskType(TaskTypeEnum.PIR.getTaskType());
////        dataTask.setTaskStartTime(System.currentTimeMillis());
////        dataTaskPrRepository.saveDataTask(dataTask);
////        DataPirTask dataPirTask = new DataPirTask();
////        dataPirTask.setTaskId(dataTask.getTaskId());
////        dataPirTask.setServerAddress(serverAddress);
////        dataPirTask.setRetrievalId(pirParam);
////        dataPirTask.setProviderOrganName(pirDataResource.get("organName").toString());
////        dataPirTask.setResourceName(pirDataResource.get("resourceName").toString());
////        dataPirTask.setResourceId(resourceId);
////        dataTaskPrRepository.saveDataPirTask(dataPirTask);
////        dataAsyncService.pirGrpcTask(dataTask,resourceId,pirParam);
////        Map<String, Object> map = new HashMap<>();
////        map.put("taskId",dataTask.getTaskId());
////        return BaseResultEntity.success(map);
////    }
//
//    //    public BaseResultEntity getPirTaskList(DataPirTaskReq req) {
////        List<DataPirTaskVo> dataPirTaskVos = dataTaskRepository.selectDataPirTaskPage(req);
////        if (dataPirTaskVos.isEmpty())
////            return BaseResultEntity.success(new PageDataEntity(0,req.getPageSize(),req.getPageNo(),new ArrayList()));
////        Integer tolal = dataTaskRepository.selectDataPirTaskCount(req);
////        Map<String,LinkedHashMap<String, Object>> resourceMap= new HashMap<>();
////        Map<String, List<DataPirTaskVo>> resourceMapList = dataPirTaskVos.stream().collect(Collectors.groupingBy(DataPirTaskVo::getServerAddress));
////        Iterator<Map.Entry<String, List<DataPirTaskVo>>> it = resourceMapList.entrySet().iterator();
////        while (it.hasNext()){
////            Map.Entry<String, List<DataPirTaskVo>> next = it.next();
////            String[] ids = next.getValue().stream().map(DataPirTaskVo::getResourceId).toArray(String[]::new);
////            BaseResultEntity baseResult = fusionResourceService.getResourceListById(next.getKey(), ids);
////            if (baseResult.getCode()==0){
////                List<LinkedHashMap<String,Object>> voList = (List<LinkedHashMap<String,Object>>)baseResult.getResult();
////                if (voList != null && voList.size()!=0){
////                    resourceMap.putAll(voList.stream().collect(Collectors.toMap(data -> data.get("resourceId").toString(), Function.identity())));
////                }
////            }
////        }
////        for (DataPirTaskVo dataPirTaskVo : dataPirTaskVos) {
////            if (resourceMap.containsKey(dataPirTaskVo.getResourceId())){
////                DataTaskConvert.dataPirTaskPoConvertDataPirTaskVo(dataPirTaskVo,resourceMap.get(dataPirTaskVo.getResourceId()));
////            }
////        }
////        return BaseResultEntity.success(new PageDataEntity(tolal,req.getPageSize(),req.getPageNo(),dataPirTaskVos));
////    }
//    public BaseResultEntity createTemplate(ScdCreateTemplateReq req, Long userId) {
//        ScdTemplate template = new ScdTemplate();
//        template.setName(req.getName());
//        template.setAttrs(req.getAttrs());
//        template.setStatus(ScdConstant.INACTIVE);
//        scdTemplateRepository.saveTemplate(template);
//        dataAsyncService.createTemplate(template);
//        //todo
//        return BaseResultEntity.success(template);
//    }
//
//    public BaseResultEntity queryTemplate(Long tempId, Long userId) {
//        ScdTemplate template = new ScdTemplate();
//        ScdTemplate scdTemplate = scdTemplateRepository.queryTemplate(tempId);
//        return BaseResultEntity.success(scdTemplate);
//    }
//    public BaseResultEntity listTemplates( Integer num, Integer limit,Long userId) {
//        if((null!=num)&&(null!=limit)){
//            PageHelper.startPage(num,limit);
//        }else{
//            PageHelper.startPage(0,0);
//        }
//        List<ScdTemplate> scdTemplates = scdTemplateRepository.listTemplates();
//        PageInfo<ScdTemplate> pageInfo = new PageInfo<>(scdTemplates);
////        return ResultLayui.success(applications,pageInfo.getTotal());
//        return BaseResultEntity.success(pageInfo);
//
//    }
//
//    public BaseResultEntity updateTemplate(ScdUpdateTemplateReq req, Long userId) {
//        ScdTemplate template = new ScdTemplate();
//        template.setId(req.getId());
//        template.setCertificate(req.getCertificate());
//        template.setPriKey(req.getPriKey());
//        template.setStatus(ScdConstant.ACTIVE);
//        scdTemplateRepository.updateTemplate(template);
//        return BaseResultEntity.success(template);
//    }
//
//
//    public BaseResultEntity createCertificate(ScdCreateCertificateReq createCertificateReq, Long userId) {
//        ScdCertificate scdCertificate = new ScdCertificate();
//        scdCertificate.setName(createCertificateReq.getName());
//        scdCertificate.setTempId(createCertificateReq.getTempId());
//        scdCertificate.setUserId(userId);
//        scdCertificate.setStatus(ScdConstant.INACTIVE);
//        scdCertificate.setAttrs(createCertificateReq.getAttrs());
//        scdCertificateRepository.saveCertificate(scdCertificate);
//        dataAsyncService.createCertificate(scdCertificate);
//        return BaseResultEntity.success(scdCertificate);
//    }
//
//    public BaseResultEntity queryCertificate(Long certId, Long userId) {
//        ScdCertificate scdCertificate = new ScdCertificate();
//        ScdCertificate scdCertificate1 = scdCertificateRepository.queryCertificate(certId);
//        return BaseResultEntity.success(scdCertificate1);
//    }
//    public BaseResultEntity listCertificates( Integer num, Integer limit,Long userId) {
//        if((null!=num)&&(null!=limit)){
//            PageHelper.startPage(num,limit);
//        }else{
//            PageHelper.startPage(0,0);
//        }
//        List<ScdCertificate> scdCertificates = scdCertificateRepository.listCertificates();
//        PageInfo<ScdCertificate> pageInfo = new PageInfo<>(scdCertificates);
//        return BaseResultEntity.success(pageInfo);
//    }
//
//    public BaseResultEntity queryAttributes(Long certId) {
//        ScdCertificate scdCertificate = new ScdCertificate();
//        ScdCertificate scdCertificate1 = scdCertificateRepository.queryCertificate(certId);
//        return BaseResultEntity.success(scdCertificate1);
//    }
//
//    public BaseResultEntity createRule(ScdCreateRuleReq req, Long userId) {
//        ScdRule rule = new ScdRule();
//        rule.setName(req.getName());
//        rule.setAttrName(req.getAttrName());
//        rule.setType(req.getType());
//        rule.setValue(req.getValue());
//        scdRuleRepository.saveRule(rule);
////        dataAsyncService.createTemplate(template);
//        //todo
//        return BaseResultEntity.success(rule);
//    }
//    public BaseResultEntity listRules( Integer num, Integer limit,Long userId) {
//        if((null!=num)&&(null!=limit)){
//            PageHelper.startPage(num,limit);
//        }else{
//            PageHelper.startPage(0,0);
//        }
//        List<ScdRule> scdRules = scdRuleRepository.listRules();
//        PageInfo<ScdRule> pageInfo = new PageInfo<>(scdRules);
//
////        dataAsyncService.createTemplate(template);
//        //todo
//        return BaseResultEntity.success(pageInfo);
//    }
//    public BaseResultEntity queryRule(Long id) {
//        ScdRule scdRule = scdRuleRepository.queryRule(id);
////        dataAsyncService.createTemplate(template);
//        //todo
//        return BaseResultEntity.success(scdRule);
//    }
//    public BaseResultEntity updateRule(ScdUpdateRuleReq req, Long userId) {
//        ScdRule scdRule = new ScdRule();
//        scdRule.setId(req.getId());
//        scdRule.setName(req.getName());
//        scdRule.setAttrName(req.getAttrName());
//        scdRule.setType(req.getType());
//        scdRule.setValue(req.getValue());
//        Long row = scdRuleRepository.updateRule(scdRule);
//        // todo 加入 if 语句的判断
//        return BaseResultEntity.success(scdRule);
//    }
//    public BaseResultEntity listRulesArray(List<Long> ids) {
//        List<ScdRule> scdRules = scdRuleRepository.listRulesArray(ids);
////        dataAsyncService.createTemplate(template);
//        //todo
//        return BaseResultEntity.success(scdRules);
//    }
//    public BaseResultEntity verify(ScdVerifyReq req, Long userId) {
////        List<ScdRule> scdRules = scdRuleRepository.listRulesArray(ids);
////        dataAsyncService.createTemplate(template);
//        List<ScdRule> scdRules = scdRuleRepository.listRulesArray(req.getIds());
//        List<String> rules = scdRules.stream().map(scdRule -> {
//            ScdPredicate scdPredicate = new ScdPredicate();
//            scdPredicate.setAttrName(scdRule.getAttrName());
//            scdPredicate.setType(scdRule.getType());
//            scdPredicate.setValue(scdRule.getValue());
//            return JSON.toJSONString(scdPredicate);
//        }).collect(Collectors.toList());
//        dataAsyncService.verify(req,rules);
//
//        //todo
//        return BaseResultEntity.success(scdRules);
//    }
//}
