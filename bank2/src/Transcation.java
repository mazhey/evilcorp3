
import java.text.NumberFormat;
import java.util.Date;


public class Transcation{
	private String type;
	private double amount;
	private Date date = new Date();


	public Transcation(String type, double amount,Date date){
		this.setType(type);
		this.setAmount(amount);
		this.setDate(date);
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getFormattedPrice(){
    	NumberFormat currency = NumberFormat.getCurrencyInstance();
    	return currency.format(amount);
    }
	public String history(){
		String str="";
		str += "Transcation type: "  + type + "\n";
		str += "Transaction amount: " + getFormattedPrice() + "\n";
		str += "Transaction date: "  + date;
		return str;
		
		
	}


	}
	
	
/*
  public long compareTo() {

       
       Date todayDate = new Date();
		long endDateMS = todayDate.getTime();
		
		long startDateMS = this.date.getTime();
		
		
		return endDateMS - startDateMS ;
		

   }
*/   

