package app.mapper;

import app.entity.LoginInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xdcao on 2017/7/26.
 */
public interface LoginInfoMapper {

    @Insert("insert into LoginInfo (noticer,noticeTime,subTitle,content) values (#{noticer},#{date},#{subTitle},#{content})")
    void insert(LoginInfo loginInfo);

    @Delete("delete from LoginInfo")
    void deleteAll();

    @Select("select * from LoginInfo")
    @Results({
            @Result(property = "noticer",column = "noticer"),
            @Result(property = "date",column = "noticeTime"),
            @Result(property = "subTitle",column = "subTitle"),
            @Result(property = "content",column = "content")
    })
    List<LoginInfo> getAll();


}
