package com.supercanvasser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.supercanvasser.bean.*;
import com.supercanvasser.constant.Constants;
import com.supercanvasser.security.JWTTokenProvider;
import com.supercanvasser.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author Zhenquan Ma
 * @date 11/25/2018
 */
@RestController
@RequestMapping("/canvasser")
public class CanvasserController extends BaseController{

    @Autowired
    TaskService taskService;

    @Autowired
    CanvasserService canvasserService;

    @Autowired
    ManagerService managerService;

    @Autowired
    JWTTokenProvider jwtTokenProvider;

    private final Logger logger = LoggerFactory.getLogger(CanvasserController.class);

    /**
     * Get all free days of a canvasser
     * @param request
     * @return
     */
    @GetMapping("/LoadCalendarEventList")
    public ResponseBean<List<String>> getFreeDays(HttpServletRequest request){
        String token = jwtTokenProvider.extractToken(request);
        Integer id = Integer.valueOf(jwtTokenProvider.getUserId(token));
        List<String> freeDays = userService.getFormattedFreeDay(id);
        if(freeDays == null)
            return new ResponseBean<>(HttpStatus.OK, Constants.NOT_FOUND_MSG);
        logger.info("Getting free days");
        return new ResponseBean<>(HttpStatus.OK, "/canvasser/LoadCalendarEventList", freeDays);
    }


    /**
     * Add a single free day for a canvasser
     * @param request
     * @return
     */
    @PostMapping("/CalendarAddEvent")
    public ResponseBean addFreeDay(@RequestBody Map<String, String> dateMap, HttpServletRequest request){
        String token = jwtTokenProvider.extractToken(request);
        Integer id = Integer.valueOf(jwtTokenProvider.getUserId(token));
        Date date;
        SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd yyyy", Locale.US);
        try{
            date = df.parse(dateMap.get("date"));
        } catch (ParseException e) {
            e.printStackTrace();
            return new ResponseBean(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        List<Task> allTasks = canvasserService.getAllTasks(id);
        boolean flag = false;
        for(Task task : allTasks){
            Date taskDate = task.getDate();
            if(df.format(taskDate).equals(df.format(date)))
                flag = true;
            if(flag){
                return new ResponseBean(HttpStatus.BAD_REQUEST, Constants.INVALID_PARAMETER);
            }
        }
        userService.addFreeDay(id, date);
        logger.info("Adding free day");
        return new ResponseBean(HttpStatus.OK, "/canvasser/CalendarAddEvent");
    }


    /**
     * Delete a single free day for a canvasser
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/CalendarRemoveEvent")
    public ResponseBean deleteFreeDay(@RequestBody Map<String, String> json, HttpServletRequest request){
        String token = jwtTokenProvider.extractToken(request);
        Integer id = Integer.valueOf(jwtTokenProvider.getUserId(token));
        String date = json.get("date");
        SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd yyyy", Locale.US);
        try {
            userService.deleteFreeDay(id, df.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return new ResponseBean(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        logger.info("deleting free day");
        return new ResponseBean(HttpStatus.OK, "/canvasser/CalendarRemoveEvent");
    }


    /**
     * Get the today's task info
     * @param request
     * @return
     */
    @GetMapping("/LoadCurrentAssignmentInfo")
    public ResponseBean<ObjectNode> getCurrentTaskInfo(HttpServletRequest request){
        String token = jwtTokenProvider.extractToken(request);
        Integer id = Integer.valueOf(jwtTokenProvider.getUserId(token));

        Date today = new Date();
        Task task = canvasserService.getTaskInfo(id, today);
        logger.info("Getting current task");
        if(task != null){
            Integer campaignId = task.getCampaignId();
            Campaign campaign = managerService.getCampaignById(campaignId);
            String talkingPoints = campaign.getTalkingPoints();
            List<Question> questions = campaign.getQuestions();
            Integer taskId = task.getId();
            Double duration = task.getDuration();
            Date taskDate = task.getDate();
            String firstName = userService.getUserById(task.getCanvasserId()).getFirstName();

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode data = objectMapper.createObjectNode();
            data.put("CampaignID", campaignId);
            data.put("CampaignName", campaign.getCampaignName());
            data.put("TalkingPoints", talkingPoints);
            ArrayNode arrayNode = objectMapper.valueToTree(questions);
            data.putArray("Questions").addAll(arrayNode);
            data.put("AssignmentID", taskId);
            data.put("VisitDuration", duration);
            data.put("AssignmentDate", canvasserService.getFormattedDateString(taskDate));
            data.put("Canvasser", firstName);
            return new ResponseBean<>(HttpStatus.OK, "/canvasser/LoadCurrentAssignmentInfo", data);
        }
        return new ResponseBean<>(HttpStatus.OK, Constants.NOT_FOUND_MSG);
    }


    @PostMapping("/LoadCurrentLocation")
    public ResponseBean<ObjectNode> getCurrentLocation(@RequestBody Map<String, String> taskIdJson, HttpServletRequest request){
        String token = jwtTokenProvider.extractToken(request);
        Integer canvasserId = Integer.valueOf(jwtTokenProvider.getUserId(token));

        String taskId = taskIdJson.get("assignmentID");
        if(taskId == null)
            return new ResponseBean<>(HttpStatus.OK, Constants.NOT_FOUND_MSG);
        Location currentLocation = canvasserService.getCurrentLocation(Integer.valueOf(taskId), canvasserId);
        if(currentLocation == null){
            return new ResponseBean<>(HttpStatus.OK, Constants.NOT_FOUND_MSG);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode data = objectMapper.createObjectNode();
        data.put("LocationID", currentLocation.getId());
        data.put("lat", currentLocation.getLatitude());
        data.put("lng", currentLocation.getLongitude());
        data.put("Info", currentLocation.toString());
        data.put("label", "Current location");
        data.put("LocationName", currentLocation.toString());
        logger.info("Getting current location");
        return new ResponseBean<>(HttpStatus.OK, "/canvasser/LoadCurrentLocation", data);
    }


    @PostMapping("/LoadNextLocation")
    public ResponseBean<ObjectNode> getNextLocation(@RequestBody Map<String, String> taskIdJson, HttpServletRequest request){
        String token = jwtTokenProvider.extractToken(request);
        Integer canvasserId = Integer.valueOf(jwtTokenProvider.getUserId(token));

        String taskId = taskIdJson.get("assignmentID");
        if(taskId == null)
            return new ResponseBean<>(HttpStatus.OK, Constants.NOT_FOUND_MSG);

        Location nextLocation = canvasserService.getNextLocation(Integer.valueOf(taskId), canvasserId);
        if(nextLocation == null)
            return  new ResponseBean<>(HttpStatus.OK, Constants.NOT_FOUND_MSG);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode data = objectMapper.createObjectNode();
        data.put("LocationID", nextLocation.getId());
        data.put("lat", nextLocation.getLatitude());
        data.put("lng", nextLocation.getLongitude());
        data.put("Info", nextLocation.toString());
        data.put("label", "Current location");
        data.put("LocationName", nextLocation.toString());
        logger.info("Getting next location");
        return new ResponseBean<>(HttpStatus.OK, "/canvasser/LoadNextLocation", data);
    }


    @PostMapping("/LoadCurrentAssignmentLocationList")
    public ResponseBean<ArrayNode> getTaskAllLocations(@RequestBody Map<String, String> taskIdJson){
        String taskId = taskIdJson.get("assignmentID");
        if(taskId == null)
            return new ResponseBean<>(HttpStatus.OK, Constants.NOT_FOUND_MSG);
        List<Location> locationList = canvasserService.getAllLocations(Integer.valueOf(taskId));

        ObjectMapper objectMapper = new ObjectMapper();
        List<ObjectNode> objects = new ArrayList<>();
        for(int i = 0; i < locationList.size(); i++){
            ObjectNode objectNode = objectMapper.createObjectNode();
            Location location = locationList.get(i);
            objectNode.put("LocationID", location.getId());
            objectNode.put("lat", location.getLatitude());
            objectNode.put("lng", location.getLongitude());
            objectNode.put("label", String.valueOf(i + 1));
            objectNode.put("Info", location.toString());
            objectNode.put("AssignmentID", taskId);
            objectNode.put("LocationName", location.toString());
            if(canvasserService.getStatus(location.getId()))
                objectNode.put("Status", "Finished");
            else
                objectNode.put("Status", "Unfinished");
            objects.add(objectNode);
        }
        ArrayNode arrayNode = objectMapper.valueToTree(objects);
        logger.info("Getting all locations of task {}", taskId);
        return new ResponseBean<>(HttpStatus.OK, "/canvasser/LoadCurrentAssignmentLocationList", arrayNode);
    }


    @PostMapping("/LoadLocationPreInfo")
    public ResponseBean<ObjectNode> getResult(@RequestBody Map<String, String> idsJson){
        Integer campaignId = Integer.valueOf(idsJson.get("campaignID"));
        if(campaignId == null)
            return new ResponseBean<>(HttpStatus.OK, Constants.NOT_FOUND_MSG);
        List<Question> questions = canvasserService.getQuestions(campaignId);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode data = objectMapper.createObjectNode();
        data.put("TalkingPoints", canvasserService.getTalkingPoint(campaignId));
        ArrayNode arrayNode = objectMapper.valueToTree(questions);
        data.putArray("Questions").addAll(arrayNode);
        logger.info("Getting empty result");
        return new ResponseBean<>(HttpStatus.OK, "/canvasser/LoadLocationPreInfo", data);
    }


    @PostMapping("/PostLocationResult")
    public ResponseBean<ObjectNode> addResult(@RequestBody Map<String, Object> resultJson, HttpServletRequest request){
        String token = jwtTokenProvider.extractToken(request);
        Integer canvasserId = Integer.valueOf(jwtTokenProvider.getUserId(token));

        Integer currentLocationId = Integer.valueOf(String.valueOf(resultJson.get("locationID")));
        Integer taskId = Integer.valueOf(String.valueOf(resultJson.get("assignmentID")));
        Task task = canvasserService.getTaskInfo(taskId);
        Integer campaignId = task.getCampaignId();
        List<Location> unvisitedLocations = task.getUnvisitedLocation();
        if(unvisitedLocations == null)
            return new ResponseBean<>(HttpStatus.OK, Constants.NOT_FOUND_MSG);

        //All unvisited locations excluding current location
        List<Integer> unvisitedLocationIds = new ArrayList<>();
        for(Location location : unvisitedLocations){
            if(location.getId() != currentLocationId){
                unvisitedLocationIds.add(location.getId());
            }
        }

        Boolean isSpeak = null;
        String speakAnswer = String.valueOf(resultJson.get("speakAnswer"));
        switch (speakAnswer){
            case "A":
                isSpeak = true;
                break;
            case "B":
                isSpeak = false;
                break;
        }
        Integer rating = Integer.valueOf(String.valueOf(resultJson.get("rating")));
        String note = String.valueOf(resultJson.get("note"));
        List questionsList = (ArrayList) resultJson.get("questions");

        //Current location
        Location location = canvasserService.getLocation(currentLocationId);

        Result result = new Result();
        result.setSpoke(isSpeak);
        result.setRating(rating);
        result.setBriefNote(note);
        result.setLocation(location);

        List<QuestionAnswer> questionAnswerList = new ArrayList<>();
        for(int i = 0; i < questionsList.size(); i++){
            Map<String, Object> answerMap = (LinkedHashMap)questionsList.get(i);

            Integer questionId = (Integer) answerMap.get("QuestionId");
            String answerStr = (String) answerMap.get("Answer");
            Boolean answer = null;
            switch(answerStr){
                case "A":
                    answer = true;
                    break;
                case "B":
                    answer = false;
                    break;
                case "C":
                    answer = null;
                    break;
            }
            QuestionAnswer questionAnswer = new QuestionAnswer();
            questionAnswer.setQuestion(canvasserService.getAQuestion(questionId));
            questionAnswer.setAnswer(answer);
            questionAnswer.setLocation(location);
            questionAnswerList.add(questionAnswer);
        }
        result.setQuestionAnswers(questionAnswerList);
        canvasserService.addResult(campaignId, taskId, canvasserId, currentLocationId, result);

        logger.info("Adding result for location {}", currentLocationId);
        return new ResponseBean<>(HttpStatus.OK, "/canvasser/PostLocationResult");
    }



    @GetMapping("/LoadUpcomingAssignmentList")
    public ResponseBean<ArrayNode> getUpcomingTasks(HttpServletRequest request){
        String token = jwtTokenProvider.extractToken(request);
        Integer canvasserId = Integer.valueOf(jwtTokenProvider.getUserId(token));

        Date today = new Date();
        List<Task> upcomingTasks = canvasserService.getUpcomingTasks(canvasserId, today);

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.createArrayNode();
        if(upcomingTasks != null){
            char taskLabel = 'A';
            for(Task task : upcomingTasks){
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("AssignmentID", task.getId());
                objectNode.put("AssignmentDate", canvasserService.getFormattedDateString(task.getDate()));

                List<Location> locations = canvasserService.getAllLocations(task.getId());
                List<ObjectNode> locationNodes = new ArrayList<>();
                if(locations != null){
                    for(int i = 0; i < locations.size(); i++){
                        ObjectNode node = objectMapper.createObjectNode();
                        Location location = locations.get(i);
                        node.put("LocationID", location.getId());
                        node.put("lat", location.getLatitude());
                        node.put("lng", location.getLongitude());
                        String label = new StringBuilder().append(taskLabel).append(String.valueOf(i + 1)).toString();
                        node.put("label", label);
                        node.put("Info", location.toString());
                        node.put("AssignmentID", task.getId());
                        locationNodes.add(node);
                    }
                    ArrayNode locationArr = objectMapper.valueToTree(locationNodes);
                    objectNode.put("LocationNumber", locations.size());
                    objectNode.put("VisitDuration", canvasserService.getVisitDuration(task.getCampaignId()));
                    objectNode.putArray("Locations").addAll(locationArr);
                    arrayNode.add(objectNode);
                }
                taskLabel++;
            }
            logger.info("Getting upcoming tasks for canvasser {}", canvasserId);
            return new ResponseBean<>(HttpStatus.OK, "/canvasser/LoadUpcomingAssignmentList", arrayNode);
        }
        return new ResponseBean<>(HttpStatus.OK, Constants.NOT_FOUND_MSG);
    }


    @GetMapping("/LoadUpcomingAssignmentToMap")
    public ResponseBean<ArrayNode> getUpcomingTasksLocations(HttpServletRequest request) {
        String token = jwtTokenProvider.extractToken(request);
        Integer canvasserId = Integer.valueOf(jwtTokenProvider.getUserId(token));

        Date today = new Date();
        List<Task> upcomingTasks = canvasserService.getUpcomingTasks(canvasserId, today);
        if (upcomingTasks == null)
            return new ResponseBean<>(HttpStatus.OK, Constants.NOT_FOUND_MSG);

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.createArrayNode();
        for (Task task : upcomingTasks) {
            List<Location> upcomingLocations = canvasserService.getAllLocations(task.getId());

            char taskLabel = 'A';
            if (upcomingLocations != null) {
                for (int i = 0; i < upcomingLocations.size(); i++) {
                    Location location = upcomingLocations.get(i);
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("LocationID", location.getId());
                    node.put("lat", location.getLatitude());
                    node.put("lng", location.getLongitude());
                    String label = new StringBuilder().append(taskLabel).append(String.valueOf(i + 1)).toString();
                    node.put("label", label);
                    node.put("Info", location.toString());
                    node.put("AssignmentID", task.getId());
                    arrayNode.add(node);
                }
            }
        }

        logger.info("Getting next locations");
        return new ResponseBean<>(HttpStatus.OK, "/canvasser/LoadUpcomingAssignmentToMap", arrayNode);
    }

}
