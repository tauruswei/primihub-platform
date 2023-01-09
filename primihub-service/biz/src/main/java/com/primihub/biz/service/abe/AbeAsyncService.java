package com.primihub.biz.service.abe;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import com.primihub.biz.config.base.BaseConfiguration;
import com.primihub.biz.constant.DataConstant;
import com.primihub.biz.constant.ScdConstant;
import com.primihub.biz.entity.abe.po.AbeCipher;
import com.primihub.biz.entity.abe.po.AbeProject;
import com.primihub.biz.entity.abe.po.AbeUserKey;
import com.primihub.biz.entity.base.BaseResultEntity;
import com.primihub.biz.entity.base.BaseResultEnum;
import com.primihub.biz.entity.data.req.ComponentTaskReq;
import com.primihub.biz.entity.data.req.DataComponentReq;
import com.primihub.biz.grpc.client.WorkGrpcClient;
import com.primihub.biz.repository.primarydb.abe.AbeCipherRepository;
import com.primihub.biz.repository.primarydb.abe.AbeProjectRepository;
import com.primihub.biz.repository.primarydb.abe.AbeUserKeyRepository;
import com.primihub.biz.service.data.component.ComponentTaskService;
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
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * abe grpc 实现
 */
@Service
@Slf4j
public class AbeAsyncService implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @Autowired
    private BaseConfiguration baseConfiguration;

    @Autowired
    private WorkGrpcClient workGrpcClient;
    @Autowired
    private AbeProjectRepository abeProjectRepository;
    @Autowired
    private AbeCipherRepository abeCipherRepository;
    @Autowired
    private AbeUserKeyRepository abeUserKeyRepository;

    public BaseResultEntity executeBeanMethod(boolean isCheck, DataComponentReq req, ComponentTaskReq taskReq) {
        String baenName = req.getComponentCode() + DataConstant.COMPONENT_BEAN_NAME_SUFFIX;
        log.info("execute : {}", baenName);
        ComponentTaskService taskService = (ComponentTaskService) context.getBean(baenName);
        if (taskService == null)
            return BaseResultEntity.failure(BaseResultEnum.DATA_RUN_TASK_FAIL, req.getComponentName() + "组件无实现方法");
        try {
            return isCheck ? taskService.check(req, taskReq) : taskService.runTask(req, taskReq);
        } catch (Exception e) {
            log.info("ComponentCode:{} -- e:{}", req.getComponentCode(), e.getMessage());
            return BaseResultEntity.failure(BaseResultEnum.DATA_RUN_TASK_FAIL, req.getComponentName() + "组件执行异常");
        }
    }
    @Async
    public void setup(AbeProject abeProject) {
        Date date = new Date();
        StringBuilder sb = new StringBuilder().append(baseConfiguration.getResultUrlDirPrefix()).append("abe").append(DateUtil.formatDate(date, DateUtil.DateStyle.HOUR_FORMAT_SHORT.getFormat())).append("_").append(abeProject.getId()).append(".json");
        // todo 模版 和 密钥 最好分开

        Common.ParamValue templateFilePath = Common.ParamValue.newBuilder().setValueString(sb.toString()).build();
//        Common.ParamValue certFilePath=Common.ParamValue.newBuilder().setValueString(sb.toString()).build();
//        Common.ParamValue priKeyFilePath=Common.ParamValue.newBuilder().setValueString(psiTask.getFilePath()).build();
        Common.Params params = Common.Params.newBuilder()
                .putParamMap("outputFullFilename", templateFilePath)
//                .putParamMap("certFilePath",clientDataParamValue)
//                .putParamMap("priKeyFilePath",serverDataParamValue)
                .build();
        PushTaskReply reply = null;
        try {

            class SetupParams {
                public String project_id;
            }
            SetupParams setupParams = new SetupParams();
            setupParams.project_id = abeProject.getId().toString();

            Common.Task task = Common.Task.newBuilder()
                    .setType(Common.TaskType.ABE_TASK)
                    .setParams(params)
//                    .setName("setup")
                    .setLanguage(Common.Language.JAVA)
//                    .setCode("import sys;")
                    .setJobId(ByteString.copyFrom(abeProject.getId().toString().getBytes(StandardCharsets.UTF_8)))
//                    .setTaskId(ByteString.copyFrom(abeProject.getId().toString().getBytes(StandardCharsets.UTF_8)))
                    .addInputDatasets("setup")
                    .addInputDatasets(JSON.toJSONString(setupParams))
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
            e.printStackTrace();
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
            abeProject.setStatus(ScdConstant.ACTIVE);
            abeProject.setPk(jsonObject.getString("pk"));
            abeProject.setMsk(jsonObject.getString("msk"));
            abeProject.setPath(sb.toString());
            Long i = abeProjectRepository.updateProject(abeProject);
        } catch (Exception e) {
            //todo 捕获异常
            log.info(" Exception:{}", e.getMessage());
        }
    }

    @Async
    public void encrypt(AbeCipher abeCipher, String pk) {
        Date date = new Date();
        StringBuilder sb = new StringBuilder().append(baseConfiguration.getResultUrlDirPrefix()).append("abe").append(DateUtil.formatDate(date, DateUtil.DateStyle.HOUR_FORMAT_SHORT.getFormat())).append("_").append(abeCipher.getId()).append(".json");
        // todo 模版 和 密钥 最好分开

        Common.ParamValue templateFilePath = Common.ParamValue.newBuilder().setValueString(sb.toString()).build();
        Common.Params params = Common.Params.newBuilder()
                .putParamMap("outputFullFilename", templateFilePath)
                .build();
        PushTaskReply reply = null;
        try {

            class EncryptParams {
               public String pk;
               public String plaintext;
               public String policy;
            }
            EncryptParams encryptParams = new EncryptParams();
            encryptParams.pk = pk;
            encryptParams.plaintext = abeCipher.getPlainText();
            encryptParams.policy = abeCipher.getPolicy();

            Common.Task task = Common.Task.newBuilder()
                    .setType(Common.TaskType.ABE_TASK)
                    .setParams(params)
                    .setName("encrypt")
                    .setLanguage(Common.Language.JAVA)
                    .setJobId(ByteString.copyFrom(abeCipher.getId().toString().getBytes(StandardCharsets.UTF_8)))
                    .addInputDatasets("encrypt")
                    .addInputDatasets(JSON.toJSONString(encryptParams))
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
            e.printStackTrace();
            log.info("grpc Exception:{}", e.getMessage());
        }
        // 读取文件并更新数据库
        try {
            // 读取文件
            File file = new File(sb.toString());
            String str = FileUtils.readFileToString(file);

            // 解析文件

            // 更新数据库
            abeCipher.setStatus(ScdConstant.ACTIVE);
            abeCipher.setCipherText(str);
            abeCipher.setPath(sb.toString());
            System.out.println("ciphertext = "+Base64.getEncoder().encodeToString(str.getBytes()));
            Long i = abeCipherRepository.updateCipher(abeCipher);
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
    public void keygen(Long projectId, AbeUserKey abeUserKey, Long userId, List<String> attrs) {
        Date date = new Date();
        StringBuilder sb = new StringBuilder().append(baseConfiguration.getResultUrlDirPrefix()).append("abe").append(DateUtil.formatDate(date, DateUtil.DateStyle.HOUR_FORMAT_SHORT.getFormat())).append("_").append(abeUserKey.getId()).append(".json");
        // todo 模版 和 密钥 最好分开

        Common.ParamValue templateFilePath = Common.ParamValue.newBuilder().setValueString(sb.toString()).build();
        Common.Params params = Common.Params.newBuilder()
                .putParamMap("outputFullFilename", templateFilePath)
                .build();
        PushTaskReply reply = null;
        try {

            class KeyGenParams {
                public String project_id;
                public String[] attributes;
            }
            KeyGenParams kekgenParams = new KeyGenParams();
            kekgenParams.project_id = projectId.toString();
            kekgenParams.attributes = new String[attrs.size()];
            for (int i = 0; i < attrs.size(); i++) {
                kekgenParams.attributes[i] = attrs.get(i);
            }
//            kekgenParams.attributes = attrs;

            Common.Task task = Common.Task.newBuilder()
                    .setType(Common.TaskType.ABE_TASK)
                    .setParams(params)
                    .setName("keygen")
                    .setLanguage(Common.Language.JAVA)
                    .setJobId(ByteString.copyFrom(abeUserKey.getId().toString().getBytes(StandardCharsets.UTF_8)))
                    .addInputDatasets("keygen")
                    .addInputDatasets(JSON.toJSONString(kekgenParams))
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
            e.printStackTrace();
            log.info("grpc Exception:{}", e.getMessage());
        }
        // 读取文件并更新数据库
        try {
            // 读取文件
            File file = new File(sb.toString());
            String str = FileUtils.readFileToString(file);

            // 解析文件

            // 更新数据库
            abeUserKey.setStatus(ScdConstant.ACTIVE);
            abeUserKey.setSk(str);
            System.out.println("sk = "+Base64.getEncoder().encodeToString(str.getBytes()));
            abeUserKey.setPath(sb.toString());
            Long i = abeUserKeyRepository.updateUserKey(abeUserKey);
            //todo 捕获异常
//            if (i!=null && i==0L){
//
//            }
        } catch (Exception e) {
            //todo 捕获异常
            log.info(" Exception:{}", e.getMessage());
        }
    }
    public String decrypt(String sk,String ciphertext) {
        String plaintext = "";
        Date date = new Date();
        StringBuilder sb = new StringBuilder().append(baseConfiguration.getResultUrlDirPrefix()).append("abe").append(DateUtil.formatDate(date, DateUtil.DateStyle.HOUR_FORMAT_SHORT.getFormat())).append("_").append("decrypt").append(".json");
//         todo 模版 和 密钥 最好分开

        Common.ParamValue templateFilePath = Common.ParamValue.newBuilder().setValueString(sb.toString()).build();
        Common.Params params = Common.Params.newBuilder()
                .putParamMap("outputFullFilename", templateFilePath)
                .build();
        PushTaskReply reply = null;
        try {

            class DecreptParams {
                public String sk;
                public String ciphertext;
            }
            DecreptParams decreptParams = new DecreptParams();
            decreptParams.sk = sk;
            decreptParams.ciphertext = ciphertext;

            Common.Task task = Common.Task.newBuilder()
                    .setType(Common.TaskType.ABE_TASK)
                    .setParams(params)
                    .setName("decrypt")
                    .setLanguage(Common.Language.JAVA)
                    .setJobId(ByteString.copyFrom(sk.getBytes(StandardCharsets.UTF_8)))
                    .addInputDatasets("decrypt")
                    .addInputDatasets(JSON.toJSONString(decreptParams))
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
            e.printStackTrace();
            log.info("grpc Exception:{}", e.getMessage());
        }
        // 读取文件并更新数据库
        try {
            // 读取文件
            File file = new File(sb.toString());
            plaintext = FileUtils.readFileToString(file);

            file.delete();
        } catch (Exception e) {
            //todo 捕获异常
            log.info(" Exception:{}", e.getMessage());
        }
        return plaintext;
    }
}
