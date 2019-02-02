package com.codebytes.utils;

public class CoinFormat{
        //Gold Silver Bronze Coins
        public int gold, silver, bronze;
        
        public int getGold() {return gold;}
        public int getSilver() {return silver;}
        public int getBronze() {return bronze;}
        
        public CoinFormat(double p){
            gold = (int)p/10000;
            p = p%10000;
            silver = (int)p/100;
            p = p%100; 
            bronze = (int)p;
        }
        
        public String toString(){
            return String.format("%dg %ds %db",gold,silver,bronze);
        }
        
        public static String convert(double v){
            int gg, ss, bb;
            gg = (int)v/10000;
            v = v%10000;
            ss = (int)v/100;
            v = v%100; 
            bb = (int)v;
            return String.format("%dg %ds %db", gg, ss, bb);
        }
        
        public static CoinFormat getPrice(double p){
            return new CoinFormat(p);
        }
}