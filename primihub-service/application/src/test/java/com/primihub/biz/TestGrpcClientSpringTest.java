package com.primihub.biz;

import com.alibaba.fastjson.JSON;
import com.primihub.biz.entity.scd.po.ScdCertificate;
import com.primihub.biz.grpc.client.TestGrpcClient;
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
    @Test
    public void test3(){
        class result {
            String msk;
            String pk;
        }
        try {
            // 读取文件
            File file = new File("/Users/fengxiaoxiao/work/zhongxin/privacyproject/server/data/test.json");
            String str = FileUtils.readFileToString(file);
            com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(str);
            System.out.println(jsonObject.getString("pk"));
//            result result1 = JSON.parseObject(str, result.getClass());
//            System.out.println(result.pk);
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    @Test
    public  void test4(){
        List<String> strs = new ArrayList<>();
        strs.add("hello");
        strs.add("world");

        String[] Sts = new String[strs.size()];
        for (int i = 0; i < strs.size(); i++) {
            Sts[i]= strs.get(i);
        }

        String s = strs.toString();
        String[] split = s.split(",");

        System.out.println(split);

    }
}
