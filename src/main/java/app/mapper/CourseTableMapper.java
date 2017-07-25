package app.mapper;

import app.entity.CourseTable;
import org.apache.ibatis.annotations.*;

/**
 * Created by xdcao on 2017/7/25.
 */
public interface CourseTableMapper {

    @Insert("insert into course_table (id,username,password,course,created,updated) values " +
            "(#{id},#{username},#{password},#{course},#{created},#{updated})")
    void insert(CourseTable courseTable);

    @Update("update course_table set course=#{course},updated=#{updated} where id=#{id}")
    void update(CourseTable courseTable);

    @Select("select * from course_table where username=#{username}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "password",column = "password"),
            @Result(property = "course",column = "course"),
            @Result(property = "created",column = "created"),
            @Result(property = "updated",column = "updated")
    })
    CourseTable getCourseByUsername(String username);


}
