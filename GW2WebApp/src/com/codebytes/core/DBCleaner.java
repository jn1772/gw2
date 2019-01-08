package com.codebytes.core;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBCleaner {

    @Autowired
    DB db;

    private ScheduledExecutorService scheduler;
    
    String dateDiff = "datediff (current_date(), price.timestamp";
    String moreThanWeekLessThanMonthClean = "delete * from price where "+dateDiff+" >= 7"
    		+ "and "+dateDiff+" <= 30"
      		+ "minute(price.timestamp) <> 0";
    String moreThanMonthClean = "delete * from price where "+dateDiff+" > 30"
    		+ "and hour(price.timestamp) <> 0";
    
    String moreThanWeekLessThanMonthGet = "select * from price where "+dateDiff+" >= 7"
    		+ "and "+dateDiff+" <= 30"
      		+ "minute(price.timestamp) <> 0";
    String moreThanMonthGet = "select * from price where "+dateDiff+" > 30"
    		+ "and hour(price.timestamp) <> 0";
    
    public DBCleaner() {
    	scheduler =  Executors.newScheduledThreadPool(1);
    	initCleanerService();
    	System.out.println("DB Cleaner set to run after every 24 hours");
    }
    
	public void initCleanerService() {
		
//    	ScheduledFuture<?> future =
        scheduler.scheduleAtFixedRate(new Runnable() {
        	public void run() {
    	    	Session session = db.getSession();
    	    	Transaction tx = session.beginTransaction();
    	    	Query<?> q1 = session.createNativeQuery(moreThanWeekLessThanMonthGet);
    	    	Query<?> q2 = session.createNativeQuery(moreThanMonthGet);
    	    	List<?> r1 = q1.getResultList();
    	    	List<?> r2 = q2.getResultList();
    	    	for(Object p : r1) {
    	    		Price price = (Price)p;
    	    		System.out.println(price.id+" "+price.timestamp+" about to be deleted");
    	    	}
    	    	for(Object p : r2) {
    	    		Price price = (Price)p;
    	    		System.out.println(price.id+" "+price.timestamp+" about to be deleted");
    	    	}
    	    	tx.commit();
    	    	session.close();
    	   }
		}, 0, 24, TimeUnit.HOURS);
	}
}

/*
String moreThanWeekClean = "select * from price where "+dateDiff+" >= 2 "
+ "and "+dateDiff+" < 2 "
+ "and id=24 and minute(price.timestamp) = 00";

"select * from price where "+dateDiff+" > 30 and hour(price.timestamp) <> 0"
*/