package com.codebytes.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codebytes.base.Item;
import com.codebytes.database.PriceDAO;
import com.codebytes.threads.Driver;

@Controller
public class APIController {

	@Autowired
	Driver d;	
	//getParams = ['sellPrice', 'buyPrice', 'sellQuantity', 'buyQuantity'];
	
	@RequestMapping(value = "v1/item/chdata", method = RequestMethod.GET)
	public String processRequest(
			@RequestParam(value = "id", required=true) Integer id,
			ModelMap model) {
		Item item = d.getItem(id);
		model.addAttribute("item", item);
		PriceDAO itemPriceHistory = new PriceDAO(item, d.getDB());
		itemPriceHistory.getDBData();
		model.addAttribute("itemPriceHistory", itemPriceHistory);
		return "apiResponse";
	}
}
