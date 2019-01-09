package com.supercanvasser.service;

import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.Skills;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.solution.route.VehicleRoute;
import com.graphhopper.jsprit.core.problem.solution.route.activity.TourActivity;
import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import com.graphhopper.jsprit.core.util.*;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.supercanvasser.bean.Campaign;
import com.supercanvasser.bean.Task;
import com.supercanvasser.bean.User;
import com.supercanvasser.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

@org.springframework.stereotype.Service
public class TaskService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GlobalVariablesMapper globalVariablesMapper;

    @Autowired
    private CampaignMapper campaignMapper;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private ManagerService managerService;


    private List<VehicleRoute> tasks;




    public boolean createCavassingAssignment(Integer campaignId, List<User> canvassers){
        Campaign campaign = campaignMapper.getCampaignById(campaignId);
        campaign.setId(campaignId);
        tasks = runCanvassAlgorithm(campaign);
        boolean b = matchCanvasserAndTask(campaign,tasks, canvassers);
        return b;//return if there is any unassigned tasks. false == has unassigned task.
    }

    //locationsInRoute is list of locations needs to visit next
    public List<Integer>  reComputeFastestRoute(Integer selectedStartLocationId, List<Integer> restlocationsInRoute){
        com.supercanvasser.bean.Location selectedStartLocation = locationMapper.getLocationById(selectedStartLocationId);
        Double selectedLatitude = selectedStartLocation.getLatitude();
        Double selectedLongitude = selectedStartLocation.getLongitude();

        //build the car for a canvasser to travel
        VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();
        VehicleTypeImpl vehicleType =  VehicleTypeImpl.Builder.newInstance("vehicleType")
                .setCostPerDistance(1.0)
                .build();
        VehicleImpl vehicle = VehicleImpl.Builder.newInstance("vehicle")
                .setStartLocation(Location.newInstance(selectedLatitude, selectedLongitude))
                .setType(vehicleType).build();
        vrpBuilder.addVehicle(vehicle);



        for(int i = 0;i < restlocationsInRoute.size(); i++){
            Double latitude = locationMapper.getLatitude(restlocationsInRoute.get(i));
            Double longtitude = locationMapper.getLongitude(restlocationsInRoute.get(i));
            Service service = Service.Builder.newInstance(restlocationsInRoute.get(i)+"")
                    .setLocation(Location.newInstance(latitude,longtitude))
                    .build();//x:latitude y: longtitude
            vrpBuilder.addJob(service);
        }

        vrpBuilder.setFleetSize(VehicleRoutingProblem.FleetSize.FINITE);
        VehicleRoutingTransportCostsMatrix costMatrix = createMatrix(vrpBuilder);
        vrpBuilder.setRoutingCost(costMatrix);
        VehicleRoutingProblem problem = vrpBuilder.build();
        VehicleRoutingAlgorithm algorithm = Jsprit.createAlgorithm(problem);
        Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();
        VehicleRoutingProblemSolution bestSolution = Solutions.bestOf(solutions);


        SolutionPrinter.print(problem, bestSolution, SolutionPrinter.Print.VERBOSE);

        List<VehicleRoute> tasks = new ArrayList<>(bestSolution.getRoutes());
        Collections.sort(tasks, new VehicleIndexComparator());

        return getTaskLocationList(tasks.get(0));

    }




    //This method create multiple tasks from locations in Campaign.
    private List<VehicleRoute> runCanvassAlgorithm(Campaign campaign){
        //we don't actually have the orgin.  We assume canvasser starts the work day at the first location in the assigned task.
        Double orginLatitude = 0.0;
        Double orgignLongitude = 0.0;


        Integer campaignId = campaign.getId();
        Double averageDuration = campaign.getVisitDuration();
        int averageDurationMinutes = (int)(averageDuration*60);
        Double workDayDurationHour = globalVariablesMapper.getDuration();
        int workDayDurationMinutes = (int)(workDayDurationHour * 60);

        VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();

        //build vehicle for  canvasser
        VehicleTypeImpl vehicleType =  VehicleTypeImpl.Builder.newInstance("vehicleType")
                .setCostPerDistance(1.0)
                .addCapacityDimension(0,workDayDurationMinutes).build();
        VehicleImpl vehicle = VehicleImpl.Builder.newInstance("vehicle")
                .setStartLocation(Location.newInstance(orginLatitude, orgignLongitude))
                .setType(vehicleType).build();
        vrpBuilder.addVehicle(vehicle);

        //build services
        List<Integer> locationIds = campaignMapper.getLocationList(campaignId);
        for(int i = 0;i < locationIds.size(); i++){
            Double latitude = locationMapper.getLatitude(locationIds.get(i));
            Double longtitude = locationMapper.getLongitude(locationIds.get(i));
            Service service = Service.Builder.newInstance(locationIds.get(i)+"")
                    .setServiceTime(averageDurationMinutes)//average duration per location and convert it to minutes
                    .setLocation(Location.newInstance(latitude,longtitude))
                    .addSizeDimension(0,averageDurationMinutes).build();//x:latitude y: longtitude
            vrpBuilder.addJob(service);
        }

        vrpBuilder.setFleetSize(VehicleRoutingProblem.FleetSize.INFINITE);
        VehicleRoutingTransportCostsMatrix costMatrix = createMatrix(vrpBuilder);
        vrpBuilder.setRoutingCost(costMatrix);
        VehicleRoutingProblem problem = vrpBuilder.build();
        VehicleRoutingAlgorithm algorithm = Jsprit.createAlgorithm(problem);
        //algorithm.setMaxIterations(5000);
        Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();
        VehicleRoutingProblemSolution bestSolution = Solutions.bestOf(solutions);


        SolutionPrinter.print(problem, bestSolution, SolutionPrinter.Print.VERBOSE);

        List<VehicleRoute> tasks = new ArrayList<>(bestSolution.getRoutes());
        Collections.sort(tasks, new VehicleIndexComparator());

        return tasks;
    }

    private VehicleRoutingTransportCostsMatrix createMatrix(VehicleRoutingProblem.Builder vrpBuilder) {
        Double averageSpeed = globalVariablesMapper.getAverageSpeed();
        VehicleRoutingTransportCostsMatrix.Builder matrixBuilder = VehicleRoutingTransportCostsMatrix.Builder.newInstance(true);
        for (String from : vrpBuilder.getLocationMap().keySet()) {
            for (String to : vrpBuilder.getLocationMap().keySet()) {
                Coordinate fromCoord = vrpBuilder.getLocationMap().get(from);
                Coordinate toCoord = vrpBuilder.getLocationMap().get(to);
                if(fromCoord.getX() == 0 && fromCoord.getY() == 0){
                    matrixBuilder.addTransportDistance(from, to, 0);
                    matrixBuilder.addTransportTime(from, to,0);//average speed
                }else{
                    double distance = ManhattanDistanceCalculator.calculateDistance(fromCoord, toCoord);
                    matrixBuilder.addTransportDistance(from, to, distance);
                    matrixBuilder.addTransportTime(from, to, (distance / averageSpeed)*60);//average speed
                }

            }
        }
        return matrixBuilder.build();
    }


    public List<Date> getCampaignDates(Date startDate, Date endDate){
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> dates = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        while (calendar.getTime().before(endDate))
        {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        dates.add(endDate);
        return dates;
    }

    private boolean matchCanvasserAndTask(Campaign campaign, List<VehicleRoute> tasks, List<User> canvassers){
        Integer totalTaskSize = tasks.size();
        Integer foundTaskCount = 0;

        Integer campaignId = campaign.getId();
        Date startDate = campaign.getStartDate();
        Date endDate = campaign.getEndDate();
        Double averageDuration = campaign.getVisitDuration();
        List<Date> campaignDates = getCampaignDates(startDate, endDate);
        List<Integer> foundCanvasserIds = new ArrayList<>();

        for(User user: canvassers){
            for(int i = 0; i < campaignDates.size(); i++) {

                if(foundTaskCount == totalTaskSize){
                    break;
                }

                Date date = campaignDates.get(i);
                List<Date> canvasserFreedays = user.getFreeDays();
                if(canvasserFreedays.contains(date)){
                    Integer foundCanvasserId = user.getId();
                    VehicleRoute taskRoute = tasks.get(foundTaskCount);
                    foundTaskCount++;
                    foundCanvasserIds.add(foundCanvasserId);
                    Task task = new Task();
                    task.setDate(date);
                    task.setDuration(averageDuration);
                    task.setCampaignId(campaignId);
                    List<Integer> taskLocationIdList = getTaskLocationList(taskRoute);
                    List<com.supercanvasser.bean.Location> locations = new ArrayList<>();
                    for(Integer id : taskLocationIdList){
                        locations.add(locationMapper.getLocationById(id));
                    }
                    task.setLocations(locations);
                    task.setCanvasserId(foundCanvasserId);
                    managerService.addTask(task);

                    campaignMapper.addCanvasserId(campaignId, foundCanvasserId);
                    userMapper.deleteFreeDayByDateUserId(date, foundCanvasserId);
                }
            }
//
//            for(int j = 0; j < tasks.size(); j++){
//                Integer foundCanvasserId = userMapper.getCanvasserByFreeDay(date);
//                if(foundCanvasserId != null && foundTaskCount <= totalTaskSize){
//                    VehicleRoute taskRoute = tasks.get(foundTaskCount);
//                    foundTaskCount++;
//
//                    foundCanvasserIds.add(foundCanvasserId);
//                    Task task = new Task();
//                    task.setCanvasserId(foundCanvasserId);
//                    task.setDate(date);
//                    task.setDuration(averageDuration);
//                    task.setCampaignId(campaignId);
//                    List<Integer> taskLocationIdList = getTaskLocationList(taskRoute);
//                    List<com.supercanvasser.bean.Location> locations = new ArrayList<>();
//                    for(Integer id : taskLocationIdList){
//                        locations.add(locationMapper.getLocationById(id));
//                    }
//                    task.setLocations(locations);
//                    managerService.addTask(task);
//                    campaignMapper.addCanvasserId(campaignId, foundCanvasserId);
//
//                    //delete canvasser free day as they are assigned a task.
//                    userMapper.deleteFreeDayByDateUserId(date, foundCanvasserId);
//                }else break;
//            }
        }


        if(foundCanvasserIds.size() == totalTaskSize){
            return true;
        }else {
            while(foundTaskCount < totalTaskSize){
                VehicleRoute taskRoute = tasks.get(foundTaskCount);
                Task task = new Task();
                task.setDuration(averageDuration);
                task.setCampaignId(campaignId);
                List<Integer> taskLocationIdList = getTaskLocationList(taskRoute);
                List<com.supercanvasser.bean.Location> locations = new ArrayList<>();
                for(Integer id : taskLocationIdList){
                    locations.add(locationMapper.getLocationById(id));
                }
                task.setLocations(locations);
                managerService.addTask(task);
                foundTaskCount++;
            }
            return false;
        }
    }

    private List<Integer> getTaskLocationList(VehicleRoute task){
        List<Integer> taskLocations = new ArrayList<>();
        TourActivity act;
        for(Iterator locSerivce = task.getActivities().iterator(); locSerivce.hasNext(); ){
            act = (TourActivity) locSerivce.next();
            String jobId;
            if (act instanceof TourActivity.JobActivity) {
                jobId = ((TourActivity.JobActivity)act).getJob().getId();
                taskLocations.add(Integer.valueOf(jobId));
            }
        }
        return taskLocations;
    }

}
