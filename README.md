# planner
Simple task scheduler

Functional:
 - Adding a task to the database
 - Editing tasks to the database
 - Deleting a task to the database
 - View tasks in the database
 - Search for a task in the database
 - Add other users for joint events
 
 When starting the application, an administrator with two roles (admin and user) is created by default
 Username - admin
 Password - adminPlanner
 
 Start the application like admin:
 POST http://localhost:8088/api/v1/auth/login
 Content-Type: application/json
 Accept: application/json
 
 { "username": "admin",
   "password": "adminPlanner" }
  
  As a result, we get a token a can work with application functional 
 

An ordinary user for first start needs to register, for example:

POST http://localhost:8088/api/v1/users
Content-Type: application/json
Accept: application/json

{
  "username": "test",
  "password": "test",
  "firstName": "test",
  "lastName": "test",
  "email": "test@gmail.com"
}

And then login, for Example
POST http://localhost:8088/api/v1/auth/login
 Content-Type: application/json
 Accept: application/json
 
 { "username": "test",
   "password": "test" }
   
   Few examples to use a functional:
   
      ### ADD NEW TASK
   
POST http://localhost:8088/tasks
Content-Type: application/json
Accept: application/json
Authorization: Bearer {{auth_token}}

{
  "taskDescription": "description",
  "startDate": "2021-12-01 00:00",
  "endDate": "2021-12-02 00:00",
  "priority": "LOW",
  "status": "TODO",
  "type": "PERSONAL"
}


### ADD USER TO TASK BOARD

PUT http://localhost:8088/tasks/20/participant
Content-Type: application/json
Accept: application/json
Authorization: Bearer {{auth_token}}

{
  "participantId": 2
}

 ### FIND ALL TASKS
GET http://localhost:8088/tasks
Accept: application/json
Authorization: Bearer {{auth_token}}  
   
   ### DELETE TASK BOARD BY ID
DELETE http://localhost:8088/tasks/18
Accept: application/json
Authorization: Bearer {{auth_token}}
   
   
### CHANGE TASK PRIORITY
PUT http://localhost:8088/tasks/19/priority
Content-Type: application/json
Accept: application/json
Authorization: Bearer {{auth_token}}

{
  "taskPriority": "HIGH"
}

