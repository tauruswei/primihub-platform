package com.primihub.biz.entity.data.req;

import lombok.Data;

@Data
public class ScdCreateCertificateReq {
    private String name;
    private Long tempId;
    private String attrs;
}

