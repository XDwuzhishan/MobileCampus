package app.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xdcao on 2017/7/20.
 */
public interface t_user_role_mapper {

    @Select("select user_id from t_user_role where role_id=#{role_id}")
    List<Integer> getUserIdsByRoleId(int role_id);


    @Select("select role_id from t_user_role where user_id=#{user_id}")
    List<Integer> getRoleIdsByUserId(int user_id);

}
