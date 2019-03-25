package com.codebytes.indexers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codebytes.base.Item;
import com.codebytes.fetchers.InfoGet;
import com.codebytes.threads.Driver;

@Component
public class Indexer {
	
	@Autowired
	Driver d;
	
	@Autowired 
	InfoGet infoGet;
	
	int nItems;
	
	public int typeCount[], rarityCount[];
	
	public HashMap<String, HashSet<Item>> nameCount;
	public ArrayList<ArrayList<Item>> byType;
	public ArrayList<ArrayList<Item>> byRarity;
	public ArrayList<Integer> demand, supply, buyPrice, sellPrice;
	
	public String[] ignoreWords = {"of", "the", "on"};
	
	public Indexer() {
		nameCount = new HashMap<>();
		
		byType = new ArrayList<>();
		byRarity = new ArrayList<>();
		
		for(int i=0;i<=Item.nTypes;++i)byType.add(new ArrayList<>());
		for(int i=0;i<=Item.nRarity;++i)byRarity.add(new ArrayList<>());
		
		typeCount = new int[Item.nTypes+1];
		rarityCount = new int[Item.nRarity+1];
		
		ignoreParts = new HashSet<>();
		for(String s:ignoreWords)ignoreParts.add(s);
	}
	
	public void beginIndexing() {
		prepareNameIndex();
		prepareTypeIndex();
		prepareRarityIndex();
		prepareDemandIndex();
		prepareSupplyIndex();
		prepareBuyPriceIndex();
		prepareSellPriceIndex();
		
	}
	
	public HashSet<String> ignoreParts;
	
	public void prepareNameIndex() {
		nItems = d.info.itemIds.size();
		for(int i=0;i<nItems;++i) {
			Item item = d.items.get((int)(long)d.itemIds.get(i));
			
			//Limited Number of items is set for quick testing
			//Remove it later on
			if(item.name == null)continue;
			String[] nameSplit = item.name.split(" ");
			for(String part:nameSplit) {
				if(!ignoreParts.contains(part)) {
					HashSet<Item> set = nameCount.get(part);
					if(set == null) {
						set = new HashSet<>();
						nameCount.put(part, set);
//						System.out.println("Adding mapping for "+part);
					}
					set.add(item);
				}
			}
		}
	}
	
	public void prepareTypeIndex() {
		for(int i=0;i<d.itemIds.size();++i) {
			Item item = d.items.get((int)((long)d.itemIds.get(i)));
			int type = (int)item.getType().ordinal();
			typeCount[type]++;
			byType.get(type).add(item);
		}
	}
	
	public void prepareRarityIndex() {
		for(int i=0;i<d.itemIds.size();++i) {
			Item item = d.items.get((int)((long)d.itemIds.get(i)));
			int rarity = (int)item.getRarity().ordinal();
			rarityCount[rarity]++;
			byRarity.get(rarity).add(item);
		}
	}
}
