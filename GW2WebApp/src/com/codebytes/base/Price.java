package com.codebytes.base;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "price")
public class Price implements Serializable{

	public static String gemIcon, goldIcon;
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	public	
	Integer id;
	
	@Id
	@Column(name = "timestamp")
	public
	java.sql.Timestamp timestamp;
	
	@Column(name = "buy_price", nullable = true)
	Double buy_price;

	@Column(name = "sell_price", nullable = true)
	Double sell_price;
	
	@Column(name = "buy_quantity", nullable = true)
	Integer buy_quantity;
	
	@Column(name = "sell_quantity", nullable = true)
	Integer sell_quantity;
	
	@Column(name = "profit", nullable=true)
	Double profit;
    
	@Transient
	Session session;
	
	public Price(){
		
	}
	
	public void setId(int id) {	this.id = id; }
	public void setBuyPrice(Double bp) { buy_price = bp; }
	public void setSellPrice(Double sp) { sell_price = sp; }
	public void setSellQuantity(Integer q) { sell_quantity = q; }
	public void setBuyQuantity(Integer q) {	buy_quantity = q; }
	
	public Integer getId() { return id;	}
	public Double getBuyPrice() { return buy_price;	}
	public Double getSellPrice() { return sell_price; }
	public Integer getBuyQuantity() { return buy_quantity; }
	public Integer getSellQuantity() { return sell_quantity; }
	public long getNano() {	return timestamp.getTime();}
	public Double getProfit() {	return profit; }
	public ChartData getChartData(){ return new ChartData(); }
	
	Date date;
	
	public void setTimeStamp(Date date) {
		timestamp = new java.sql.Timestamp(date.getTime());
		this.date = date;
	}
	
	public java.sql.Timestamp getTimeStamp() {	return timestamp; }
	
	public void setProfit() {
		if (buy_price != null && sell_price != null) {
			profit = (sell_price-buy_price) - sell_price*0.15;
		}
	}
	
	public boolean equals(Price p) {
		return id == p.id && timestamp.equals(p.timestamp);
	}
	
	public int hashCode() {
		return (id+" "+timestamp.getNanos()).hashCode();
	}
}

class ChartData{
	ArrayList<Integer> buyPrices, sellPrices, demand, supply;
	
	ChartData(){
		buyPrices = new ArrayList<>();
		sellPrices = new ArrayList<>();
		demand = new ArrayList<>();
		supply = new ArrayList<>();
	}
}
