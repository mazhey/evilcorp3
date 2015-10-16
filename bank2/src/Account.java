import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
//import java.util.Date;
//import java.util.Map;
//import java.util.TreeMap;



public class Account {
	private String accountNum;
	private String accountName;
	private double balance;
	private boolean status;
	private ArrayList<Transcation> transaction = new ArrayList<Transcation>(); 
	
	public Account(String accountNum,String accountName,double balance){
		this.setAccountName(accountName);
		this.setAccountNum(accountNum);
		this.setBalance(balance);
		this.status=true;
	}
	public Account(){
		
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public int searchAccount(ArrayList<Account> myAccount, String accountNum ){
		
		int index=-1;
		for (int i = 0; i < myAccount.size(); i++) {
			if (myAccount.get(i).getAccountNum().equals(accountNum)) {
				index = i;}
			
		}
		
		return index;
	}
	
	public boolean withdrawalTransaction( double amount){
		boolean isEnough = false;
		if (this.balance < amount){
			return isEnough;
		}else{
			this.balance -= amount;
			isEnough = true;
			Date date = new Date();
			Transcation transcation = new Transcation("W", amount, date);
			this.addTranscation(transcation);
			return isEnough;
		}
		
	}
	public void depositTranscation(double amount){
		this.balance += amount;
		Date date = new Date();
		Transcation transcation = new Transcation("D", amount, date);
		this.addTranscation(transcation);
			
	}
	public void addTranscation(Transcation transcation){
		transaction.add(transcation);
	}
	
	

	
	public void sortTransactions(){
		int i,j;
		for (i=0; i<transaction.size()-1; i++){
			for (j=i; j < transaction.size(); j++){
				if (transaction.get(i).getDate().getTime() > transaction.get(j).getDate().getTime()){
					Transcation temp = transaction.get(i);
					transaction.add(i,transaction.get(j));
					transaction.add(j,temp);
				}
					
			}
		}
	}
	
	public void sortByAmount(){
		int i,j;
		int size = transaction.size();
		for (i=0; i<size-1; i++){
			for (j=i; j < size; j++){
				if (transaction.get(i).getAmount() < transaction.get(j).getAmount()){
					Transcation temp = transaction.get(i);
					transaction.add(i,transaction.get(j));
					transaction.add(j,temp);
				}
					
			}
		}
	}
	
	
	public String calculateNewBalance(){
		double newBalanceByDate = this.getBalance();
		int i;
		this.sortTransactions();
		
		for (i=0; i< transaction.size(); i++){
			newBalanceByDate -= transaction.get(i).getAmount();
			if (newBalanceByDate < 0){
				newBalanceByDate -= 35;
			}
			
		}
		double newBalanceByAmount = this.getBalance();
		this.sortByAmount();
		for (i=0; i<transaction.size(); i++){
			newBalanceByAmount -= transaction.get(i).getAmount();
			if (newBalanceByAmount < 0){
				newBalanceByAmount -= 35;
			}
			
		}
		if (newBalanceByAmount>newBalanceByDate){
			this.setBalance(newBalanceByDate);
			return getFormattedPrice();
		}
		else
		{
			this.setBalance(newBalanceByAmount);
			return getFormattedPrice();
		}
		
	}
	public String getFormattedPrice(){
    	NumberFormat currency = NumberFormat.getCurrencyInstance();
    	return currency.format(balance);
    }
	
	public String getAccountTransaction(){
		String strAccount="";
		strAccount += "Account#: " + this.accountNum + "\n";
		strAccount += "Account Name: " + this.accountName + "\n";
		strAccount += "Account Initial Balance: " + getFormattedPrice() + "\n";
		strAccount += "Transactions History: \n";
		for (int i=0; i<transaction.size(); i++){
			strAccount += transaction.get(i).history();
			strAccount += "\n";
		}
		return strAccount;
	}

	
/*
	
    public TreeMap<Long, Double > sort(){
    	long differenceMS;
    
    	TreeMap<Long,Double> tempTransaction = new TreeMap<Long,Double>();
    	
    	for (int i=0; i<transaction.size(); i++){
    	differenceMS = transaction.get(i).compareTo();
    
    	tempTransaction.put(differenceMS, transaction.get(i).getAmount());
    	
 
    	}
    	return tempTransaction;
    	
	}
    
  public double calculate(){
	  TreeMap<Long, Double > transaction1 = new TreeMap<Long, Double >();
	  transaction1 = this.sort();
	  double newBalance = this.getBalance();
	  for (int i = 0; i<transaction1.size(); i++ ){
		
		  newBalance = newBalance -  transaction1.get(i);
		  if (newBalance < 0 ){
			  newBalance = newBalance - 35.0;
		  }
		  
	  }
	  this.setBalance(newBalance);
	  return   newBalance;
  }
*/
 
}
