package com.exercise.scregistration.web.listener;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.exercise.scregistration.db.connection.ConcurrentBlockingConnectionPool;
import com.exercise.scregistration.db.init.DatabaseInitializeProcessor;

@WebListener
public class DataBaseInitializerContextListener implements ServletContextListener{
	
	private static final Logger LOG = Logger.getLogger(DataBaseInitializerContextListener.class.getName());
    
	private ConcurrentBlockingConnectionPool connectionPool = null;
	
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    	if(connectionPool!=null){
    		LOG.log(Level.WARNING, "Started DB shuttingdown");
    		connectionPool.shutdown();
    		LOG.log(Level.WARNING, "Sucessfully shuttingdown");
    	}
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
    	try{
    		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    		InputStream input = classLoader.getResourceAsStream("myapp_db.properties");
    		LOG.log(Level.INFO, "DB properies file readed");
    		Properties properties = new Properties();
    		properties.load(input);
    		LOG.log(Level.INFO, "DB properies file loaded");
    		connectionPool = ConcurrentBlockingConnectionPool.initializeDBPool((String)properties.get("DBURL"), (String)properties.get("DBUSER"), (String)properties.get("DBPASSWORD"));
    		LOG.log(Level.INFO, "Sucessfully initialized DB Pool in context listener");
    		DatabaseInitializeProcessor tableCreator = new DatabaseInitializeProcessor();
    		tableCreator.init();
    		LOG.log(Level.INFO, "Sucessfully initialized tables created in context listener");
    	}catch(Exception ex){
    		LOG.log(Level.SEVERE, "Context Initialization failed : "+ ex.getMessage());
    		throw new RuntimeException(ex.getMessage());
    	}
    }
}