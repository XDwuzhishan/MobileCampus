package app.service;

import app.Model.CommonResult;
import app.entity.CourseTable;
import app.entity.LoginInfo;
import app.mapper.CourseTableMapper;
import app.mapper.LoginInfoMapper;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xdcao on 2017/7/10.
 */
@Service
public class LoginJiaoWuchuService {

    @Autowired
    private CourseTableMapper courseTableMapper;

    @Autowired
    private LoginInfoMapper loginInfoMapper;

    private static Logger logger= LoggerFactory.getLogger(LoginJiaoWuchuService.class);

    private static final String authUrl="http://ids.xidian.edu.cn/authserver/login?service=http%3A%2F%2Fjwxt.xidian.edu.cn%2Fcaslogin.jsp";

    private static final String loginUrl="http://jwxt.xidian.edu.cn/caslogin.jsp";

    private static CloseableHttpClient httpClient= HttpClients.createDefault();

    @Cacheable(value = "LoginInfos",keyGenerator = "keyGenerator")
    public CommonResult getAllLoginInfos(){
        List<LoginInfo> loginInfos = loginInfoMapper.getAll();
        if (loginInfos!=null&&loginInfos.size()>0){
            return new CommonResult(200,"success",loginInfos);
        }else {
            return new CommonResult(500,"failure",null);
        }

    }

    @Cacheable(value = "CourseTable",keyGenerator = "keyGenerator")
    public CommonResult getCourseTable(String username, String password) {

        CourseTable courseTable = courseTableMapper.getCourseByUsername(username);
        if (courseTable!=null){
            CommonResult commonResult=new CommonResult(200,"success",courseTable);
            return commonResult;
        }else {
            try {
                String cookie = login(username, password);
                CourseTable courseTable1 = crawAndSaveCourse(cookie, username, password);
                return new CommonResult(200,"success",courseTable1);
            }catch (Exception e){
                return new CommonResult(500,"failure",null);
            }

        }

    }




    @Transactional
    @CacheEvict(value = "LoginInfos")
    public void crawAndSaveInfo(String cookie) throws IOException {

        loginInfoMapper.deleteAll();
        logger.info("清除旧信息");

        HttpGet info=new HttpGet("http://yjsxt.xidian.edu.cn/info/findAllBroadcastMessageAction.do?flag=findAll");
        info.setHeader("Cookie",cookie);
        CloseableHttpResponse infoResp = httpClient.execute(info);
        Html infoHtml=new Html(EntityUtils.toString(infoResp.getEntity()),"http://yjsxt.xidian.edu.cn/info/findAllBroadcastMessageAction.do?flag=findAll");
        List<String> tableRows=infoHtml.xpath("//div[@id='list']/form/table/tbody/tr").all();
        for (String tableRow:tableRows){
            LoginInfo loginInfo=new LoginInfo();
            loginInfo.setNoticer(tableRow.substring(tableRow.indexOf("发布人"),tableRow.indexOf("发布人")+8));
            loginInfo.setDate(tableRow.substring(tableRow.indexOf("<li> 20")+5,tableRow.indexOf("<li> 20")+25));
            loginInfo.setSubTitle(tableRow.substring(tableRow.indexOf("subtitle")+10,tableRow.lastIndexOf("</span>")));
            loginInfo.setContent(tableRow.substring(tableRow.indexOf("<hr></li> <li>")+14,tableRow.lastIndexOf("</li> <li>")));
            loginInfoMapper.insert(loginInfo);
            logger.info("新增一条通知信息loginInfo");
        }

        List<String> links = infoHtml.xpath("//div[@id='list']/form/span[@class='pagelinks']/a[@title]").links().regex("http://yjsxt\\.xidian\\.edu\\.cn/info/findAllBroadcastMessageAction\\.do.*").all();
        System.out.println(links);
        for (String link:links){
            HttpGet info1=new HttpGet(link);
            info1.setHeader("Cookie",cookie);
            CloseableHttpResponse infoResp1 = httpClient.execute(info1);
            Html infoHtml1=new Html(EntityUtils.toString(infoResp1.getEntity()),"http://yjsxt.xidian.edu.cn/info/findAllBroadcastMessageAction.do?flag=findAll");
            List<String> tableRows1=infoHtml1.xpath("//div[@id='list']/form/table/tbody/tr").all();
            for (String tableRow:tableRows1){
                LoginInfo loginInfo=new LoginInfo();
                loginInfo.setNoticer(tableRow.substring(tableRow.indexOf("发布人"),tableRow.indexOf("发布人")+8));
                loginInfo.setDate(tableRow.substring(tableRow.indexOf("<li> 20")+5,tableRow.indexOf("<li> 20")+25));
                loginInfo.setSubTitle(tableRow.substring(tableRow.indexOf("subtitle")+10,tableRow.lastIndexOf("</span>")));
                loginInfo.setContent(tableRow.substring(tableRow.indexOf("<hr></li> <li>")+14,tableRow.lastIndexOf("</li> <li>")));
                loginInfoMapper.insert(loginInfo);
                logger.info("新增一条通知信息loginInfo");
            }
        }

    }

    @Transactional
    @CacheEvict(value = "CourseTable")
    public CourseTable crawAndSaveCourse(String cookie, String username, String password) throws IOException {

        HttpGet course=new HttpGet("http://yjsxt.xidian.edu.cn/eduadmin/findCaresultByStudentAction.do");
        course.setHeader("Cookie",cookie);
        CloseableHttpResponse courseResp = httpClient.execute(course);
        Html courseHtml=new Html(EntityUtils.toString(courseResp.getEntity()),"http://yjsxt.xidian.edu.cn/eduadmin/findCaresultByStudentAction.do");
//        logger.info(courseHtml.xpath("//div[@id='list']/table").all().toString());

        //先看有没有以前的数据,有就更新，没有就新建
        CourseTable old=courseTableMapper.getCourseByUsername(username);
        if (old==null){
            CourseTable courseTable=new CourseTable();
            courseTable.setUsername(username);
            courseTable.setPassword(password);
            courseTable.setCourse(courseHtml.xpath("//div[@id='list']/table").all().toString());
            Date date=new Date();
            courseTable.setCreated(date);
            courseTable.setUpdated(date);
            courseTableMapper.insert(courseTable);
            logger.info("新建用户： "+username+"的课程表");
            return courseTable;
        }else {
            old.setCourse(courseHtml.xpath("//div[@id='list']/table").all().toString());
            old.setUpdated(new Date());
            courseTableMapper.update(old);
            logger.info("更新用户： "+username+"的课程表");
            return old;
        }




    }




    public String login(String username, String password) throws IOException {

        String cookie=null;


        try {
            HttpGet httpGet=new HttpGet(authUrl);
            CloseableHttpResponse get1 = httpClient.execute(httpGet);
            String cookie0 = getCookieStoreFirstTime(get1);
            Html html1=new Html(EntityUtils.toString(get1.getEntity()),authUrl);
            String lt=html1.xpath("//div[@class='form-area']/ul/form/input[@name='lt']").get().substring("<input type=\"hidden\" name=\"lt\" value=\"".length(),html1.xpath("//div[@class='form-area']/ul/form/input[@name='lt']").get().length()-2);
            String exe=html1.xpath("//div[@class='form-area']/ul/form/input[@name='execution']").get().substring("<input type=\"hidden\" name=\"execution\" value=\"".length(),html1.xpath("//div[@class='form-area']/ul/form/input[@name='execution']").get().length()-2);


            HttpPost httpPost=new HttpPost(authUrl);
            List<NameValuePair> data=new ArrayList<NameValuePair>();

            data.add(new BasicNameValuePair("username",username));
            data.add(new BasicNameValuePair("password",password));
            data.add(new BasicNameValuePair("submit",""));
            data.add(new BasicNameValuePair("lt",lt));
            data.add(new BasicNameValuePair("execution",exe));
            data.add(new BasicNameValuePair("_eventId","submit"));
            data.add(new BasicNameValuePair("rmShown","1"));

            httpPost.setEntity(new UrlEncodedFormEntity(data));

            httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0");
            httpPost.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            httpPost.setHeader("Accept-Encoding", "gzip, deflate");
            httpPost.setHeader("Referer", "http://ids.xidian.edu.cn/authserver/login?service=http%3A%2F%2Fjwxt.xidian.edu.cn%2Fcaslogin.jsp");
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setHeader("Cookie",cookie0);

            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

            cookie=getCookieStoreFirstTime(httpResponse);

            HttpGet httpGet1=new HttpGet(loginUrl);
            httpGet.setHeader("Cookie",cookie);

            CloseableHttpResponse res = httpClient.execute(httpGet);

            return cookie;


        }catch (Exception e){
            e.printStackTrace();
        }

        return cookie;

    }



    private static void printResponse(HttpResponse httpResponse)
            throws ParseException, IOException {
        // 获取响应消息实体
        HttpEntity entity = httpResponse.getEntity();
        // 响应状态
        System.out.println("status:" + httpResponse.getStatusLine());
        System.out.println("headers:");
        HeaderIterator iterator = httpResponse.headerIterator();
        while (iterator.hasNext()) {
            System.out.println("\t" + iterator.next());
        }
        //判断响应实体是否为空
        if (entity != null) {
            String responseString = EntityUtils.toString(entity);
            System.out.println("response length:" + responseString.length());
            System.out.println("response content:"
                    + responseString.replace("\r\n", ""));
        }
    }




    private static String getCookieStoreFirstTime(HttpResponse httpResponse) {
        System.out.println("----setCookieStore");

        Header[] setCookies = httpResponse.getHeaders("Set-Cookie");



        if (setCookies!=null&&setCookies.length>0){

            //ROUTE
            String route=setCookies[0].getValue();
//        System.out.println("route cookie: "+route);

            if (setCookies.length==1){
                return route+";";
            }
            // JSESSIONID
            String JSESSIONID = setCookies[1].getValue();
//        System.out.println("JSESSIONID cookie:" + JSESSIONID);

            if (setCookies.length==2){
                return route+";"+JSESSIONID;
            }else {
                //BIGipServeridsnew.xidian.edu.cn
                String big=setCookies[2].getValue();
//        System.out.println("BIGipServeridsnew.xidian.edu.cn cookie: "+big);

                return route+";"+JSESSIONID+" "+big;
            }
        }else {
            return null;
        }


    }

}
