package com.codebytes.fetchers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.client.utils.URIBuilder;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codebytes.base.ExchangeRate;
import com.codebytes.base.Item;
import com.codebytes.base.Price;
import com.codebytes.database.DB;
import com.codebytes.threads.Driver;

/*
This class basically requests info from the GW2 servers and populates the local
structures.
*/
@Component
public
class InfoGet{
    
	@Autowired
	Driver d;
	
	@Autowired
    DB db;
    
	@Autowired
	Indexer indexer;
	
	public ArrayList<Long> itemIds;
	public ArrayList<Item> items;
	public ArrayList<Price> prices;
    
    int max;
    
    MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
    HttpClient httpClient; 
    
    static boolean debug = false;
    static boolean respDebug = true;
    
    static int maxThreads = 200;
    
	InfoGet(){
//    	itemIds = getItemsIds();
//    	items = initItems();
//    	prices = new ArrayList<>();
//    	for(int i=0;i<itemIds.size();++i)prices.add(new Price());
	}
	
    public void updateInfo(LocalDateTime dt) {
    	itemIds = getItemsIds();
    	items = initItems();
    	prices = new ArrayList<>();
    	for(int i=0;i<itemIds.size();++i)prices.add(new Price());
    	
    	max = 200;//itemIds.size(); //400
    	
    	getItemsInfoFast();
    	getItemPricesFast(dt);
    	updateExchangeRate();
    	
    	d.setItems(items);
    	d.setPrices(prices);
    	d.setItemIds(itemIds);
    }

    /*
     * Fetch info from GW2 servers.
     * Takes parameters and values
     */
    String fetchInfo(String path, String parameter1, String p1value){
    	if(httpClient == null) {
    		httpClient = new HttpClient(connectionManager);
    	}
        try{
            URIBuilder urib = new URIBuilder()
                        .setScheme("https")
                        .setHost("api.guildwars2.com")
                        .setPort(443)
                        .setPath(path);
            
            if(parameter1 != null)
                urib = urib.addParameter(parameter1, p1value);
            
            URI uri = urib.build();
            
            
            //HttpClient client = HttpClientBuilder.create().build();
            GetMethod method = new GetMethod(uri.toString());
            int response = httpClient.executeMethod(method);
            
            if(response < 0)System.out.println("Error response : "+response);
            
            //HttpResponse response = httpClient.executeMethod(get);
            String ret = method.getResponseBodyAsString();
            method.releaseConnection();
            return ret;
        }catch (URISyntaxException | IOException e){
            System.err.println("Exception! : "+e.toString());
            e.printStackTrace();
        }
        return null;
    }

    /*
     * Get item id list from GW2 servers
     */
    ArrayList<Long> getItemsIds() {
        String response = fetchInfo("/v2/items", null, null);

        JSONParser parser = new JSONParser();
        Object obj = null;
		try {
			obj = parser.parse(response);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        JSONArray itemIds = (JSONArray) obj; 
        ArrayList<Long> ret = new ArrayList<>();
        Iterator<?> it = itemIds.iterator();
        while(it.hasNext())ret.add((long)it.next());
        return ret;
    }
    
    /*
     * Init the item array for given ids fetched from getItemIds()
     */
    ArrayList<Item> initItems(){
        int max = 0;
        for(int i=0;i<itemIds.size();++i){
            max = Math.max((int)(long)itemIds.get(i), max);
        }
        ArrayList<Item> items = new ArrayList<>();
        for(int i=0;i<max+1;++i)items.add(new Item());
        
        for(int i=0;i<itemIds.size();++i){
            Item item = items.get((int)(long)itemIds.get(i));
            item.id = itemIds.get(i);
        }
        return items;
    }

    /*
    Get Item Prices at TP for 'max' number of items in items[]. 200 at a time.
    */
    ArrayList<Long> getItemsListings(int maxx) throws Exception {
        int max = maxx;//ids.size();
        int processed = 0;
        while(processed < max){
            StringBuilder sbb = new StringBuilder();
            int j;
            for (j = processed; j < Math.min(200 + processed, max); ++j) {
                sbb.append(items.get((int)(long)itemIds.get(j)).id).append(",");
            }
            processed = j;
            sbb.deleteCharAt(sbb.length()-1);
            
            String response = fetchInfo("/v2/commerce/listings", "ids", sbb.toString());
            
            System.out.println("items (commerce listings) processed : "+processed+"/"+max);
            JSONParser parser = new JSONParser();
            try{
                Object obj = parser.parse(response);
                JSONArray itemIds = (JSONArray) obj;
                Iterator<?> iterator = itemIds.iterator();

                int curr=0;
                while (iterator.hasNext()) {

                    JSONObject object = (JSONObject) iterator.next();
                    JSONArray buys = (JSONArray) object.get("buys");
                    JSONArray sells = (JSONArray) object.get("sells");
                    int id = (int)((long)object.get("id"));

                    Iterator<?> itt = buys.iterator();
                    while(itt.hasNext()){
                        JSONObject listing = (JSONObject)itt.next();
                        long listings = (long)listing.get("listings");
                        long price = (long)listing.get("unit_price");
                        long quantity = (long)listing.get("quantity");
                        if(debug)System.out.println("curr: "+curr+
                                " Buy : [listings, price, quantity]: "+
                                listings+", "+price+", "+quantity);

                        items.get(id).bListings.add(listings);
                        items.get(id).bUnitPrice.add(price);
                        items.get(id).bQuantity.add(quantity);
                    }

                    itt = sells.iterator();
                    while(itt.hasNext()){
                        JSONObject listing = (JSONObject)itt.next();
                        long listings = (long)listing.get("listings");
                        long price = (long)listing.get("unit_price");
                        long quantity = (long)listing.get("quantity");

                        if(debug)System.out.println("curr: "+curr+
                                " Sell : [listings, price, quantity]: "+
                                listings+", "+price+", "+quantity);
                        items.get(id).sListings.add(listings);
                        items.get(id).sUnitPrice.add(price);
                        items.get(id).sQuantity.add(quantity);
                    }
                    curr++;
                }
            }catch (ClassCastException e){
                System.err.println("!Exception (Class Cast). Continuing...");
                System.err.println("Item ids were : "+sbb);
            }
        }
        return null;
    }

    /*
    Exchange Rate Data
    */
    int getCoinsForGems(int nGems){
        String response = fetchInfo("/v2/commerce/exchange/gems", "quantity", ""+nGems);
        Integer cpg = 0, quantity = 0;
        try{
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(response);
            //System.out.println("response  "+response);
            cpg = (int)(long)obj.get("coins_per_gem");
            quantity = (int)(long)obj.get("quantity");
            d.exchangeRate.coins_per_gem_buy = cpg;
            //write db insertion code here
        }catch(ParseException e){
            System.out.println("Parse Exception for response : "+response);
        }
        return quantity;
    }
    
    int getGemsForCoins(int nCoins){
       String response = fetchInfo("/v2/commerce/exchange/coins", "quantity", ""+nCoins);
       Integer cpg = 0, quantity = 0;
        try{
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(response);
            //System.out.println("response  "+response);
            cpg = (int)(long)obj.get("coins_per_gem");
            quantity = (int)(long)obj.get("quantity");
            d.exchangeRate.coins_per_gem_sell = cpg;
            //write db insertion code here
        }catch(ParseException e){
            System.out.println("Parse Exception for response : "+response);
        }
        
        return quantity;
    }
    
    void updateExchangeRate() {
    	String resp_cpg = fetchInfo("/v2/commerce/exchange/gems", "quantity", ""+10000);
    	String resp_gpc = fetchInfo("/v2/commerce/exchange/coins", "quantity", ""+1000000);
    	
        Integer cpg = 0, gpc = 0;
        JSONParser parser = new JSONParser();
        
        Session session = db.getSession();
        Transaction tx = session.beginTransaction();
        try{
            JSONObject obj = (JSONObject)parser.parse(resp_cpg);
            
            ExchangeRate er = new ExchangeRate();
            
            cpg = (int)(long)obj.get("coins_per_gem");
            er.coins_per_gem_buy = cpg;
            
            obj = (JSONObject)parser.parse(resp_gpc);
            gpc = (int)(long)obj.get("coins_per_gem");
            er.coins_per_gem_sell = gpc;
            
            er.setTimestamp();
            er.setCPGB(cpg);
            er.setCPGS(gpc);
            
            d.exchangeRate = er;
            
            session.save(er);
            tx.commit();
            session.close();
        }catch(Exception e){
            System.out.println("Exception resp_cpg : "+resp_cpg+"\nresp_gpc : "+resp_gpc);
            e.printStackTrace();
        }
    }
    
    /*
    Get item info for 'max' number of Items in items[]. 200 at a time.
    */
    void getItemsInfoFast() {
    	 try {
    		items = initItems();
    		
    		int offset = 0;
    		
    		
    		ExecutorService executor = Executors.newFixedThreadPool(maxThreads);
	    	
    		ArrayList<Fetcher> fetchers = new ArrayList<>();
    			
 	    	while(offset < max) {
 	    		int i=0;
 		    	
 	    		for(i=0;i<maxThreads && offset < max;++i) {
 		    		Fetcher fetcher = new Fetcher(this, offset, "v2/items", "ids"); 
 		    		fetchers.add(fetcher);
 		    		executor.submit(fetcher);
 		    		offset = offset + 200;
 		    	}
 	    	}
 	    	
	    	executor.shutdown();
	    	executor.awaitTermination(5, TimeUnit.MINUTES);
	    	
	    	for(Fetcher f:fetchers) {
 		    		System.out.println("Info fetched for : "+f.processed+"/"+max+" items.");

 		            JSONParser parser = new JSONParser();
 		            Object obj = parser.parse(f.response);
 		            JSONArray itemIds = (JSONArray) obj;
 		            Iterator<?> iterator = itemIds.iterator();

 		            try {
	 		            while (iterator.hasNext()) {
	 		                
	 		                JSONObject object = (JSONObject) iterator.next();
	 		                int id = (int)(long)object.get("id");
	 		                String chat_link = (String)object.get("chat_link");
	 		                String name = (String)object.get("name");
	 		                String icon = (String)object.get("icon");
	 		                String description = (String)object.get("description");
	 		                String type = (String)object.get("type");
	 		                String rarity = (String)object.get("rarity");
	 		                int level = (int)(long)object.get("level");
	 		                int vendor_value = (int)(long)object.get("vendor_value");
	 		                Long df = (Long)object.get("default_skin");
	 		                Integer default_skin = null;
	 		                if(df!=null) default_skin = (int)(long)df;
	 		                	 		                
	 		                items.get(id).setChatLink(chat_link);
	 		                items.get(id).setName(name);
	 		                items.get(id).setIcon(icon);
	 		              	items.get(id).setDescription(description);
	 		             	items.get(id).setType(Item.Type.valueOf(type));
	 		            	items.get(id).setRarity(Item.Rarity.valueOf(rarity));
	 		           		items.get(id).setLevel(level);
	 		          		items.get(id).setVendorValue(vendor_value);
	 		         		items.get(id).setDefaultSkin(default_skin);
	 		            }
 		    	}catch (ClassCastException e){
	                System.err.println("!Exception (Class Cast). Continuing...");
	                System.err.println("Item ids were : "+f.request_string);
	            }
    	   }	
    	}
     	catch(Exception e) {
     		System.out.println("Exception : "+e.getMessage());
     		e.printStackTrace();
     	}
    }

    void getItemPricesFast(LocalDateTime lastQuarter) {
        
    	try {
    		int offset = 0;
    		
        	Session session = db.getSession();
        	Transaction tx = session.beginTransaction();

        	ExecutorService executor = Executors.newFixedThreadPool(maxThreads);
	    	
    		ArrayList<Fetcher> fetchers = new ArrayList<>();
    			
 	    	while(offset < max) {
 	    		int i=0;
 		    	
 	    		for(i=0;i<maxThreads && offset < max;++i) {
 		    		Fetcher fetcher = new Fetcher(this, offset, "v2/commerce/prices", "ids"); 
 		    		fetchers.add(fetcher);
 		    		executor.submit(fetcher);
 		    		offset = offset + 200;
 		    	}
 	    	}

 	    	executor.shutdown();
 	    	executor.awaitTermination(5, TimeUnit.MINUTES);
 	    	
 	    	for(Fetcher f:fetchers) {
		    		System.out.println("Price fetched for : "+f.processed+"/"+max+" items.");
		            JSONParser parser = new JSONParser();
		            try{
		                Object obj = parser.parse(f.response);
		                JSONArray itemIds = (JSONArray) obj;
		                Iterator<?> iterator = itemIds.iterator();

		                while (iterator.hasNext()) {

		                    JSONObject object = (JSONObject) iterator.next();
		                    
		                    int id = (int)(long)object.get("id");
		                    JSONObject buys = (JSONObject) object.get("buys");
		                    JSONObject sells = (JSONObject) object.get("sells");

		                    Integer b_quant = (int)(long)buys.get("quantity");
		                    double b_price = (int)(long)buys.get("unit_price");
		                    
		                    Integer s_quant = (int)(long)sells.get("quantity");
		                    double s_price = (int)(long)sells.get("unit_price");
		                    
		                    items.get(id).demand = b_quant;
		                    items.get(id).supply = s_quant;
		                    items.get(id).setBuyUnitPrice(b_price);
		                    items.get(id).setSellUnitPrice(s_price);
		                    items.get(id).calcProfit();
		                    items.get(id).setLastUpdated();
		                    
		                    prices.get(id).setId(id);
		                    prices.get(id).setBuyPrice(b_price);
		                    prices.get(id).setSellPrice(s_price);
		                    prices.get(id).setBuyQuantity(b_quant);
		                    prices.get(id).setSellQuantity(s_quant);
		                    prices.get(id).setTimeStamp(Date.from(lastQuarter.atZone(ZoneId.systemDefault()).toInstant()));
		                    prices.get(id).setProfit();
		                    
		                    session.save(prices.get(id));
		                }
		            }catch (ClassCastException e){
		                System.err.println("!Exception (Class Cast). Continuing...");
		                System.err.println("Item ids were : "+f.request_string);
		            }
	    	}
 	    	tx.commit();
	    	session.close();
    	}catch(Exception e) {
    		System.out.println("Exception : "+e.getMessage());
    		e.printStackTrace();
    	}
    }
}

class Fetcher implements Runnable{

	ArrayList<Item> items;
	ArrayList<Long> ids;
	int maxx;
	
	InfoGet info;
	
	public 
	int offset;
	String request_string, response;
	int processed;
	String path, param1, p1value;
	
	public Fetcher(InfoGet info, int offset, String path, String parameter1) {
		this.info = info;
		items = info.items;
		ids = info.itemIds;
		maxx = info.max;
		
		this.offset = offset;
		this.path = path;
		this.param1 = parameter1;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
	        processed = 0;
	        
            StringBuilder sbb = new StringBuilder();
            int j;
            for (j = offset; j < Math.min(200 + offset, maxx); ++j) {
                sbb.append(items.get((int)(long)ids.get(j)).id).append(",");
            }
            processed = j;
            sbb.deleteCharAt(sbb.length()-1);
            request_string = sbb.toString();
            response = info.fetchInfo(path, param1, sbb.toString());
            System.out.println("Completed : "+offset+"/"+maxx);

            System.out.println(Runtime.getRuntime().freeMemory()/(1024*1024));
    	}catch(Exception e) {
    		System.out.println("Exception : "+e.getMessage());
    		e.printStackTrace();
    	}
	}
}