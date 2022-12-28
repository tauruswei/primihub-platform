package com.primihub.biz.service.abe;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.primihub.biz.constant.ScdConstant;
import com.primihub.biz.entity.abe.po.AbeCipher;
import com.primihub.biz.entity.abe.po.AbeProject;
import com.primihub.biz.entity.abe.po.AbeUserKey;
import com.primihub.biz.entity.base.BaseResultEntity;
import com.primihub.biz.entity.base.BaseResultEnum;
import com.primihub.biz.entity.data.req.*;
import com.primihub.biz.repository.primarydb.abe.AbeCipherRepository;
import com.primihub.biz.repository.primarydb.abe.AbeProjectRepository;
import com.primihub.biz.repository.primarydb.abe.AbeUserKeyRepository;
import com.primihub.biz.service.data.DataAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

import static com.primihub.biz.util.DataUtil.trimFirstAndLastChar;

@Service
@Slf4j
public class AbeService {
    @Autowired
    private DataAsyncService dataAsyncService;
    @Autowired
    private AbeProjectRepository abeProjectRepository;
    @Autowired
    private AbeCipherRepository abeCipherRepository;
    @Autowired
    private AbeUserKeyRepository abeUserKeyRepository;

    public BaseResultEntity saveProject(ScdCreateTemplateReq req, Long userId) {
        AbeProject abeProject = new AbeProject();
        abeProject.setName(req.getName());
        abeProject.setStatus(ScdConstant.INACTIVE);
        abeProjectRepository.saveProject(abeProject);
        dataAsyncService.setup(abeProject);
        //todo
        return BaseResultEntity.success(abeProject);
    }

    public BaseResultEntity queryProject(Long tempId, Long userId) {
        AbeProject abeProject = new AbeProject();
         abeProject = abeProjectRepository.queryProject(tempId);
        return BaseResultEntity.success(abeProject);
    }
    public BaseResultEntity listProjects( Integer num, Integer limit,Long userId) {
        if((null!=num)&&(null!=limit)){
            PageHelper.startPage(num,limit);
        }else{
            PageHelper.startPage(0,0);
        }
        List<AbeProject> abeProjects = abeProjectRepository.listProjects();
        PageInfo<AbeProject> pageInfo = new PageInfo<>(abeProjects);
//        return ResultLayui.success(applications,pageInfo.getTotal());
        return BaseResultEntity.success(pageInfo);
    }
    public BaseResultEntity listProjectsByName( Integer num, Integer limit,Long userId,String name) {
        if((null!=num)&&(null!=limit)){
            PageHelper.startPage(num,limit);
        }else{
            PageHelper.startPage(0,0);
        }
        List<AbeProject> abeProjects = abeProjectRepository.listProjectsByName(name);
        PageInfo<AbeProject> pageInfo = new PageInfo<>(abeProjects);
//        return ResultLayui.success(applications,pageInfo.getTotal());
        return BaseResultEntity.success(pageInfo);
    }
//
    public BaseResultEntity updateProject(AbeUpdateProjectReq req, Long userId) {
        AbeProject template = new AbeProject();
        template.setId(req.getId());
        template.setMsk(req.getMsk());
        template.setPath(req.getPath());
        template.setStatus(ScdConstant.ACTIVE);
        template.setPk(req.getPk());
        Long aLong = abeProjectRepository.updateProject(template);
        return BaseResultEntity.success(aLong);
    }
//
    public BaseResultEntity encrypt(AbeEncryptReq req, Long userId) {
        AbeProject abeProject = new AbeProject();
        abeProject = abeProjectRepository.queryProject(req.getProjectId());
        if (abeProject==null){
            return BaseResultEntity.failure(BaseResultEnum.DATA_QUERY_NULL,"projectId = "+req.getProjectId());
        }
        AbeCipher abeCipher = new AbeCipher();
        abeCipher.setProjectId(req.getProjectId());
        abeCipher.setPolicy(req.getPolicy());
        abeCipher.setPlainText(req.getPlainText());
        abeCipher.setStatus(ScdConstant.INACTIVE);
        abeCipherRepository.saveCipher(abeCipher);
        //todo
        dataAsyncService.encrypt(abeCipher,abeProject.getPk());
        return BaseResultEntity.success(abeCipher);
    }
    //
    public BaseResultEntity queryCipher(Long id, Long userId) {
        AbeCipher abeCipher = abeCipherRepository.queryCipher(id);
        return BaseResultEntity.success(abeCipher);
    }
    public BaseResultEntity listCiphers( Integer num, Integer limit,Long userId) {
        if((null!=num)&&(null!=limit)){
            PageHelper.startPage(num,limit);
        }else{
            PageHelper.startPage(0,0);
        }
        List<AbeCipher> abdCiphers = abeCipherRepository.listCiphers();
        PageInfo<AbeCipher> pageInfo = new PageInfo<>(abdCiphers);
        return BaseResultEntity.success(pageInfo);
    }

    public BaseResultEntity updateCipher(AbeUpdateCipherReq req, Long userId) {
        AbeCipher abeCipher = new AbeCipher();
        abeCipher.setId(req.getId());
        abeCipher.setCipherText(req.getCipherText());
        abeCipher.setPath(req.getPath());
        abeCipher.setStatus(ScdConstant.ACTIVE);
        Long aLong = abeCipherRepository.updateCipher(abeCipher);
        return BaseResultEntity.success(aLong);
    }

    public BaseResultEntity saveUserKey(AbeCreateUserKeyReq req, Long userId) {
        // todo 判断是否存在
        AbeProject abeProject = abeProjectRepository.queryProject(req.getProjectId());
        AbeUserKey abeUserKey = new AbeUserKey();
        abeUserKey.setProjectId(req.getProjectId());
        abeUserKey.setUserId(userId);
        abeUserKey.setAttrs(req.getAttrs().toString());
        abeUserKey.setStatus(ScdConstant.INACTIVE);
        abeUserKeyRepository.saveUserKey(abeUserKey);
        //todo
        dataAsyncService.keygen(abeProject.getId(),abeUserKey,abeUserKey.getUserId(),req.getAttrs());
        return BaseResultEntity.success(abeUserKey);
    }
    //
    public BaseResultEntity queryUserKey(Long id, Long userId) {
        AbeUserKey abeUserKey = abeUserKeyRepository.queryUserKey(id);
        return BaseResultEntity.success(abeUserKey);
    }
    public BaseResultEntity listUserKeys( Integer num, Integer limit,Long userId) {
        if((null!=num)&&(null!=limit)){
            PageHelper.startPage(num,limit);
        }else{
            PageHelper.startPage(0,0);
        }
        List<AbeUserKey> abeUserKeys = abeUserKeyRepository.listUserKeys();
        PageInfo<AbeUserKey> pageInfo = new PageInfo<>(abeUserKeys);
        return BaseResultEntity.success(pageInfo);
    }

    public BaseResultEntity updateUserKey(String sk, String path,Long id, Long userId) {
        AbeUserKey  abeUserKey = new AbeUserKey();
        abeUserKey.setId(id);
        abeUserKey.setSk(sk);
        abeUserKey.setPath(path);
        abeUserKey.setStatus(ScdConstant.ACTIVE);
        Long aLong = abeUserKeyRepository.updateUserKey(abeUserKey);
        return BaseResultEntity.success(aLong);
    }

    public BaseResultEntity decrypt(AbeDecryptReq req, Long userId) {
        System.out.println(req.getSk());
        System.out.println(req.getCipherText());
//        byte[] decode = Base64.getDecoder().decode(req.getSk());
//        System.out.println(new String(decode));
//        System.out.println(Base64.getDecoder().decode(req.getSk()));
//        System.out.println(Base64.getDecoder().decode(req.getCipherText()));
        String decrypt = dataAsyncService.decrypt(new String(Base64.getDecoder().decode(req.getSk())), new String(Base64.getDecoder().decode(req.getCipherText())));
        String s = trimFirstAndLastChar(decrypt, "\"");
        return BaseResultEntity.success(s);
    }

}
