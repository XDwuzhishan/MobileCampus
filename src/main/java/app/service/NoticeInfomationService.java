package app.service;

import app.Model.CommonResult;
import app.entity.NoticeInformation;
import app.mapper.NoticeInfomationMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by xdcao on 2017/7/27.
 */
@Service
public class NoticeInfomationService {

    @Autowired
    private NoticeInfomationMapper noticeInfomationMapper;

    private static Logger logger= LoggerFactory.getLogger(NoticeInfomationService.class);

    private static CloseableHttpClient httpClient= HttpClients.createDefault();

    @Transactional
    @CacheEvict(value = "NoticeInfos")
    public void crawAndSaveNoticeInfomation(){

        noticeInfomationMapper.deleteAll();
        logger.info("删除旧信息，NoticeInfomation");

        HttpGet httpGet=new HttpGet("http://gr.xidian.edu.cn/tzgg1.htm");
        CloseableHttpResponse response=null;
        Html page=null;
        try {
            response = httpClient.execute(httpGet);
            page=new Html(EntityUtils.toString(response.getEntity(),"utf-8"),"http://gr.xidian.edu.cn/tzgg1.htm");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("访问页面失败");
        }

        List<String> linklist=page.links().regex("http://gr\\.xidian\\.edu\\.cn/tzgg1/.\\.htm").all();
        Set<String> linkset=new HashSet<String>(linklist);

        for (String link:linkset){

            HttpGet httpGet1=new HttpGet(link);
            CloseableHttpResponse response1=null;
            Html page1=null;
            try {
                response1 = httpClient.execute(httpGet1);
                page1=new Html(EntityUtils.toString(response1.getEntity(),"utf-8"),link);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("访问页面失败");
            }

            List<String> allTitle = page1.xpath("//div[@class='main-right-list']/ul/li/a/text()").all();
            List<String> allLinks = page1.xpath("//div[@class='main-right-list']/ul/li/a/@href").all();
            List<String> allDates = page1.xpath("//div[@class='main-right-list']/ul/li/span").all();


//            List<NoticeInformation> informationList=new ArrayList<NoticeInformation>();

            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");

            for (int i=0;i<allTitle.size();i++) {
                try {
                    NoticeInformation noticeInformation = new NoticeInformation();
                    noticeInformation.setTitle(allTitle.get(i));
                    noticeInformation.setLink(allLinks.get(i).replaceAll(".*info", "http://gr.xidian.edu.cn/info"));
                    noticeInformation.setDate(formatter.parse(allDates.get(i).substring(13, 23)));
                    HttpGet httpGet2 = new HttpGet(noticeInformation.getLink());
                    CloseableHttpResponse response2 = httpClient.execute(httpGet2);
                    String content = EntityUtils.toString(response2.getEntity(), "utf-8");
                    noticeInformation.setContent(content);
//                    System.out.println(noticeInformation);
                    noticeInfomationMapper.insert(noticeInformation);
                    logger.info("新增一条信息： "+noticeInformation.getTitle());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }





    }


    @Cacheable(value = "NoticeInfos",keyGenerator = "keyGenerator")
    public CommonResult getAllInfosOrderByDate() {

        List<NoticeInformation> noticeInformations=noticeInfomationMapper.getAllNoticeInfomationsOrderByDate();
        if (noticeInformations!=null&&noticeInformations.size()>0){
            return new CommonResult(200,"success",noticeInformations);
        }else {
            return new CommonResult(500,"failure",null);
        }

    }


}
