package app.mapper;

import app.Shiro.t_entity.t_role;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xdcao on 2017/7/20.
 */
public interface t_role_mapper {

    @Select("select * from t_role")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName")
    })
    List<t_role> getAllRoles();

    @Select("select * from t_role where id=#{id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName")
    })
    t_role getRoleById(int id);

}
