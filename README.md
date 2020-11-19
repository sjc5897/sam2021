First Time Set up:
In order to set up the applicaiton, a user must install maven depenedancies.

Via Intellij IDE:
Simply right click the pom.xml: Maven -> Import project

Via Command Line:
Run the install.bat file
For this to work you must configure a environment variable JAVA_HOME to point to your JDK:
Windows 10:

Search Environment Variables in the windows search bar
Select "Edit environment variables for your account"
Select "New"
In variable name add "JAVA_HOME" in variable value add the path to your JDK, most likely found in Program Files/Java
NOTE: make sure the points to the toplevel JDK directory and not an internal directory, like bin
See here for help or for older windows versions: https://www.computerhope.com/issues/ch000549.htm
Via Intellij IDE:
Run BandmeetupApplication as main

Via Command Line:
Run the run.bat file
NOTE: the above Enviromental Variable configuration must be done for the run.bat to work as well

Check http://localhost:8080/
You can create your own profile.
A preloaded admin account is provided.
username: admin@admin.com password qwerty
