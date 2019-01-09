package com.supercanvasser.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.maps.errors.ApiException;
import com.supercanvasser.bean.*;
import com.supercanvasser.constant.Constants;
import com.supercanvasser.dto.CampaignDTO;
import com.supercanvasser.dto.EntityDTOConvertor;
import com.supercanvasser.mapper.ResultMapper;
import com.supercanvasser.mapper.TaskMapper;
import com.supercanvasser.mapper.UserMapper;
import com.supercanvasser.security.JWTTokenProvider;
import com.supercanvasser.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class ManagerController extends BaseController{

    @Autowired
    ManagerService managerService;

    @Autowired
    LocationService locationService;

    @Autowired
    QuestionService questionService;

    @Autowired
    CanvasserService canvasserService;

    @Autowired
    TaskService taskService;

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    ResultMapper resultMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    JWTTokenProvider jwtTokenProvider;

    private Logger logger = LoggerFactory.getLogger(ManagerController.class);

//    @PostMapping("/addCampaign")
//    public ResponseBean<List<Campaign>> addCampaign(@RequestBody CampaignDTO campaignDTO, HttpServletRequest request){
//        List<Location> locationList = locationService.retrieveLocations(campaignDTO.getLocations());
//        List<User> canvassers = userService.retrieveUsers(campaignDTO.getCanvassers());
//        List<User> managers = userService.retrieveUsers(campaignDTO.getManagers());
//        List<Question> questions = questionService.retrieveQuestions(campaignDTO.getQuestions());
//
//        Campaign campaign = EntityDTOConvertor.convertToCampaign(campaignDTO, locationList, canvassers, managers, questions);
//
//        if(managerService.addCampaign(campaign)){
//            Integer managerId = Integer.valueOf(jwtTokenProvider.getUserId(jwtTokenProvider.extractToken(request)));
//            List<Campaign> campaignList = managerService.getCampaignListByManagerId(managerId);
//            return new ResponseBean(HttpStatus.OK, Constants.SUCCESS_MSG, campaignList);
//        }
//        else{
//            return new ResponseBean(HttpStatus.BAD_REQUEST, Constants.FAILURE_MSG);
//        }
//    }
//
//    @PostMapping("/manager/PostCampaign")
//    public ResponseBean addCampaign(@RequestBody CampaignDTO campaignDTO){
//
//        List<Location> locationList = locationService.retrieveLocations(campaignDTO.getLocations());
//
//        List<User> canvassers = userService.retrieveUsers(campaignDTO.getCanvassers());
//        List<User> managers = userService.retrieveUsers(campaignDTO.getManagers());
//        List<Question> questions = questionService.retrieveQuestions(campaignDTO.getQuestions());
//
//        Campaign campaign = EntityDTOConvertor.convertToCampaign(campaignDTO, locationList, canvassers, managers, questions);
//        if(managerService.addCampaign(campaign)){
//            //todo
//            Boolean isAllAssigned = taskService.createCavassingAssignment(campaign.getId(), canvassers);
//            return new ResponseBean(HttpStatus.OK, "/manager_PostCampaign");
//        }else {
//            return new ResponseBean(HttpStatus.BAD_REQUEST, Constants.FAILURE_MSG);
//        }
//    }


    @PostMapping("/manager/PostCampaign")
    public ResponseBean addCampaign(@RequestBody CampaignDTO campaignDTO){
        if(campaignDTO.getCampaignName() == null ||
                campaignDTO.getQuestions() == null ||
                campaignDTO.getStartDate() == null ||
                campaignDTO.getEndDate() == null ||
                campaignDTO.getVisitDuration() == null ||
                campaignDTO.getTalkingPoints() == null ||
                campaignDTO.getLocations() == null ||
                campaignDTO.getManagers().size() == 0){
            return new ResponseBean(HttpStatus.BAD_REQUEST, Constants.FIELDEMPTY);
        }


        List<Location> locationList = locationService.retrieveLocations(campaignDTO.getLocations());

        List<User> canvassers = userService.retrieveUsers(campaignDTO.getCanvassers());
        List<User> managers = userService.retrieveUsers(campaignDTO.getManagers());
        List<Question> questions = questionService.retrieveQuestions(campaignDTO.getQuestions());

        Campaign campaign = EntityDTOConvertor.convertToCampaign(campaignDTO, locationList, null, managers, questions);


        if(managerService.addCampaign(campaign)){
            //todo
            Boolean isAllAssigned = taskService.createCavassingAssignment(campaign.getId(), canvassers);
            if(!isAllAssigned){
                return new ResponseBean(HttpStatus.OK, Constants.TASK_NOT_ALL_ASSIGNED);
            }
            return new ResponseBean(HttpStatus.OK, "/manager_PostCampaign");
        }else {
            return new ResponseBean(HttpStatus.BAD_REQUEST, Constants.FAILURE_MSG);
        }
    }

    @PostMapping("/manager/GetCampaignInfo")
    public ResponseBean<ObjectNode> getCampaignByCampaignId(@RequestBody Map<String, String> campaignIdMap){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String campaignIdStr = campaignIdMap.get("campaignID");
        Integer campaignId = Integer.valueOf(campaignIdStr);
        Campaign campaign = managerService.getCampaignById(campaignId);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("CampaignID", campaignIdStr);
        objectNode.put("CampaignName", campaign.getCampaignName());
        objectNode.put("TalkingPoints", campaign.getTalkingPoints());
        objectNode.put("StartDate", format.format(campaign.getStartDate()));
        objectNode.put("EndDate", format.format(campaign.getEndDate()));
        ArrayNode arrayNode1 = objectMapper.valueToTree(userService.retrieveUsersString(campaign.getManagers()));
        objectNode.putArray("Managers").addAll(arrayNode1);
        ArrayNode arrayNode2 = objectMapper.valueToTree(userService.retrieveUsersString(campaign.getCanvassers()));
        objectNode.put("Canvassers", arrayNode2);

        List<Question> questions = campaign.getQuestions();
        objectNode.put("Questions", managerService.printQuestions(questions));

        List<Location> locations = campaign.getLocations();
        objectNode.put("Locations", managerService.printLocations(locations));

        objectNode.put("VisitDuration", campaign.getVisitDuration());

        return new ResponseBean<>(HttpStatus.OK, "/manager_GetCampaignInfo", objectNode);
    }

    @PostMapping("/manager/EditCampaignWindow")
    public ResponseBean<ObjectNode> getEditCampaignWindow(@RequestBody Map<String, String> campaignIdMap){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String todayDate = format.format(today);

        String campaignIdStr = campaignIdMap.get("campaignID");
        Integer campaignId = Integer.valueOf(campaignIdStr);
        Campaign campaign = managerService.getCampaignById(campaignId);
        String startDate = format.format(campaign.getStartDate());

        if(todayDate.equals(startDate) || today.compareTo(campaign.getStartDate()) > 0){
            return new ResponseBean<>(HttpStatus.BAD_REQUEST, Constants.CAMPAIGN_STARTED);
        }


        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("CampaignID", campaignIdStr);
        objectNode.put("CampaignName", campaign.getCampaignName());
        objectNode.put("TalkingPoints", campaign.getTalkingPoints());
        objectNode.put("StartDate", format.format(campaign.getStartDate()));
        objectNode.put("EndDate", format.format(campaign.getEndDate()));
        ArrayNode arrayNode1 = objectMapper.valueToTree(userService.retrieveUsersString(campaign.getManagers()));
        objectNode.put("Managers", arrayNode1);
        List<String> canvassers = userService.retrieveUsersString(campaign.getCanvassers());
        ArrayNode arrayNode2 = objectMapper.valueToTree(canvassers);
        objectNode.put("Canvassers", arrayNode2);

        List<Question> questions = campaign.getQuestions();
        objectNode.put("Questions", managerService.printQuestions(questions));

        List<Location> locations = campaign.getLocations();
        objectNode.put("Locations", managerService.printLocations(locations));

        ArrayNode arrayNode5 = objectMapper.valueToTree(userService.retrieveUsersString(managerService.getManagers()));
        objectNode.put("ManagersOption", arrayNode5);

        HashSet<String> canvassersOption = managerService.getAvailableCanvassersByStartDateEndDate(campaign.getStartDate(), campaign.getEndDate());
        canvassersOption.addAll(canvassers);
        ArrayNode arrayNode6 = objectMapper.valueToTree(canvassersOption);
        objectNode.put("CanvassersOption", arrayNode6);

        objectNode.put("VisitDuration", String.valueOf(campaign.getVisitDuration()));

        return new ResponseBean<>(HttpStatus.OK, "/manager_EditCampaignWindow", objectNode);
    }


    @GetMapping("/manager/GetCampaignList")
    public ResponseBean<ArrayNode> getManagerCampaignList(HttpServletRequest request){
        ObjectMapper objectMapper = new ObjectMapper();
        List<ObjectNode> list = new ArrayList<>();
        Integer managerId = Integer.valueOf(jwtTokenProvider.getUserId(jwtTokenProvider.extractToken(request)));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<Campaign> listOfCampaign = managerService.getCampaignListByManagerId(managerId);
        for(int i = 0; i < listOfCampaign.size(); i++){
            Campaign campaign = listOfCampaign.get(i);
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("CampaignID", campaign.getId());
            objectNode.put("CampaignName", campaign.getCampaignName());
            objectNode.put("StartDate", format.format(campaign.getStartDate()));
            objectNode.put("EndDate", format.format(campaign.getEndDate()));
            list.add(objectNode);
        }
        ArrayNode arrayNode = objectMapper.valueToTree(list);
        return new ResponseBean<>(HttpStatus.OK, Constants.SUCCESS_MSG, arrayNode);
    }

    @PostMapping("/manager/DeleteCampaign")
    public ResponseBean deleteCampaign(@RequestBody Map<String, String> campaignIdMap){
        String campaignIdStr = campaignIdMap.get("campaignID");
        Integer campaignId = Integer.valueOf(campaignIdStr);
        managerService.deleteCampaign(campaignId);
        return new ResponseBean(HttpStatus.OK, "/manager_DeleteCampaign");
    }

    @PostMapping("/manager/EditCampaign")
    public ResponseBean editCampaign(@RequestBody Map<String, Object> map) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Integer campaignId = Integer.valueOf(String.valueOf(map.get("campaignID")));
        String campaignName = String.valueOf(map.get("campaignName"));
        Date startDate = simpleDateFormat.parse(String.valueOf(map.get("startDate")));
        Date endDate = simpleDateFormat.parse(String.valueOf(map.get("endDate")));
        Double visitDuration = Double.valueOf(String.valueOf(map.get("visitDuration")));
        String talkingPoints = String.valueOf(map.get("talkingPoints"));
        String questions = String.valueOf(map.get("questions"));
        String locations = String.valueOf(map.get("locations"));
        List<String> managers = (List<String>) map.get("managers");
        List<String> canvassers = (List<String>) map.get("canvassers");

        if(campaignName == null ||
                startDate == null ||
                endDate == null ||
                String.valueOf(map.get("visitDuration")) == null||
                talkingPoints == null ||
                questions == null ||
                locations == null ||
                managers.size() == 0){
            return new ResponseBean(HttpStatus.BAD_REQUEST, Constants.FIELDEMPTY);
        }

        List<Location> locationList = locationService.retrieveLocations(locations);
        List<User> canvassersList = userService.retrieveUsers(canvassers);
        List<User> managersList = userService.retrieveUsers(managers);
        List<Question> questionsList = questionService.retrieveQuestions(questions);


        Campaign newCampaign = new Campaign();
        newCampaign.setId(campaignId);
        newCampaign.setCampaignName(campaignName);
        newCampaign.setStartDate(startDate);
        newCampaign.setEndDate(endDate);
        newCampaign.setVisitDuration(visitDuration);
        newCampaign.setTalkingPoints(talkingPoints);
        newCampaign.setLocations(locationList);
        newCampaign.setCanvassers(canvassersList);
        newCampaign.setManagers(managersList);
        newCampaign.setQuestions(questionsList);

        //recovery canvasser free day before discarding task
        managerService.recoveryUserFreeday(newCampaign.getId());
        managerService.discardTask(newCampaign.getId());
        managerService.updateCampaign(newCampaign);
        Boolean isAllAssigned = taskService.createCavassingAssignment(newCampaign.getId(), canvassersList);
        return new ResponseBean(HttpStatus.OK, "/manager_EditCampaign");
    }

    @PostMapping("/manager/CreateCampaign_LoadCanvasserAccordingToDate")
    public ResponseBean<ArrayNode> loadCanvasserAccordingToDate(@RequestBody Map<String, String> dateMap) throws ParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateStr = dateMap.get("startDate");
        String endDateStr = dateMap.get("endDate");

        if(startDateStr == null || endDateStr == null){
            return new ResponseBean<>(HttpStatus.OK, "");
        }

        Date startDate = simpleDateFormat.parse(startDateStr);
        Date endDate = simpleDateFormat.parse(endDateStr);



        if(startDate.compareTo(endDate) > 0){
            return new ResponseBean<>(HttpStatus.BAD_REQUEST, "Start date should be prior then end date");
        }
        HashSet<String> canvassersOption = managerService.getAvailableCanvassersByStartDateEndDate(startDate, endDate);
        ArrayNode arrayNode = objectMapper.valueToTree(canvassersOption);
        return new ResponseBean<>(HttpStatus.OK, "/manager_EditCampaign_LoadCanvasserAccordingToDate", arrayNode);



    }


    @PostMapping("/manager/EditCampaign_LoadCanvasserAccordingToDate")
    public ResponseBean<ArrayNode> loadCanvasserAccordingToDateEditCampaign(@RequestBody Map<String, String> dateMap) throws ParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateStr = dateMap.get("startDate");
        String endDateStr = dateMap.get("endDate");
        Date startDate = simpleDateFormat.parse(startDateStr);
        Date endDate = simpleDateFormat.parse(endDateStr);

        if(startDateStr == null || endDateStr == null){
            return new ResponseBean<>(HttpStatus.OK, "");
        }

        if(startDate.compareTo(endDate) > 0){
            return new ResponseBean<>(HttpStatus.BAD_REQUEST, "Start date should be prior then end date");
        }
        HashSet<String> canvassersOption = managerService.getAvailableCanvassersByStartDateEndDate(startDate, endDate);
        ArrayNode arrayNode = objectMapper.valueToTree(canvassersOption);
        return new ResponseBean<>(HttpStatus.OK, "/manager_EditCampaign_LoadCanvasserAccordingToDate", arrayNode);
    }


    @GetMapping("/manager/CreateCampaign_LoadManager")
    public ResponseBean<ArrayNode> loadManager(){
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.valueToTree(userService.retrieveUsersString(managerService.getManagers()));
        return new ResponseBean<>(HttpStatus.OK, "/manager_CreateCampaign_LoadManager", arrayNode);
    }

    @PostMapping("/manager/GetCampaignLocationsList")
    public ResponseBean<ArrayNode> getCampaignLocationsList(@RequestBody Map<String, String> campaignIdMap) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ObjectNode> list = new ArrayList<>();
        String campaignIdStr = campaignIdMap.get("campaignID");
        Integer campaignId = Integer.valueOf(campaignIdStr);
        Campaign campaign = managerService.getCampaignById(campaignId);
        List<Location> locations = campaign.getLocations();
        char label = 'A';
        for(int i = 0; i < locations.size(); i++){
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("LocationID", locations.get(i).getId());
            objectNode.put("lat", locations.get(i).getLatitude());
            objectNode.put("lng", locations.get(i).getLongitude());
            objectNode.put("label", String.valueOf(label));
            objectNode.put("Info", locations.get(i).toString());
            list.add(objectNode);
            label++;
        }
        ArrayNode arrayNode = objectMapper.valueToTree(list);
        return new ResponseBean<>(HttpStatus.OK, "/manager_GetCampaignLocationsList", arrayNode);
    }

    //todo we have result after canvassers all finishing their tasks.
    @PostMapping("/manager/GetCampaignStatisticalResult")
    public ResponseBean<ObjectNode> getCampaignStatisticalResult(@RequestBody Map<String, String> campaignIdMap){
        ObjectMapper objectMapper = new ObjectMapper();
        String campaignIdStr = campaignIdMap.get("campaignID");
        Integer campaignId = Integer.valueOf(campaignIdStr);
        Statistic statistic = managerService.summary(campaignId);
        ObjectNode objectNode = objectMapper.createObjectNode();
        DecimalFormat df2 = new DecimalFormat(".##");
        Double mean = statistic.getMean();
        Double standev = statistic.getStdev();

        objectNode.put("AverageOfRating", df2.format(mean));
        objectNode.put("StandardDeviationOfRating", df2.format(standev));
        return new ResponseBean<>(HttpStatus.OK, "/manager_GetCampaignStatisticalResult", objectNode);
    }

    @PostMapping("/manager/GetCampaignLocationResult")
    public ResponseBean<ArrayNode> getCampaignLocationResult(@RequestBody Map<String, String> campaignIdMap){
        ObjectMapper objectMapper = new ObjectMapper();
        List<ObjectNode> list = new ArrayList<>();
        String campaignIdStr = campaignIdMap.get("campaignID");
        Integer campaignId = Integer.valueOf(campaignIdStr);
        Campaign campaign = managerService.getCampaignById(campaignId);
        List<Location> locations = campaign.getLocations();
        for(Location location: locations){
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("LocationID", String.valueOf(location.getId()));
            objectNode.put("LocationName", location.getNumber() +" "+ location.getStreet());
            objectNode.put("lat", location.getLatitude());
            objectNode.put("lng", location.getLongitude());
            objectNode.put("Info", location.toString());
            if(taskMapper.getLocationStatus(location.getId()) == true){
                objectNode.put("Status", "Finish");
                Result result = resultMapper.getResultByLocation(location.getId());
                objectNode.put("Rating", result.getRating());
                objectNode.put("label", String.valueOf(result.getRating()));
            }else {
                objectNode.put("Status", "Pending");
                objectNode.put("Rating", 0);
                objectNode.put("label", "");
            }
            list.add(objectNode);
        }
        ArrayNode arrayNode = objectMapper.valueToTree(list);
        return new ResponseBean<>(HttpStatus.OK, "/manager/GetCampaignLocationResult", arrayNode);
    }

    @PostMapping("/manager/GetCampaignQuestionResult")
    public ResponseBean<ArrayNode> getCampaignQuestionResult(@RequestBody Map<String, String> campaignIdMap){
        ObjectMapper objectMapper = new ObjectMapper();
        List<ObjectNode> list = new ArrayList<>();
        String campaignIdStr = campaignIdMap.get("campaignID");
        Integer campaignId = Integer.valueOf(campaignIdStr);
        Campaign campaign = managerService.getCampaignById(campaignId);
        List<Question> questions = campaign.getQuestions();
        for(int i = 0; i < questions.size(); i++){
            Integer questionId= questions.get(i).getId();
            List<Double> percentageList = managerService.percentageYes(campaignId, questionId);
            double yesPercentage = percentageList.get(0);
            double noPercentage = percentageList.get(1);
            double giveupPercentage = percentageList.get(2);

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("Question", questions.get(i).getContent());
            objectNode.put("Yes", yesPercentage);
            objectNode.put("No", noPercentage);
            objectNode.put("GiveUp", giveupPercentage);
            list.add(objectNode);
        }
        ArrayNode arrayNode = objectMapper.valueToTree(list);
        return new ResponseBean<>(HttpStatus.OK, "/manager/GetCampaignQuestionResult", arrayNode);
    }



    @PostMapping("/manager/GetAssignmentList")
    public ResponseBean<ArrayNode> getAssignmentList(@RequestBody Map<String, String> campaignIdMap){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ObjectMapper objectMapper = new ObjectMapper();
        List<ObjectNode> list = new ArrayList<>();
        String campaignIdStr = campaignIdMap.get("campaignID");
        Integer campaignId = Integer.valueOf(campaignIdStr);
        List<Task> tasks = managerService.getTasksByCampaignId(campaignId);
        Campaign campaign = managerService.getCampaignById(campaignId);
        for(int i = 0; i < tasks.size(); i++){
            List<Integer> locationsInTask = taskMapper.getLocationList(tasks.get(i).getId());
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("CampaignID", campaignIdStr);
            objectNode.put("AssignmentID", tasks.get(i).getId());
            objectNode.put("AssignmentName", tasks.get(i).getTaskName());
            if(tasks.get(i).getDate() != null){
                objectNode.put("AssignmentDate", format.format(tasks.get(i).getDate()));
            }else {
                objectNode.put("AssignmentDate", "");
            }

            if(tasks.get(i).getCanvasserId() != null){
                objectNode.put("Canvasser", userService.getUserById(tasks.get(i).getCanvasserId()).getUsername());
            }else{
                objectNode.put("Canvasser", "");
            }

            objectNode.put("LocationNumber", String.valueOf(locationsInTask.size()));
            objectNode.put("Duration", String.valueOf(tasks.get(i).getDuration()));

            objectNode.put("StartDate", format.format(campaign.getStartDate()));
            objectNode.put("EndDate", format.format(campaign.getEndDate()));
            list.add(objectNode);
        }
        ArrayNode arrayNode = objectMapper.valueToTree(list);
        return new ResponseBean<>(HttpStatus.OK, "/manager_GetAssignmentList", arrayNode);
    }

    @PostMapping("/manager/GetAssignmentInfo")
    public ResponseBean<ObjectNode> getAssignmentInfo(@RequestBody Map<String, String> assignmentIdMap){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ObjectMapper objectMapper = new ObjectMapper();
        String taskIdstr = assignmentIdMap.get("assignmentID");
        Integer taskId = Integer.valueOf(taskIdstr);
        Task task = managerService.getTask(taskId);
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("AssignmentID", task.getId().toString());


        if(task.getDate() != null){
            objectNode.put("AssignmentDate", format.format(task.getDate()));
        }else{
            objectNode.put("AssignmentDate", "");
        }


        List<Location> locations = task.getLocations();
        ArrayNode arrayNode = objectMapper.createArrayNode();
        objectNode.put("Locations", managerService.printLocations(locations));

        if(task.getCanvasserId() != null){
            objectNode.put("Canvasser", userService.getUserById(task.getCanvasserId()).getUsername());
        }else {
            objectNode.put("Canvasser", "");
        }

        objectNode.put("CampaignID", String.valueOf(task.getCampaignId()));

        Campaign campaign = managerService.getCampaignById(task.getCampaignId());
        objectNode.put("CampaignName", campaign.getCampaignName());
        objectNode.put("TalkingPoints", campaign.getTalkingPoints());
        List<Question> questions = campaign.getQuestions();
        objectNode.put("Questions", managerService.printQuestions(questions));

        objectNode.put("VisitDuration", String.valueOf(task.getDuration()));
        return new ResponseBean<>(HttpStatus.OK, "/manager_GetAssignmentInfo", objectNode);
    }

    @PostMapping("/manager/EditAssignmentWindow")
    public ResponseBean<ObjectNode> editAssignmentWindow(@RequestBody Map<String, String> assignmentIdMap){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ObjectMapper objectMapper = new ObjectMapper();
        String taskIdstr = assignmentIdMap.get("assignmentID");
        Integer taskId = Integer.valueOf(taskIdstr);
        Task task = managerService.getTask(taskId);
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("CampaignID", String.valueOf(task.getCampaignId()));
        objectNode.put("AssignmentID", String.valueOf(task.getId()));
        if(task.getDate() == null){
            objectNode.put("AssignmentDate", "");
        }else {
            objectNode.put("AssignmentDate", format.format(task.getDate()));
        }

        Integer canvasserId = task.getCanvasserId();
        if(canvasserId == null){
            objectNode.put("Canvasser","");
            objectNode.put("CanvassersOption", "");
        }else{
            User canvasser = userService.getUserById(canvasserId);
            objectNode.put("Canvasser", canvasser.getUsername());
            List<User> canvassers = managerService.getAvailableCanvassers(task.getDate());
            List<String> firstNames = new ArrayList<>();
            for(User u: canvassers){
                firstNames.add(u.getUsername());
            }
            firstNames.add(canvasser.getUsername());
            ArrayNode arrayNode = objectMapper.valueToTree(firstNames);
            objectNode.putArray("CanvassersOption").addAll(arrayNode);
        }




        return new ResponseBean<>(HttpStatus.OK, "/manager/EditAssignmentWindow", objectNode);
    }

    @PostMapping("/manager/EditAssignment")
    public ResponseBean editAssignment(@RequestBody Map<String, String> assignmentMap) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String taskIdstr = assignmentMap.get("assignmentID");
        Integer taskId = Integer.valueOf(taskIdstr);
        String canvasserUsername = assignmentMap.get("canvasser");
        Task task = managerService.getTask(taskId);
        Integer campaignId = task.getCampaignId();
        String date = assignmentMap.get("assignmentDate");
        Date selectDate = null;
        if(date != null){
            selectDate = format.parse(date);
        }

        Integer currentCanvasserId = task.getCanvasserId();

        User canvasser = userMapper.getUserByUsername(canvasserUsername);
        Integer newCanvasserId = canvasser.getId();

        Date today = new Date();

        if( date != null && today.compareTo(selectDate) > 0){
            return new ResponseBean<>(HttpStatus.BAD_REQUEST, Constants.CAMPAIGN_STARTED);
        }

        if(currentCanvasserId == newCanvasserId){
            return new ResponseBean(HttpStatus.OK, "");
        }

        if(date != null && currentCanvasserId != null){
            managerService.updateTaskCanvasser(taskId,newCanvasserId, selectDate);
            managerService.recoveryUserFreedayOne(selectDate, currentCanvasserId);
            managerService.addCampaignCanvasser(campaignId, newCanvasserId);
            managerService.deleteCampaignCanvasser(campaignId, currentCanvasserId);
        }else {
            managerService.updateTaskCanvasser(taskId, newCanvasserId, selectDate);
            managerService.addCampaignCanvasser(campaignId, newCanvasserId);
        }

        return new ResponseBean(HttpStatus.OK,"/manager/EditAssignment");
    }

    @PostMapping("/manager/EditAssignment_LoadCanvasserAccordingToDate")
    public ResponseBean EditAssignment_LoadCanvasserAccordingToDate(@RequestBody Map<String, String> assignmentMap) throws ParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = assignmentMap.get("date");
        Date selectDate = format.parse(date);

        HashSet<String> canvassersOption = managerService.getAvailableCanvassersByStartDateEndDate(selectDate, selectDate);
        ArrayNode arrayNode = objectMapper.valueToTree(canvassersOption);
        return new ResponseBean<>(HttpStatus.OK, "/manager/EditAssignment_LoadCanvasserAccordingToDate", arrayNode);
    }



    @PostMapping("/manager/GetAssignmentAllLocation")
    public ResponseBean<ArrayNode> getAssignmentAllLocation(@RequestBody Map<String, String> assignmentIdMap){
        ObjectMapper objectMapper = new ObjectMapper();
        List<ObjectNode> list = new ArrayList<>();
        String taskIdstr = assignmentIdMap.get("assignmentID");
        Integer taskId = Integer.valueOf(taskIdstr);
        Task task = managerService.getTask(taskId);
        Integer campaignId = task.getCampaignId();
        List<Location> locations = task.getLocations();
        char label = 'A';
        for(int i = 0; i < locations.size(); i++){
            Location location = locations.get(i);
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("LocationID", String.valueOf(location.getId()));
            objectNode.put("LocationName", location.getNumber() +" "+ location.getStreet());
            objectNode.put("AssignmentID", taskIdstr);
            objectNode.put("CampaignID", String.valueOf(campaignId));
            if(canvasserService.getStatus(location.getId()) == true){
                objectNode.put("Status", "Finish");
            }else{
                objectNode.put("Status", "Pending");
            }

            objectNode.put("lat", location.getLatitude());
            objectNode.put("lng", location.getLongitude());
            objectNode.put("label", String.valueOf(label));
            objectNode.put("Info", location.toString());
            list.add(objectNode);
            label++;
        }
        ArrayNode arrayNode = objectMapper.valueToTree(list);
        return new ResponseBean<>(HttpStatus.OK, "/manager_GetAssignmentLocation", arrayNode);
    }




//    @GetMapping("/getCampaignList/{id}")
//    public ResponseBean<List<Campaign>> getCampaignsByManagerId(@PathVariable Integer id ){
//        List<Campaign> campaignList = managerService.getCampaignListByManagerId(id);
//        if(campaignList != null)
//            return new ResponseBean(HttpStatus.OK, Constants.SUCCESS_MSG, managerService.getCampaignListByManagerId(id));
//        else
//            return new ResponseBean(HttpStatus.NOT_FOUND, Constants.FAILURE_MSG);
//    }




//    @GetMapping("/getCampaign/{id}")
//    public ResponseBean<Campaign> getCampaignByCampaignId(@PathVariable Integer id){
//        return new ResponseBean(HttpStatus.OK, Constants.SUCCESS_MSG, managerService.getCampaignById(id));
//    }

    @PostMapping("/Array")
    public ResponseBean testing(){
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        ArrayNode an = objectMapper.createArrayNode();
        an.add("this");
        an.add("that");
        objectNode.put("Name", an);

        return new ResponseBean(HttpStatus.OK, Constants.SUCCESS_MSG, objectNode);

    }


 
}
