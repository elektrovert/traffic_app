# traffic_app
Doro test
The project is currently a work in progress, as I have a very busy work schedule at the moment. 
While I have had a lot of expereince writing these types of applications in the past, doing this test has made me realise how much of this work 
has been abstracted away with custom frameworks and annotations at my current job.

The project was written using Intellij Idea 
Java 11
Maven 3.6.3

So far I have focused entirely on the backend. 

Client Package
The clients currently have hard coded values (removed in the commit) for usernames, passwords, api keys, etc.... 
In a production ready project these parameters would be stored in something like ansible, and added as environment variables for a dedicated 'app only' user. 

Repository
Currently I'm using a concurrent hashmap to store values, I haven't implemented database functionality yet. In a finished product I would preferably query stored procedures 
in the database, or failing that, sql statements using prepared statements to ensure the queries and parameteres are correctly sanatizeed.  

Controllers 
The controllers have very basic validation, some null checks and regex email/phone number validation. 
I've also implemented a error handler so a simple error message is returned to the client, and stack traces are not returned to the client. 

  
