package com.codebytes.DisplayItems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codebytes.base.Item;
import com.codebytes.threads.Driver;

public class PriceRecommender implements DisplayItems{
	
	Driver d;
	
	ArrayList<Item> items;
    
    public PriceRecommender(Driver d) {
    	this.d = d;
    	if(d==null)System.out.println("\n\n\n\nD is null\n\n\n\n");
    	else System.out.println("\n\n\n\nD Is not null\n\n\n\n");
    	refreshItems();
    }
    
	@Override
	public ArrayList<Item> getTopN(int n) {
		return new ArrayList<>(items.subList(0, n));
	}
	
	@Override
	public void refreshItems() {
		ArrayList<Item> itemsNew = d.items;
		items = computeProfitableItems(itemsNew);
	}
	
	public ArrayList<Item> computeProfitableItems(ArrayList<Item> itemsNew) {
		ArrayList<Item> sorted = sortItemsByMaxProfit(itemsNew);
	    //genie.displayTopItems(sorted, max, user_gold * 100 * 100);
	    return sorted;
	}
	
	public ArrayList<Item> sortItemsByMaxProfit(ArrayList<Item> itemsNew) {
		ArrayList<Item> curItems = new ArrayList<>(itemsNew);
        
        Collections.sort(curItems, Comparator.nullsLast((Item a, Item b) -> {
            if(a.profit > b.profit)return -1;
            else if(a.profit < b.profit)return 1;
            return 0;
        }));
        return curItems;
    }
}    