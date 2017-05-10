package com.fortune.utils;

import java.sql.*;

public class DataBaseConnection {
	   // JDBC driver name and database URL
	    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	    static final String DB_URL = "jdbc:mysql://localhost/rsa";

	    //  Database credentials
	    static final String USER = "fortune";
	    static final String PASS = "fortune";

	    public Connection  getConnection () {
	        Connection conn = null;
	        Statement stmt = null;
	        try{
	            //STEP 2: Register JDBC driver
	            Class.forName("com.mysql.jdbc.Driver");

	            //STEP 3: Open a connection
	            System.out.println("Connecting to database...");
	            conn =  DriverManager.getConnection(DB_URL,USER,PASS);




	        }catch(SQLException se){
	            //Handle errors for JDBC
	            se.printStackTrace();
	        }catch(Exception e){
	            //Handle errors for Class.forName
	            e.printStackTrace();
	        }finally{
	            //finally block used to close resources
	            try{
	                if(stmt!=null)
	                    stmt.close();
	            }catch(SQLException se2){
	            }// nothing we can do

	        }//end try
	        System.out.println("Goodbye!");

	        return  conn;
	    }//end main

	}