import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Date;

public class GW2TP {
    
    static InfoGet info;
    static ArrayList<Long> itemIds;
    static Item[] items;
    static Price[] prices;
    static Genie genie;
    static DB db;
    static int max;
    static boolean was_old, exists, update_in_progress;
    static BufferedReader br;
    static ExchangeRate exchangeRate;
    
    public static void main(String[] args) throws Exception{
        //Connect to DB
        db = new DB();
        
        //Connects to GW2 servers and gets required Information
        info = new InfoGet();
	
        //List of all item IDS returned by server
        itemIds = info.getItemsIds();

        //Max items from TP (only for testing)
        max = itemIds.size();

        //Struct Items
        items = info.initItems(itemIds);
        
        //Prices
        prices = new Price[items.length];
        
        //Calculator
        genie = new Genie();
        
        exchangeRate = new ExchangeRate();
        
        while(true) {
        	update_new_price_data();
        }
    }
    
    static Date last_update;
    
    //Every 15 mins
    static long UPDATE_FREQUENCY = 15 * 60 * 60 * 1000L;
    
    public static void update_new_price_data() {
    	Date now = new Date();
    	if(last_update == null)last_update = now;
    	
        boolean old = 
        		(Math.abs(now.getTime() - last_update.getTime()) > UPDATE_FREQUENCY 
        	    || last_update == now);
        try {
	        if(old) {
	        	update_in_progress = true;
				//info.getItemsInfo(items, itemIds, max, db);
	        	info.getItemPricesFast(items, prices, itemIds, max, db, 0);
	        	last_update = now;
	        	update_exchange_rate();
				update_in_progress = false;
	        	//displayProfitableItems(40);
	        } else {
				Thread.sleep(UPDATE_FREQUENCY);
				System.out.println("Slept "+(UPDATE_FREQUENCY/(60*60*1000L))+"minutes.");
	        }
        }catch(Exception e) {
        	System.out.println("Exception in update thread : "+e.getMessage());
        	e.printStackTrace();
        }
    }
    
    public static Item[] displayProfitableItems(int user_gold) throws Exception {
        Item[] sorted = genie.sortItemsByMaxProfit(items);
        genie.displayTopItems(sorted, max, user_gold * 100 * 100);
        return sorted;
    }
    
    public static void update_exchange_rate() {
    	System.out.println("Updating exchange rate [Coins-Gems]");
    	info.update_exchange_rate(db);
    }
    
    //How many gems for this gold?
    public static int exchange_gold_with_gems(int gold) {
    	return info.getGemsForCoins(db, gold);
    }
    
    //How many gold coins for these many gems?
    public static int exchange_gems_with_gold(int gems) {
    	return info.getCoinsForGems(db, gems);
    }
}
