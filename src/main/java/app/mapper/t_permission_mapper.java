package app.mapper;

import app.Shiro.t_entity.t_permission;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xdcao on 2017/7/20.
 */
public interface t_permission_mapper {

    @Select("select * from t_permission")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "permissionName",column = "permissionName"),
            @Result(property = "role_id",column = "role_id")
    })
    List<t_permission> getAllPermission();


    @Select("select * from t_permission where id=#{id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "permissionName",column = "permissionName"),
            @Result(property = "role_id",column = "role_id")
    })
    t_permission getPermissionById(int id);


}
