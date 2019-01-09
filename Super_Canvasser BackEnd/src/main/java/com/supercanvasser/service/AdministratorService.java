package com.supercanvasser.service;

import com.supercanvasser.bean.UserRole;
import com.supercanvasser.mapper.GlobalVariablesMapper;
import com.supercanvasser.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {

    @Autowired
    private GlobalVariablesMapper globalVariablesMapper;



    public boolean updateDuration(Double duration){
        try {
            globalVariablesMapper.updateDuration(duration);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean updateAverageSpeed(Double averageSpeed){
        try {
            globalVariablesMapper.updateAverageSpeed(averageSpeed);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }




    public double getAveSpeed(){
        return globalVariablesMapper.getAverageSpeed();
    }

    public double getDuration(){
        return globalVariablesMapper.getDuration();
    }

}
