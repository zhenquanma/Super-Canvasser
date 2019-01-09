var express = require('express')
var app = express()
bodyParser = require('body-parser')
app.use(bodyParser.json())



app.all('*', function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept,Authorization");
    res.header("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
    res.header("X-Powered-By",' 3.2.1')
    res.header("Content-Type", "application/json;charset=utf-8");
    res.setHeader("Access-Control-Expose-Headers","Authorization")
    next();
});



//post
//{Username:"",
// Password:""}

app.post("/login", function (req, res) {
    var loginJson={
        status:"OK",
        msg:"login sucessful",
        data:{

                id: 1,
                userName: "admin test",
                token: "fake-jwt-token",
                role:["Manager","Canvasser","Admin"]

        }
    }
    console.log("get /login");

 
    res.setHeader("Authorization","1234567")

    res.send(loginJson)
})














// admin user operation
// {firstName:"",
// lastName: "",
// userName: "",
//password:"",
// role:""}
app.post("/admin/PostUser", function(req, res){
    console.log("post /admin_PostUser")

    
var postUserInfoJson={
    status:'OK',
    msg:'post UserSuccessful',
    data:''
}
    res.send(postUserInfoJson)
})






app.get("/admin/GetUserList", function(req, res){
    console.log("get /admin_GetUserList")

var getUserListJson={
    status:'OK',
    msg: 'get Campaign InfoList Successful',
    data:[{
        UserID:"1",
        FirstName:"Nice",
        LastName:"Fish",
        UserName:"trump",
        Role:["Admin","Manager"]
    },{
        UserID:"2",
        FirstName:"Nicole",
        LastName:"song",
        UserName:"niconico",
        Role:["Manager"]
    },{
        UserID:"3",
        FirstName:"cindy",
        LastName:"lu",
        UserName:"cindyLu",
        Role:["Canvasser","Admin"]
    }]
}
    res.send(getUserListJson)
})


// {userID: "",
// firstName:"",
// lastName: "",
// userName: "",
//password:"",
// role:""}

app.post("/admin/EditUser", function(req, res){
    console.log("post /admin_EditUser")
    var tempJson={
        status:'OK',
        msg: 'edit user',
        data:""
    }
    res.send(tempJson)
})



//{userId:""}
app.post("/admin/EditUserLoadWindow", function(req,res){
    console.log("post /admin_EditUserLoadWindow")
    var tempJson={
        status:'OK',
        msg: 'load edit user window',
        data:{
            UserID:"1",
            FirstName:"Nice",
            LastName:"Fish",
            UserName:"trump",
            Password:"123445",
            Role:["Admin","Canvasser"]
        }  
    }
    res.send(tempJson)
})





//{userID:""}

app.post("/admin/DeleteUser",function(req, res){
    console.log("post /admin_DeleteUser")

var deleteUserJson={
    status:'OK',
    msg:'delete User Successful',
    data:''   
}
    res.send(deleteUserJson)    
})









///////admin setting
//{maxDuration:""}
app.post("/admin/EditMaxDuration",function(req, res){
    console.log("post /editMaxDuration")

var tempJson={
    status:'OK',
    msg:'edit maxduration',
    data:''   
}
    res.send(tempJson)    
})

//{aveSpeed:""}
app.post("/admin/EditAveSpeed",function(req, res){
    console.log("post /editAveSpeed")

var tempJson={
    status:'OK',
    msg:'edit AveSpeed',
    data:''   
}
    res.send(tempJson)    
})



app.get("/admin/GetAveSpeed",function(req, res){
    console.log("get /getAveSpeed")

var tempJson={
    status:'OK',
    msg:'get AveSpeed',
    data:'989'   
}
    res.send(tempJson)    
})


app.get("/admin/GetMaxDuration",function(req, res){
    console.log("get /getMaxDuration")

var tempJson={
    status:'OK',
    msg:'get MaxDuration',
    data:'967544'   
}
    res.send(tempJson)    
})
//manager campaign




//use to get camoaign all infomation
//post {CampaignID:""}


app.post("/manager/GetCampaignInfo", function (req, res) {
    var getCampaignInfoJson={
        status:'OK',
        msg:'/manager_GetCampaignInfo',
        data:{
            CampaignID:"1",
            CampaignName:"c1",
            TalkingPoints:"666",
            VisitDuration:"4444",
            StartDate:"2018-03-11",
            EndDate:"2018-04-09",
            Managers:["sdfdf","1234","qwe"],
            Canvassers:["dfffdfd","dffe"],
            Questions:["2222","3333"],
            Locations:["dfdf","dfert"]
        }
    }
    console.log("post /manager_GetCampaignInfo");
    res.send(getCampaignInfoJson)
})



//use to get edit campaign window pre information
//post  {CampaignID:CampaignID}
app.post("/manager/EditCampaignWindow", function (req, res) {
    var editCampaignWindowJson={
        status:'OK',
        msg:'/manager_EditCampaignWindow',
        data:{
            CampaignID:"1",
            CampaignName:"c1",
            TalkingPoints:"666",
 
            VisitDuration:"4444",
            StartDate:"2018-03-11",
            EndDate:"2018-04-09",

            Questions:["1111","ddd"],
            Locations:["1111","dfdf"],
            Managers:["abc","efg","zxc"],
            Canvassers:["123","456","789"],
            ManagersOption:["plplp","abc","efg","zxc","opl","098"],
            CanvassersOption:["123","asdd","456","dfss","789"]

        }
    }
    console.log("post /manager_EditCampaignWindow");
    res.send(editCampaignWindowJson)
})


//use to get current manager all campaign
//get

app.get("/manager/GetCampaignList", function (req, res) {
    var getCampaignListJson={
        status:'OK',
        msg:'/manager_GetCampaignList',
        data:[{
            CampaignID:1,
            CampaignName:"c1",
            StartDate:"2018-03-11",
            EndDate:"2018-03-11"
        },{
            CampaignID:2,
            CampaignName:"c2",
            StartDate:"2018-03-11",
            EndDate:"2018-03-11"
        },{
            CampaignID:3,
            CampaignName:"c3",
            StartDate:"2018-03-11",//date type later
            EndDate:"2018-03-11"
        }]
    }
    console.log("get /manager_GetCampaignList");
    res.send(getCampaignListJson)
})




//use to create new campaign
//post

// {      CampaignName:,
//     StartDate:,
//     EndDate:,
//     Managers:,
//     Canvassers:,
//     TalkingPoints:,
//     Questions:,
//     Locations:,
//     VisitDuration:
// }
app.post("/manager/PostCampaign", function (req, res) {
    console.log(req.body)

var postCampaignJson={
    status:'OK',
    msg:'/manager_PostCampaign',
    data:''
}
    console.log("post /manager_PostCampaign");
    //console.log(req.body);
    //console.log(req.headers)
    res.send(postCampaignJson)
})






//use to delete campaign
//post  {CampaignID:""}
app.post("/manager/DeleteCampaign", function (req, res) {

var deleteCampaignJson={
    status:'OK',
    msg:'/manager_DeleteCampaign',
    data:''
}
    console.log("post /manager_DeleteCampaign");
    res.send(deleteCampaignJson)
})




//use to edit current campaign
//post
// {
// CampaignID:"",
// CampaignName:"",
// StartDate:"",
// EndDate:"",
// Managers:"",
// Canvassers:"",
// TalkingPoints:"",
// Questions:"",
// Locations:"",
// VisitDuration:""
//}

app.post("/manager/EditCampaign", function (req, res) {
    console.log(req.body)

var editCampaignJson={
    status:'OK',
    msg:'/manager_EditCampaign',
    data:''
}
    console.log("post /manager_EditCampaign");
    res.send(editCampaignJson);
})



//字符start date

//use to load different canvasser for create campaign according to the date, 
//post

// {
      
//     StartDate:"",
//     EndDate:"",

//    }

app.post("/manager/CreateCampaign_LoadCanvasserAccordingToDate",function(req,res){

    console.log(req.body)
    console.log("post /manager_CreateCampaign_LoadCanvasserAccordingToDate")

    var createCampaignLoadCanvasserAccordingToDateJson={
        status:'OK',
        msg:'/manager_CreateCampaign_LoadCanvasserAccordingToDate',
        data:["create","1","2"]
    }
    res.send(createCampaignLoadCanvasserAccordingToDateJson)

})




// use to load manager when create campaign
//get
app.get("/manager/CreateCampaign_LoadManager",function(req,res){
    console.log("get /manager_CreateCampaign_LoadManager")
    var tempjson={
        status:'OK',
        msg:'/manager_CreateCampaign_LoadManager',
        data:["create1","create2","create3"]

    }

    res.send(tempjson)
})



//use to load canvasser for edit campaign according to date
//post

// {
//     
//     StartDate:"",
//     EndDate:"",

//    }
app.post("/manager/EditCampaign_LoadCanvasserAccordingToDate",function(req,res){
    console.log(req.body)
    console.log("post /manager_EditCampaign_LoadCanvasserAccordingToDate")

    var tempJson={
        status:'OK',
        msg:'/manager_EditCampaign_LoadCanvasserAccordingToDate',
        data:["edit1","edit2","edit3"]
    }
    res.send(tempJson)

})



//view campaign
//use to get one campaign all location simple information
//post
//{CampaignID:""}
app.post("/manager/GetCampaignLocationsList",function(req,res){

    console.log("post /manager_GetCampaignLocationsList")

    var tempJson={
        status:'OK',
        msg:'/manager_GetCampaignLocationsList',
        data:[{
            LocationID:"123",
            lat: 51.673858,
            lng: 7.815982,
            label: "A",
            Info:"stony brook",


        },{
            LocationID:"123",
            lat: 51.373858,
            lng: 7.215982,
            label: "B",
            Info:"stony brook",


        },{
            LocationID:"123",
            lat: 51.723858,
            lng: 7.895982,
            label: "C",
            Info:"stony brook",


        }]
    }

    res.send(tempJson)
})









//campaign result

//use to get campaign statistical result
//post
//{CampaignID:""}
// manager/GetCampaignStatisticalResult 
app.post("/manager/GetCampaignStatisticalResult", function (req, res) {
    console.log("post /manager_GetCampaignStatisticalResult");
    //console.log(req.body);
    //console.log(req.headers)

    var getCampaignStatisticalResultJson={
        status:'OK',
        msg:'/manager_GetCampaignStatisticalResult',
        data:{
            AverageOfRating:2.3,
            StandardDeviationOfRating:3.4

        }
    }
    res.send(getCampaignStatisticalResultJson)
})



//use to get campaign each location result
//post
//{CampaignID:""}
app.post("/manager/GetCampaignLocationResult", function (req, res) {
    console.log("post /manager_GetCampaignLocationResult");
    //console.log(req.body);


    var getCampaignLocationResultJson={
        status:'OK',
        msg:'/manager/GetCampaignLocationResult',
        data:[{
            LocationID:"123",
            LocationName:"1233333",
            Status:"finish",
            Rating:4,
            lat: 51.673858,
            lng: 7.815982,
            label: 'A',
            Info:"A"

        },{
            LocationID:"123",
            LocationName:"sdfdfdfdfd",
            Status:"finish",
            Rating:2,
            lat: 51.723858,
            lng: 7.895982,
            label: "C",
            Info:"stony brook",

        }]
    }
    res.send(getCampaignLocationResultJson)
})



//use to get campaign question percent result
//post
//{CampaignID:""}

app.post("/manager/GetCampaignQuestionResult", function (req, res) {
    console.log("post /manager_GetCampaignQuestionResult");
    //console.log(req.body);


    var getCampaignQuestionResultJson={
        status:'OK',
        msg:'/manager_GetCampaignQuestionResult',
        data:[{
            Question:"1233333",
            Yes:45,
            No:3,
            GiveUp:34

        },{
            Question:"uyioijh",
            Yes:45,
            No:3,
            GiveUp:34
        }]
    }
    res.send(getCampaignQuestionResultJson)
})









////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//assignment


//修改

// //use to create new assignment
// //post       //locationID???
// //{      
// //     CampaignID:""     
// //     AssignmentDate:"",
// //     Locations:["",""],
// //     Canvasser:"",
// // }
// app.post("/manager_PostAssignment",function(req,res){
// console.log("post /manager_PostAssignment")
// var postAssignmentJson={
//     status:'OK',
//     msg:'/manager_PostAssignment',
//     data:''
// }

//     res.send(postAssignmentJson)

// })




//use to get one campaign all assignment
//post
//{CampaignID:""}
app.post("/manager/GetAssignmentList",function(req,res){
    var getAssignmentListJson={
        status:'Error',
        msg:'/manager_GetAssignmentList',
        data:[{
            CampaignID:"123",
            AssignmentID:1,
            AssignmentName:"c1",
            AssignmentDate:"2018-03-11",
            Canvasser:"mike",
            LocationNumber:"sb",
            Duration:"4hour",
            StartDate:"2018-03-14",
            EndDate:"2018-04-6"
        },{
            CampaignID:"123",
            AssignmentID:2,
            AssignmentName:"c1",
            AssignmentDate:"2018-03-11",
            Canvasser:"mike",
            LocationNumber:"sb",
            Duration:"4hour",
            StartDate:"2018-03-14",
            EndDate:"2018-04-6"
        },{
            CampaignID:"123",
            AssignmentID:2,
            AssignmentName:"c1",
            AssignmentDate:"2018-03-11",
            Canvasser:"mike",
            LocationNumber:"sb",
            Duration:"4hour",
            StartDate:"2018-03-14",
            EndDate:"2018-04-6"
        }]
    }

    console.log("post /manager_GetAssignmentList")
    res.send(getAssignmentListJson)
})



//use to get assignment info
//post
//{AssignmentID:""}
app.post("/manager/GetAssignmentInfo",function(req,res){

    var getAssignmentInfoJson={
        status:'OK',
        msg:'/manager_GetAssignmentInfo',
        data:{
            AssignmentID:"1",
           
            AssignmentDate:"2018-03-11",
            Locations:["1111","2222"],
            Canvasser:"fffff",

            CampaignID:"1234",
            CampaignName:"c1",
            TalkingPoints:"1234",
            Questions:["123444","ererer"],
            VisitDuration:"3bour",
        }
    }



    console.log("post /manager_GetAssignmentInfo")
    res.send(getAssignmentInfoJson)
})



// //get assignment window info
// //post
// //{AssignmentID:""}
app.post("/manager/EditAssignmentWindow",function(req,res){

var editAssignmentWindowJson={
    status:'OK',
    msg:'/manager_EditAssignmentWindow',
    data:{
        CampaignID:"123",
        AssignmentID:"1",
        AssignmentName:"c1",
        AssignmentDate:"2018-03-11",
        LocationsOption:["1111","lklk","222","opop","33"],
        Locations:["1111","222","33"],
        CanvassersOption:["qwee","weedee","eer"],
        Canvasser:"eer"
        //LocationID
    }
}
    console.log("post /manager_EditAssignmentWindow")
    res.send(editAssignmentWindowJson)
})



// //edit assignment
// //post
// // {      
// //     AssignmentID:"",
// //     AssignmentDate:"",
// //     Locations:["",""],
// //     Canvasser:"",
// // }
app.post("/manager/EditAssignment",function(req,res){

var editAssignmentJson={
    status:'OK',
    msg:'/manager_EditAssignment',
    data:''
}
    console.log("post /manager_EditAssignment")
    res.send(editAssignmentJson)
})



// //use to delete assignment
// //post
// //{
// //     AssignmentID:""
// // }

// app.post("/manager_DeleteAssignment",function(req,res){

// var deleteAssignmentJson={
//     status:'OK',
//     msg:'/manager_DeleteAssignment',
//     data:''
// }
//     console.log("post /manager_DeleteAssignment")
//     res.send(deleteAssignmentJson)

// })



// //use to load canvasser according to date when create assignment
// //post
// //{   CampaignID:"",
// //    Date:""}
// app.post("/manager_CreateAssignment_LoadCanvasserAccordingToDate",function(req,res){
//     console.log("post /manager_CreateAssignment_LoadCanvasserAccordingToDate")

//     var createAssignmentLoadCanvasserAccordingToDateJson={
//         status:'OK',
//         msg:'/manager_CreateAssignment_LoadCanvasserAccordingToDate',
//         data:["qq","ee","ff"]
//     }
//     res.send(createAssignmentLoadCanvasserAccordingToDateJson)

// })



// //use to load canvasser according to date when edit assignment
// //post
// //{   CampaignID:"",
// //    AssignmentID:"",
// //  Date:""}
app.post("/manager/EditAssignment_LoadCanvasserAccordingToDate",function(req,res){
    console.log("post /manager_EditAssignment_LoadCanvasserAccordingToDate")

    var tempJson={
        status:'OK',
        msg:'/manager_EditAssignment_LoadCanvasserAccordingToDate',
        data:["qqedit","eeedit","ffedit"]
    }
    res.send(tempJson)

})



// //use to load locations when create assignment
// //post
// //{CampaignID:""}
// app.post("/manager_CreateAssignment_LoadLocations",function(req,res){
//     console.log("post /manager_CreateAssignment_LoadLocations")

//     var tempJson={
//         status:'OK',
//         msg:'/manager_CreateAssignment_LoadLocations',
//         data:["l1","l2","l3"]
//     }
//     res.send(tempJson)

// })


//when viewAssignment
//use to load one assignment all location
//post
//{AssignmentID:""}
app.post("/manager/GetAssignmentAllLocation",function(req,res){
    console.log("post /manager_GetAssignmentAllLocation")

    var createAssignmentLoadCanvasserAccordingToDateJson={
        status:'OK',
        msg:'/manager_GetAssignmentLocation',
        data:[{
            LocationID:"123",
               LocationName:"Stony Brook",
             AssignmentID:"1234",
             CampaignID:"123",
             Status:"Finish",

             lat: 51.373858,
             lng: 7.215982,
             label: "B",
             Info:"stony brook"
            },
             {
                 LocationID:"1233",
                 LocationName:"New york",
                 AssignmentID:"1234",
                 CampaignID:"123",
                 Status:"to be continue",

                 lat: 51.723858,
                 lng: 7.895982,
                 label: "C",
                 Info:"stony brook"

             }]
    }
    res.send(createAssignmentLoadCanvasserAccordingToDateJson)

})


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//canvasser calendar  (canvasserCalendar.service)
//by token
app.get("/canvasser/LoadCalendarEventList",function(req,res){
    console.log("get /canvasser_LoadCalendarEventList")

    var tempJson={
        status:'OK',
        msg:'/canvasser_LoadCalendarEventList',
        data:[
            "Wed Nov 07 2018",
            "Fri Nov 09 2018"
        ]
    }
    res.send(tempJson)

})

//add event
//post
//{
//   Date:""
//}

//and token to get user
app.post("/canvasser/CalendarAddEvent",function(req,res){
    console.log("post /canvasser_CalendarAddEvent")

    var tempJson={
        status:'OK',
        msg:'/canvasser_CalendarAddEvent',
        data:""
    }
    res.send(tempJson)

})



//remove event
//post
//{
//   Date:""
//}

//and token to get user
app.post("/canvasser/CalendarRemoveEvent",function(req,res){
    console.log("post /canvasser_CalendarRemoveEvent")

    var tempJson={
        status:'OK',
        msg:'/canvasser_CalendarRemoveEvent',
        data:""
    }
    res.send(tempJson)

})




//canvasser current assignment, (canvasserCurrentAssignment.Service)

//load current Assignment info
//by token
app.get("/canvasser/LoadCurrentAssignmentInfo",function(req,res){
    console.log("get /canvasser_LoadCurrentAssignmentInfo")

    var tempJson={
        status:'OK',
        msg:'/canvasser_LoadCurrentAssignmentInfo',
        data:{
            CampaignID: "1234",
            CampaignName: "c1",
            TalkingPoints: "1234",
            Questions: "123444",
            AssignmentID: "qweweeq",
            VisitDuration: "3bour",
            AssignmentDate: "3/20/2018",
            Canvasser: "mike"
        }
    }
    res.send(tempJson)

})

//load current location
//by token
//post
//{assignmentID:""}
app.post("/canvasser/LoadCurrentLocation",function(req,res){
    console.log("post /canvasser_LoadCurrentLocation")

    var tempJson={
        status:'OK',
        msg:'/canvasser_LoadCurrentLocation',
        data:{
            LocationID:"123",
            lat:50,
            lng:50,
            Info:"tt",
            label:"Current locaion",
            LocationName:"stony brook"


        }
    }
    res.send(tempJson)

})


//load next location
//by token
//post
//{assignmentID:""}
app.post("/canvasser/LoadNextLocation",function(req,res){
    console.log("post /canvasser_LoadNextLocation")

    var tempJson={
        status:'OK',
        msg:'/canvasser_LoadNextLocation',
        data:{
            LocationID:"aqwe",
            lat:50,
            lng:50,
            Info:"tt",
            label:"Current locaion",
            LocationName:"new york"


        }
    }
    res.send(tempJson)

})


//load location list in order
//token
//post
//{assignmentID:""}
app.post("/canvasser/LoadCurrentAssignmentLocationList",function(req,res){
    console.log("post /canvasser_LoadCurrentAssignmentLocationList")

    var tempJson={
        status:'OK',
        msg:'/canvasser_LoadCurrentAssignmentLocationList',
        data:[{
            LocationID:"qwe",
            lat: 51.673858,
            lng: 7.815982,
            label: "45",
            Info:"stony brook",
            AssignmentID:"1234",
            LocationName:"123",
            Status:"finish"


        },{
            LocationID:"wer",
            lat: 51.373858,
            lng: 7.215982,
            label: "89",
            Info:"stony brook",
            AssignmentID:"avcdf",
            LocationName:"234",
            Status:"0909"


        },{
            LocationID:"wer",
            lat: 51.723858,
            lng: 7.895982,
            label: "09",
            Info:"stony brook",
            AssignmentID:"ookokoko",
            LocationName:"popo",
            Status:"finish"


        }]
    }
    res.send(tempJson)

})



//修改

//load location information
//post
// {
//     campaignID:"",    
//     AssignmentID:"",
//     LocationID:""
//   }
app.post("/canvasser/LoadLocationPreInfo",function(req,res){
    console.log("post /canvasser_LoadLocationPreInfo")

    var tempJson={
        status:'OK',
        msg:'/canvasser_LoadLocationPreInfo',
        data:{
            TalkingPoints:"123",
            //Rating:3,
            //SpeakingToAnyone:"Yes",
            
            //questionId
            Questions:[{
                        QuestionId:"123",
                        Question:"do u want to build a snow man?"},
                       {QuestionId:"1234",
                         Question:"do u want to kill a snowman?"}
                        ]
        }
    }
    res.send(tempJson)

})


//post the result
//post
// {

//     AssignmentID:"",
//     LocationID:"",
//     rating:""
//     SpeakAnswer:"",
//     Note:"",
//     Questions:[{QuestionId:"",Answer:"",Question:""}
//                  {QuestionId:"",Answer:"",Question:""}]
//   }
app.post("/canvasser/PostLocationResult",function(req,res){
    console.log("post /canvasser_PostLocationResult")

    var tempJson={
        status:'OK',
        msg:'/canvasser_PostLocationResult',
        data:""
    }
    res.send(tempJson)

})



//canvasser upcoming assignment /canvasserUpcomingAssignmentService

//load upcoming assignment list
//by token
app.get("/canvasser/LoadUpcomingAssignmentList",function(req,res){
    console.log("get /canvasser_LoadUpcomingAssignmentList")

    var tempJson={
        status:'OK',
        msg:'/canvasser_LoadUpcomingAssignmentList',
        data:[{

            AssignmentID:"abc",
            AssignmentDate:"2018-4-15",
            LocationNumber:3,
            VisitDuration:"3hours",
            Locations:[{
                LocationID:"123",
                lat: 51.673858,
                lng: 7.815982,
                label: "45",
                Info:"stony brook",
                AssignmentID:"1234"


            },{
                LocationID:"123",
                lat: 51.373858,
                lng: 7.215982,
                label: "89",
                Info:"stony brook",
                AssignmentID:"avcdf"


            },{
                LocationID:"123",
                lat: 51.723858,
                lng: 7.895982,
                label: "09",
                Info:"stony brook",
                AssignmentID:"ookokoko"


            }]

        },{

            AssignmentID:"abc",
            AssignmentDate:"2018-4-15",
            LocationNumber:3,
            VisitDuration:"3hours",
            Locations:[{
                LocationID:"123",
                lat: 51.673858,
                lng: 7.815982,
                label: "A1",
                Info:"stony brook",
                AssignmentID:"1234"


            },{
                LocationID:"123",
                lat: 51.373858,
                lng: 7.215982,
                label: "B2",
                Info:"stony brook",
                AssignmentID:"avcdf"


            },{
                LocationID:"123",
                lat: 51.723858,
                lng: 7.895982,
                label: "C3",
                Info:"stony brook",
                AssignmentID:"ookokoko"


            }]

        }

    ]
    }
    res.send(tempJson)

})

//load one upcoming assignment all location info
//by token
app.get("/canvasser/LoadUpcomingAssignmentToMap",function(req,res){
    console.log("get /canvasser_LoadUpcomingAssignmentToMap")

    var tempJson={
        status:'OK',
        msg:'/canvasser_LoadUpcomingAssignmentToMap',
        data:[{
            LocationID:"123",
            lat: 51.673858,
            lng: 7.815982,
            label: "A",
            Info:"stony brook",
            AssignmentID:"1234"


        },{
            LocationID:"123",
            lat: 51.373858,
            lng: 7.215982,
            label: "B",
            Info:"stony brook",
            AssignmentID:"avcdf"


        },{
            LocationID:"123",
            lat: 51.723858,
            lng: 7.895982,
            label: "C",
            Info:"stony brook",
            AssignmentID:"ookokoko"


        }]
    }
    res.send(tempJson)

})


app.listen(5438, function () {
    console.log("start http://localhost:5438 ")
})

































// var express = require('express') //加载模块
// var app = express() //实例化之

// var map = {"1":{id:1,name:"test"},"2":{id:2,name:"test"}} //定义一个集合资源，key为字符串完全是模仿java MAP<T,E>，否则谁会这么去写个hash啊！

// app.get('/devices',function(req, res){ //Restful Get方法,查找整个集合资源
//     res.set({'Content-Type':'text/json','Encodeing':'utf8'});
//     res.send(map)
// })
// app.get('/devices/:id',function(req, res){ //Restful Get方法,查找一个单一资源
//     res.set({'Content-Type':'text/json','Encodeing':'utf8'});
//     res.send(map[req.param('id')])
//     //console.log(req.param('id'))
// })
// app.post('/devices/', express.bodyParser(), function(req, res){ //Restful Post方法,创建一个单一资源
//     res.set({'Content-Type':'text/json','Encodeing':'utf8'});
//     map[req.body.id] = req.body
//     res.send({status:"success",url:"/devices/"+req.body.id}) //id 一般由数据库产生
// })
// app.put('/devices/:id', express.bodyParser(), function(req, res){ //Restful Put方法,更新一个单一资源
//     res.set({'Content-Type':'text/json','Encodeing':'utf8'});
//     map[req.body.id] = req.body
//     res.send({status:"success",url:"/devices/"+req.param('id'),device:req.body});
// })
// app.delete('/devices/:id',function(req, res){ //Restful Delete方法,删除一个单一资源
//     res.set({'Content-Type':'text/json','Encodeing':'utf8'});
//     delete map[req.param('id')]
//     res.send({status:"success",url:"/devices/"+req.param('id')})
//     console.log(map)
// })
// app.listen(8888); //监听8888端口，没办法，总不好抢了tomcat的8080吧！
