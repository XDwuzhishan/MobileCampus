package app.service;

import app.Model.CommonResult;
import app.entity.DynamicNews;
import app.mapper.DynamicNewsMapper;
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
 * Created by xdcao on 2017/7/26.
 */
@Service
public class DynamicNewsService {

    @Autowired
    private DynamicNewsMapper dynamicNewsMapper;

    private static Logger logger= LoggerFactory.getLogger(DynamicNewsService.class);

    private static CloseableHttpClient httpClient= HttpClients.createDefault();


    @Cacheable(value = "DynamicNews",keyGenerator = "keyGenerator")
    public CommonResult getAllDynamicNewsOrderByDate(){
        List<DynamicNews> allDynamicNewsOrderByDate = dynamicNewsMapper.getAllDynamicNewsOrderByDate();
        if (allDynamicNewsOrderByDate!=null&&allDynamicNewsOrderByDate.size()>0){
            return new CommonResult(200,"success",allDynamicNewsOrderByDate);
        }else {
            return new CommonResult(500,"failure,no data",null);
        }
    }


    @Transactional
    @CacheEvict(value = "DynamicNews")
    public void crawAndSaveDynamicNews() {

        dynamicNewsMapper.deleteAll();

        HttpGet httpGet=new HttpGet("http://gr.xidian.edu.cn/zxdt/1.htm");
        CloseableHttpResponse resp = null;
        Html page= null;
        try {
            resp = httpClient.execute(httpGet);
            page = new Html(EntityUtils.toString(resp.getEntity(),"utf-8"),"http://gr.xidian.edu.cn/zxdt/1.htm");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("获取最新动态基地址页失败");
        }


        List<String> linkList = page.links().regex("http://gr\\.xidian\\.edu\\.cn/zxdt.*\\.htm").all();
        Set<String> linkSet=new HashSet<String>(linkList);

        for (String link:linkSet){
            HttpGet httpGet1=new HttpGet(link);
            CloseableHttpResponse resp1 = null;
            Html page1= null;
            try {
                resp1 = httpClient.execute(httpGet1);
                page1 = new Html(EntityUtils.toString(resp1.getEntity(),"utf-8"),link);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("获取网页： "+link+" 失败");
            }



            List<String> allTitle = page1.xpath("//div[@class='main-right-list']/ul/li/a/text()").all();
            List<String> allLinks = page1.xpath("//div[@class='main-right-list']/ul/li/a/@href").all();
            List<String> allDates = page1.xpath("//div[@class='main-right-list']/ul/li/span").all();

            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");

            for (int i=0;i<allTitle.size();i++){
                try {
                    DynamicNews dynamicNews=new DynamicNews();
                    dynamicNews.setTitle(allTitle.get(i));
                    dynamicNews.setLink(allLinks.get(i).replaceAll(".*info","http://gr.xidian.edu.cn/info"));
                    dynamicNews.setDate(formatter.parse(allDates.get(i).substring(13, 23)));
                    HttpGet httpGet2=new HttpGet(dynamicNews.getLink());
                    CloseableHttpResponse response = httpClient.execute(httpGet2);
                    String content= EntityUtils.toString(response.getEntity(),"utf-8");
                    dynamicNews.setContent(content);
                    dynamicNewsMapper.insert(dynamicNews);
                    logger.info("新增最新动态："+dynamicNews.getTitle());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        }
    }



}
