package app.mapper;

import app.entity.NoticeInformation;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xdcao on 2017/7/27.
 */
public interface NoticeInfomationMapper {

    @Select("select * from notice_info")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "title",column = "title"),
            @Result(property = "link",column = "link"),
            @Result(property = "content",column = "content"),
            @Result(property = "date",column = "date_time")
    })
    List<NoticeInformation> getAllNoticeInfomations();


    @Select("select * from notice_info order by date_time desc")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "title",column = "title"),
            @Result(property = "link",column = "link"),
            @Result(property = "content",column = "content"),
            @Result(property = "date",column = "date_time")
    })
    List<NoticeInformation> getAllNoticeInfomationsOrderByDate();


    @Insert("insert into notice_info (title,link,content,date_time) values (#{title},#{link},#{content},#{date})")
    void insert(NoticeInformation noticeInformation);

    @Delete("delete from notice_info")
    void deleteAll();



}
