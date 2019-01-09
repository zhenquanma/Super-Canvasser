package com.supercanvasser.mapper;

import com.supercanvasser.bean.Question;
import com.supercanvasser.bean.QuestionAnswer;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface QuestionMapper {

    @Insert("INSERT INTO question(content) VALUES (#{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addQuestion(Question question);


    @Delete("DELETE FROM question WHERE id = #{id}")
    void deleteQuestion(Integer Id);


    @Select("SELECT * FROM question WHERE id IN (SELECT questionId FROM campaign_questionIdList WHERE campaignId = #{id})")
    List<Question> getQuestionByCampaignId(Integer id);


    @Select("SELECT * FROM question WHERE id = #{id}")
    Question getQuestionById(Integer id);



    @Insert("INSERT INTO questionAnswer(questionId, locationId, answer) VALUES(#{questionAnswer.question.id}, #{questionAnswer.location.id}, #{questionAnswer.answer})")
    @Options(useGeneratedKeys = true, keyProperty = "questionAnswer.id", keyColumn = "id")
    void addAnswer(@Param("questionAnswer") QuestionAnswer questionAnswer);


    @Select("SELECT * FROM questionAnswer WHERE id IN (SELECT questionAnswerId FROM result_questionAnswer WHERE resultId = #{resultId})")
    List<QuestionAnswer> getQuestionAnswerByResultId(Integer resultId);



    @Select("SELECT * FROM questionAnswer WHERE id = #{id}")
    @Results({
            @Result(
                    property = "question", column = "questionId",
                    one = @One(
                            select = "com.supercanvasser.mapper.QuestionMapper.getQuestionById",
                            fetchType = FetchType.EAGER
                    )
            ),
            @Result(
                    property = "location", column = "locationId",
                    one = @One(
                            select = "com.supercanvasser.mapper.LocationMapper.getLocationById",
                            fetchType = FetchType.EAGER
                    )
            ),
            @Result(property = "answer", column = "answer", javaType = Boolean.class)
    })
    QuestionAnswer getQuestionAnswerById(Integer id);


    @Select("SELECT answer FROM questionAnswer WHERE questionId = #{questionId} AND locationId = #{locationId}")
    @ResultType(Boolean.class)
    Boolean getAnswerByTwoId(@Param("questionId") Integer questionId, @Param("locationId") Integer locationId);

}
