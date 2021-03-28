# traffic_app
  
I have decided that this is as much work as I can put into this at the moment. I hope it's sufficient.
The project is currently a work in progress, as I have a very busy work schedule.
While I have had a lot of expereince writing these types of applications in the past, doing this test has made me realise how much of this work 
has been abstracted away with custom frameworks and annotations at my current job.

The project was written using Intellij Idea     

Java 11

Maven 3.6.3

I have focused entirely on the backend. 

Client Package

The clients currently have hard coded values (removed in the commit) for usernames, passwords, api keys, etc.... 
In a production ready project these parameters would be stored in something like ansible, and added as environment variables for a dedicated 'app only' user. 

Repository

Currently I'm using a concurrent hashmap to store values, I won't implement database functionality . In a finished product I would preferably query stored procedures 
in the database, or failing that, sql statements using prepared statements to ensure the queries and parameteres are correctly sanatizeed. 
I know some databases support functionality such as the auto delete functionality requested as part of this challenge, but that is functionality that I would not utalize. 
Business logic should not be in the database, and I would consider the auto-delete functionality as business logic.  
I think I should mention when implementing a database solution I would not use foriegn keys (I would reference keys in other tables, but not create them as foreign keys) as this 
adds a lot of complexity to a database and can make it difficult to maintain. 
 

Controllers 

The controllers have very basic validation, some null checks and regex email/phone number validation. 
I've also implemented a error handler so a simple error message is returned to the client, and stack traces are not exposed. 

Tests 

I've included some unit tests and a couple of integration tests just to give a basic idea of what my testing looks like. 
With my current work projects we tend to focus more on integration and scenairo tests and less on individual unit tests. 
We will do unit tests if for example a class does internal calculations, but generally it's left up to the discression of the developer. 


  
 
