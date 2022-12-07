package com.primihub.biz.repository.primarydb.scd;

import com.primihub.biz.entity.scd.po.ScdCertificate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScdCertificateRepository {

    Long saveCertificate(ScdCertificate scdCertificate);
    List<ScdCertificate> listCertificates();
    ScdCertificate queryCertificate(Long certId);
    Long updateCertificate(ScdCertificate scdCertificate);
}
