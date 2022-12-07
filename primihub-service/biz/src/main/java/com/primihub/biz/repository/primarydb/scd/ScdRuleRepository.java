package com.primihub.biz.repository.primarydb.scd;

import com.primihub.biz.entity.scd.po.ScdCertificate;
import com.primihub.biz.entity.scd.po.ScdRule;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScdRuleRepository {

    Long saveRule(ScdRule scdRule);
    List<ScdRule> listRules();
    List<ScdRule> listRulesArray(List<Long> ids);
    ScdRule queryRule(Long id);
    Long updateRule(ScdRule scdRule);
}
