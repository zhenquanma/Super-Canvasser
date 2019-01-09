package com.supercanvasser.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface GlobalVariablesMapper {


    /**
     * get global variable duration
     */
    @Select("SELECT duration FROM globalVariables")
    public Double getDuration();

    /**
     * get averageSpeed duration
     */
    @Select("SELECT averageSpeed FROM globalVariables")
    public Double getAverageSpeed();


    @Update("UPDATE globalVariables SET duration = #{duration}")
    public void updateDuration(Double duration);


    @Update("UPDATE globalVariables SET averageSpeed = #{averageSpeed}")
    public void updateAverageSpeed(Double averageSpeed);
}
