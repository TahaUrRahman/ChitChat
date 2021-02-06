# ChitChat
This is a chat application created using _Java_ with UI built in _JavaFx_. It lets you send/receive messages and friend requests through internet. There are 2 java packages.
* Client
* Server

Client and server packages include their respective UI's, controllers and application layers. _Resources_ include the graphics/icons that are used throughout the application. Application provides both Light Theme and Dark Theme options. 

___
You __must__ run server.main() and start the server from the serverr UI before running clients. After running server.main(), run client.main() as many times you want.
___
## Adding Libraries

Both, MySql connector and JavaFx library, are included in libs folder. You will need to __manually__ add these in your project configurations. The below links will help you if you are using Intellij IDEA.

[How to add JavaFX in project configuration in Intelij IDEA](https://www.jetbrains.com/help/idea/javafx.html#add-javafx-lib)\
[How to add a library in IntelliJ IDEA](https://www.jetbrains.com/help/idea/library.html)\
You can search on google in case you are using some other IDE, but the process is pretty similar.

___

## Database Connectivity

You can set your Database port\Username\Password through these lines in makeDatabaseConnection() in server.WelcomeThread.java
```java
String url = "jdbc:mysql://localhost:<YourDatabasePort>/chitchat";
String Username = "<YourDatabaseUsername>";
String Password = "<YourDatabasePassword>";
```
