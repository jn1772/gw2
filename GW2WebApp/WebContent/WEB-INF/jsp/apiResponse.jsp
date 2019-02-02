<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="com.codebytes.base.Item"%>
<%@ page import ="com.codebytes.database.PriceDAO"%>
<%@ page import ="org.json.simple.*"%>

<%
	Item item = (Item)request.getAttribute("item");
	PriceDAO itemPriceHistory = (PriceDAO)request.getAttribute("itemPriceHistory");
	JSONObject returnJSON = new JSONObject();
	
	JSONArray buyPrice = new JSONArray();
	JSONArray sellPrice = new JSONArray();
	JSONArray demand = new JSONArray();
	JSONArray supply = new JSONArray();
	JSONArray timestamps = new JSONArray();
	
	ArrayList<Long> timestampData = itemPriceHistory.getTimeStamps();
	ArrayList<Double> buyPriceData = itemPriceHistory.getBuyPriceData();
	ArrayList<Double> sellPriceData = itemPriceHistory.getSellPriceData();
	ArrayList<Integer> supplyData = itemPriceHistory.getSupplyData();
	ArrayList<Integer> demandData = itemPriceHistory.getDemandData();
	
	for(int i=0;i<buyPriceData.size();++i)
		buyPrice.add(i, buyPriceData.get(i));
	returnJSON.put("buyPrice", buyPrice);
	
	for(int i=0;i<sellPriceData.size();++i)
		sellPrice.add(i, sellPriceData.get(i));
	returnJSON.put("sellPrice", sellPrice);
	
	for(int i=0;i<demandData.size();++i)
		demand.add(i, demandData.get(i));
	returnJSON.put("demand", demand);
	
	for(int i=0;i<supplyData.size();++i)
		supply.add(i, supplyData.get(i));
	returnJSON.put("supply", supply);
	
	for(int i=0;i<timestampData.size();++i)
		timestamps.add(i, timestampData.get(i));
	returnJSON.put("timestamps", timestamps);
	
	out.println(returnJSON.toJSONString());
	/*
	public Response Register(String json) {
        Date dt = new Date();
        formatter.setTimeZone(TimeZone.getTimeZone("IST"));
        String currentTime = formatter.format(dt);
        JSONObject returnJson = new JSONObject();
        try {
            JSONObject innerJsonObj = new JSONObject(json);
            String email = innerJsonObj.getString("email");

            JSONObject jsonData = new JSONObject();
            jsonData.put("id", email);
            returnJson.put("success", true);
        } catch (Exception e) {
            JSONObject errorJson = new JSONObject();
            errorJson.put("success", false);
            return Response.ok(errorJson.toString()).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.ok(returnJson.toString()).header("Access-Control-Allow-Origin", "*").build();
    }
	*/
%>