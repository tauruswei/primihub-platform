package com.primihub.biz.entity.data.req;

import lombok.Data;

import java.util.List;

@Data
public class ScdVerifyReq {
    Long tempId;
    Long certId;
    List<Long> ids;
}

