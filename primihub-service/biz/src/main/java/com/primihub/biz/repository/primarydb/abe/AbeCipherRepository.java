package com.primihub.biz.repository.primarydb.abe;

import com.primihub.biz.entity.abe.po.AbeCipher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbeCipherRepository {
    Long saveCipher(AbeCipher abeCipher);
    List<AbeCipher> listCiphers();
    AbeCipher queryCipher(Long id);
//    List<AbeProject> listProjectsByName(String name);
//    Long deleteProject(Long id);
    Long updateCipher(AbeCipher abeCipher);
}
