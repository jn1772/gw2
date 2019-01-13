package com.codebytes.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Indexer {
	
	@Autowired
	GW2TP gInstance;
	
	@Autowired 
	InfoGet infoGet;
	
	int nItems;
	
	public HashMap<String, HashSet<Item>> nameIndex;
	public ArrayList<ArrayList<Integer>> typeIndex;
	public ArrayList<ArrayList<Integer>> rarityIndex;
	
	public String[] ignoreWords = {"of", "the", "on"};
	
	public Indexer() {
		nameIndex = new HashMap<>();
		typeIndex = new ArrayList<>();
		rarityIndex = new ArrayList<>();
		ignoreParts = new HashSet<>();
		for(String s:ignoreWords)ignoreParts.add(s);
	}
	
	public void beginIndexing() {
		prepareNameIndex();
		prepareTypeIndex();
		prepareRarityIndex();
	}
	
	public HashSet<String> ignoreParts;
	
	public void prepareNameIndex() {
		nItems = gInstance.info.itemIds.size();
		for(int i=0;i<nItems;++i) {
			Item item = gInstance.items[(int)(long)gInstance.itemIds.get(i)];
			
			//Limited Number of items is set for quick testing
			//Remove it later on
			if(item.name == null)continue;
			String[] nameSplit = item.name.split(" ");
			for(String part:nameSplit) {
				if(!ignoreParts.contains(part)) {
					HashSet<Item> set = nameIndex.get(part);
					if(set == null) {
						set = new HashSet<>();
						nameIndex.put(part, set);
						System.out.println("Adding mapping for "+part);
					}
					set.add(item);
				}
			}
		}
	}
	
	public void prepareTypeIndex() {
		
	}
	
	public void prepareRarityIndex() {
	
	}
}
