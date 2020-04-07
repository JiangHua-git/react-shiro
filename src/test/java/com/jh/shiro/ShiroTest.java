package com.jh.shiro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jh.shiro.entity.TeacherEntity;
import com.jh.shiro.util.ConvertUpMoney;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Data
public class ShiroTest {

    @Test
    public void contextLoads() {
        TeacherEntity entity = new TeacherEntity(1, "张三", BigDecimal.valueOf(10000));
        JSONObject obj = (JSONObject) JSON.toJSON(entity);
        System.out.println(obj);
    }

    /**
     * 创建Jwt（json web token）Token
     *
     * @return: JwtBuilder
     * 注：加上@Test注解不能有返回值
     */
//    @Test
    public String createJwtToken() {
        JwtBuilder jwtBuilder = Jwts.builder().setId("1111").setSubject("王某").setIssuedAt(new Date())
                //自定义私钥（shiro）
                .signWith(SignatureAlgorithm.HS256, "shiro")
                //存储自定义属性KV
                .claim("companyId", "1238765445678")
                .claim("companyName", "某公司");
        String token = jwtBuilder.compact();
        System.out.println("token" + token);
        return token;
    }

    /**
     * 解析Jwt（json web token）Token
     *
     * @return: JwtBuilder
     */
    @Test
    public void parseJwtToken() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String token = createJwtToken();
        Claims claims = Jwts.parser()
                //必须指定同一个私钥（shiro）
                .setSigningKey("shiro")
                .parseClaimsJws(token).getBody();
        String id = claims.getId();
        String name = claims.getSubject();
        Date date = claims.getIssuedAt();
        //获取自定义数据（返回值类型Object）
        String companyId = claims.get("companyId").toString();
        String companyName = claims.get("companyName").toString();
        System.out.println("----------------------");
        System.out.println(new StringBuilder("id->").append(Integer.valueOf(id).intValue())
                .append(" name->").append(name).append(" date->").append(simpleDateFormat.format(date))
                .append(" companyId->").append(companyId)
                .append(" companyName->").append(companyName));
    }

    @Test
    public void contextLoad() {
        TeacherEntity oldBean = new TeacherEntity(1, "张三", BigDecimal.valueOf(10000));
        TeacherEntity newBean = new TeacherEntity();
        newBean.setId(2);
        BeanUtils.copyProperties(oldBean, newBean);
//        System.out.println(oldBean);
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void monneyTest() {
        String monney = ConvertUpMoney.toChinese("3456789.56");
        System.out.println(monney);
    }

    @Test
    public void dateTest() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONDAY)+1);
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
        System.out.println(calendar.get(Calendar.MINUTE));
        System.out.println(calendar.get(Calendar.SECOND));
        System.out.println(calendar.get(Calendar.MILLISECOND));
    }
}
