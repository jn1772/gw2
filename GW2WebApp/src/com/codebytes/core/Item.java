package com.codebytes.core;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Item implements Comparable<Item>{

        //Item ID
        public long id;
        public int level, vendorValue;
        public Integer defaultSkin;
        
        public CoinFormat buyUnitPriceCF, sellUnitPriceCF;
        
        //buying / selling unit prices and quantity
        public Double buyUnitPrice, sellUnitPrice;
        public Integer buyQuantity, sellQuantity;
        
        public Date lastUpdated;
        
        public String chatLink, name, icon, description, type, rarity;
        
        //Listings at Trading Post
        public ArrayList<Long> bListings, bUnitPrice, bQuantity;
        public ArrayList<Long> sListings, sUnitPrice, sQuantity;

        //Profit on resell
        public  double profit;
        
        public void setId(int id) {this.id = id;}
        public void setName(String name) {this.name = name;}
        public void setDescription(String desc) {this.description = desc;}
        public void setIcon(String icon) {this.icon = icon;}
        public void setChatLink(String cl) {this.chatLink = cl;}
        public void setType(String type) {this.type = type;}
        public void setRarity(String rarity) {this.rarity = rarity;}
        public void setLevel(int level) {this.level = level;}
        public void setVendorValue(int vendor_value) {this.vendorValue = vendor_value;}
        public void setDefaultSkin(Integer df) {this.defaultSkin = df;}
        public void setBuyUnitPrice(double buyUnitPrice) { this.buyUnitPrice = buyUnitPrice; this.buyUnitPriceCF = new CoinFormat(buyUnitPrice);}
        public void setSellUnitPrice(double sellUnitPrice) { this.sellUnitPrice = sellUnitPrice; this.sellUnitPriceCF = new CoinFormat(sellUnitPrice);}
        public void setLastUpdated() { this.lastUpdated = new Date();}
        
        public long getId() {return id;}
        public String getName() {return name;}
        public String getDescription() {return description;}
        public String getIcon() {return icon;}
        public String getChatLink() {return chatLink;}
        public String getType() {return this.type;}
        public String getRarity() {return rarity;}
        public Integer getDefaultSkin() {return defaultSkin;}
        public int getLevel() {return level;}
        public int getVendorValue() {return vendorValue;}
        public double getBuyUnitPrice() {return buyUnitPrice;}
        public double getSellUnitPrice() {return sellUnitPrice;}
        public CoinFormat getBuyUnitPriceCF() {return buyUnitPriceCF;}
        public CoinFormat getSellUnitPriceCF() {return sellUnitPriceCF;}
        public Date getLastUpdated() { return lastUpdated; }
        
        Item(){
            bListings = new ArrayList<>();
            bUnitPrice = new ArrayList<>();
            bQuantity = new ArrayList<>();

            sListings = new ArrayList<>();
            sUnitPrice = new ArrayList<>();
            sQuantity = new ArrayList<>();
            
            buyUnitPrice = (double) 0;
            buyQuantity = 0;
            sellUnitPrice = (double) 0;
            sellQuantity = 0;
            
            profit = 0;
            defaultSkin = null;
        }

        double calcProfit(){
            if(name == null || buyQuantity == 0 || sellQuantity == 0)return 0;
            double diff = sellUnitPrice - buyUnitPrice;
            double tax = sellUnitPrice*0.15;
            return profit = diff-tax;
        }
        
        @Override
        public int compareTo(Item o) {
            //if(o == null)return 1; 
            if(this.profit < o.profit)return 1;
            else if(this.profit > o.profit)return -1;
            return 0;
        }
        
        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append("ID: ").append(id).append(" Name: ").append(name).append(" Level: ").append(level).append(" Rarity: ").append(rarity);
            return sb.toString();
        }
}