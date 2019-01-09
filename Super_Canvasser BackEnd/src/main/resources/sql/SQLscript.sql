create table campaign
(
  id            int auto_increment
    primary key,
  campaignName  varchar(255) null,
  endDate       date         null,
  visitDuration double       null,
  talkingPoints varchar(255) null,
  startDate     date         null
);

create table globalVariables
(
  duration     double null,
  averageSpeed double null
);

create table location
(
  id        int auto_increment
    primary key,
  number    int          null,
  street    varchar(255) null,
  unit      varchar(255) null,
  city      varchar(255) null,
  state     varchar(255) null,
  zip       int          null,
  longitude double       null,
  latitude  double       null
);

create table campaign_locationIdList
(
  campaignId int null,
  locationId int null,
  id         int auto_increment
    primary key,
  constraint campaign_locationIdList_campaign_campaignId_fk
  foreign key (campaignId) references campaign (id)
    on delete cascade,
  constraint campaign_locationIdList_location_id_fk
  foreign key (locationId) references location (id)
    on delete cascade
);

create table question
(
  id      int auto_increment
    primary key,
  content varchar(255) null
);

create table campaign_questionIdList
(
  id         int auto_increment
    primary key,
  campaignId int null,
  questionId int null,
  constraint campaign_questionidlist_ibfk_1
  foreign key (campaignId) references campaign (id)
    on delete cascade,
  constraint campaign_questionidlist_ibfk_2
  foreign key (questionId) references question (id)
    on delete cascade
);

create index campaignId
  on campaign_questionIdList (campaignId);

create index questionId
  on campaign_questionIdList (questionId);

create table questionAnswer
(
  id         int auto_increment
    primary key,
  questionId int        null,
  answer     tinyint(1) null,
  locationId int        not null,
  constraint questionAnswer_location_id_fk
  foreign key (locationId) references location (id)
    on delete cascade,
  constraint questionanswer_ibfk_1
  foreign key (questionId) references question (id)
);

create index questionId
  on questionAnswer (questionId);

create table result
(
  id         int auto_increment
    primary key,
  isSpoke    tinyint(1)   null,
  rating     int          null,
  briefNotes varchar(255) null,
  locationId int          null,
  constraint result_location_id_fk
  foreign key (locationId) references location (id)
);

create table campaign_resultIdList
(
  campaignId int null,
  resultId   int null,
  id         int auto_increment
    primary key,
  constraint campaign_resultIdList_campaign_campaignId_fk
  foreign key (campaignId) references campaign (id)
    on delete cascade,
  constraint campaign_resultidlist_ibfk_1
  foreign key (resultId) references result (id)
    on delete cascade
);

create table result_questionAnswer
(
  resultId         int null,
  questionAnswerId int null,
  constraint result_questionAnswer_questionAnswer_id_fk
  foreign key (questionAnswerId) references questionAnswer (id)
    on delete cascade,
  constraint result_questionAnswer_result_id_fk
  foreign key (resultId) references result (id)
    on delete cascade
);

create table role
(
  id   int auto_increment
    primary key,
  name varchar(40) null
);

create table user
(
  id         int auto_increment
    primary key,
  first_name varchar(255) null,
  last_name  varchar(255) null,
  username   varchar(255) null,
  email      varchar(255) null,
  password   varchar(255) null
);

create table campaign_canvasserIdList
(
  campaignId  int null,
  canvasserId int null,
  id          int auto_increment
    primary key,
  constraint campaign_canvasseridlist_ibfk_1
  foreign key (campaignId) references campaign (id)
    on delete cascade,
  constraint campaign_canvasseridlist_ibfk_2
  foreign key (canvasserId) references user (id)
    on delete cascade
);

create table campaign_managerIdList
(
  campaignId int null,
  managerId  int null,
  id         int auto_increment
    primary key,
  constraint campaign_manageridlist_ibfk_1
  foreign key (campaignId) references campaign (id)
    on delete cascade,
  constraint campaign_manageridlist_ibfk_2
  foreign key (managerId) references user (id)
    on delete cascade
);

create table task
(
  id          int auto_increment
    primary key,
  canvasserId int                    null,
  date        date                   null,
  duration    double                 null,
  campaignId  int                    null,
  status      tinyint(1) default '0' null,
  taskName    varchar(255)           null,
  constraint task_ibfk_1
  foreign key (canvasserId) references user (id)
    on delete cascade,
  constraint task_ibfk_2
  foreign key (campaignId) references campaign (id)
    on delete cascade
);

create table task_location
(
  taskId     int                    null,
  locationId int                    null,
  status     tinyint(1) default '0' null,
  constraint task_location_ibfk_1
  foreign key (taskId) references task (id)
    on delete cascade,
  constraint task_location_ibfk_2
  foreign key (locationId) references location (id)
    on delete cascade
);

create table user_campaignIdList
(
  id         int auto_increment
    primary key,
  user_id    int not null,
  campaignId int null,
  constraint user_campaignIdList_user_id_fk
  foreign key (user_id) references user (id)
    on delete cascade
);

create table user_currentLocation
(
  id         int auto_increment
    primary key,
  userId     int null,
  locationId int null,
  constraint user_currentLocation_location_id_fk
  foreign key (locationId) references location (id)
    on delete cascade,
  constraint user_currentLocation_user_id_fk
  foreign key (userId) references user (id)
    on delete cascade
);

create table user_freeDays
(
  id       int auto_increment
    primary key,
  free_day date null,
  user_id  int  not null,
  constraint user_freeDays_user_id_fk
  foreign key (user_id) references user (id)
    on delete cascade
);

create table user_role
(
  id     int auto_increment
    primary key,
  userId int null,
  roleId int null,
  constraint user_role_ibfk_1
  foreign key (userId) references user (id)
    on delete cascade,
  constraint user_role_ibfk_2
  foreign key (roleId) references role (id)
);

create index roleId
  on user_role (roleId);


