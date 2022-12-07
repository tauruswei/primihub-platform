package com.primihub.biz;

import com.alibaba.fastjson.JSON;
import com.primihub.biz.entity.scd.po.ScdCertificate;
import com.primihub.biz.grpc.client.TestGrpcClient;
import com.primihub.biz.util.crypt.DateUtil;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import java_test.Request;
import java_test.Result;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TestGrpcClientSpringTest {


//    @Test
    public void testRun() {
        Request request = Request.newBuilder().setRequest1("test1").setRequest2("test2").build();
        Channel channel= ManagedChannelBuilder
                .forAddress("localhost",9090)
                .usePlaintext()
                .build();
        Result result = new TestGrpcClient().run(o -> o.method(request),channel);
        System.out.println(result);
    }
    @Test
    public void test1() throws IOException {
        String path = "/Users/fengxiaoxiao/Desktop/test_temandsk.json";
        File file = new File(path);
        String str = FileUtils.readFileToString(file);
        System.out.println(str);
    }
    @Data
    class Predicate{
        String attrName;
        String type;
        int value;
    }
    @Test
    public void  test2(){
        Predicate p = new Predicate();
        p.setAttrName("age");
        p.setType("EQ");
        p.setValue(10);
        Predicate p1 = new Predicate();
        p1.setAttrName("contibution");
        p1.setType("EQ");
        p1.setValue(10);
        List list = new ArrayList<>();
        list.add(JSON.toJSONString(p));
        list.add(JSON.toJSONString(p1));
//        list.toString();
        System.out.println(JSON.toJSONString(list));
        JSONObject jsonObject = new JSONObject();

        ScdCertificate scdCertificate = new ScdCertificate();
        scdCertificate.setTempId(1L);
        scdCertificate.setUserId(1L);
        scdCertificate.setAttrs("{ \n" +
                "   \"age\":10,\n" +
                "   \"contribution\":10\n" +
                "}");
        System.out.println(JSON.toJSONString(scdCertificate));




    }
}
