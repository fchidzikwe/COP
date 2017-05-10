package com.fortune.controller;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class AddTime {
  public static void main( String[] args ) {
	 
	 Timestamp timestamp = new Timestamp(new Date().getTime());
 
    Timestamp currentTimestamp = new Timestamp(new Date().getTime());
    
    System.out.println("current "+currentTimestamp);
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(currentTimestamp.getTime());
 
  
 
    // subract  3 days
    cal.setTimeInMillis(timestamp.getTime());
    cal.add(Calendar.DAY_OF_MONTH, - 5);
    timestamp = new Timestamp(cal.getTime().getTime());
    System.out.println("subracted: "+timestamp);
 
    
  }
}
 