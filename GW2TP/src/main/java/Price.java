import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "price")
public class Price implements Serializable{

	@Id
	@Column(name = "id", nullable = false)	
	Integer id;
	
	@Id
	@Column(name = "timestamp")
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
	
	public Price(){
		
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setBuyPrice(Double bp) {
		buy_price = bp;
	}
	
	public Double getBuyPrice() {
		return buy_price;
	}
	
	public void setSellPrice(Double sp) {
		sell_price = sp;
	}
	
	public Double getSellPrice() {
		return sell_price;
	}
	
	public void setBuyQuantity(Integer q) {
		buy_quantity = q;
	}
	
	public Integer getBuyQuantity() {
		return buy_quantity;
	}
	
	public void setSellQuantity(Integer q) {
		sell_quantity = q;
	}
	
	public Integer getSellQuantity() {
		return sell_quantity;
	}
	
	public void setTimeStamp() {
		timestamp = new java.sql.Timestamp(new Date().getTime());	
	}
	
	public java.sql.Timestamp getTimeStamp() {
		return timestamp;
	}
	
	public void setProfit() {
		if (buy_price != null && sell_price != null) {
			profit = (sell_price-buy_price) - sell_price*0.15;
		}
	}
	
	public Double getProfit() {
		return profit;
	}
	
	public boolean equals(Price p) {
		return id == p.id && timestamp.equals(p.timestamp);
	}
	
	public int hashCode() {
		return (id+" "+timestamp.getNanos()).hashCode();
	}
}
