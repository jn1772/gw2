import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="exchange_rate")
public class ExchangeRate{
	
	@Id
	@Column(name = "timestamp", nullable = false)
	Timestamp timestamp;
	
	@Column(name = "coins_per_gem_buy", nullable = false)
	int coins_per_gem_buy;
	
	@Column(name = "coins_per_gem_sell", nullable = false)
	int coins_per_gem_sell;
	
	public ExchangeRate() {};
	
	public void setTimestamp() {
		Date now = new Date();
		timestamp = new Timestamp(now.getTime());
	}
	
	public void setCPGB(int cpgb) {
		coins_per_gem_buy = cpgb;
	}
	
	public void setCPGS(int cpgs) {
		coins_per_gem_sell = cpgs;
	}
	
	public int getCPGB() {
		return coins_per_gem_buy;
	}
	
	public int getCPGS() {
		return coins_per_gem_sell;
	}
	
	public Timestamp getTimestamp() {
		return timestamp;
	}
}
