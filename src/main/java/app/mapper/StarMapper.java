package app.mapper;

import app.entity.Star;
import org.apache.ibatis.annotations.*;

/**
 * Created by xdcao on 2017/7/30.
 */
public interface StarMapper {

    @Select("select * from star where answerId=#{arg0} and userId=#{arg1}")
    @Results({
            @Result(property = "answerId",column = "answerId"),
            @Result(property = "userId",column = "userId")
    })
    Star getOne(long answerId,long userId);


    @Insert("insert into star (userId,answerId) values (#{userId},#{answerId})")
    void insert(Star star);

    @Delete("delete from star where userId=#{arg0} and answerId=#{arg1}")
    void deleteOne(long user_id,long answer_id);

    @Delete("delete from star where userId=#{user_id}")
    void deleteByUser(long user_id);

    @Delete("delete from star where answerId=#{answer_id}")
    void deleteByAnswer(long answer_id);

}
