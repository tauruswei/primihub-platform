package com.primihub.biz.repository.primarydb.scd;

import com.primihub.biz.entity.scd.po.ScdTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScdTemplateRepository {
    Long saveTemplate(ScdTemplate scdTemplate);
    ScdTemplate queryTemplate(Long tempId);
    List<ScdTemplate> listTemplates();
    Long updateTemplate(ScdTemplate scdTemplate);
}
