package com.supercanvasser.mapper;

import com.supercanvasser.bean.QuestionAnswer;
import com.supercanvasser.bean.Result;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface ResultMapper {



    @Select("SELECT * FROM result WHERE id IN (SELECT resultId FROM campaign_resultIdList WHERE campaignId = #{id})")
    List<com.supercanvasser.bean.Result> getResultByCampaignId(Integer id);

    @Select("SELECT * FROM result WHERE id = #{id}")
    @Results({
            @org.apache.ibatis.annotations.Result(property = "id", column = "id"),
            @org.apache.ibatis.annotations.Result(property = "briefNote", column = "briefNotes"),
            @org.apache.ibatis.annotations.Result(
                    property = "questionAnswers", column = "id",
                    many = @Many(
                            select = "com.supercanvasser.mapper.ResultMapper.getQuestionAnswerByResultId",
                            fetchType = FetchType.LAZY
                    )
            ),
            @org.apache.ibatis.annotations.Result(
                    property = "location", column = "locationId",
                    one = @One(
                            select = "com.supercanvasser.mapper.LocationMapper.getLocationById",
                            fetchType = FetchType.EAGER
                    )
            )
    })
    Result getResultById(Integer id);


    @Select("SELECT * FROM result WHERE locationId = #{locationId}")
    @Results({
            @org.apache.ibatis.annotations.Result(property = "id", column = "id"),
            @org.apache.ibatis.annotations.Result(property = "isSpoke", column = "isSpoke"),
            @org.apache.ibatis.annotations.Result(property = "rating", column = "rating"),
            @org.apache.ibatis.annotations.Result(property = "briefNote", column = "briefNote"),
            @org.apache.ibatis.annotations.Result(
                    property = "questionAnswers", column = "id",
                    many = @Many(
                            select = "com.supercanvasser.mapper.QuestionMapper.getQuestionAnswerByResultId",
                            fetchType = FetchType.LAZY
                    )
            ),
            @org.apache.ibatis.annotations.Result(
                    property = "location", column = "locationId",
                    one = @One(
                            select = "com.supercanvasser.mapper.LocationMapper.getLocationById",
                            fetchType = FetchType.EAGER
                    )
            )
    })
    Result getResultByLocation(Integer locationId);


    @Insert("INSERT INTO result(isSpoke, rating, briefNotes, locationId) VALUES(#{isSpoke}, #{rating}, #{briefNote}, #{location.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addResult(Result result);


    @Insert("INSERT INTO result_questionAnswer(resultId, questionAnswerId) VALUES(#{resultId}, #{questionAnswerId})")
    void addResultAnswers(@Param("resultId") Integer resultId, @Param("questionAnswerId") Integer questionAnswerId);


    @Insert("INSERT INTO campaign_resultIdList(campaignId, resultId) VALUES(#{campaignId}, #{resultId})")
    void addCampaignResultTable(@Param("campaignId") Integer campaignId, @Param("resultId") Integer resultId);

}
