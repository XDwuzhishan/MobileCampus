package app.mapper;

import app.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xdcao on 2017/6/5.
 */

public interface UserMapper {

    @Select("select * from mc_user")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "password",column = "password"),
            @Result(property = "created",column = "created"),
            @Result(property = "updated",column = "updated"),
            @Result(property = "sex",column = "sex"),
            @Result(property = "age",column = "age"),
            @Result(property = "school",column = "school")
    })
    List<User> getAllUsers();

    @Select("select * from mc_user where id=#{id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "password",column = "password"),
            @Result(property = "created",column = "created"),
            @Result(property = "updated",column = "updated"),
            @Result(property = "sex",column = "sex"),
            @Result(property = "age",column = "age"),
            @Result(property = "school",column = "school")
    })
    User getUserById(Long id);

    @Insert("insert into mc_user (username,password,created,updated,sex,age,school) " +
            "values (#{username},#{password},#{created},#{updated},#{sex},#{age},#{school})")
    void insert(User user);

    @Update("update mc_user set username=#{username},password=#{password},created=#{created},updated=#{updated},sex=#{sex},age=#{age}," +
            "school=#{school} where id=#{id}")
    void update(User user);

    @Delete("delete from mc_user where id=#{id}")
    void delete(Long id);

}
