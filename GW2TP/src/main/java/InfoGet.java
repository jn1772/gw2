import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
This class basically requests info from the GW2 servers and populates the local
structures.
*/
class InfoGet{
    static boolean debug = false;
    static boolean respDebug = true;
    
    InfoGet(boolean d){
        debug = d;
    }
    
    InfoGet(){
        debug = false;
    }
    
    /*
     * Fetch info from GW2 servers.
     * Takes parameters and values
     */
    String fetchInfo(String path, String parameter1, String p1value){
        try{
            URIBuilder urib = new URIBuilder()
                        .setScheme("https")
                        .setHost("api.guildwars2.com")
                        .setPort(443)
                        .setPath(path);
            
            if(parameter1 != null)
                urib = urib.addParameter(parameter1, p1value);
            
            URI uri = urib.build();
            
            HttpGet httpGet = new HttpGet(uri);
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(httpGet);
        
            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                //if(respDebug)System.out.println(line);
                sb.append(line);
            }
            return sb.toString();
        }catch (URISyntaxException | IOException e){
            System.err.println("Exception! : "+e.toString());
        }
        return null;
    }

    /*
     * Get item id list from GW2 servers
     */
    ArrayList<Long> getItemsIds() throws Exception {
        String response = fetchInfo("/v2/items", null, null);

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response);
        JSONArray itemIds = (JSONArray) obj; 
        ArrayList<Long> ret = new ArrayList<>();
        Iterator it = itemIds.iterator();
        while(it.hasNext())ret.add((long)it.next());
        return ret;
    }
    
    /*
     * Init the item array for given ids fetched from getItemIds()
     */
    Item[] initItems(ArrayList<Long> ids){
        int max = 0;
        for(int i=0;i<ids.size();++i){
            max = Math.max((int)(long)ids.get(i), max);
        }
        Item[] items = new Item[max+1];
        for(int i=0;i<ids.size();++i){
            items[(int)(long)ids.get(i)] = new Item();
            items[(int)(long)ids.get(i)].id = ids.get(i);
        }
        return items;
    }

    /*
    Get item info for 'max' number of Items in items[]. 200 at a time.
    */
    void getItemsInfo(Item items[], ArrayList<Long> ids, int maxx, DB db) throws Exception {
        int processed = 0;
        int max = maxx;//ids.size();
        
        while(processed < max){
            StringBuilder sbb = new StringBuilder();
            int j;
            for (j = processed; j < Math.min(200 + processed, max); ++j) { 
                sbb.append(items[(int)(long)ids.get(j)].id).append(",");
            }
            sbb.deleteCharAt(sbb.length()-1);
            processed = j;
            
            if(debug);
            System.out.println("Info fetched for : "+processed+"/"+max+" items.");

            String response = fetchInfo("/v2/items", "ids", sbb.toString());
            
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response);
            JSONArray itemIds = (JSONArray) obj;
            Iterator<Object> iterator = itemIds.iterator();

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
                
                items[id].chat_link = chat_link;
                items[id].name = name;
                items[id].icon = icon;
                items[id].description = description;
                items[id].type = type;
                items[id].rarity = rarity;
                items[id].level = level;
                items[id].vendor_value = vendor_value;
                items[id].default_skin = default_skin;
            }
        }
    }

    /*
    Get Item Prices at TP for 'max' number of items in items[]. 200 at a time.
    */
    ArrayList<Long> getItemsListings(Item []items, ArrayList<Long> ids, int maxx) throws Exception {
        int max = maxx;//ids.size();
        int processed = 0;
        while(processed < max){
            StringBuilder sbb = new StringBuilder();
            int j;
            for (j = processed; j < Math.min(200 + processed, max); ++j) {
                sbb.append(items[(int)(long)ids.get(j)].id).append(",");
            }
            processed = j;
            sbb.deleteCharAt(sbb.length()-1);
            
            String response = fetchInfo("/v2/commerce/listings", "ids", sbb.toString());
            
            System.out.println("items (commerce listings) processed : "+processed+"/"+max);
            JSONParser parser = new JSONParser();
            try{
                Object obj = parser.parse(response);
                JSONArray itemIds = (JSONArray) obj;
                Iterator<Object> iterator = itemIds.iterator();

                int curr=0;
                while (iterator.hasNext()) {

                    JSONObject object = (JSONObject) iterator.next();
                    JSONArray buys = (JSONArray) object.get("buys");
                    JSONArray sells = (JSONArray) object.get("sells");
                    int id = (int)((long)object.get("id"));

                    Iterator<Object> itt = buys.iterator();
                    while(itt.hasNext()){
                        JSONObject listing = (JSONObject)itt.next();
                        long listings = (long)listing.get("listings");
                        long price = (long)listing.get("unit_price");
                        long quantity = (long)listing.get("quantity");
                        if(debug)System.out.println("curr: "+curr+
                                " Buy : [listings, price, quantity]: "+
                                listings+", "+price+", "+quantity);

                        items[id].b_listings.add(listings);
                        items[id].b_unit_price.add(price);
                        items[id].b_quantity.add(quantity);
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
                        items[id].s_listings.add(listings);
                        items[id].s_unit_price.add(price);
                        items[id].s_quantity.add(quantity);
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
    int getCoinsForGems(DB db, int nGems){
        String response = fetchInfo("/v2/commerce/exchange/gems", "quantity", ""+nGems);
        Integer cpg = 0, quantity = 0;
        try{
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(response);
            //System.out.println("response  "+response);           
            cpg = (int)(long)obj.get("coins_per_gem");
            quantity = (int)(long)obj.get("quantity");
            GW2TP.exchangeRate.coins_per_gem_buy = cpg;
            //write db insertion code here
        }catch(ParseException e){
            System.out.println("Parse Exception for response : "+response);
        }
        return quantity;
    }
    
    int getGemsForCoins(DB db, int nCoins){
       String response = fetchInfo("/v2/commerce/exchange/coins", "quantity", ""+nCoins);
       Integer cpg = 0, quantity = 0;
        try{
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(response);
            //System.out.println("response  "+response);
            cpg = (int)(long)obj.get("coins_per_gem");
            quantity = (int)(long)obj.get("quantity");
            GW2TP.exchangeRate.coins_per_gem_sell = cpg;
            //write db insertion code here
        }catch(ParseException e){
            System.out.println("Parse Exception for response : "+response);
        }
        
        return quantity;
    }
    
    void update_exchange_rate(DB db) {
    	String resp_cpg = fetchInfo("/v2/commerce/exchange/gems", "quantity", ""+10000);
    	String resp_gpc = fetchInfo("/v2/commerce/exchange/coins", "quantity", ""+1000000);
    	
        Integer cpg = 0, gpc = 0, quantity = 0;
        JSONParser parser = new JSONParser();
        
        Session session = db.getSession();
        Transaction tx = session.beginTransaction();
        try{
            JSONObject obj = (JSONObject)parser.parse(resp_cpg);
            cpg = (int)(long)obj.get("coins_per_gem");
            GW2TP.exchangeRate.coins_per_gem_buy = cpg;
            
            obj = (JSONObject)parser.parse(resp_gpc);
            gpc = (int)(long)obj.get("coins_per_gem");
            GW2TP.exchangeRate.coins_per_gem_sell = gpc;
            
            ExchangeRate er = new ExchangeRate();
            er.setTimestamp();
            er.setCPGB(cpg);
            er.setCPGS(gpc);
            
            session.save(er);
            tx.commit();
        }catch(Exception e){
            System.out.println("Exception resp_cpg : "+resp_cpg+"\nresp_gpc : "+resp_gpc);
            e.printStackTrace();
        }
    }
    
    void getItemPricesFast(Item[] items, Price[] prices, ArrayList<Long> ids, int maxx, DB db, int offset) {
        try {
	    	while(offset < maxx) {
	    		int maxThreads=50;
	    		
	    		ArrayList<Session> sessions = new ArrayList<>();
		    	ArrayList<Transaction> txs = new ArrayList<>();
		    	
	    		Fetcher fetchers[] = new Fetcher[maxThreads];
	    		Thread threads[] = new Thread[maxThreads];
	    		
	    		int i=0;
		    	for(i=0;i<maxThreads && offset < maxx;++i) {
		    		Session session = db.getSession();
			        Transaction tx = session.beginTransaction();
			        sessions.add(session);
			        txs.add(tx);
		    		fetchers[i] = new Fetcher(items, prices, ids, maxx, db, this, offset, session);
		    		threads[i] = new Thread(fetchers[i]);
		    		threads[i].start();
		    		offset = offset + 200;
		    	}
		    	for(int j=0;j<i;++j) {
		    		threads[j].join();
		    	}
		    	for(Transaction tx:txs) {
		    		tx.commit();
		    	}
		    	for(Session session:sessions) {
		    		session.close();
		    	}
	    	}
	    	
    	}catch(Exception e) {
    		System.out.println("Exception : "+e.getMessage());
    		e.printStackTrace();
    	}
    }
}

class Fetcher implements Runnable{

	Item[] items;
	Price[] prices;
	ArrayList<Long> ids;
	int maxx;
	DB db;
	InfoGet info;
	int offset;
	Session session;
	
	public Fetcher(Item[] items, Price[] prices, ArrayList<Long> ids, int maxx, DB db, InfoGet info, int offset, Session session) {
		this.items = items;
		this.prices = prices;
		this.ids = ids;
		this.maxx = maxx;
		this.db = db;
		this.info = info;
		this.offset = offset;
		this.session = session;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
	        int processed = 0;
	        
            StringBuilder sbb = new StringBuilder();
            int j;
            for (j = offset; j < Math.min(200 + offset, maxx); ++j) {
                sbb.append(items[(int)(long)ids.get(j)].id).append(",");
            }
            processed = j;
            sbb.deleteCharAt(sbb.length()-1);
            
            String response = info.fetchInfo("/v2/commerce/prices", "ids", sbb.toString());
            
            System.out.println("Price fetched for : "+processed+"/"+maxx+" items.");
            JSONParser parser = new JSONParser();
            try{
                Object obj = parser.parse(response);
                JSONArray itemIds = (JSONArray) obj;
                Iterator<Object> iterator = itemIds.iterator();

                while (iterator.hasNext()) {

                    JSONObject object = (JSONObject) iterator.next();
                    
                    int id = (int)(long)object.get("id");
                    JSONObject buys = (JSONObject) object.get("buys");
                    JSONObject sells = (JSONObject) object.get("sells");

                    Integer b_quant = (int)(long)buys.get("quantity");
                    double b_price = (int)(long)buys.get("unit_price");
                    
                    Integer s_quant = (int)(long)sells.get("quantity");
                    double s_price = (int)(long)sells.get("unit_price");
                    
                    items[id].b_num = b_quant;
                    items[id].b_upr = b_price;
                    
                    items[id].s_num = s_quant;
                    items[id].s_upr = s_price;
                    
                    items[id].calcProfit();
                    
                    prices[id] = new Price();
                    prices[id].setId(id);
                    prices[id].setBuyPrice(b_price);
                    prices[id].setSellPrice(s_price);
                    prices[id].setBuyQuantity(b_quant);
                    prices[id].setSellQuantity(s_quant);
                    prices[id].setTimeStamp();
                    prices[id].setProfit();
                    
                    session.save(prices[id]);
                }
            }catch (ClassCastException e){
                System.err.println("!Exception (Class Cast). Continuing...");
                System.err.println("Item ids were : "+sbb);
            }
    	}catch(Exception e) {
    		System.out.println("Exception : "+e.getMessage());
    		e.printStackTrace();
    	}
	}
}