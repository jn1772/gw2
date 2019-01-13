package com.codebytes.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codebytes.core.GW2TP;
import com.codebytes.core.UIDataPrep;

@Controller
public class HomepageController {

	@Autowired
	GW2TP gInstance;
	
	@Autowired
	UIDataPrep uiDataPrep;
	
	int elementsPerPage = 10;
	
	@RequestMapping(value = {"/*", "/home"} , method = RequestMethod.GET)
	public String printWelcome(
		@RequestParam(value = "page", required=false) Integer pageRequest,
		@RequestParam(value = "item", required=false) Integer itemId,
		ModelMap model) {
		
		if(itemId != null) {
			System.out.println("Info requested for itemId "+itemId);
			model.addAttribute("item", gInstance.getItem(itemId));
			return "itemInfo";
		}
		
		int total = uiDataPrep.itemRecommendations.size();
		
		PageState ps = PageState.controlPagination(pageRequest, total, elementsPerPage);
		
		model.addAttribute("welcomeShort", "Welcome to GreedyTP!");
		model.addAttribute("welcomeLong",
				"What you can do: Historic prices, currency conversion rate, item recommendations along with some nice charts.");
		model.addAttribute("items", uiDataPrep.itemRecommendations);
		model.addAttribute("elementsPerPage", elementsPerPage);
		for(int i=0;i<ps.pageNumbers.length;++i) {
			model.addAttribute("page_"+(i+1), ps.pageNumbers[i]);
		}
		model.addAttribute("begin", ps.begin);
		model.addAttribute("end", ps.end);
		
		System.out.println("begin = "+ps.begin+" end = "+ps.end);
		return "home";
	}
	
	@RequestMapping(value = "/home/final", method = RequestMethod.GET)
	public String printHello(ModelMap model) {
		return "final";
	}
}