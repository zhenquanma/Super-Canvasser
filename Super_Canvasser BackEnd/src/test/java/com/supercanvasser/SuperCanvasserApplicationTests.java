package com.supercanvasser;

import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.problem.Skills;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.solution.route.VehicleRoute;
import com.graphhopper.jsprit.core.problem.solution.route.activity.TourActivity;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import com.graphhopper.jsprit.core.util.*;
import com.supercanvasser.bean.Campaign;
import com.supercanvasser.bean.Location;
import com.supercanvasser.bean.Task;
import com.supercanvasser.mapper.CampaignMapper;
import com.supercanvasser.mapper.GlobalVariablesMapper;
import com.supercanvasser.mapper.LocationMapper;
import com.supercanvasser.mapper.UserMapper;
import com.supercanvasser.service.*;
import com.supercanvasser.bean.*;
import com.supercanvasser.constant.Constants;
import com.supercanvasser.mapper.*;
import com.supercanvasser.service.LocationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SuperCanvasserApplicationTests {

    @Autowired
    GlobalVariablesMapper gvm;

    @Autowired
    UserMapper um;

    @Autowired
    CampaignMapper cm;

    @Autowired
    LocationMapper lm;

    @Autowired
    ManagerService ms;

    @Autowired
    UserService us;

    @Autowired
    LocationService locationService;

    @Autowired
    QuestionMapper questionMapper;


    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    CanvasserService canvasserService;

    @Autowired
    TaskService taskService;

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    QuestionService questionService;

    Logger logger = LoggerFactory.getLogger(SuperCanvasserApplicationTests.class);

    @Test
    public void contextLoads() {
    }


    /**
     * Update the global variables(duration and average speed)
     * for system administrator update global variables functionality
     */
    @Test
    public void globalVariableTest(){
        Double duration = gvm.getDuration();
        logger.info("Duration: {}" , duration);

        Double averageSpeed = gvm.getAverageSpeed();
        logger.info("Average Speed:{}", averageSpeed);

        gvm.updateDuration(8.0);
        Double newDuration = gvm.getDuration();
        logger.info("New Duration: {}", newDuration);

        gvm.updateAverageSpeed(88.0);
        Double newAverageSpeed = gvm.getAverageSpeed();
        logger.info("New Average Speed: {}" , newAverageSpeed);

    }

    /**
     * add,delete and update user.
     * for system administrator edit user
     */
    @Test
    public void userTest(){
//        User user = new User(null,"ma","chen","zhifchen","1234","abc@123.com",null,null,null);
//        us.addUser(user);
        us.deleteUser(9);

//        User user2 = new User(9,"feng","chen","zhifchen","1234","abc@123.com",null,null,null);
//        us.updateUser(user2);
    }



    @Test
    public void campaignMapperTest(){
        //String talkingpoints = "Hello, I am Zhenquan Ma";

        //Campaign campaign = new Campaign(1, null, new Date(), new Date(), 2.5, talkingpoints, null, null, null, null);

        //cm.addCampaign(campaign);
        //cm.updateCampaign(campaign);

        //System.out.println(cm.getCampaignById(1));
        //System.out.println(cm.getCampaignById(2));
        cm.deleteCampaign(1);
    }

    @Test
    public void campaignMapperSubListTest(){
        cm.addLocationId(1, 2);
        cm.addLocationId(1,2);
        List<Integer> list = cm.getLocationList(1);
        System.out.println(list);
        cm.addCanvasserId(1,2);
        cm.addManagerId(1,1);
        cm.addQuestionId(1, 1);
        cm.deleteLocationByTwoId(1,2);
        cm.deleteCanvasserByTwoId(1,2);
        cm.deleteManagerByTwoId(1,1);
        cm.deleteQuestionByTwoId(1, 1);

    }


    @Test
    public void locationMapperTest(){
        lm.getLocationById(1);
        Location newLocation = new Location(null, 1111, "Stony Brook Rd", null, "Stony Brook", "NY", 11790);
        lm.addLocation(newLocation);

        lm.getLocationById(2);
        lm.deleteLocation(1);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Canvasser

    @Test
    public void addFreeDayTest(){
        Date now = new Date();
        Calendar cald = Calendar.getInstance();
        cald.setTime(now);
        cald.add(Calendar.DATE, 1);
        Date tomorrow = cald.getTime();
        um.addFreeDay(now, 2);
        um.addFreeDay(tomorrow, 2);
        List<Date> dates = um.getFreeDaysByCanvasserId(2);

        logger.info("Free days: {}", dates);

    }

    @Test
    public void viewAllFreeDaysTest(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> dates = um.getFreeDaysByCanvasserId(2);

        //logger.info("Free days: {}", dates);
        for(int i = 0; i < dates.size(); i++){
            logger.info("Free day: {}", format.format(dates.get(i)));
        }
    }


//    @Test
//    public void deleteFreeDayTest(){
//        um.deleteFreeDay(1);
//    }

//    @Test
//    public void addResultTest(){
//        Result result = new Result();
//        result.setLocation(lm.getLocationById(62));
//        result.setBriefNote("Good Morning");
//        result.setRating(6);
//        result.setSpoke(true);
//        List<QuestionAnswer> questionAnswers = new ArrayList<>();
//        questionAnswers.add(new QuestionAnswer(questionMapper.getQuestionById(56), lm.getLocationById(62), true));
//        result.setQuestionAnswers(questionAnswers);
//        canvasserService.addResult(30, result);
//    }

    @Test
    public void getAnswerTest(){
        Boolean answer = questionMapper.getAnswerByTwoId(56, 62);
        logger.info("Answer: {}", answer);
        Assert.assertTrue(answer);
    }

    @Test
    public void getResultTest(){
        Result result = ms.getResultById(23);
        logger.info("Result: {}", result);
    }

    @Test
    public void statiticTest(){
        Statistic statistic = ms.summary(30);
        logger.info("Mean: {}, Stdev: {}", statistic.getMean(), statistic.getStdev());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Manager
    @Test
    public void locationTest(){
        String location = "277, Bedford Avenue, Brooklyn, NY, 11211";
        Map<String, Double> latlng = locationService.getLatlng(location);
        logger.info("Location:{ longitude: {}, latitude: {}}", latlng.get(Constants.LONGITUDE), latlng.get(Constants.LATITUDE));
    }


    /**
     * Create a new campaign and insert it into the database
     * Delete this campaign
     * update this campaign
     * For manager create, view update functionalities
     * See change in database
     */
    @Test
    public void campaignManageTest(){
        Campaign campaign = new Campaign();
        Date now = new Date();
        Calendar cald = Calendar.getInstance();
        cald.setTime(now);
        cald.add(Calendar.DATE, 1);
        Date tomorrow = cald.getTime();
        campaign.setStartDate(now);
        campaign.setEndDate(tomorrow);

        campaign.setManagers(Arrays.asList(um.getUserById(1)));
        campaign.setCanvassers(Arrays.asList(um.getUserById(2)));

        String addr = "100, Nicolls Rd, Stony Brook, NY, 11794\n600, Circle Rd, Stony Brook, NY, 11790";
        List<Location> locationList = locationService.retrieveLocations(addr);
        campaign.setLocations(locationList);

        String str1 = "Are you Ok?";
        String str2 = "How are you?";
        Question q1 = new Question();
        Question q2 = new Question();

        q1.setContent(str1);
        q2.setContent(str2);
        campaign.setQuestions(Arrays.asList(q1, q2));

        campaign.setTalkingPoints("FreeStyle");
        campaign.setVisitDuration(1.0);
        ms.addCampaign(campaign);
        logger.info("Add campaign with id {}: {}", campaign.getId(), ms.getCampaignById(campaign.getId()));

//        campaign.setManagers(Arrays.asList(um.getUserById(1), um.getUserById(6)));
//        campaign.setCanvassers(Arrays.asList(um.getUserById(2), um.getUserById(7)));
//        ms.updateCampaign(campaign);
//        logger.info("Update campaign with id {}: {}", campaign.getId(), ms.getCampaignById(campaign.getId()));
//
//        ms.deleteCampaign(campaign.getId());
//        Assert.assertNull(ms.getCampaignById(campaign.getId()));
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Administrator
//    @Test
//    public void changeUserRoleTest(){
//        UserRole userRole = userRoleMapper.getByUserId(6);
//        Role role = roleMapper.getRoleById(userRole.getRoleId());
//        logger.info("User id: 6, Role before change: {}", role.getName());
//        userRoleMapper.updateUserRole(6, 1);
//        userRole = userRoleMapper.getByUserId(6);
//        role = roleMapper.getRoleById(userRole.getRoleId());
//
//        logger.info("User id: 6, Role after changed: {}", role.getName());
//
//    }
//
//        logger.info("User id: 6, Role after changed: {}", role.getName());
//    }




//    @Test
//    public void createTask(){
//
//        Task task = new Task();
//        List<Integer> locationList = new ArrayList<Integer>();
//        locationList.add(62);
//        locationList.add(63);
//        Integer canvasserId = 2;
//        Integer campaignId = 30;
//        Date today = new Date();
//        Double duration = 2.5;
//        task.setLocationList(locationList);
//        task.setCanvasserId(canvasserId);
//        task.setCampaignId(campaignId);
//        task.setDate(today);
//        task.setDuration(duration);
//
//        logger.info("Created Task: " + task.toString());
//        ms.addTask(task);
//    }

    @Test
    public void getTask(){
        Task returnTask = ms.getTask(5);
        logger.info("return Task: " + returnTask.toString());
    }

    @Test
    public void deleteTask(){
        ms.deleteTask(5);
    }


    @Test
    public void sometest(){
        List<Integer> canvasserIds = us.getUserIdsByRoleId(2);
        System.out.println(canvasserIds.get(0));
        System.out.println(canvasserIds.get(1));
    }

    @Test
    public void algorithmTest(){
        VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();

        //build vehicles for each canvasser
//        List<Integer> canvasserIds = new ArrayList<>();
//        canvasserIds.add(1);
//        canvasserIds.add(2);
//        canvasserIds.add(3);
//        for(int i = 0; i < canvasserIds.size(); i++){
//            VehicleTypeImpl vehicleType =  VehicleTypeImpl.Builder.newInstance(canvasserIds.get(i) + "_type")
//                                            .setCostPerDistance(1.0)
//                                            .addCapacityDimension(0,8).build();//8=global maximum duration of task
//            VehicleImpl vehicle = VehicleImpl.Builder.newInstance(canvasserIds.get(i) + "_vehicle")
//                                    .setStartLocation(com.graphhopper.jsprit.core.problem.Location.newInstance(0, 0))
//                                    .setType(vehicleType).build();
//            vrpBuilder.addVehicle(vehicle);
//        }
        Skills.Builder skillsBuilder = Skills.Builder.newInstance();
        skillsBuilder.addSkill("12");
        Skills skills = skillsBuilder.build();

        //canvasserIds.add(4);
        VehicleTypeImpl vehicleType = VehicleTypeImpl.Builder.newInstance(4+"_type")
                                        .setCostPerDistance(1.0)
                                        .addCapacityDimension(0,8).build();
        VehicleImpl vehicle = VehicleImpl.Builder.newInstance(4+"_vehicle")
                                .setStartLocation(com.graphhopper.jsprit.core.problem.Location.newInstance(0,0))
                                .setType(vehicleType).build();
        vrpBuilder.addVehicle(vehicle);



        //build service
        Service service = Service.Builder.newInstance("" + 1)
                .setServiceTime(2)
                .setLocation(com.graphhopper.jsprit.core.problem.Location.newInstance(0.06,0.01)).addSizeDimension(0,2).build();//x:latitude y: longtitude
        vrpBuilder.addJob(service);
        Service service2 = Service.Builder.newInstance("" + 2)
                .setServiceTime(2)
                .setLocation(com.graphhopper.jsprit.core.problem.Location.newInstance(0.06,0.02)).addSizeDimension(0,2).build();//x:latitude y: longtitude
        vrpBuilder.addJob(service2);
        Service service3 = Service.Builder.newInstance("" + 3)
                .setServiceTime(2)
                .setLocation(com.graphhopper.jsprit.core.problem.Location.newInstance(0.08,0)).addSizeDimension(0,2).build();//x:latitude y: longtitude
        vrpBuilder.addJob(service3);
        Service service4 = Service.Builder.newInstance("" + 4)
                .setServiceTime(2)
                .setLocation(com.graphhopper.jsprit.core.problem.Location.newInstance(0.07,0.03)).addSizeDimension(0,2).build();//x:latitude y: longtitude
        vrpBuilder.addJob(service4);
//        Service service5 = Service.Builder.newInstance("" + 5)
//                .setLocation(com.graphhopper.jsprit.core.problem.Location.newInstance(50,20)).addSizeDimension(0,2).build();//x:latitude y: longtitude
//        vrpBuilder.addJob(service5);
//        Service service6 = Service.Builder.newInstance("" + 6)
//                .setLocation(com.graphhopper.jsprit.core.problem.Location.newInstance(70,20)).addSizeDimension(0,2).build();//x:latitude y: longtitude
//        vrpBuilder.addJob(service6);
//        Service service7 = Service.Builder.newInstance("" + 7)
//                .setLocation(com.graphhopper.jsprit.core.problem.Location.newInstance(30,30)).addSizeDimension(0,2).build();//x:latitude y: longtitude
//        vrpBuilder.addJob(service7);
//        Service service8 = Service.Builder.newInstance("" + 8)
//                .setLocation(com.graphhopper.jsprit.core.problem.Location.newInstance(60,30)).addSizeDimension(0,2).build();//x:latitude y: longtitude
//        vrpBuilder.addJob(service8);
//        Service service9 = Service.Builder.newInstance("" + 9)
//                .setLocation(com.graphhopper.jsprit.core.problem.Location.newInstance(50,50)).addSizeDimension(0,2).build();//x:latitude y: longtitude
//        vrpBuilder.addJob(service9);
//        Service service10 = Service.Builder.newInstance("" + 10)
//                .setLocation(com.graphhopper.jsprit.core.problem.Location.newInstance(80,50)).addSizeDimension(0,2).build();//x:latitude y: longtitude
//        vrpBuilder.addJob(service10);
//        Service service11 = Service.Builder.newInstance("" + 11)
//                .setLocation(com.graphhopper.jsprit.core.problem.Location.newInstance(10,60)).addSizeDimension(0,2).build();//x:latitude y: longtitude
//        vrpBuilder.addJob(service11);
//        Service service12 = Service.Builder.newInstance("" + 12)
//                .setLocation(com.graphhopper.jsprit.core.problem.Location.newInstance(20,60)).addSizeDimension(0,2).build();//x:latitude y: longtitude
//        vrpBuilder.addJob(service12);
//        Service service13 = Service.Builder.newInstance("" + 13)
//                .setLocation(com.graphhopper.jsprit.core.problem.Location.newInstance(30,70)).addSizeDimension(0,2).build();//x:latitude y: longtitude
//        vrpBuilder.addJob(service13);
//        Service service14 = Service.Builder.newInstance("" + 14)
//                .setLocation(com.graphhopper.jsprit.core.problem.Location.newInstance(60,70)).addSizeDimension(0,2).build();//x:latitude y: longtitude
//        vrpBuilder.addJob(service14);
//        Service service15 = Service.Builder.newInstance("" + 15)
//                .setLocation(com.graphhopper.jsprit.core.problem.Location.newInstance(0,80)).addSizeDimension(0,2).build();//x:latitude y: longtitude
//        vrpBuilder.addJob(service15);
//        Service service16 = Service.Builder.newInstance("" + 16)
//                .setLocation(com.graphhopper.jsprit.core.problem.Location.newInstance(70,80)).addSizeDimension(0,2).build();//x:latitude y: longtitude
//        vrpBuilder.addJob(service16);


        vrpBuilder.setFleetSize(VehicleRoutingProblem.FleetSize.FINITE);
        VehicleRoutingTransportCostsMatrix costMatrix = createMatrix(vrpBuilder);
        vrpBuilder.setRoutingCost(costMatrix);

        VehicleRoutingProblem vrp = vrpBuilder.build();

        VehicleRoutingAlgorithm vra = Jsprit.Builder.newInstance(vrp).buildAlgorithm();

        Collection<VehicleRoutingProblemSolution> solutions = vra.searchSolutions();
        VehicleRoutingProblemSolution bestSolution = Solutions.bestOf(solutions);
        SolutionPrinter.print(vrp, bestSolution, SolutionPrinter.Print.VERBOSE);

        List<VehicleRoute> tasks = new ArrayList<>(bestSolution.getRoutes());
        Collections.sort(tasks, new VehicleIndexComparator());
//        for(Iterator task = tasks.iterator(); task.hasNext();){
//            VehicleRoute route = (VehicleRoute) task.next();
//            TourActivity act;
//            for(Iterator locSerivce = route.getActivities().iterator(); locSerivce.hasNext(); ){
//                act = (TourActivity) locSerivce.next();
//                String jobId;
//                if (act instanceof TourActivity.JobActivity) {
//                    jobId = ((TourActivity.JobActivity)act).getJob().getId();
//                    System.out.println(jobId);
//                }
//
//            }
//        }
        VehicleRoute route = tasks.get(0);
        TourActivity act;
        for(Iterator locSerivce = route.getActivities().iterator(); locSerivce.hasNext(); ){
            act = (TourActivity) locSerivce.next();
            String jobId;
            if (act instanceof TourActivity.JobActivity) {
                jobId = ((TourActivity.JobActivity)act).getJob().getId();
                System.out.println(jobId);
            }

        }


    }

    private  VehicleRoutingTransportCostsMatrix createMatrix(VehicleRoutingProblem.Builder vrpBuilder) {
        VehicleRoutingTransportCostsMatrix.Builder matrixBuilder = VehicleRoutingTransportCostsMatrix.Builder.newInstance(true);
        for (String from : vrpBuilder.getLocationMap().keySet()) {
            for (String to : vrpBuilder.getLocationMap().keySet()) {
                Coordinate fromCoord = vrpBuilder.getLocationMap().get(from);
                Coordinate toCoord = vrpBuilder.getLocationMap().get(to);
                double distance = ManhattanDistanceCalculator.calculateDistance(fromCoord, toCoord);
                matrixBuilder.addTransportDistance(from, to, distance);
                matrixBuilder.addTransportTime(from, to, (distance / 41));//average speed
            }
        }
        return matrixBuilder.build();
    }

    @Test
    public void getCampaignDatesTest(){
        Date startDate = new GregorianCalendar(2018, Calendar.NOVEMBER, 5).getTime();
        Date endDate = new GregorianCalendar(2018, Calendar.NOVEMBER, 5).getTime();
        List<Date> dateList = taskService.getCampaignDates(startDate,endDate);
        System.out.println(dateList);
    }

    @Test
    public void getCanvasserByFreeDayTest(){
        Integer canvasserId = um.getCanvasserByFreeDay(new GregorianCalendar(2018, Calendar.OCTOBER, 11).getTime());
        System.out.println(canvasserId);
    }

    @Test
    public void deleteFreeDayByDateUserIdTest(){
        um.deleteFreeDayByDateUserId(new GregorianCalendar(2018, Calendar.NOVEMBER, 13).getTime(), 2);
    }

    @Test
    public void createCavassingAssignmentTest(){
        User u1 = um.getUserById(2);
        User u2 = um.getUserById(7);
        List<User> canvassers = new ArrayList<User>();
        canvassers.add(u1);
        canvassers.add(u2);
        Boolean b = taskService.createCavassingAssignment(31, canvassers);
        System.out.println(b);
    }

//    @Test
//    public void getCanvassingListTest(){
//        List<Integer> list = taskMapper.getLocationList(40);
//        System.out.println(list);
//    }

    @Test
    public void reComputeFastestRouteTest(){
        List<Integer> list = new ArrayList<>();
        list.add(111);
        list.add(112);
        list.add(113);
        list.add(114);
        list.add(118);

        List<Integer> returnList = taskService.reComputeFastestRoute(77, list);
        System.out.println(returnList);
    }

    @Test
    public void retrieveQuestionsTest(){
        String question = "do you build a snow man\n" +
                            "do you build a snow man 2\n" +
                            "do you build a snow man 3";
        System.out.println(questionService.retrieveQuestions(question));
    }

    @Test
    public void getAvailableCanvassersByStartDateEndDateTest(){
        Date startDate = new GregorianCalendar(2018, Calendar.NOVEMBER, 11).getTime();
        Date endDate = new GregorianCalendar(2018, Calendar.NOVEMBER, 13).getTime();
        HashSet<String> canvassers = ms.getAvailableCanvassersByStartDateEndDate(startDate, endDate);
        System.out.println(canvassers);
    }

    @Test
    public void getLocationStatusTest(){
        Boolean b = taskMapper.getLocationStatus(62);
        System.out.println(b);

    }



}
