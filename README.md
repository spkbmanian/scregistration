I have used following technologies used on this project

MySQL 8.0.26
Tomcat 8.5.70
Java 8
Maven
to build the project please run the below command mvn clean install

to configure DB connection detail to use in application please find the myapp_db.properties in repository. make necessary changes Please add the location of the above file in shared.loader from CATALINA_HOME/conf/catalina.properties . example like shared.loader=${catalina.home}/credentials