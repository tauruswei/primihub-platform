package com.primihub.biz.entity.data.req;

import lombok.Data;

@Data
public class AbeDecryptReq {
    private String sk;
    private String cipherText;
}

