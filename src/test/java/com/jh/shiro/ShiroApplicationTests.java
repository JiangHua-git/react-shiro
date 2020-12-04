package com.jh.shiro;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jh.shiro.entity.TeacherEntity;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Scanner;

@SpringBootTest
class ShiroApplicationTests {

    @Test
    void contextLoads() {
        TeacherEntity entity = new TeacherEntity(1, "张三", BigDecimal.valueOf(10000));
        JSONObject obj = (JSONObject) JSONArray.toJSON(entity);
        System.out.println(obj);
    }

    @Test
    public void test1() {
        Scanner input = new Scanner(System.in);
        double x = input.nextDouble();
        double y = 0.0;
        if (x > 0 && x <= 10) {
            y = x * 0.1;
        }
        System.out.println(y);
    }
}
