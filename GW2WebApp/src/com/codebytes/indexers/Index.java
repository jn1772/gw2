package com.codebytes.indexers;

import java.util.ArrayList;

import com.codebytes.base.Item;

 interface Index2 {
	
}

public abstract class Index implements Index2 {
	int nResults;
	ArrayList<Item> results;
	
	Index(){
		nResults = 0;
		results = new ArrayList<>();
	}
	
	public int getNumberOfResults() {
		return nResults;
	}
	
	public ArrayList<Item> getMatchingItems() {
		return results;
	}
}

class TypeIndex extends Index{
	public void search(Type type) {
		
	}
}

class RarityIndex extends Index{
	public void search(Rarity rarity) {
		
	}
}

class NameIndex extends Index{
	public void search(String s) {
		
	}
}


class PriceIndex extends Index{
	public void search(Integer min, Integer max) {
		
	}
}

class LevelIndex extends Index{
	public void search(Integer min, Integer max) {
		
	}
}

class PriceIndex extends Index{
	public void search(Integer min, Integer max) {
		
	}
}