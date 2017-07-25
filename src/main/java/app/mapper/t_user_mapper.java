package app.mapper;

import app.Shiro.t_entity.t_user;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xdcao on 2017/7/20.
 */
public interface t_user_mapper {

    @Select("select * from t_user where id=#{id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "password",column = "password")
    })
    t_user getUserById(int id);

    @Select("select * from t_user")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "password",column = "password")
    })
    List<t_user> getAllUsers();


}
