package com.baizhi.wy;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.baizhi.wy.util.QueryGoEasyUtil;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestGoeasy {

    @Test
    public void createBucket() {
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            Random random = new Random();
            map.put("month", Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月"));
            map.put("boys", Arrays.asList(random.nextInt(500), random.nextInt(500), random.nextInt(500), random.nextInt(500), random.nextInt(500), random.nextInt(500)));
            map.put("girls", Arrays.asList(random.nextInt(500), random.nextInt(500), random.nextInt(500), random.nextInt(500), random.nextInt(500), random.nextInt(500)));
            QueryGoEasyUtil.queryGoEasy(map);
        }
    }

}
