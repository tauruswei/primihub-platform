package com.primihub.biz.entity.data.req;

import lombok.Data;

@Data
public class AbeUpdateCipherReq {
    private Long id;
    private String cipherText;
    private String path;
}

