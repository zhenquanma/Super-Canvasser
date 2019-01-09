package com.supercanvasser.mapper;

import com.supercanvasser.bean.Campaign;
import com.supercanvasser.bean.Location;
import com.supercanvasser.bean.Question;
import com.supercanvasser.bean.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface CampaignMapper {


    @Select("SELECT * FROM user WHERE id IN (SELECT managerId FROM campaign_managerIdList WHERE campaignId = #{id})")
    List<User> getManagerByCampaignId(Integer id);


    @Select("SELECT * FROM user WHERE id IN (SELECT canvasserId FROM campaign_canvasserIdList WHERE campaignId = #{id})")
    List<User> getCanvasserByCampaignId(Integer id);


    @Select("SELECT campaignId FROM campaign_managerIdList WHERE managerId = #{id}")
    List<Integer> getCampaignIdsByManagerId(Integer id);


    @Select("SELECT * FROM campaign WHERE id IN (SELECT campaignId FROM campaign_managerIdList WHERE managerId = #{id})")
    List<Campaign> getCampaignByManagerId(Integer id);



    /**
     * Get a campaign by the campaign id
     * @param id
     * @return
     */
    @Select("SELECT * FROM campaign WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(
                    property = "managers", column = "id",
                    many = @Many(
                            select = "com.supercanvasser.mapper.CampaignMapper.getManagerByCampaignId",
                            fetchType = FetchType.LAZY
                    )
            ),
            @Result(
                    property = "canvassers", column = "id",
                    many = @Many(
                            select = "com.supercanvasser.mapper.CampaignMapper.getCanvasserByCampaignId",
                            fetchType = FetchType.LAZY
                    )
            ),
            @Result(
                    property = "locations", column = "id",
                    many = @Many(
                            select = "com.supercanvasser.mapper.LocationMapper.getLocationByCampaignId",
                            fetchType = FetchType.LAZY
                    )
            ),
            @Result(
                    property = "results", column = "id",
                    many = @Many(
                            select = "com.supercanvasser.mapper.ResultMapper.getResultByCampaignId",
                            fetchType = FetchType.LAZY
                    )
            ),
            @Result(
                    property = "questions", column = "id",
                    many = @Many(
                            select = "com.supercanvasser.mapper.QuestionMapper.getQuestionByCampaignId",
                            fetchType = FetchType.LAZY
                    )
            )
    })
    Campaign getCampaignById(Integer id);



    /**
     * Insert a new campaign
     * @param campaign
     */
    @Insert("INSERT INTO campaign(campaignName, startDate, endDate, visitDuration, talkingPoints)" +
            "VALUES(#{campaignName}, #{startDate}, #{endDate}, #{visitDuration}, #{talkingPoints})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addCampaign(Campaign campaign);

    @Delete("DELETE FROM campaign WHERE id = #{id}")
    void deleteCampaign(Integer id);

    @Update("UPDATE campaign SET campaignName = #{campaignName}, startDate = #{startDate}, endDate = #{endDate}, visitDuration = #{visitDuration}, talkingPoints = #{talkingPoints}" +
            "WHERE id = #{id}")
    void updateCampaign(Campaign campaign);


    /**campaign_locationIdList
     * Insert, delete and get campaign_locationIdList
     */
    @Insert("INSERT INTO campaign_locationIdList(campaignId, locationId)" + "VALUES(#{campaignId}, #{locationId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addLocationId(@Param("campaignId") Integer campaignId, @Param("locationId") Integer locationId);

    @Delete("DELETE FROM campaign_locationIdList WHERE locationId = #{locationId} AND campaignId = #{campaignId}")
    void deleteLocationByTwoId(@Param("campaignId") Integer campaignId, @Param("locationId") Integer locationId);

    @Delete("DELETE FROM campaign_locationIdList WHERE id = #{id}")
    void deleteLocationById(Integer id);

    @Delete("DELETE FROM campaign_locationIdList WHERE campaignId = #{campaignId}")
    void deleteLocationByCampaignId(Integer campaignId);


    @Select("SELECT locationId FROM campaign_locationIdList WHERE campaignId = #{campaignId}")
    List<Integer> getLocationList(Integer campaignId);



    /**campaign_canvasserIdList
     * Insert, delete and get campaign_canvasserIdList
     */
    @Insert("INSERT INTO campaign_canvasserIdList(campaignId, canvasserId)" + "VALUES(#{campaignId}, #{canvasserId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addCanvasserId(@Param("campaignId") Integer campaignId, @Param("canvasserId") Integer canvasserId);

    @Delete("DELETE FROM campaign_canvasserIdList WHERE canvasserId = #{canvasserId} AND campaignId = #{campaignId}")
    void deleteCanvasserByTwoId(@Param("campaignId") Integer campaignId, @Param("canvasserId") Integer canvasserId);

    @Delete("DELETE FROM campaign_canvasserIdList WHERE id = #{id}")
    void deleteCanvasserById(Integer id);

    @Delete("DELETE FROM campaign_canvasserIdList WHERE campaignId = #{campaignId}")
    void deleteCanvasserByCampaignId(Integer campaignId);

    @Select("SELECT canvasserId FROM campaign_canvasserIdList WHERE campaignId = #{campaignId}")
    List<Integer> getCanvasserList(Integer campaignId);


    /**campaign_managerIdList
     * Insert, delete and get campaign_managerIdList
     */
    @Insert("INSERT INTO campaign_managerIdList(campaignId, managerId)" + "VALUES(#{campaignId}, #{managerId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addManagerId(@Param("campaignId") Integer campaignId, @Param("managerId") Integer managerId);

    @Delete("DELETE FROM campaign_managerIdList WHERE managerId = #{managerId} AND campaignId = #{campaignId}")
    void deleteManagerByTwoId(@Param("campaignId") Integer campaignId, @Param("managerId") Integer managerId);

    @Delete("DELETE FROM campaign_managerIdList WHERE id = #{id}")
    void deleteManagerById(Integer id);

    @Delete("DELETE FROM campaign_managerIdList WHERE campaignId = #{campaignId}")
    void deleteManagerByCampaignId(Integer campaignId);

    @Select("SELECT managerId FROM campaign_managerIdList WHERE campaignId = #{campaignId}")
    List<Integer> getManagerList(Integer campaignId);



    /**campaign_questionIdList
     * Insert, delete and get campaign_questionIdList
     */
    @Insert("INSERT INTO campaign_questionIdList(campaignId, questionId)" + "VALUES(#{campaignId}, #{questionId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addQuestionId(@Param("campaignId") Integer campaignId, @Param("questionId") Integer questionId);

    @Delete("DELETE FROM campaign_questionIdList WHERE questionId = #{questionId} AND campaignId = #{campaignId}")
    void deleteQuestionByTwoId(@Param("campaignId") Integer campaignId, @Param("questionId") Integer questionId);

    @Delete("DELETE FROM campaign_questionIdList WHERE id = #{id}")
    void deleteQuestionById(Integer id);

    @Delete("DELETE FROM campaign_questionIdList WHERE campaignId = #{campaignId}")
    void deleteQuestionByCampaignId(Integer campaignId);

    @Select("SELECT questionId FROM campaign_questionIdList WHERE campaignId = #{campaignId}")
    List<Integer> getQuestionList(Integer campaignId);




    /**campaign_resultIdList
     * Insert, delete and get campaign_resultIdList
     */
    @Insert("INSERT INTO campaign_resultIdList(campaignId, resultId)" + "VALUES(#{campaignId}, #{resultId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addResultId(@Param("campaignId") Integer campaignId, @Param("resultId") Integer resultId);

    @Delete("DELETE FROM campaign_resultIdList WHERE resultId = #{resultId} AND campaignId = #{campaignId}")
    void deleteResultByTwoId(@Param("campaignId") Integer campaignId, @Param("resultId") Integer resultId);

    @Delete("DELETE FROM campaign_resultIdList WHERE id = #{id}")
    void deleteResultById(Integer id);

    @Delete("DELETE FROM campaign_resultIdList WHERE campaignId = #{campaignId}")
    void deleteResultByCampaignId(Integer campaignId);

}
