package com.codebytes.database;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.codebytes.base.Item;
import com.codebytes.base.Price;

public class PriceDAO {
	
	Item item;
	
	Session session;
	int nEntries;
	
	ArrayList<Double> buyPrice, sellPrice;
	ArrayList<Integer> demand, supply;
	ArrayList<Long> timestamps;
	
	public ArrayList<Double> getBuyPriceData(){ return buyPrice; }
	public ArrayList<Double> getSellPriceData(){ return sellPrice; }
	public ArrayList<Integer> getDemandData(){	return demand; }
	public ArrayList<Integer> getSupplyData(){	return supply; }
	public ArrayList<Long> getTimeStamps(){ return timestamps; }
	
	public PriceDAO(Item item, DB db){
		buyPrice = new ArrayList<>();
		sellPrice = new ArrayList<>();
		demand = new ArrayList<>();
		supply = new ArrayList<>();
		timestamps = new ArrayList<>();
		session = db.getSession();
		this.item = item;
	}
	
	public void getDBData() {
		String hql = "from com.codebytes.base.Price P where P.id = "+(item.id)+" order by P.timestamp";
//		System.out.println("GETTING DATA FROM DB");
		Query query = session.createQuery(hql);
		List<Price> results = query.list();
		for(Price p:results) {
			buyPrice.add(p.getBuyPrice());
			sellPrice.add(p.getSellPrice());
			demand.add(p.getBuyQuantity());
			supply.add(p.getSellQuantity());
			timestamps.add(p.getNano());
//			System.out.println(p.getTimeStamp()+" "+p.getBuyPrice()+" "+p.getSellPrice()+" "+p.getBuyQuantity()+" "+p.getSellQuantity()+"\n");
		}
	}
}
