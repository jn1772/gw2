import java.util.Arrays;
import java.util.Comparator;

public class Genie {
    
    Item[] sortItemsByMaxProfit(Item[] items) throws Exception{
        Item[] curItems = new Item[items.length];
        
        System.arraycopy(items, 0, curItems, 0, curItems.length);
        
        Arrays.sort(curItems, Comparator.nullsLast((Item a, Item b) -> {
            if(a.profit > b.profit)return -1;
            else if(a.profit < b.profit)return 1;
            return 0;
        }));
        return curItems;
    }
    
    void displayTopItems(Item[] item, int top, int max_buy_price) throws Exception{
        for(int i=0;i<top;++i){
            Item it = item[i];
            
            if(it.name == null || it.b_upr == null || it.b_upr > max_buy_price)
            	continue;
            
            /*
            System.out.println("Get profit for item : "+it.name 
                    +" lvl "+it.level+" desc : "+it.description
                    +" type : "+it.type+" id : "+it.id);
            */
            
            if(it.profit == 0)continue;
            
            System.out.println(
            		"--------------------------------------\n"+
                    it.name+"\n"+
                    "--------------------------------------\n"+
                    "Buy at: "+CoinFormat.getPrice(it.b_upr)+"\n"+
                    "Sell at: "+CoinFormat.getPrice(it.s_upr)+"\n"+
                    "Profit: "+CoinFormat.getPrice(it.profit)+"\n"+
                    "--------------------------------------\n"
            );
        }
    }
}
