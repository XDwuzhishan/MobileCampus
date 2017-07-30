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
            @Result(property = "mydesc",column = "mydesc"),
            @Result(property = "ownerId",column = "ownerId"),
            @Result(property = "ownername",column = "ownername"),
            @Result(property = "acknum",column = "acknum"),
            @Result(property = "images",column = "images")
    })
    List<Question> getAll();

    @Select("select * from mc_question where id=#{id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "created",column = "created"),
            @Result(property = "updated",column = "updated"),
            @Result(property = "title",column = "title"),
            @Result(property = "mydesc",column = "mydesc"),
            @Result(property = "ownerId",column = "ownerId"),
            @Result(property = "ownername",column = "ownername"),
            @Result(property = "acknum",column = "acknum"),
            @Result(property = "images",column = "images")
    })
    Question getById(long id);


    @Update("update mc_question set updated=#{updated},acknum=#{acknum} where id=#{id}")
    void update(Question question);

    @Insert("insert into mc_question (title,mydesc,ownerId,acknum,ownername,created,updated,images) values (#{title},#{mydesc},#{ownerId},#{acknum},#{ownername},#{created},#{updated},#{images})")
    void insert(Question question);

    @Delete("delete from mc_question where id=#{id}")
    void delete(long id);

    @Select("select * from mc_question where ownerId=#{ownerId}")
    List<Question> getByOwnerId(long ownerId);

}
