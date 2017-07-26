package app.mapper;

import app.entity.DynamicNews;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xdcao on 2017/7/26.
 */
public interface DynamicNewsMapper {

    @Select("select * from dynamic_news")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "title",property = "title"),
            @Result(column = "link",property = "link"),
            @Result(column = "content",property = "content"),
            @Result(column = "date_time",property = "date")
    })
    List<DynamicNews> getAllDynamicNews();


    @Insert("insert into dynamic_news (title,link,content,date_time) values (#{title},#{link},#{content},#{date})")
    void insert(DynamicNews dynamicNews);

    @Delete("delete from dynamic_news")
    void deleteAll();

    @Select("select * from dynamic_news order by date_time desc")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "title",property = "title"),
            @Result(column = "link",property = "link"),
            @Result(column = "content",property = "content"),
            @Result(column = "date_time",property = "date")
    })
    List<DynamicNews> getAllDynamicNewsOrderByDate();
}
