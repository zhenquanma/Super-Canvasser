package com.supercanvasser.service;

import com.supercanvasser.bean.*;
import com.supercanvasser.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Service
public class CanvasserService {

    @Autowired
    CampaignMapper campaignMapper;

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    ResultMapper resultMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    LocationMapper locationMapper;

    @Autowired
    TaskService taskService;

    final static SimpleDateFormat dFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public void addQuestionAnswer(QuestionAnswer questionAnswer){
        questionMapper.addAnswer(questionAnswer);
    }

    public Boolean getQuestionAnswer(Integer questionId, Integer locationId){
        return questionMapper.getAnswerByTwoId(questionId, locationId);
    }


    public void addResult(Integer campaignId, Integer taskId, Integer canvasserId, Integer locationId, Result result){
        resultMapper.addResult(result);
        campaignMapper.addResultId(campaignId, result.getId());
        taskMapper.setLocationStatus(locationId);
        if(userMapper.getCurrentLocationId(canvasserId) == null)
            userMapper.addCurrentLocationId(canvasserId, locationId);
        List<Location> unvisitedLocations = taskMapper.getUnvisitedLocations(taskId);
        if(unvisitedLocations == null || unvisitedLocations.size() == 0){
            userMapper.deleteCurrentLocationId(canvasserId);
        }
        else {
            userMapper.setCurrentLocationId(canvasserId, locationId);
        }
        for (QuestionAnswer questionAnswer : result.getQuestionAnswers()) {
            questionMapper.addAnswer(questionAnswer);
            resultMapper.addResultAnswers(result.getId(), questionAnswer.getId());
        }
    }


    /**
     * Get a task by task id
     * @param taskId
     * @return
     */
    public Task getTaskInfo(Integer taskId){
        Task task = taskMapper.getTaskById(taskId);
        if(task != null) {
            task.setLocations(taskMapper.getLocations(taskId));
            List<Location> visitedLocations = taskMapper.getVisitedLocations(taskId);
            if(visitedLocations != null)
                task.setVisitedLocation(visitedLocations);
            List<Location> unvisitedLocations = taskMapper.getUnvisitedLocations(taskId);
            if(unvisitedLocations != null)
                task.setUnvisitedLocation(unvisitedLocations);
        }
        return task;
    }

    /**
     * Get a task on a specific date of a canvasser
     * @param canvasserId
     * @param date
     * @return
     */
    public Task getTaskInfo(Integer canvasserId, Date date){
        List<Task> tasks = taskMapper.getAllTasksByCanvasserId(canvasserId);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        if(tasks != null){
            for(Task task : tasks){
                if(dateFormatter.format(date).equals(dateFormatter.format(task.getDate()))){
                    task.setLocations(taskMapper.getLocations(task.getId()));
                    List<Location> visitedLocations = taskMapper.getVisitedLocations(task.getId());
                    if(visitedLocations != null)
                        task.setVisitedLocation(visitedLocations);
                    List<Location> unvisitedLocations = taskMapper.getUnvisitedLocations(task.getId());
                    if(unvisitedLocations != null)
                        task.setUnvisitedLocation(unvisitedLocations);
                    return task;
                }
            }
        }
        return null;
    }


    public List<Task> getAllTasks(Integer canvasserId){
        return taskMapper.getAllTasksByCanvasserId(canvasserId);
    }

    /**
     * Get all upcoming tasks after a given date
     * @param canvasserId
     * @param date
     * @return
     */
    public List<Task> getUpcomingTasks(Integer canvasserId, Date date){
        List<Task> tasks = taskMapper.getUnfinishedTasksByCanvasserId(canvasserId);
        for(int i = 0; i<tasks.size(); i++){
            if(tasks.get(i).getDate().before(date) || tasks.get(i).getDate().equals(date))
                tasks.remove(i);
        }
        return tasks;
    }


    /**
     * Get the current location
     * @param taskId
     * @return
     */
    public Location getCurrentLocation(Integer taskId, Integer userId){

        Integer currentLocationId = userMapper.getCurrentLocationId(userId);
        if(currentLocationId != null){
            return locationMapper.getLocationById(currentLocationId);
        }

        List<Location> unvisitedLocations = taskMapper.getUnvisitedLocations(taskId);
        if(unvisitedLocations != null && unvisitedLocations.size() != 0){
            Location location = unvisitedLocations.get(0);
            return location;
        }
        return null;
    }

    /**
     * Get the next location
     * @param taskId
     * @return
     */
    public Location getNextLocation(Integer taskId, Integer userId){
        Integer currentLocationId = userMapper.getCurrentLocationId(userId);
        List<Location> unvisitedLocations = taskMapper.getUnvisitedLocations(taskId);

        if(unvisitedLocations == null || unvisitedLocations.size() == 0)
            return null;
        List<Integer> unvisitedLocationIds = new ArrayList<>();
        for(Location location : unvisitedLocations){
            unvisitedLocationIds.add(location.getId());
        }
        if(currentLocationId == null){
            currentLocationId = unvisitedLocationIds.get(0);
            if(unvisitedLocations.size() > 1)
                return locationMapper.getLocationById(unvisitedLocationIds.get(1));
            else
                return null;
        }
        List<Integer> fastestRoute = taskService.reComputeFastestRoute(currentLocationId, unvisitedLocationIds);
        return locationMapper.getLocationById(fastestRoute.get(0));


    }

    /**
     * fixme whether need to include current location
     * @return
     */
    public List<Location> getUpcomingLocations(Integer taskId){
        List<Location> unvisitedLocations = taskMapper.getUnvisitedLocations(taskId);
//        unvisitedLocations.remove(0);
        return unvisitedLocations;
    }


    /**
     * Get a location by its id
     * @param locationId
     * @return
     */
    public Location getLocation(Integer locationId){
        return locationMapper.getLocationById(locationId);
    }

    /**
     * Get all locations of a task
     * @param taskId
     * @return
     */
    public List<Location> getAllLocations(Integer taskId){
        return taskMapper.getLocations(taskId);
    }


    /**
     * Get the finishing status of a location
     * @param locationId
     * @return
     */
    public Boolean getStatus(Integer locationId){
        return taskMapper.getLocationStatus(locationId);
    }


    /**
     * Get the campaign result of a location
     * @param locationId
     * @return
     */
    public Result getResultByLocation(Integer locationId){
        return resultMapper.getResultByLocation(locationId);
    }


    /**
     * Get the questionnaire of a campaign
     * @param campaignId
     * @return
     */
    public List<Question> getQuestions(Integer campaignId){
        return questionMapper.getQuestionByCampaignId(campaignId);
    }


    /**
     * Get a single question by its id
     * @param questionId
     * @return
     */
    public Question getAQuestion(Integer questionId){
        return questionMapper.getQuestionById(questionId);
    }


    /**
     * Get the talking point
     * @param campaignId
     * @return
     */
    public String getTalkingPoint(Integer campaignId){
        return campaignMapper.getCampaignById(campaignId).getTalkingPoints();
    }


    /**
     * Get the visit duration of a campaign for each location
     * @param campaignId
     * @return
     */
    public Double getVisitDuration(Integer campaignId){
        return campaignMapper.getCampaignById(campaignId).getVisitDuration();
    }


    /**
     * Convert plain dates into formatted strings. e.g. 2018-11-23
     * @param plainDates
     * @return
     */
    public List<String> getFormattedDateString(List<Date> plainDates){
        List<String> formattedDates = new ArrayList<>();
        for(Date plainDate : plainDates){
            String formattedDate = dFormatter.format(plainDate);
            formattedDates.add(formattedDate);
        }
        return formattedDates;
    }

    /**
     * Convert a single plain date into a formatted string. e.g. 2018-11-23
     * @param plainDate
     * @return
     */
    public String getFormattedDateString(Date plainDate){
        return dFormatter.format(plainDate);
    }

    public Date getFormattedDate(String dateStr) throws ParseException {
        return dFormatter.parse(dateStr);
    }



}
