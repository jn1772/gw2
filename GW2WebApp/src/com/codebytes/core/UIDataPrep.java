package com.codebytes.core;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class UIDataPrep {
	
	public ArrayList<Item> itemRecommendations;
	
	UIDataPrep(){
		itemRecommendations = new ArrayList<>();
	}
	
    public void prepareRecommendations(Item[] pItems, int maxR) {
    	
    	ArrayList<Item> itemRecommendationsUpd = new ArrayList<>();
    	for(int i=0;i<pItems.length && itemRecommendationsUpd.size() < maxR;++i) {
    		if(pItems[i] != null) {
    			itemRecommendationsUpd.add(pItems[i]);
    		}
    	}
    	itemRecommendations = itemRecommendationsUpd;
    }
}
