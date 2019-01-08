package com.codebytes.core;

import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GW2TP implements Runnable{
	
    @Autowired
    InfoGet info;
    
    @Autowired
    UIDataPrep uiDataPrep;
    
    ArrayList<Long> itemIds;
    
    Item[] items;
    
    Price[] prices;
    
    @Autowired
    Genie genie;
    
    @Autowired
    DB db;
    
    long UPDATE_FREQUENCY = 15 * 60 * 60 * 1000L;
    
    BufferedReader br;
    
    ExchangeRate exchangeRate;
    
    ExecutorService executor;
    
    public Thread thread;
    
    static LocalDateTime lastQuarterLogged;
    
    public GW2TP(){}
    
    public Genie getGenie() { return genie; }
    public void setGenie(Genie genie) { this.genie = genie; }
    
    public void setDB(DB db) { this.db = db; }
    public DB getDB() {	return db; }
    
    public void init() {
     	try {
	    	executor = Executors.newFixedThreadPool(1);
	        //Max items from TP (only for testing)
	    	thread = new Thread(this);
	    	thread.start();
     	}catch(Exception e) {
     		e.printStackTrace();
     	}
    }
    
    public Item[] computeProfitableItems(int user_gold) {
        Item[] sorted = genie.sortItemsByMaxProfit(items);
        //genie.displayTopItems(sorted, max, user_gold * 100 * 100);
        return sorted;
    }

    public LocalDateTime getLastQuarter() {
    	LocalDateTime time = LocalDateTime.now();
    	LocalDateTime lastQuarter = time.truncatedTo(ChronoUnit.HOURS)
    	                                .plusMinutes(15 * (time.getMinute() / 15));
    	return lastQuarter;
    }
    
	@Override
	public void run() {
		try {
			while(true) {
		    	LocalDateTime lastQuarter = getLastQuarter();
		    	
		    	while(lastQuarterLogged != null && lastQuarter.isEqual(lastQuarterLogged)) {
		    		Thread.sleep(1*60*1000L);
		    		System.out.println("Slept "+(1)+"minute.");
		    		lastQuarter = getLastQuarter();
		    	}
		    	
		    	lastQuarterLogged = lastQuarter;
		    	
	        	info.updateInfo(lastQuarter);

	        	items = info.items;
	        	prices = info.prices;
	        	
	        	Item[] pItems = computeProfitableItems(100);
	        	uiDataPrep.prepareRecommendations(pItems, 10);
	        	
	        	//displayProfitableItems(40);
		    }
		}
		catch(Exception e) {
	       	System.out.println("Exception in update thread : "+e.getMessage());
	       	e.printStackTrace();
	    }
	}
}
