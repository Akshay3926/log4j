package com.nt.jdbc;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

public class SelectTest {
   private static final String GET_ALL_STUDENTS_QUERY="SELECT SNO,SNAME,SADD,AVG FROM STUDENT";
   
	//create logger
	private static Logger logger=Logger.getLogger(com.nt.jdbc.SelectTest.class);
	
	
	static {
	SimpleLayout layout=null;
	ConsoleAppender appender=null;
	
	try {
	//create layout
	layout=new SimpleLayout();
	//create Appender
	appender=new ConsoleAppender(layout);
	//add appender
	logger.addAppender(appender);
	//specify logger level to retrieve log messages
	logger.setLevel(Level.ALL);
	logger.info("Log4J app Started");
	}
	catch(Exception e) {
		e.printStackTrace();
		logger.error("problem in activating log4j");
	}
	}
	public static void main(String[] args) {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		boolean flag=false;
				
	try {
		//register driver
		Class.forName("oracle.jdbc.driver.OracleDriver");
		logger.debug("driver registered");
		
		//establish the connection
		con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","manager");
		logger.debug("connection established");
		
		//create Prepared statement object
		if(con!=null) {
			ps=con.prepareStatement(GET_ALL_STUDENTS_QUERY);
		logger.debug("prepared statement obj is created");
		}
		//send and execute sql query
		if(ps!=null) {
			rs=ps.executeQuery();
		logger.debug("resultsetobj is created");
		}
		//process the resultset
		if(rs!=null) {
			while(rs.next()) {
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getFloat(4));
			}//while
		logger.debug("resultset object is processed");
		}//if
        if(flag==false) {
        	System.out.println("record  not found");
        	logger.info("table empty"+new Date());
        }
        else {
        	System.out.println("record found");
        	logger.info("table having data"+new Date());
        	
        }
	}
	   catch(SQLException se) {
		   logger.error("DB problem:"+se.getErrorCode());
		   se.printStackTrace();
	   }
	catch(ClassNotFoundException cnf) {
		logger.error("Driver Problem:"+cnf.getMessage());
	  cnf.printStackTrace();
	}
	catch(Exception e) {
		logger.error("unknown problem:"+e.getMessage());
	}
	finally {
		logger.debug("finally block");
		try {
			if(rs!=null) {
				rs.close();
			logger.debug("resultset close");
		}
		}
	  catch(SQLException se) {
		  logger.equals("resultset not closed");
		  se.printStackTrace();
	  }
		try {
			if(ps!=null) {
				ps.close();
			logger.debug("preparedStatement obj close");
		}
		}
	  catch(SQLException se) {
		  logger.equals("prepared statement obj not closed");
		  se.printStackTrace();
	  }
		try {
			if(con!=null) {
				con.close();
			logger.debug("connection close");
		}
		}
	  catch(SQLException se) {
		  logger.equals("connection not closed");
		  se.printStackTrace();
	  }
		logger.info("application exit");
	}//finally
}//main
}//class
