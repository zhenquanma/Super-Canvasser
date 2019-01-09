package com.supercanvasser.service;


import com.supercanvasser.bean.*;
import com.supercanvasser.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class ManagerService {
    @Autowired
    private CampaignMapper campaignMapper;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private ResultMapper resultMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private TaskService taskService;
    /*
        Create, retrieve, update, and delete for campaign
     */
    public Boolean addCampaign(Campaign campaign){
        //Generate ID here
        campaignMapper.addCampaign(campaign);
        Integer campaignId = campaign.getId();
        List<User> managerList = campaign.getManagers();
        List<User> canvasserList = campaign.getCanvassers();
        List<Location> locationList = campaign.getLocations();
        List<Question> questionList = campaign.getQuestions();

        //add manager id to the internal table with corresponding campaign id
        if(managerList != null) {
            for (User manager : managerList) {
                campaignMapper.addManagerId(campaignId, manager.getId());
            }
        }

        //add canvasser id to the internal table with corresponding campaign id
        if(canvasserList != null) {
            for (User canvasser : canvasserList) {
                campaignMapper.addCanvasserId(campaignId, canvasser.getId());
            }
        }

        //add location id to the internal table with corresponding campaign id
        if(locationList != null) {
            for (Location location : locationList) {
                locationMapper.addLocation(location);
                campaignMapper.addLocationId(campaignId, location.getId());
            }
        }


        //add question id to the internal table with corresponding campaign id
        if(questionList != null) {
            for (Question question : questionList) {
                questionMapper.addQuestion(question);
                campaignMapper.addQuestionId(campaignId, question.getId());
            }
        }

        if(campaignMapper.getCampaignById(campaignId) != null){
            return true;
        }
        return false;
    }


    public Campaign getCampaignById(Integer id){
        return campaignMapper.getCampaignById(id);
    }



    public void deleteCampaign(Integer campaignId){
        Campaign campaign = campaignMapper.getCampaignById(campaignId);
        campaignMapper.deleteCanvasserByCampaignId(campaignId);
        campaignMapper.deleteManagerByCampaignId(campaignId);

        for (Location location : campaign.getLocations()) {
            locationMapper.deleteLocation(location.getId());
        }
        campaignMapper.deleteLocationByCampaignId(campaignId);


        for (Question question : campaign.getQuestions()) {
            questionMapper.deleteQuestion(question.getId());
        }
        campaignMapper.deleteQuestionByCampaignId(campaignId);


        campaignMapper.deleteResultByCampaignId(campaignId);
        campaignMapper.deleteCampaign(campaignId);
    }


    public List<Campaign> getCampaignListByManagerId(Integer managerId){
        List<Integer> campaignIds =  campaignMapper.getCampaignIdsByManagerId(managerId);
        List<Campaign> campaigns = new ArrayList<>();
        for (Integer id : campaignIds) {
            campaigns.add(campaignMapper.getCampaignById(id));
        }
        return campaigns;
    }

    public void updateCampaign(Campaign campaign){
        campaignMapper.updateCampaign(campaign);
        Integer campaignId = campaign.getId();
        List<User> managerList = campaign.getManagers();
        List<User> canvasserList = campaign.getCanvassers();
        List<Location> locationList = campaign.getLocations();
        List<Question> questionList = campaign.getQuestions();

        //delete canvasserList
        campaignMapper.deleteCanvasserByCampaignId(campaignId);
        //delete locationList
        campaignMapper.deleteLocationByCampaignId(campaignId);
        //delete managerList
        campaignMapper.deleteManagerByCampaignId(campaignId);
        //delete questionList
        campaignMapper.deleteQuestionByCampaignId(campaignId);
        //add canvasserList
        for(int i = 0; i < canvasserList.size(); i++){
            campaignMapper.addCanvasserId(campaignId, canvasserList.get(i).getId());
        }
        //add locationList
        for(int i = 0; i < locationList.size(); i++){
            locationMapper.addLocation(locationList.get(i));
            campaignMapper.addLocationId(campaignId, locationList.get(i).getId());
        }
        //add managerList
        for(int i = 0; i < managerList.size(); i++){
            campaignMapper.addManagerId(campaignId, managerList.get(i).getId());
        }
        //add questionList
        for(int i = 0; i < questionList.size(); i++){
            questionMapper.addQuestion(questionList.get(i));
            campaignMapper.addQuestionId(campaignId, questionList.get(i).getId());
        }
    }

    public void addTask(Task task){

        //Add data in task table
        taskMapper.addTask(task);
        Integer taskId = task.getId();
        List<Location> locations = task.getLocations();

        //add data in task_location table
        for (int i = 0; i < locations.size(); i++) {
            taskMapper.addLocationId(taskId, locations.get(i).getId());
        }
    }

    public Task getTask(Integer taskId){
        Task returnTask = taskMapper.getTaskById(taskId);
        List<Location> locations = taskMapper.getLocations(taskId);
        returnTask.setLocations(locations);
        return returnTask;
    }

    public List<Task> getTasksByCampaignId(Integer campaignId){
        List<Integer> taskIds = taskMapper.getTaskIdsByCampaignId(campaignId);
        List<Task> tasks = new ArrayList<>();
        for(Integer i: taskIds){
            Task task = taskMapper.getTaskById(i);
            tasks.add(task);
        }
        return tasks;
    }

    public void deleteTask(Integer taskId){
        taskMapper.deleteTask(taskId);
    }


    public void updateTaskCanvasser(Integer taskId, Integer canvasserId, Date date){
        taskMapper.updateCanvasser(canvasserId, taskId);
        taskMapper.updateDate(date, taskId);
        userMapper.deleteFreeDayByDateUserId(date, canvasserId);
    }

    public void recoveryUserFreedayOne(Date date, Integer canvasserId){
        userMapper.addFreeDay(date, canvasserId);
    }

    //use this function when (if the dates, canvassers, or locations for a campaign are modified,
    //the system updates or discards any previously computed canvassing assignment for it.)
    public void discardTask(Integer campaignId){
        taskMapper.deleteTaskByCampaignId(campaignId);
    }

    public void recoveryUserFreeday(Integer campaignId){
        List<Integer> taskIds = taskMapper.getTaskIdsByCampaignId(campaignId);
        for(Integer i: taskIds){
            Task task = taskMapper.getTaskById(i);
            if(task.getDate() == null && task.getCanvasserId() == null){
                break;
            }
            Integer canvasserId = task.getCanvasserId();
            Date freeday = task.getDate();
            userMapper.addFreeDay(freeday, canvasserId);
        }
    }

    public void addCampaignCanvasser(Integer campaignId, Integer canvasserId){
        campaignMapper.addCanvasserId(campaignId,canvasserId);
    }

    public void deleteCampaignCanvasser(Integer campaignId, Integer canvasserId){
        campaignMapper.deleteCanvasserByTwoId(campaignId, canvasserId);
    }

    public void addTaskCanvasserIdDate(Integer taskId, Integer canvasserId, Date date){
        taskMapper.updateCanvasser(canvasserId, taskId);
        taskMapper.updateDate(date, taskId);
        userMapper.deleteFreeDayByDateUserId(date, canvasserId);
    }


    public Result getResultById(Integer resultId){
        return resultMapper.getResultById(resultId);
    }


    /**
     * Calculate the mean and standard deviation for the ratings of a campaign.
     * Result is stored in a instance class named Statistic.
     * @param campaignId
     * @return
     */
    public Statistic summary(Integer campaignId){
        Campaign campaign = this.getCampaignById(campaignId);
        List<Location> locationList = campaign.getLocations();
        int sum = 0;
        int number = locationList.size();
        for(int i = 0; i < number; i++){
            Result result = resultMapper.getResultByLocation(locationList.get(i).getId());
            if(result != null){
                sum += result.getRating();
            }

        }

        double mean = (double)sum / number;

        sum = 0;
        for(int i = 0; i < number; i++){
            Result result = resultMapper.getResultByLocation(locationList.get(i).getId());
            if(result != null){
                sum += Math.pow((result.getRating() - mean), 2);
            }

        }
        double stdev = Math.sqrt((double)sum / number);

        return new Statistic(mean, stdev);
    }


    /**
     * Calculate the percentage of yes for a question in each questionnaire
     * @param campaignId
     * @param questionId
     * @return value in percentage
     */
    public List<Double> percentageYes(Integer campaignId, Integer questionId){
        Campaign campaign = this.getCampaignById(campaignId);
        List<Location> locationList = campaign.getLocations();
        int numYes = 0;
        int numNo = 0;
        int numGiveup = 0;
        int total = 0;
        for(int i = 0; i< locationList.size(); i++){

            Boolean answer = questionMapper.getAnswerByTwoId(questionId, locationList.get(i).getId());
            if(answer != null){
                if(answer == true){
                    numYes++;
                }else if(answer == false){
                    numNo++;
                }
            }else{
                numGiveup++;
            }
            total++;

        }

        List<Double> returnList = new ArrayList<>();
        returnList.add(((double)numYes / total) * 100);
        returnList.add(((double)numNo / total) * 100);
        returnList.add(((double)numGiveup / total) * 100);

        return returnList;
    }



    /**
     * Get all managers
     * @return
     */
    public List<User> getManagers(){
        List<UserRole> userRoles = userRoleMapper.getByRoleId(1);
        if(userRoles != null){
            List<User> managers = new ArrayList<>();
            for(UserRole userRole: userRoles){
                User manager = userRole.getUser();
                managers.add(manager);
            }
            return managers;
        }
        else{
            return null;
        }
    }


    /**
     * Get available canvassers on a specific date
     * @param date on which to work
     * @return
     */
    public List<User> getAvailableCanvassers(Date date) {
        List<UserRole> userRoles = userRoleMapper.getByRoleId(2);
        if (userRoles != null) {
            List<User> canvassers = new ArrayList<>();
            for (UserRole userRole : userRoles) {
                User canvasser = userRole.getUser();
                List<Date> freeDays = canvasser.getFreeDays();
                if(freeDays != null){
                    for(Date freeDay : freeDays){
                        if(freeDay.equals(date))
                            canvassers.add(canvasser);
                    }
                }
            }
            return canvassers;
        }
        else{
            return null;
        }
    }

    public HashSet<String> getAvailableCanvassersByStartDateEndDate(Date startDate, Date endDate){
        HashSet<String> returnCanvassers = new HashSet<>();
        List<Date> datesList = taskService.getCampaignDates(startDate, endDate);
        for(int i = 0; i < datesList.size(); i++){
            Date date = datesList.get(i);
            List<User> canvassers = getAvailableCanvassers(date);
            for(User user: canvassers){
                String userName = userMapper.getUsernameById(user.getId());
                returnCanvassers.add(userName);
            }
        }
        return returnCanvassers;
    }



    public String printQuestions(List<Question> questions){
        String returnString = "";
        for(Question q: questions){
            returnString=returnString+q.getContent()+"\n";
        }
        return  returnString;
    }

    public String printLocations(List<Location> locations){
        String returnString = "";
        for(Location location: locations){
            returnString=returnString+location.toString()+"\n";
        }
        return  returnString;
    }

}
