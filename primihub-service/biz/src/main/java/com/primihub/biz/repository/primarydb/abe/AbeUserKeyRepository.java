package com.primihub.biz.repository.primarydb.abe;

import com.primihub.biz.entity.abe.po.AbeUserKey;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbeUserKeyRepository {
    Long saveUserKey(AbeUserKey abeUserKey);
    List<AbeUserKey> listUserKeys();
    AbeUserKey queryUserKey(Long id);
//    List<AbeProject> listProjectsByName(String name);
//    Long deleteProject(Long id);
    Long updateUserKey(AbeUserKey abeUserKey);
}
