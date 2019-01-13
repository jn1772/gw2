package com.codebytes.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;	

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codebytes.core.GW2TP;
import com.codebytes.core.Indexer;
import com.codebytes.core.Item;
import com.codebytes.core.UIDataPrep;

@Controller
public class ItemSearchController {

	@Autowired
	GW2TP gInstance;
	
	@Autowired
	UIDataPrep uiDataPrep;
		
	@Autowired
	Indexer indexer;
		
	HashSet<Item> results;
	
	public ItemSearchController(){
		results = new HashSet<>();
	}
		
	int elementsPerPage = 10;
	

	
	@RequestMapping(value = "itemSearch", method = RequestMethod.GET)
	public String returnResults(
			@RequestParam(value = "name", required=false) String name,
			@RequestParam(value = "type", required=false) String type,
			@RequestParam(value = "rarity", required=false) String rarity,
			@RequestParam(value = "buyPriceMin", required=false) Integer bMinPrice,
			@RequestParam(value = "buyPriceMax", required=false) Integer bMaxPrice,
			@RequestParam(value = "sellPriceMin", required=false) Integer sMinPrice,
			@RequestParam(value = "sellPriceMax", required=false) Integer sMaxPrice,
			@RequestParam(value = "profitMin", required=false) Integer profitMin,
			@RequestParam(value = "profitMax", required=false) Integer profitMax,
			@RequestParam(value = "demandMin", required=false) Integer demandMin,
			@RequestParam(value = "demandMax", required=false) Integer demandMax,
			@RequestParam(value = "supplyMin", required=false) Integer supplyMin,
			@RequestParam(value = "supplyMax", required=false) Integer supplyMax, ModelMap model) {
		
		results.clear();
		
		ArrayList<Item> output = new ArrayList<>();
		
		Item.Type iType = null;
		Item.Rarity iRarity = null;
		
		try { iType = Item.Type.valueOf(type); }
		catch (Exception e) { System.out.println("Type invalid...");}
		
		try { iRarity = Item.Rarity.valueOf(rarity); }
		catch(Exception e) { System.out.println("Rarity invalid..."); }
		
		if(name!=null) {
			searchByName(name);
	    	for(Item item :results) {
	    		if(iType != null && item.getType() != iType)continue;
	    		if(iRarity != null && item.getRarity() != iRarity)continue;
	    		if(bMinPrice != null && item.getBuyUnitPrice() < bMinPrice)continue;
	    		if(bMaxPrice != null && item.getBuyUnitPrice() > bMaxPrice)continue;
	    		if(sMinPrice != null && item.getSellUnitPrice() < sMinPrice)continue;
	    		if(sMaxPrice != null && item.getSellUnitPrice() > sMaxPrice)continue;
	    		if(profitMin != null && item.getProfit() < profitMin)continue;
	    		if(profitMax != null && item.getProfit() > profitMax)continue;
	    		if(demandMin != null && item.getDemand() < demandMin)continue;
	    		if(demandMax != null && item.getDemand() > demandMax)continue;
	    		if(supplyMin != null && item.getSupply() < supplyMin)continue;
	    		if(supplyMax != null && item.getSupply() > supplyMax)continue;
	    		output.add(item);
	    	}
	    }else {
	    	System.out.println("Need to enter name");
	    }
		
	    model.addAttribute("items", output);
	    
	    PageState ps = PageState.controlPagination(1, output.size(), elementsPerPage);
		for(int i=0;i<ps.pageNumbers.length;++i) {
			model.addAttribute("page_"+(i+1), ps.pageNumbers[i]);
		}
		model.addAttribute("begin", ps.begin);
		model.addAttribute("end", ps.end);
		
		System.out.println("begin = "+ps.begin+" end = "+ps.end);
		
		return "itemSearch";
	}
	
	public void searchByName(String name){
		String[] parts = name.split(" ");
		for(String s:parts) {
			HashSet<Item> set = indexer.nameIndex.get(s);
			System.out.println("Checking mapping for " +s+" null ? "+(set==null));
			if(set==null)continue;
			Iterator<Item> it = set.iterator();
			while(it.hasNext()) {
				Item a = it.next();
				System.out.println("Added item : "+a.name);
				results.add(a);
			}
		}
	}
	
	public void searchByType(String t) {
		Item.Type type = Item.Type.valueOf(t);
	}
}