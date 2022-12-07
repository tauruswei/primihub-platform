package com.primihub.biz.repository.primarydb.scd;

import com.primihub.biz.entity.scd.po.ScdTemplate;
import org.springframework.stereotype.Repository;

@Repository
public interface ScdTemplateRepository {
    Long saveTemplate(ScdTemplate scdTemplate);
    ScdTemplate queryTemplate(Long tempId);
    Long updateTemplate(ScdTemplate scdTemplate);
}
