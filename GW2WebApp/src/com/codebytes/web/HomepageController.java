package com.codebytes.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codebytes.core.GW2TP;
import com.codebytes.core.UIDataPrep;

@Controller
public class HomepageController {

	@Autowired
	GW2TP gInstance;
	
	@Autowired
	UIDataPrep uiDataPrep;
	
	@RequestMapping(value = {"/*", "/home"} , method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("welcomeShort", "Welcome to GreedyTP!");
		model.addAttribute("welcomeLong",
				"What you can do: Historic prices, currency conversion rate, item recommendations along with some nice charts.");
		model.addAttribute("items", uiDataPrep.itemRecommendations);
		return "home";
	}

	@RequestMapping(value = "/home/final", method = RequestMethod.GET)
	public String printHello(ModelMap model) {
		return "final";
	}
}