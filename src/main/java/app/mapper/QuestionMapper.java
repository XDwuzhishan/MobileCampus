package app.mapper;

import app.entity.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xdcao on 2017/6/12.
 */
public interface QuestionMapper {

    @Select("select * from mc_question")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "created",column = "created"),
            @Result(property = "updated",column = "updated"),
            @Result(property = "title",column = "title"),
            @Result(property = "desc",column = "desc"),
            @Result(property = "ownerId",column = "ownerId"),
            @Result(property = "ownername",column = "ownername"),
            @Result(property = "acknum",column = "acknum")
    })
    List<Question> getAll();

    @Select("select * from mc_question where id=#{id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "created",column = "created"),
            @Result(property = "updated",column = "updated"),
            @Result(property = "title",column = "title"),
            @Result(property = "desc",column = "desc"),
            @Result(property = "ownerId",column = "ownerId"),
            @Result(property = "ownername",column = "ownername"),
            @Result(property = "acknum",column = "acknum")
    })
    Question getById(long id);


    @Update("update mc_question set updated=#{updated},acknum=#{acknum} where id=#{id}")
    void update(Question question);

    @Insert("insert into mc_question (created,updated,title,desc,ownerId,acknum,ownername) values (#{created},#{updated},#{title}," +
            "#{desc},#{ownerId},#{acknum},#{ownername})")
    void insert(Question question);

    @Delete("delete from mc_question where id=#{id}")
    void delete(long id);

}
