package app.mapper;

import app.entity.Answer;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xdcao on 2017/6/12.
 */
public interface AnswerMapper {

    @Select("select * from mc_answer")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "content",column = "content"),
            @Result(property = "created",column = "created"),
            @Result(property = "ownerId",column = "ownerId"),
            @Result(property = "ownername",column = "ownername"),
            @Result(property = "star",column = "star"),
            @Result(property = "updated",column = "updated"),
            @Result(property = "quesId",column = "quesId"),
            @Result(property = "comNum",column = "comNum")
    })
    List<Answer> getAll();

    @Select("select * from mc_answer where id=#{id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "content",column = "content"),
            @Result(property = "created",column = "created"),
            @Result(property = "ownerId",column = "ownerId"),
            @Result(property = "ownername",column = "ownername"),
            @Result(property = "star",column = "star"),
            @Result(property = "updated",column = "updated"),
            @Result(property = "quesId",column = "quesId"),
            @Result(property = "comNum",column = "comNum")
    })
    Answer getById(Long id);

    @Select("select * from mc_answer where quesId=#{quesId}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "content",column = "content"),
            @Result(property = "created",column = "created"),
            @Result(property = "ownerId",column = "ownerId"),
            @Result(property = "ownername",column = "ownername"),
            @Result(property = "star",column = "star"),
            @Result(property = "updated",column = "updated"),
            @Result(property = "quesId",column = "quesId"),
            @Result(property = "comNum",column = "comNum")
    })
    List<Answer> getByQuesId(long quesId);


    @Insert("insert into mc_answer (id,content,created,ownerId,ownername,star,updated,quesId,comNum) values (#{id},#{content},#{created},#{ownerId}," +
            "#{ownername},#{star},#{updated},#{quesId},#{comNum})")
    void insert(Answer answer);

    @Update("update mc_answer set comNum=#{comNum},star=#{star},updated=#{updated} where id=#{id}")
    void update(Answer answer);

    @Delete("delete from mc_answer where id=#{id}")
    void delete(long id);

}
