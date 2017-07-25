package app.service;

import app.entity.CourseTable;
import app.mapper.CourseTableMapper;
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

    private static Logger logger= LoggerFactory.getLogger(LoginJiaoWuchuService.class);

    private static final String authUrl="http://ids.xidian.edu.cn/authserver/login?service=http%3A%2F%2Fjwxt.xidian.edu.cn%2Fcaslogin.jsp";

    private static final String loginUrl="http://jwxt.xidian.edu.cn/caslogin.jsp";

    private static CloseableHttpClient httpClient= HttpClients.createDefault();

    // TODO: 2017/7/25 通知信息的持久化
    public void crawAndSaveInfo(String cookie) throws IOException {

        HttpGet info=new HttpGet("http://yjsxt.xidian.edu.cn/info/findAllBroadcastMessageAction.do?flag=findAll");
        info.setHeader("Cookie",cookie);
        CloseableHttpResponse infoResp = httpClient.execute(info);
        Html infoHtml=new Html(EntityUtils.toString(infoResp.getEntity()),"http://yjsxt.xidian.edu.cn/info/findAllBroadcastMessageAction.do?flag=findAll");
        String table=infoHtml.xpath("//div[@id='list']/form/table").get();
        logger.info(table);
        List<String> links = infoHtml.xpath("//div[@id='list']/form/span[@class='pagelinks']/a[@title]").links().regex("http://yjsxt\\.xidian\\.edu\\.cn/info/findAllBroadcastMessageAction\\.do.*").all();
        logger.info(links.toString());
        for (String link:links){
            HttpGet info1=new HttpGet(link);
            info1.setHeader("Cookie",cookie);
            CloseableHttpResponse infoResp1 = httpClient.execute(info1);
            Html infoHtml1=new Html(EntityUtils.toString(infoResp1.getEntity()),"http://yjsxt.xidian.edu.cn/info/findAllBroadcastMessageAction.do?flag=findAll");
            String table1=infoHtml1.xpath("//div[@id='list']/form/table").get();
            logger.info(table1);
        }

    }

    @Transactional
    public void crawAndSaveCourse(String cookie,String username,String password) throws IOException {

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
        }else {
            old.setCourse(courseHtml.xpath("//div[@id='list']/table").all().toString());
            old.setUpdated(new Date());
            courseTableMapper.update(old);
            logger.info("更新用户： "+username+"的课程表");
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

        System.out.println(setCookies[0].getValue());
        System.out.println(setCookies[1].getValue());

        //ROUTE
        String route=setCookies[0].getValue();
//        System.out.println("route cookie: "+route);


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
    }

}
