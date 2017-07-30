package app.mapper;

import app.entity.Reply;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xdcao on 2017/6/18.
 */
public interface ReplyMapper {


    @Select("select * from mc_reply")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "comId",column = "comId"),
            @Result(property = "content",column = "content"),
            @Result(property = "created",column = "created"),
            @Result(property = "updated",column = "updated"),
            @Result(property = "ownerId",column = "ownerId"),
            @Result(property = "ownerName",column = "ownerName"),
            @Result(property = "to",column = "to"),
            @Result(property = "images",column = "images")
    })
    List<Reply> getAll();

    @Select("select * from mc_reply where id=#{id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "comId",column = "comId"),
            @Result(property = "content",column = "content"),
            @Result(property = "created",column = "created"),
            @Result(property = "updated",column = "updated"),
            @Result(property = "ownerId",column = "ownerId"),
            @Result(property = "ownerName",column = "ownerName"),
            @Result(property = "to",column = "to"),
            @Result(property = "images",column = "images")
    })
    Reply getReplyById(Long id);

    @Insert("insert into mc_reply (id,comId,content,created,updated,ownerId,ownerName,to,images) values " +
            "(#{id},#{comId},#{content},#{created},#{updated},#{ownerId},#{ownerName},#{to},#{images})")
    void insert(Reply reply);

    @Update("update mc_reply set comId=#{comId},content=#{content},created=#{created},updated=#{updated},ownerId=#{ownerId}," +
            "ownerName=#{ownerName},to=#{to} where id=#{id}")
    void update(Reply reply);

    @Delete("delete from mc_reply where id=#{id}")
    void delete(Long id);

    @Delete("delete from mc_reply where comId=#{comId}")
    void deleteByComId(Long comId);

    @Select("select * from mc_reply where comId=#{id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "comId",column = "comId"),
            @Result(property = "content",column = "content"),
            @Result(property = "created",column = "created"),
            @Result(property = "updated",column = "updated"),
            @Result(property = "ownerId",column = "ownerId"),
            @Result(property = "ownerName",column = "ownerName"),
            @Result(property = "to",column = "to"),
            @Result(property = "images",column = "images")
    })
    List<Reply> getReplyByComId(Long id);
}
