package com.primihub.biz.entity.data.req;

import lombok.Data;

import java.util.List;

@Data
public class ScdCreateCertificateReq {
    private String name;
    private Long tempId;
    private List<String> attrs;
}

