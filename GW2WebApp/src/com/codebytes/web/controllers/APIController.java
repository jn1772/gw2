package com.codebytes.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codebytes.threads.Driver;

@Controller
public class APIController {

	@Autowired
	Driver gInstance;	
	//getParams = ['sellPrice', 'buyPrice', 'sellQuantity', 'buyQuantity'];
	
	@RequestMapping(value = "v1/item", method = RequestMethod.GET)
	public String processRequest(
			@RequestParam(value = "id", required=true) Integer id,
			ModelMap model) {
		model.addAttribute("item", gInstance.getItem(id));
		return "apiResponse";
	}
}
