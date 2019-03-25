package com.codebytes.threads;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codebytes.DisplayItems.DisplayItems;
import com.codebytes.DisplayItems.PriceRecommender;

@Component
public class DataUpdater implements Runnable{
	
	@Autowired
	Driver d;
	
	ArrayList<DisplayItems> di;
	
	@Autowired
	public PriceRecommender byPriceDisplay;
	
	public DataUpdater() {
		di = new ArrayList<>();
		byPriceDisplay = new PriceRecommender();
	}
	
	@PostConstruct
	void addRecommenders() {
		di.add(byPriceDisplay);
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
		    	
		    	d.info.updateInfo(lastQuarter);	        	
	        	d.indexer.beginIndexing();
	        	
	        	for(DisplayItems displayItem:di) {
	        		displayItem.refreshItems();
	        	}
		    }
		}
		catch(Exception e) {
	       	System.out.println("Exception in update thread : "+e.getMessage());
	       	e.printStackTrace();
	    }
	}
	
	public LocalDateTime getLastQuarter() {
    	LocalDateTime time = LocalDateTime.now();
    	LocalDateTime lastQuarter = time.truncatedTo(ChronoUnit.HOURS)
    	                                .plusMinutes(15 * (time.getMinute() / 15));
    	return lastQuarter;
    }
	
    static LocalDateTime lastQuarterLogged;
}
