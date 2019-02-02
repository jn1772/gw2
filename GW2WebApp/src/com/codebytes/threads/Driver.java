package com.codebytes.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codebytes.base.ExchangeRate;
import com.codebytes.base.Item;
import com.codebytes.base.Price;
import com.codebytes.database.DB;
import com.codebytes.fetchers.Indexer;
import com.codebytes.fetchers.InfoGet;

@Component
public class Driver{
	
    @Autowired
	public InfoGet info;
    
    @Autowired
    Indexer indexer;
    
    @Autowired
    DB db;
    
    public ArrayList<Long> itemIds;
    public ArrayList<Item> items;
    public ArrayList<Price> prices;
    
    public void setItems(ArrayList<Item> items) {this.items = items;}
    public void setPrices(ArrayList<Price> prices) {this.prices = prices;}
    public void setItemIds(ArrayList<Long> itemIds) {this.itemIds = itemIds;}
    public void setDB(DB db) { this.db = db; }

    public DB getDB() {	return db; }
    public Item getItem(int id) { return items.get(id); }
    public ExchangeRate exchangeRate;
    public Thread thread;
    ExecutorService executor;   
    
    long UPDATE_FREQUENCY = 15 * 60 * 60 * 1000L;
    
	public List<Price> getAllPriceInfo(int id){
    	String hql = "FROM Price p WHERE p.id="+id+" ORDER BY timestamp";
    	Session session = db.getSession();
    	Query query = session.createQuery(hql);
    	List<Price> results = query.list();
    	for(Price i:results) {
    		System.out.println(i.id+" time : "+i.timestamp);
    	}
    	return results;
    }
	
	@PostConstruct
    public void init() {
     	try {
	    	executor = Executors.newFixedThreadPool(1);
	        //Max items from TP (only for testing)
	    	thread = new Thread(new DataUpdater(this));
	    	thread.start();
     	}catch(Exception e) {
     		e.printStackTrace();
     	}
    }
}
