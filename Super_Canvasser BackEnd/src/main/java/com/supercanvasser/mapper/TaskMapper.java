package com.supercanvasser.mapper;

import com.supercanvasser.bean.Location;
import com.supercanvasser.bean.Task;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface TaskMapper {

    @Select("SELECT * FROM task WHERE id = #{id}")
    Task getTaskById(Integer id);

    @Select("SELECT * FROM task WHERE canvasserId = #{canvasserId} AND date = #{date}")
    Task getTaskByCanvasserIdAndDate(@Param("canvasserId") Integer canvasserId, @Param("date") Date date);

    @Select("SELECT * FROM task WHERE canvasserId = #{canvasserId}")
    List<Task> getAllTasksByCanvasserId(Integer canvasserId);

    @Select("SELECT * FROM task WHERE canvasserId = #{canvasserId} AND status IS NOT TRUE")
    List<Task> getUnfinishedTasksByCanvasserId(Integer canvasserId);

    @Insert("INSERT INTO task(taskName, canvasserId, date, duration, campaignId)" +
            "VALUES(#{taskName}, #{canvasserId}, #{date}, #{duration}, #{campaignId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addTask(Task task);

    @Delete("DELETE FROM task WHERE id = #{id}")
    void deleteTask(Integer id);

    @Delete("DELETE FROM task WHERE campaignId = #{campaignId}")
    void deleteTaskByCampaignId(Integer campaignId);

    @Update("UPDATE task SET canvasserId = #{canvasserId} WHERE id = #{taskId}")
    void updateCanvasser(@Param("canvasserId") Integer canvasserId, @Param("taskId") Integer taskId);

    @Update("UPDATE task SET date = #{date} WHERE id = #{taskId}")
    void updateDate(@Param("date") Date date, @Param("taskId") Integer taskId);



    /**task_locationList
     * Insert, delete and get task_locationList
     */
    @Insert("INSERT INTO task_location(taskId, locationId)" + "VALUES(#{taskId}, #{locationId})")
    void addLocationId(@Param("taskId") Integer taskId, @Param("locationId") Integer locationId);

    @Delete("DELETE FROM task_location WHERE taskId = #{taskId} AND locationId = #{locationId}")
    void deleteLocationByTwoId(@Param("taskId") Integer taskId, @Param("locationId") Integer locationId);

    @Delete("DELETE FROM task_location WHERE taskId = #{taskId}")
    void deleteLocationIdByTaskId(Integer taskId);


    @Select("SELECT id FROM task WHERE campaignId = #{campaignId}")
    List<Integer> getTaskIdsByCampaignId(Integer campaignId);
    @Select("SELECT * FROM location WHERE id IN (SELECT locationId FROM task_location WHERE taskId = #{taskId})")
    List<Location>  getLocations(Integer taskId);


    @Select("SELECT IF(status, 'true', 'false') status FROM task_location WHERE locationId = #{locationId}")
    Boolean getLocationStatus(Integer locationId);

    @Select("SELECT * FROM location WHERE id IN (SELECT locationId FROM task_location WHERE taskId = #{taskId} AND status IS TRUE)")
    List<Location> getVisitedLocations(Integer taskId);

    @Select("SELECT * FROM location WHERE id IN (SELECT locationId FROM task_location WHERE taskId = #{taskId} AND status IS NOT TRUE)")
    List<Location> getUnvisitedLocations(Integer taskId);

    @Select("SELECT locationId FROM task_location WHERE taskId = #{taskId}")
    List<Integer> getLocationList(Integer taskId);

    @Update("UPDATE task_location SET status = 1 WHERE locationId = #{locationId}")
    void setLocationStatus(Integer locationId);

}
