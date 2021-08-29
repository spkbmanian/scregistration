package com.exercise.scregistration.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.exercise.scregistration.db.init.DatabaseInitializeProcessor;

public class ConcurrentBlockingConnectionPool implements ConnectionPool<BlockingQueue<Connection>> {

	private static final Logger LOG = Logger.getLogger(DatabaseInitializeProcessor.class.getName());
	
    private final String url;
    private final String user;
    private final String password;
    private final List<Connection> usedConnections = new ArrayList<>();
    private final BlockingQueue<Connection> connectionPool;
    private static final int INIT_POOL_SIZE=3;
    private static final int MAX_POOL_SIZE = 20;
    private static final int MAX_TIMEOUT = 5;
    private static final ReentrantLock reLock = new ReentrantLock(true);
    private static volatile boolean initialized = false;
    
    private static volatile ConcurrentBlockingConnectionPool INSTANCE = null;
    
    public static ConcurrentBlockingConnectionPool initializeDBPool(String url, String user, String password) throws SQLException, InterruptedException, ClassNotFoundException {
    	Class.forName("com.mysql.cj.jdbc.Driver");
		if(!initialized){
			reLock.lock();
			try{
				if(!initialized){
					BlockingQueue<Connection> pool = new LinkedBlockingDeque<>(INIT_POOL_SIZE);
					LOG.log(Level.CONFIG,"started create connection pool with inital pool : " +INIT_POOL_SIZE);
					for (int i = 0; i < INIT_POOL_SIZE; i++) {
						pool.put(createConnection(url, user, password));
					}
					LOG.log(Level.CONFIG,"sucessfully created connection pool with inital pool : " +pool.size());
					INSTANCE = new ConcurrentBlockingConnectionPool(url, user, password, pool); 
					initialized = true;
				}
			}finally{
				reLock.unlock();
			}
		}
         return INSTANCE;
    }
    
    public static ConcurrentBlockingConnectionPool getSingleton(){
    	if(initialized)
    		return INSTANCE;
    	else
    		return null;
    }

    private ConcurrentBlockingConnectionPool(String url, String user, String password, BlockingQueue<Connection> connectionPool) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connectionPool = connectionPool;
    }

    @Override
    public Connection getConnection() throws SQLException, InterruptedException {
        if(connectionPool.isEmpty()){
        	LOG.log(Level.CONFIG, "Connection pool is empty at this time :  " + LocalDateTime.now() 
        	+ " left over remain allocate connection object size = "+ (MAX_POOL_SIZE - this.usedConnections.size()));
        	if(this.usedConnections.size()<MAX_POOL_SIZE){
        		connectionPool.put(createConnection(url, user, password));
        		LOG.log(Level.CONFIG,"sucessfully added new connection in pool and current size: " + this.getSize());
            } else {
            	LOG.log(Level.SEVERE, "Maximum pool size reached at :  " + LocalDateTime.now() 
            	+ " no available connections! used couts = "+ (this.usedConnections.size()));            	
                throw new RuntimeException("Maximum pool size reached, no available connections!");
            }
        }

        Connection connection = connectionPool.take();

        if(!connection.isValid(MAX_TIMEOUT)){
            connection = createConnection(url, user, password);
        }

        usedConnections.add(connection);
        LOG.log(Level.CONFIG,"sucessfully return connection ");
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection)  {
    	boolean removed = false;
    	try{
    		connectionPool.put(connection);
    		removed  = usedConnections.remove(connection);
    	}catch(Exception ex){
    		LOG.log(Level.SEVERE,"Failed to release connection : "+ ex.getMessage());
    		throw new RuntimeException(ex);
    	}finally{
    		if(!removed){
    			usedConnections.remove(connection);
    		}
    		connection = null;
    	}
    	return removed;
    }

    private static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    @Override
    public BlockingQueue<Connection> getConnectionPool() {
        return connectionPool;
    }
    
    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public void shutdown() {
    	LOG.log(Level.WARNING,"Initialized shutdown connections ");
    	final ConcurrentBlockingConnectionPool thisPool = this;
    	Consumer<Connection> connectionConsumer = new Consumer<Connection>() {
    	    public void accept(Connection currentConnection) {
    	    	thisPool.releaseConnection(currentConnection);
    	    };
    	};
        usedConnections.forEach(connectionConsumer);
        LOG.log(Level.WARNING,"Sucessfully release connections ");
        for (Connection c : connectionPool) {
        	try{
        		c.close();
        	}catch(Exception ex){
        		//SKIPPING excepiton to close remaining connections
        		LOG.log(Level.WARNING,"Connection failed to close");
        	}
        }
        LOG.log(Level.WARNING,"Sucessfully closed connections ");
        connectionPool.clear();
        LOG.log(Level.INFO,"Sucessfully cleared references ");
    }
}