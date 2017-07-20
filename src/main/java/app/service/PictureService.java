package app.service;

import app.Model.PictureResult;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by xdcao on 2017/6/9.
 */
@Service
public class PictureService {

    private org.slf4j.Logger logger= LoggerFactory.getLogger(this.getClass());

//    private static String filepath="D://upload";
//    private static String baseUrl="http://localhost/";

    private static String filepath="/data/wwwroot/default";
    private static String baseUrl="http://101.200.59.58/";

    public PictureResult uploadPicture(MultipartFile pic) {

        PictureResult pictureResult=new PictureResult();

        if (pic==null){
            System.out.println("文件为空");
            pictureResult.setError(1);
            pictureResult.setMessage("文件为空");
            return pictureResult;
        }

        String picName=System.currentTimeMillis()+pic.getOriginalFilename();
        File file=new File(filepath,picName);
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            fileOutputStream.write(pic.getBytes());
        }catch (Exception e){
            pictureResult.setError(1);
            System.out.println("上传文件失败");
            pictureResult.setMessage("上传文件失败");
            return pictureResult;
        }

        pictureResult.setError(0);

        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
//        String ip=addr.getHostAddress().toString();//获得本机IP

        pictureResult.setUrl(baseUrl+picName);
        return pictureResult;


    }

    public PictureResult batchPicture(HttpServletRequest request) {

        List<MultipartFile> files=((MultipartHttpServletRequest)request).getFiles("uploadFile");
        MultipartFile file= null;
        BufferedOutputStream stream=null;
        List<String> urls=new ArrayList<String>();
        for (int i=0;i<files.size();i++){
            file=files.get(i);
            if (!file.isEmpty()){
                try {
                    byte[] bytes=file.getBytes();
                    String picName=System.currentTimeMillis()+file.getOriginalFilename();
                    File pic=new File(filepath,picName);
                    stream = new BufferedOutputStream(new FileOutputStream(pic));
                    stream.write(bytes);
                    stream.close();
                    urls.add(baseUrl+picName);
                }catch (Exception e){
                    logger.error(e.getMessage());
                    stream=null;
                    PictureResult pictureResult=new PictureResult();
                    pictureResult.setError(1);
                    pictureResult.setMessage("upload file failed"+i+"=>"+e.getMessage());
                    return pictureResult;
                }
            }else {
                PictureResult pictureResult=new PictureResult();
                pictureResult.setError(1);
                pictureResult.setMessage("The file was empty");
            }
        }

        if (urls.size()==files.size()){
            PictureResult pictureResult=new PictureResult();
            pictureResult.setError(0);
            pictureResult.setMessage("Success");
            pictureResult.setUrls(urls);
            return pictureResult;
        }else {
            PictureResult pictureResult=new PictureResult();
            pictureResult.setError(1);
            pictureResult.setMessage("Some file was failed");
            return pictureResult;
        }

    }
}
