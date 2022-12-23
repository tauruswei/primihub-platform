package com.primihub.biz.entity.data.req;

import lombok.Data;

import java.util.List;

@Data
public class AbeCreateUserKeyReq {
    private Long projectId;
    private List<String> attrs;
}

