package com.primihub.biz.entity.data.req;

import lombok.Data;

@Data
public class AbeEncryptReq {
    private Long projectId;
    private String plainText;
    private String pk;
    private String policy;
}

