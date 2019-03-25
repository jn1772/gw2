package com.codebytes.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codebytes.base.Item;
import com.codebytes.base.Price;
import com.codebytes.database.PriceDAO;
import com.codebytes.threads.DataUpdater;
import com.codebytes.threads.Driver;
import com.codebytes.web.pageTools.PageState;

@Controller
public class HomepageController {

	@Autowired
	Driver d;
	
	@Autowired
	DataUpdater updater;
	
	int elementsPerPage = 10;
	
	@RequestMapping(value = {"/*", "/home"} , method = RequestMethod.GET)
	public String printWelcome(
		@RequestParam(value = "page", required=false) Integer pageRequest,
		@RequestParam(value = "item", required=false) Integer itemId,
		ModelMap model) {
		
		if(itemId != null) {
			Item item = d.getItem(itemId);
			System.out.println("Info requested for itemId "+itemId);
			model.addAttribute("item", item);
//			List<Price> priceInfo = gInstance.getAllPriceInfo(itemId);
//			model.addAttribute("priceInfo", priceInfo);
			PriceDAO itemPriceHistory = new PriceDAO(item, d.getDB());
			itemPriceHistory.getDBData();
			return "itemInfo";
		}
		
		ArrayList<Item> top = updater.byPriceDisplay.getTopN(200);
		int total = top.size();
		
		PageState ps = PageState.controlPagination(pageRequest, total, elementsPerPage);
		
		model.addAttribute("welcomeShort", "Welcome to GreedyTP!");
		model.addAttribute("welcomeLong",
				"What you can do: Historic prices, currency conversion rate, item recommendations along with some nice charts.");
		model.addAttribute("items", top);
		model.addAttribute("elementsPerPage", elementsPerPage);
		for(int i=0;i<ps.pageNumbers.length;++i) {
			model.addAttribute("page_"+(i+1), ps.pageNumbers[i]);
		}
		model.addAttribute("begin", ps.begin);
		model.addAttribute("end", ps.end);
		
		return "home";
	}
	
	@RequestMapping(value = "/home/final", method = RequestMethod.GET)
	public String printHello(ModelMap model) {
		return "final";
	}
}