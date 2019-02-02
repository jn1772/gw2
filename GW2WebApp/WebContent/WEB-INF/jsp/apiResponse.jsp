<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="com.codebytes.base.Item"%>

<%
	Item item = (Item)request.getAttribute("item");
	
	//JSONArray returnJson = new JSONArray();
	
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