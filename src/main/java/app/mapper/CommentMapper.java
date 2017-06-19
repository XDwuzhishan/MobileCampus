package app.mapper;

import app.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xdcao on 2017/6/16.
 */
public interface CommentMapper {


    @Select("select * from mc_comment where id=#{id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "answer_id",column = "answer_id"),
            @Result(property = "content",column = "content"),
            @Result(property = "created",column = "created"),
            @Result(property = "updated",column = "updated"),
            @Result(property = "ownerId",column = "ownerId"),
            @Result(property = "ownerName",column = "ownerName"),
            @Result(property = "repNum",column = "repNum")
    })
    Comment getComById(Long id);

    @Select("select * from mc_comment where answer_id=#{answer_id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "answer_id",column = "answer_id"),
            @Result(property = "content",column = "content"),
            @Result(property = "created",column = "created"),
            @Result(property = "updated",column = "updated"),
            @Result(property = "ownerId",column = "ownerId"),
            @Result(property = "ownerName",column = "ownerName"),
            @Result(property = "repNum",column = "repNum")
    })
    List<Comment> getCommentsByAnswerId(long answer_id);


    @Select("select * from mc_comment where ownerId=#{ownerId}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "answer_id",column = "answer_id"),
            @Result(property = "content",column = "content"),
            @Result(property = "created",column = "created"),
            @Result(property = "updated",column = "updated"),
            @Result(property = "ownerId",column = "ownerId"),
            @Result(property = "ownerName",column = "ownerName"),
            @Result(property = "repNum",column = "repNum")
    })
    List<Comment> getCommentsByOwnerId(long ownerId);


    @Update("upadte mc_comment set updated=#{updated},repNum=#{repNum}")
    void update(Comment comment);

    @Insert("insert into mc_comment (answer_id,content,created,updated,ownerId,ownerName,repNum) values " +
            "(#{answer_id},#{content},#{created},#{updated},#{ownerId},#{ownerName},#{repNum})")
    void insert(Comment comment);

    @Delete("delete from mc_comment where id=#{id}")
    void delete(long id);
}
