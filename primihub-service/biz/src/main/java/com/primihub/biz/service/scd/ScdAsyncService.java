package com.primihub.biz.service.scd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import com.primihub.biz.config.base.BaseConfiguration;
import com.primihub.biz.constant.ScdConstant;
import com.primihub.biz.entity.scd.po.ScdCertificate;
import com.primihub.biz.entity.scd.po.ScdTemplate;
import com.primihub.biz.grpc.client.WorkGrpcClient;
import com.primihub.biz.repository.primarydb.scd.ScdCertificateRepository;
import com.primihub.biz.repository.primarydb.scd.ScdTemplateRepository;
import com.primihub.biz.util.crypt.DateUtil;
import java_worker.PushTaskReply;
import java_worker.PushTaskRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import primihub.rpc.Common;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * psi 异步调用实现
 */
@Service
@Slf4j
public class ScdAsyncService implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @Autowired
    private WorkGrpcClient workGrpcClient;
    @Autowired
    private BaseConfiguration baseConfiguration;
    @Autowired
    private ScdTemplateRepository scdTemplateRepository;
    @Autowired
    private ScdCertificateRepository scdCertificateRepository;

//    @Async
    public void createTemplate(ScdTemplate template,List<String> attr_names) {
        Date date = new Date();
        StringBuilder sb = new StringBuilder().append(baseConfiguration.getResultUrlDirPrefix()).append("scd").append(DateUtil.formatDate(date, DateUtil.DateStyle.HOUR_FORMAT_SHORT.getFormat())).append("_").append(template.getId()).append(".json");
        // todo 模版 和 密钥 最好分开

        Common.ParamValue templateFilePath = Common.ParamValue.newBuilder().setValueString(sb.toString()).build();
//        Common.ParamValue certFilePath=Common.ParamValue.newBuilder().setValueString(sb.toString()).build();
//        Common.ParamValue priKeyFilePath=Common.ParamValue.newBuilder().setValueString(psiTask.getFilePath()).build();
        Common.Params params = Common.Params.newBuilder()
                .putParamMap("outputFullFilename", templateFilePath)
                .build();
        PushTaskReply reply = null;
        try {
            class CreateParam {
                public String template_id;
                public String[] attribute_names;
            }
            CreateParam createParam =new CreateParam();
            createParam.template_id  = template.getId().toString();

            createParam.attribute_names = new String[attr_names.size()];
            for (int i = 0; i < attr_names.size(); i++) {
                createParam.attribute_names[i] = attr_names.get(i);
            }
            Common.Task task = Common.Task.newBuilder()
                    .setType(Common.TaskType.SCD_TASK)
                    .setParams(params)
                    .setName("create template")
                    .setLanguage(Common.Language.JAVA)
//                    .setCode("import s")
                    .setJobId(ByteString.copyFrom(template.getId().toString().getBytes(StandardCharsets.UTF_8)))
                    .setTaskId(ByteString.copyFrom(template.getId().toString().getBytes(StandardCharsets.UTF_8)))
                    .addInputDatasets("create_template")
                    .addInputDatasets(JSON.toJSONString(createParam))
                    .build();
            log.info("grpc Common.Task : \n{}", task.toString());
            PushTaskRequest request = PushTaskRequest.newBuilder()
                    .setIntendedWorkerId(ByteString.copyFrom("1".getBytes(StandardCharsets.UTF_8)))
                    .setTask(task)
                    .setSequenceNumber(11)
                    .setClientProcessedUpTo(22)
                    .build();
            reply = workGrpcClient.run(o -> o.submitTask(request));
            log.info("grpc结果:" + reply);
        } catch (Exception e) {
            log.info("grpc Exception:{}", e.getMessage());
        }
        // 读取文件并更新数据库
        try {
            // 读取文件
            File file = new File(sb.toString());
            String str = FileUtils.readFileToString(file);

            // 解析文件
//            JSONObject jsonObject = JSON.parseObject(str);
            // 更新数据库
            template.setStatus(ScdConstant.ACTIVE);
//            template.setCertificate(jsonObject.getString("certificate_template"));
            template.setCertificate(str);
//            template.setPriKey();
            template.setTempPath(sb.toString());
            Long i = scdTemplateRepository.updateTemplate(template);
            //todo 捕获异常
//            if (i!=null && i==0L){
//
//            }
        } catch (Exception e) {
            //todo 捕获异常
            log.info(" Exception:{}", e.getMessage());
        }
    }

    @Async
    public void createCertificate(ScdCertificate scdCertificate,List<String> attrs) {
        Date date = new Date();
        StringBuilder sb = new StringBuilder().append(baseConfiguration.getResultUrlDirPrefix()).append("scd").append(DateUtil.formatDate(date, DateUtil.DateStyle.HOUR_FORMAT_SHORT.getFormat())).append("_").append(scdCertificate.getId()).append(".json");
        // todo 证书 和 用户密钥 最好分开

        Common.ParamValue certFilePath = Common.ParamValue.newBuilder().setValueString(sb.toString()).build();
//        Common.ParamValue certFilePath=Common.ParamValue.newBuilder().setValueString(sb.toString()).build();
//        Common.ParamValue priKeyFilePath=Common.ParamValue.newBuilder().setValueString(psiTask.getFilePath()).build();
        Common.Params params = Common.Params.newBuilder()
                .putParamMap("outputFullFilename", certFilePath)
//                .putParamMap("certFilePath",clientDataParamValue)
//                .putParamMap("priKeyFilePath",serverDataParamValue)
                .build();
        PushTaskReply reply = null;
        try {
            class SignParam {
                public String template_id;
                public String[] attributes;
                public String template;
            }
            SignParam createParam =new SignParam();
            createParam.template_id  = scdCertificate.getId().toString();

            createParam.attributes = new String[attrs.size()];
            for (int i = 0; i < attrs.size(); i++) {
                createParam.attributes[i] = attrs.get(i);
            }
            ScdTemplate scdTemplate = scdTemplateRepository.queryTemplate(scdCertificate.getTempId());
            createParam.template = scdTemplate.getCertificate();
            Common.Task task = Common.Task.newBuilder()
                    .setType(Common.TaskType.SCD_TASK)
                    .setParams(params)
                    .setName("create certificate")
                    .setLanguage(Common.Language.JAVA)
//                    .setCode("import sys;")
                    .setJobId(ByteString.copyFrom(scdCertificate.getId().toString().getBytes(StandardCharsets.UTF_8)))
                    .setTaskId(ByteString.copyFrom(scdCertificate.getId().toString().getBytes(StandardCharsets.UTF_8)))
                    .addInputDatasets("sign_certificate")
//                    .addInputDatasets(scdCertificate.getTempId().toString())
//                    .addInputDatasets(scdCertificate.getAttrs())
                    .addInputDatasets(JSON.toJSONString(createParam))
                    .build();
            log.info("grpc Common.Task : \n{}", task.toString());
            PushTaskRequest request = PushTaskRequest.newBuilder()
                    .setIntendedWorkerId(ByteString.copyFrom("1".getBytes(StandardCharsets.UTF_8)))
                    .setTask(task)
                    .setSequenceNumber(11)
                    .setClientProcessedUpTo(22)
                    .build();
            reply = workGrpcClient.run(o -> o.submitTask(request));
            log.info("grpc结果:" + reply);
        } catch (Exception e) {
            log.info("grpc Exception:{}", e.getMessage());
        }
        // 读取文件并更新数据库
        try {
            // 读取文件
            File file = new File(sb.toString());
            String str = FileUtils.readFileToString(file);

            // 解析文件
            JSONObject jsonObject = JSON.parseObject(str);

            // 更新数据库
            scdCertificate.setStatus(ScdConstant.ACTIVE);
            scdCertificate.setCertificate(jsonObject.getString("signature"));
            scdCertificate.setPriKey(jsonObject.getString("user_private_key_str"));
            scdCertificate.setCertPath(sb.toString());
            Long i = scdCertificateRepository.updateCertificate(scdCertificate);
            //todo 捕获异常
//            if (i!=null && i==0L){
//
//            }
        } catch (Exception e) {
            //todo 捕获异常
            log.info(" Exception:{}", e.getMessage());
        }
    }


    @Async
    public String verify(String template,String certificate, List<String> rules) {
        String result = "";
        Date date = new Date();
        StringBuilder sb = new StringBuilder().append(baseConfiguration.getResultUrlDirPrefix()).append("scd").append(DateUtil.formatDate(date, DateUtil.DateStyle.HOUR_FORMAT_SHORT.getFormat())).append("_").append(UUID.randomUUID()).append(".json");
        // todo 证书 和 用户密钥 最好分开

        Common.ParamValue certFilePath = Common.ParamValue.newBuilder().setValueString(sb.toString()).build();
//        Common.ParamValue certFilePath=Common.ParamValue.newBuilder().setValueString(sb.toString()).build();
//        Common.ParamValue priKeyFilePath=Common.ParamValue.newBuilder().setValueString(psiTask.getFilePath()).build();
        Common.Params params = Common.Params.newBuilder()
                .putParamMap("outputFullFilename", certFilePath)
//                .putParamMap("certFilePath",clientDataParamValue)
//                .putParamMap("priKeyFilePath",serverDataParamValue)
                .build();
        PushTaskReply reply = null;
        try {
            class VerifyParam {
                public String certificate;
                public String[] rule_set;
                public String template;
            }
            VerifyParam createParam =new VerifyParam();
            createParam.template  = template;
            createParam.certificate = certificate;

            createParam.rule_set = new String[rules.size()];
            for (int i = 0; i < rules.size(); i++) {
                createParam.rule_set[i] = rules.get(i);
            }
            Common.Task task = Common.Task.newBuilder()
                    .setType(Common.TaskType.SCD_TASK)
//                    .setParams(params)
                    .setName("verify")
                    .setLanguage(Common.Language.PROTO)
                    .setCode("import sys;")
//                    .setJobId(ByteString.copyFrom(scdCertificate.getId().toString().getBytes(StandardCharsets.UTF_8)))
//                    .setTaskId(ByteString.copyFrom(scdCertificate.getId().toString().getBytes(StandardCharsets.UTF_8)))
                    .addInputDatasets("verify_certificate")
                    .addInputDatasets(JSON.toJSONString(createParam))
                    .build();
            log.info("grpc Common.Task : \n{}", task.toString());
            PushTaskRequest request = PushTaskRequest.newBuilder()
                    .setIntendedWorkerId(ByteString.copyFrom("1".getBytes(StandardCharsets.UTF_8)))
                    .setTask(task)
                    .setSequenceNumber(11)
                    .setClientProcessedUpTo(22)
                    .build();
            reply = workGrpcClient.run(o -> o.submitTask(request));
            log.info("grpc结果:" + reply);
        // 读取文件并更新数据库
            // 读取文件
            File file = new File(sb.toString());
            result= FileUtils.readFileToString(file);



           file.delete();

        } catch (Exception e) {
            log.info("grpc Exception:{}", e.getMessage());
        }
        return result;
    }
}
