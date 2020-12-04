package com.jh.shiro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jh.shiro.entity.AuditTqBeanPo;
import com.jh.shiro.entity.DataResult;
import com.jh.shiro.entity.TeacherEntity;
import com.jh.shiro.entity.UserEntity;
import com.jh.shiro.util.ConvertUpMoney;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Slf4j
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
    @Test
    public void getJwtToken() {
        long now = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        map.put("sex", "男");
        JwtBuilder jwtBuilder = Jwts.builder().setId("1").setSubject("admin")
                //自定义私钥（shiro）
                .signWith(SignatureAlgorithm.HS256, "shiro")
//                .setClaims(map)
                .setIssuedAt(new Date())
                .setExpiration(new Date(now + 360000));
        String token = jwtBuilder.compact();
        System.out.println("token: " + token);
    }

    /**
     * 创建Jwt（json web token）Token
     *
     * @return: JwtBuilder
     * 注：加上@Test注解不能有返回值
     */
    public String createJwtToken() {
        JwtBuilder jwtBuilder = Jwts.builder().setId("1111").setSubject("王某").setIssuedAt(new Date())
                //自定义私钥（shiro）
                .signWith(SignatureAlgorithm.HS256, "shiro")
                //存储自定义属性KV
                .claim("companyId", "1238765445678")
                .claim("companyName", "某公司")
                .setExpiration(new Date());
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
//        String token = createJwtToken();
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE1ODcwNTA2NjAsImV4cCI6MTU4NzA1MTAyMH0.8Z2OMbEjMpNw9U9f53KWAnz-VN-YypnfynABMuH8gEA";
        Claims claims = Jwts.parser()
                //必须指定同一个私钥（shiro）
                .setSigningKey("shiro")
                .parseClaimsJws(token).getBody();
        String id = claims.getId();
//        String name = claims.get("name").toString();
//        String name = claims.getSubject();
//        Date date = claims.getIssuedAt();
        //获取自定义数据（返回值类型Object）
//        String companyId = claims.get("companyId").toString();
//        String companyName = claims.get("companyName").toString();
        System.out.println("----------------------" + id);
//        System.out.println(new StringBuilder("id->").append(Integer.valueOf(id).intValue())
//                .append(" name->").append(name).append(" date->").append(simpleDateFormat.format(date))
//                .append(" companyId->").append(companyId)
//                .append(" companyName->").append(companyName));
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
        String monney = ConvertUpMoney.toChinese("1112343456789.56");
        System.out.println(monney);
    }

    /**
     * 日期转换
     */
    @Test
    public void dateTest() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONDAY) + 1);
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
        System.out.println(calendar.get(Calendar.MINUTE));
        System.out.println(calendar.get(Calendar.SECOND));
        System.out.println(calendar.get(Calendar.MILLISECOND));
    }

    @Test
    /**
     *      BigDecimal.ROUND_DOWN(1):直接省略多余的小数，比如1.28如果保留1位小数，得到的就是1.2
     *
     *     BigDecimal.ROUND_UP(0):直接进位，比如1.21如果保留1位小数，得到的就是1.3
     *
     *     BigDecimal.ROUND_HALF_UP(4):四舍五入，2.35保留1位，变成2.4
     *
     *     BigDecimal.ROUND_HALF_DOWN(5):四舍五入，2.35保留1位，变成2.3
     */
    public void divisionTest() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        BigDecimal a = new BigDecimal(10);
        BigDecimal b = new BigDecimal(3);
        System.out.println(a.divide(b, 2, BigDecimal.ROUND_HALF_UP));
    }

    @Test
    public void stringTest() {
        String dept = "/阿勒泰市住房保障和房产服务中心";
        String deptName = dept.substring((dept.lastIndexOf("/")) + 1);
        System.out.println((dept.lastIndexOf("/")));
        System.out.println(deptName);
    }

    @Test
    public void bigDecim() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        double a = 20;
        double b = 456783.1111234;
        double c = a * b;
        System.out.println(decimalFormat.format(c));
    }

    /**
     * 获取主机ip
     *
     * @param
     * @return: void
     */
    @Test
    public void getHostInfo() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            System.out.println("addr: " + addr);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jsonObjectTest() {
        String str = "{msrxm: 12312,cmrBean: {xm: 张三},msrBean: {jgbm: 01140101,htbh: 220011401010015538,xm: 李四}}";
        JSONObject json = JSON.parseObject(str);
        System.out.println(json.get("cmrBean"));
    }

    @Test
    public void stringSplitTest() {
        String str = "32,12,41,52,137,138,2,66,1,182,151,5,160,9,";
        int[] array = Arrays.asList(str.split(",")).stream().mapToInt(Integer::parseInt).toArray();
        Arrays.sort(array);
        System.out.println(array[0]);
//        for (Integer i : array) {
//            System.out.println(i);
//        }
    }

    @Test
    public void getDifferentContent() {
        String str = "2,4,9,7,8,1,6,";
        Integer[] b = {2, 3, 5, 6};
        List<Integer> list1 = Arrays.asList(str.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> list2 = Arrays.asList(b);

        List<Integer> newList = new ArrayList<>();
        for (Integer t : list2) {
            if (!list1.contains(t)) {
                newList.add(t);
            }
        }
        newList.forEach(System.out::println);
    }

    @Test
    public void test1() {
        String str = "AuditTqBeanPo(xingming_tqr=李四, tqrbz=0, sfdrtqbz=0, htFtpPath=, grbh_tqr=150101467000001, gjjtqOperator={zxjgbm:01140101,jgbm:01140101,applyUser:01140101_187851,userid:187851,username:姜华,xzqhbm:654301,blqd:01,f_name:jh,grbh:01000000000317,bpmid:100001555826,lcbz:0,processKey:spfwq_gjjtq,htbh:120011401010016180}, Userid1=0, dataResult=null, useridgjj=null, taskId=null, processInstanceId=null, zjhm_tqr=33032319711006331X, gflx=01, gfhth=120011401010016180, xingming_gfr=李四, zjhm_gfr=33032319711006331X, fwmj=100.0, fjk=100000.0, xmmc=null, zhuanghao=null, yskxz=null, dyh=null, fwch=null, fjh=null, fwxxdz=阿勒泰市布尔津县演示项目00021幢1单元1层101室, ygfrgx=01, qsfph=, gfrq=Mon Apr 27 00:00:00 CST 2020, lnljtqbj=0.0, sfdk=0, grzhye=0.0, gryjce=79.0, ktqe=5800.0, djje=0.0, tqbj=5800.0, jyfs=1, grzh_gfr=150101467000001, tqclbh=, tqyy=, bpmid=100001555027, ywlsh=01200427_00000130784, zrzxqc=靳性大, tqyhzh=443332225666, skyh=中国工商银行渭南华阴市支行, dbrxm=null, dbrzjhm=null, dbrsjhm=null, beizhu=仿古电话机, grzh=150101467000001, dwzh=150101467, grbh=150101467000001, dwbh=null, id=7a368f6fe0184a5a80f49855cc7d2775, htqdrq=2020-04-27, zlksrq=null, zljsrq=null, ydtqr=null, dlrlx=null, tqlxbm=null, zjhm=null, dwmc=天中十公司, xingming=null, tqfs=null, dkhth=null, dkbj=null, dkye=null, yhbxje=null, zlhtbh=null, tqlx=null, tqjehj=null, dlryhzh=null, name=null, sfdgqtzh=null, qsjg=null, lcbz=0, blqd=01, userid=187851, czyuserid=null, sptg=null, zxbm=null, jgbm=01140101, ywfl=null, ywlb=null, khbh=null, zhbh=null, ffbm=null, wf_counid=null, jsid=null, khjgbm=null, yhbm=null, sfjs=null, jspt=null, jsfs=null, ywfsrq=null, fy=null, czbz=null, bczywlsh=null, description=null, applyUser=01140101_187851, bllx=null, tqjebz=null, sftj=null, userId=187851, processKey=null, businessKey=null, channgel=null, source=null, tenantId=null, msg=, ret=0, zxjgbm=01140101, xzqhbm=654301, sfk=0.0, username=姜华, zjbzxbm=C65430, gjjjgbm=1508, grbh_gfr=null, page=1, size=null, htmc=商品房预售数据.pdf, yhzh=null, skyhmc=null, lhh=null, pdfYwlsh=01200427_00000130768, bpmresult=null";
        String s = JSON.toJSONString(str);
        System.out.println(s);
    }

    @Test
    public void md5() {
        System.out.println(new Md5Hash("1234", "李四", 3));
    }

    @Test
    public void sl4jTest() {
        log.info("测试: {}{}", "占位符1{}", "嵌套占位符", "占位符2");
    }

    @Test
    public void nullPorintTest() {
//        UserEntity user = new UserEntity();
        UserEntity user = null;
        String name = user.getName();
        log.info("nullPorintTest: {}", name);
    }

    @Test
    public void jsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ywlsh", "0001");
        this.addAttribute(jsonObject);
//        jsonObject.put("ywlsh","0002");
//        jsonObject.put("ywlsh","0003");
//        System.out.println(jsonObject);
        String str = "{\"success\":true,\"bpmParam\":{\"processDefinitionId\":\"jcr_gmzftq_sp:19:1900625c-8a00-11ea-abc4-02427bf2af5d\",\"processInstanceId\":\"c6f93674-9681-11ea-aa14-02427bf2af5d\",\"taskDefinitionKey\":\"sqrtzfcs\",\"formKey\":\"4079880455190\",\"candidateUsers\":[\"150103168957130_15_00\",\"150103168774081_15_00\",\"150103168732034_15_00\",\"15000000009000429376_15_00\",\"15000000009000514891_15_00\"],\"businessKey\":\"4081301245597\",\"processKey\":\"jcr_gmzftq_sp\",\"taskName\":\"综合柜员岗\",\"processDefinitionName\":\"缴存人购买住房提取\",\"taskId\":\"c7527a21-9681-11ea-aa14-02427bf2af5d\",\"processDefinitionKey\":\"jcr_gmzftq_sp\"},\"isEnd\":false,\"businessParam\":{\"msg\":\"提取申请提交审批成功。\",\"ret\":0,\"success\":true,\"description\":\"111111【370323199002050016】购房提取：￥20150.0元\"}}";
        JSONObject jsonObject1 = JSONObject.parseObject(str).getJSONObject("bpmParam");
        Object objcet = jsonObject1.getString("businessKey");
        System.out.println(objcet);
    }

    private void addAttribute(JSONObject jsonObject) {
        jsonObject.put("ywlsh", "0002");
    }

    @Test
    public void uuidTest() {
        String id = UUID.randomUUID().toString().replace("-", "");
        System.out.println(id);
    }

    @Test
    public void osTest() {
        String os = System.getProperty("os.name").toLowerCase();
        System.out.println(os);
    }

    @Test
    public void java8DateTest() {
//        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        boolean a = true;
//        System.out.println(String.valueOf(a));
        String string = "{\"applyUser\":\"01140101_187288\",\"blqd\":\"01\",\"bpmid\":\"100001763142\",\"bpmresult\":\"{\\\"success\\\":true,\\\"bpmParam\\\":{\\\"processDefinitionId\\\":\\\"jcr_gmzftq_sp:19:1900625c-8a00-11ea-abc4-02427bf2af5d\\\",\\\"processInstanceId\\\":\\\"f92a963d-9b09-11ea-a127-02427bf2af5d\\\",\\\"taskDefinitionKey\\\":\\\"sqrtzfcs\\\",\\\"formKey\\\":\\\"4079880455190\\\",\\\"candidateUsers\\\":[\\\"15000000009000603759_15_00\\\",\\\"150103168202665_15_00\\\",\\\"150103168942549_15_00\\\",\\\"15000000009000603720_15_00\\\",\\\"15000000009000603705_15_00\\\",\\\"15000000009000058306_15_00\\\",\\\"150103168956862_15_00\\\",\\\"150103168957175_15_00\\\",\\\"150103168957161_15_00\\\",\\\"150103168957142_15_00\\\",\\\"150103168957155_15_00\\\",\\\"150103168957156_15_00\\\",\\\"150103168957174_15_00\\\"],\\\"businessKey\\\":\\\"4081301732907\\\",\\\"processKey\\\":\\\"jcr_gmzftq_sp\\\",\\\"taskName\\\":\\\"综合柜员岗\\\",\\\"processDefinitionName\\\":\\\"缴存人购买住房提取\\\",\\\"taskId\\\":\\\"fa3eec8a-9b09-11ea-a127-02427bf2af5d\\\",\\\"processDefinitionKey\\\":\\\"jcr_gmzftq_sp\\\"},\\\"isEnd\\\":false,\\\"businessParam\\\":{\\\"msg\\\":\\\"提取申请提交审批成功。\\\",\\\"ret\\\":0,\\\"success\\\":true,\\\"description\\\":\\\"1231231【370323199002050016】购房提取：￥200.0元\\\"}}\",\"djje\":0.0,\"dwmc\":\"许过这公司\",\"dwzh\":\"150800043\",\"f_name\":\"djj\",\"fjk\":100000.0,\"fwmj\":100.0,\"fwxxdz\":\"阿勒泰市楼盘表申请出战楼盘表6号幢1单元1层101室\",\"gfhth\":\"120011401010015750\",\"gflx\":\"01\",\"gfrq\":1589990400000,\"gjjbpmid\":\"\",\"gjjjgbm\":\"1501\",\"gjjtqOperator\":\"{\\\"jgbm\\\":\\\"01140101\\\",\\\"zxjgbm\\\":\\\"01140101\\\",\\\"applyUser\\\":\\\"01140101_187288\\\",\\\"userid\\\":\\\"187288\\\",\\\"username\\\":\\\"杜佳佳a\\\",\\\"xzqhbm\\\":\\\"654301\\\",\\\"blqd\\\":\\\"01\\\",\\\"f_name\\\":\\\"djj\\\",\\\"grbh\\\":\\\"01000000000074\\\",\\\"bpmid\\\":\\\"100001763142\\\",\\\"taskId\\\":\\\"33cfc33c-9b0f-11ea-b045-52540087f704\\\",\\\"lcbz\\\":1,\\\"processKey\\\":\\\"spfwq_gjjtq\\\",\\\"htbh\\\":\\\"120011401010015750\\\"}\",\"gjjtqzt\":\"退回调整\",\"grbh\":\"15000000009000019306\",\"grbh_tqr\":\"15000000009000019306\",\"gryjce\":682.0,\"grzh\":\"150100000680871\",\"grzh_gfr\":\"150100000680871\",\"grzhye\":0.0,\"htFtpPath\":\"\",\"htmc\":\"商品房预售0512模板乌苏专用数据.pdf\",\"htqdrq\":\"2020-05-21\",\"id\":\"40c26fea2d4b4fb2b6f13f7a748d85e3\",\"jgbm\":\"01140101\",\"jyfs\":\"1\",\"ktqe\":20150.0,\"lcbz\":\"1\",\"lnljtqbj\":0.0,\"page\":1,\"pageSize\":10,\"pdfYwlsh\":\"01200422_00000122277\",\"qsfph\":\"\",\"sfdk\":\"0\",\"sfdrtqbz\":0,\"sfk\":0.0,\"skyh\":\"中国工商银行股份有限公司贵州省贵阳开阳支行开磷分理处\",\"spyj\":\"11\",\"status\":\"0\",\"success\":false,\"taskId\":\"33cfc33c-9b0f-11ea-b045-52540087f704\",\"tqbj\":200.0,\"tqclbh\":\"\",\"tqrbz\":\"0\",\"tqyhzh\":\"2445324325234534\",\"tqyy\":\"\",\"userid\":187288,\"userid1\":0,\"useridgjj\":\"\",\"username\":\"杜佳佳a\",\"xingming_gfr\":\"1231231\",\"xingming_tqr\":\"1231231\",\"xzqhbm\":\"654301\",\"ygfrgx\":\"01\",\"ywlsh\":\"01200521_00000147313\",\"zjbzxbm\":\"C65430\",\"zjhm\":\"370323199002050016\",\"zjhm_gfr\":\"370323199002050016\",\"zjhm_tqr\":\"370323199002050016\",\"zrzxqc\":\"伊感日\",\"zxjgbm\":\"01140101\"}";
        JSONObject jsonObject = JSONObject.parseObject(string);
        AuditTqBeanPo auditTqBeanPo = jsonObject.toJavaObject(AuditTqBeanPo.class);
        System.out.println(auditTqBeanPo);
    }


    @Test
    public void indexOfTest() {
        System.out.println("".length());
    }

    @Test
    public void java8Test() {
        UserEntity[] stringArray = {new UserEntity(1, "张三"), new UserEntity(1, "张三"), new UserEntity(3, "王五")};
        UserEntity[] stringArray1 = {new UserEntity(1, "张三"), new UserEntity(2, "李四"), new UserEntity(3, "孙七")};
//        List<UserEntity> stringList = Arrays.asList(stringArray);
//        List<UserEntity> stringList1 = Arrays.asList(stringArray1);
        List<String> stringList = Arrays.asList("123", "1234", "12345", "12345", "123456");
        List<String> stringList1 = Arrays.asList("123", "234", "2345", "23456", "123456");
        //过滤
        List<String> newStringList = stringList.stream().distinct().filter(item -> !stringList1.contains(item)).collect(Collectors.toList());

        //去重
//        List<UserEntity> newStringList = stringList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(UserEntity::getName))), ArrayList::new)
//        );
//        List<UserEntity> newStringList= stringList.stream().distinct().collect(Collectors.toList());
//        List<UserEntity> newStringList = new ArrayList<>(new TreeSet<>(stringList));
//        newStringList.forEach(System.out::println);
        System.out.println(System.getProperty("os.name").toLowerCase());
        System.out.println("windows 10".contains("windows"));
    }

    @Test
    public void dataURLTest() throws Exception {
        File file = new File("D:/a.jpg");
        byte[] bytes = toByteArray(file);
        BASE64Encoder encoder = new BASE64Encoder();
        System.out.println(encoder.encode(bytes));
    }

    public byte[] toByteArray(File file) throws IOException {
        File f = file;
        if (!f.exists()) {
            throw new FileNotFoundException("file not exists");
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }

    @Test
    public void stringTest1() throws Exception {
        String s = "D:/a.jpg";
        File file = new File("D:/a.jpg");
        Base64.Encoder encoder = Base64.getEncoder();
        System.out.println(encoder.encodeToString(Files.readAllBytes(file.toPath())));
    }

    @Test
    public void test2() {
        String sftg = "sftg";
        Map<String, Object> map = new HashMap<>();
        map.put("sftg", "6");
        System.out.println(Integer.parseInt(map.get(sftg).toString()) == 6);
    }

    @Test
    public void test3() {
        DataResult dataResult = getDataResult();
        String data = dataResult.getData().toString().split("&")[7].split("=")[1].split("\\.")[0];
        try {
            System.out.println(URLDecoder.decode(data, "utf8"));
        } catch (UnsupportedEncodingException e) {
            log.info("转码失败");
        }
    }

    private DataResult getDataResult() {
        DataResult dataResult = new DataResult();
        String s = "http://192.168.5.161:18006/pageoffice/previewInWayOne?userNo=GT&userName=%25E6%259F%259C%25E5%258F%25B0&mode=21&jgbm=01140101&formType=CLFHT&formId=220011401010019268&urlPath=null&newFtpPath=%252F01%252F06%252F01140101%252FCLFHT%252F220011401010019268%252F%25E9%2598%25BF%25E5%258B%2592%25E6%25B3%25B0%25E5%25AD%2598%25E9%2587%258F%25E6%2588%25BF%25E8%25BD%25AC%25E8%25AE%25A9%25E5%2590%2588%25E5%2590%258C0624.docx&ftpPath=%252F01%252F06%252F01140101%252FCLFHT%252F220011401010019268%252F%25E9%2598%25BF%25E5%258B%2592%25E6%25B3%25B0%25E5%25AD%2598%25E9%2587%258F%25E6%2588%25BF%25E8%25BD%25AC%25E8%25AE%25A9%25E5%2590%2588%25E5%2590%258C0624.docx&templateType=CLFHT_TEMPLATE&update=true";
        dataResult.setData(s);
        return dataResult;
    }

    @Test
    public void test4() {
        long l = 652923202006100019L;
    }
}
