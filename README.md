# **Introduction**

![Build Status](passing.svg)

Based on what is asked from the question this app:
- have been written using IntelliJ Idea
- is fully plain Java. I didn't add any dependency to my pom.xml file except JUnit which is for tests.
- has some tests. You can refer to those tests if you wish
- is packed with maven and I used multiple plugins for creating an exe file and jar file for running it easily
- is written for showing the result within console
- process the plain text file and apply whatever is asked for
- calculates the number of words, number of dots and most used word

### Running the application:
To run the application you should compile and build the app using maven. To do that follow the below instruction:

- navigate to the project root directory where [pom.xml] file exists.
- open a console/cmd prompt terminal and execute this command --> mvn clean install
Take note you should have installed maven before doing the above.

The above commands compile and build the project using maven and create a new directory called *target.
Navigate to [target] directory and you will see 2 files:

- hicx-solution-1.0-SNAPSHOT.jar (to run the application in all platforms, Linux, Windows, Mac, etc)
- hicx-solution-1.0-SNAPSHOT.exe (to run the application in windows applications only)

All of the above run the same application but in different environment. You can run:
```sh
java -jar hicx-solution-1.0-SNAPSHOT.jar /home/myuser/Desktop/resource-dir
cmd -> hicx-solution-1.0-SNAPSHOT.exe C:\users\myuser\desktop\hicx-solution-1.0-SNAPSHOT.exe C:\Path\To\Root\Resource\Directory
```
After you run the application, the app will create input, processed, unsupported folders if they don't exist within the root folder..
In this phase:
- If there are files withint the input folder, they are processed automatically
- If you copy some file within the input folder, the application will process those files too
- The results will be available within the console
- The file will move from input folder to the processed folder
- Just in case if the file is not a plain text file, the application recognize that and move the file to unsupported folder 

---

I tries to implement all the requirements and I tested the solution on **Linux Ubuntu version 20.04**. Moreover, for better explanation, I put some documents for the classes and interfaces..



> Mehdi Afsarikashi - July 13 2021