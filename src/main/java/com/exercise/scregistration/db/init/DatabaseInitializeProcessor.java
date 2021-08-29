package com.exercise.scregistration.db.init;

import java.util.logging.Logger;

import com.exercise.scregistration.db.connection.ConcurrentBlockingConnectionPool;
import com.exercise.scregistration.exception.DatabaseException;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseInitializeProcessor {
    private static final Logger LOG = Logger.getLogger(DatabaseInitializeProcessor.class.getName());

    private final ConcurrentBlockingConnectionPool connectionPool = ConcurrentBlockingConnectionPool.getSingleton();
    private Connection connection ; 

    public DatabaseInitializeProcessor() {
        try {
            connection = connectionPool.getConnection();
        } catch (InterruptedException | SQLException e) {
            LOG.severe(e.getMessage());
        }
    }

    public void init() {
        createTables();
    }

    private void createTables() {
        try {
        	LOG.fine("started creating the student table");
            connection.createStatement().executeUpdate("create table if not exists STUDENT  (STUDENT_ID int  not null auto_increment, "
            		+ " FIRST_NAME VARCHAR(50) NOT NULL, LAST_NAME VARCHAR(50) NOT NULL, "
            		+ " DATE_OF_BIRTH DATE NOT NULL, MOBILE_NO VARCHAR(20), "
            		+ " PRIMARY KEY (STUDENT_ID), "
            		+ " UNIQUE KEY student_unique (FIRST_NAME,LAST_NAME,DATE_OF_BIRTH,MOBILE_NO))");
            LOG.fine("sucessfully created the student table");
            
        	LOG.fine("started creating the course table");
            connection.createStatement().executeUpdate("create table if not exists COURSE  (COURSE_ID int not null auto_increment, "
            		+ " NAME VARCHAR(50) NOT NULL, DETAIL_INFO VARCHAR(200) NOT NULL, "
            		+ " PRIMARY KEY (COURSE_ID), "
            		+ "UNIQUE KEY course_name_unique (NAME))");
            LOG.fine("sucessfully created the student table");
            
            LOG.fine("started creating the student-course table");
            connection.createStatement().executeUpdate("create table if not exists  STUDENT_COURSE  (SC_ID int  not null auto_increment, "
            		+ " STUDENT_ID int NOT NULL, "
            		+ " COURSE_ID int NOT NULL,  PRIMARY KEY (SC_ID), "
            		+ " CONSTRAINT FK_SUDENTID FOREIGN KEY (STUDENT_ID) REFERENCES STUDENT(STUDENT_ID), "
            		+ " CONSTRAINT FK_COURSEID FOREIGN KEY (COuRSE_ID) REFERENCES COURSE(COURSE_ID), "
            		+ " UNIQUE KEY sudent_course_unique (STUDENT_ID,COURSE_ID))");
            LOG.fine("sucessfully created the student-course table");
        } catch (SQLException e) {
        	LOG.severe(e.getMessage());
        	throw new DatabaseException(e.getMessage());
        }finally{
        	connectionPool.releaseConnection(connection);
        	connection = null;
        }
    }
}
