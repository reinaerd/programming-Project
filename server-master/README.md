# Stratego Server project group [number]

## Parent group
https://git.ti.howest.be/TI/2020-2021/s2/programming-project/projects/group-23

## Remote urls

* https://project-i.ti.howest.be/stratego-23/ (this is your web client)
* https://project-i.ti.howest.be/stratego-23/api/stratego/versions/original (this will be available as soon as this project is deployed)
* https://project-i.ti.howest.be/stratego-api-spec/ (this is a common resource: no write access)

## Before you start: Configure your server
* Find the file `src > main > resources > server-config.json` and replace the 99 in 8099 by your group number.
  e.g., for group 1 this will be 8001
* Find the file `src > main > resources > vertx-default-jul-logging.properties` and replace the XX in `%t/stratego-XX.log` by your group number.
  e.g., for group 1 this will be `%t/stratego-01`
* Find the file `gradle.properties` and replace the XX in `2021.project-i:stratego-server-XX` by your group number.
  e.g. for group 1 this will be `2021.project-i:stratego-server-01`
* Find the file `stetting.gradle` and replace the XX in `stratego-server-groupXX` by your group number.
  e.g. for group 1 this will be `stratego-server-group01`

Only one team member should do this:
create an issue, do the configuration push and merge into master, then the other members can start working as well.

Check the url `https://project-i.ti.howest.be/stratego-XX/api/versions`, (replace the XX), it should show:
````JSON
{
  "message" : "Not Yet Implemented (NYI)"
}
````
Check the url `https://sonar.ti.howest.be/`, your project (server) should be in the list.
  

## Please complete the following before committing the final version on the project
Please **add** any **instructions** required to
* Make your application work if applicable
* Be able to test the application (login data)
* View the wireframes

Also clarify
* If there are known **bugs**
  - None that we haven't fixed yet
  

* If you haven't managed to finish certain required functionality
  - Infiltrator cannot attack on enemy territory, only infiltrate.


* Token Scheme
  1) Filters special characters out of the roomID, so it doesn't break anything server side
  2) translates the roomId into a char[array]
  3) translates the roomId char[array] into an int[array] of ASCII values
  4) loops over the ASCII int[array], adding incremental values based on the length of the playerToken
     (starting from i == 0; i < playerToken.length)
  
  --> length of the playerToken makes it, so the gameIds in both tokens will be the same encrypted, BUT the player tokens will always be different
  because of the length of your roomId



## Instructions for local CI testing
You can **run** the Sonar validator **locally.**
There is no need to push to the server to check if you are compliant with our rules.
In the interest of sparing the server, please result to local testing as often as possible.

If everyone pushes to test, the remote will not last.

Most of the configuration of Sonar is the same in IntelliJ as it is/was in PhpStorm/WebStorm. Only running the actual analysis is different.

First, make sure you have updated the gradle.properties file (see above).
To run the Java analysis open the gradle tab (right side, with the elephant icon), open the project, open Tasks, open verification and run (double click) te sonraqube task.
If this analysis is run at least once, your project will be created on our sonarqube server.
You can now configure the SonarLint plugin (and IntelliJ) to make a binding with the sonarqube server as well.

For more details, please consult the [Sonar guide](https://git.ti.howest.be/TI/2020-2021/s2/programming-project/documentation/stratego-documentation/-/blob/master/sonar-guide/Sonar%20guide.md).

## Roadmap
In order to help you along with planning, we've provided a server creation [roadmap](https://git.ti.howest.be/TI/2020-2021/s2/programming-project/documentation/stratego-documentation/-/blob/master/server-roadmap.md)

