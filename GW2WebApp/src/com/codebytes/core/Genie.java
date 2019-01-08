package com.codebytes.core;

import java.util.Arrays;
import java.util.Comparator;

import org.springframework.stereotype.Component;

@Component
public class Genie {
    
	public Genie() {}

	Item[] sortItemsByMaxProfit(Item[] items) {
        Item[] curItems = new Item[items.length];
        
        System.arraycopy(items, 0, curItems, 0, curItems.length);
        
        Arrays.sort(curItems, Comparator.nullsLast((Item a, Item b) -> {
            if(a.profit > b.profit)return -1;
            else if(a.profit < b.profit)return 1;
            return 0;
        }));
        return curItems;
    }
    
    void displayTopItems(Item[] item, int top, int max_buy_price) {
        for(int i=0;i<top;++i){
            Item it = item[i];
            
            if(it.name == null || it.buyUnitPrice == null 
            		|| it.buyUnitPrice > max_buy_price)
            	continue;
            
            System.out.println(
            		"--------------------------------------\n"+
                    it.name+"\n"+
                    "--------------------------------------\n"+
                    "Buy at: "+CoinFormat.getPrice(it.buyUnitPrice)+"\n"+
                    "Sell at: "+CoinFormat.getPrice(it.sellUnitPrice)+"\n"+
                    "Profit: "+CoinFormat.getPrice(it.profit)+"\n"+
                    "--------------------------------------\n"
            );
        }
    }
}
