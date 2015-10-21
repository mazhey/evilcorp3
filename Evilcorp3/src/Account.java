import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;



public class Account {
	private String accountNum;
	private String accountName;
	private boolean status;
	private int accountID;
	private Connection conn;
	private ArrayList<Transcation> transaction = new ArrayList<Transcation>(); 
	
	//CONSTRUCTORS
	public Account(String accountNum,String accountName){
		this.setAccountName(accountName);
		this.setAccountNum(accountNum);
		this.status=true;
	}
	public Account(){
		
	}
	
	//GETTERS AND SETTERS
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public int getAccountID() {
		return accountID;
	}
	public void setaccountID(int accountID) {
		this.accountID = accountID;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	//SEARCH FOR AN ACCOUNT IN ORACLE
	public ResultSet getAccount() throws SQLException{
		String url = "jdbc:oracle:thin:system/password@localhost"; 
	      
        //properties for creating connection to Oracle database
        Properties props = new Properties();
        props.setProperty("user", "TESTUSERDB");
        props.setProperty("password", "password");
      
        //creating connection to Oracle database using JDBC
         conn = DriverManager.getConnection(url,props);
		 String sql = "select  * from  ACCOUNT where ACCOUNT_NUM = '" + accountNum +"'";

	        //creating PreparedStatement object to execute query
	        PreparedStatement preStatement = conn.prepareStatement(sql);
	    
	        ResultSet result = preStatement.executeQuery();
	        
	        return result;
	      
	}
	
	
	//ADD NEW ACCOUNT TO DATABASE
	public boolean addNewAccount() throws SQLException {
		boolean isAdded = false;
		String url = "jdbc:oracle:thin:system/password@localhost";

		// properties for creating connection to Oracle database
		Properties props = new Properties();
		props.setProperty("user", "TESTUSERDB");
		props.setProperty("password", "password");

		// creating connection to Oracle database using JDBC
		Connection conn = DriverManager.getConnection(url, props);

		String sqlIdentifier = "SELECT SEQ_ACCOUNT.NEXTVAL FROM DUAL";
		PreparedStatement preStatement = conn.prepareStatement(sqlIdentifier);
		ResultSet result = preStatement.executeQuery();

		if (result.next()) {
			int accountID = result.getInt("NEXTVAL");

			String sql = "INSERT INTO ACCOUNT (ACCOUNT_ID,ACCOUNT_NUM,ACCOUNT_NAME,STATUS) VALUES ("
					+ accountID
					+ ",'"
					+ accountNum
					+ "', '"
					+ accountName
					+ "',1)"; // 1 for open status
			preStatement = conn.prepareStatement(sql);

			result = preStatement.executeQuery();
			isAdded = true;

		}
		
		return isAdded;
	}
	
	
	//REMOVE AN ACCOUNT FROM DATABASE
	public void removeAccount() throws SQLException {

		String url = "jdbc:oracle:thin:system/password@localhost";

		// properties for creating connection to Oracle database
		Properties props = new Properties();
		props.setProperty("user", "TESTUSERDB");
		props.setProperty("password", "password");

		// creating connection to Oracle database using JDBC
		Connection conn = DriverManager.getConnection(url, props);

		String sql = "delete from ACCOUNT where Account_NUM='"
				+ this.accountNum + "'";
		PreparedStatement preStatement = conn.prepareStatement(sql);
		ResultSet result = preStatement.executeQuery();
		

	}

	
	//CLOSE AN ACCOUNT (CHANGE STATUS TO 0)
	public void colseAccount() throws SQLException {
		String url = "jdbc:oracle:thin:system/password@localhost";

		// properties for creating connection to Oracle database
		Properties props = new Properties();
		props.setProperty("user", "TESTUSERDB");
		props.setProperty("password", "password");

		// creating connection to Oracle database using JDBC
		Connection conn = DriverManager.getConnection(url, props);
		String sql = "update ACCOUNT set status=0 where Account_NUM='"
				+ accountNum + "'";
		PreparedStatement preStatement = conn.prepareStatement(sql);

		ResultSet result = preStatement.executeQuery();
		

	}


	//CALCULATE CURRENT BALANACE
	public double getCurrentBalance() throws SQLException {
		String url = "jdbc:oracle:thin:system/password@localhost";

		// properties for creating connection to Oracle database
		Properties props = new Properties();
		props.setProperty("user", "TESTUSERDB");
		props.setProperty("password", "password");

		// creating connection to Oracle database using JDBC
		Connection conn = DriverManager.getConnection(url, props);
		String sql = "select * from transaction where Account_Id ="
				+ this.accountID;
		PreparedStatement preStatement = conn.prepareStatement(sql);
		ResultSet result = preStatement.executeQuery();
		

		double balance = 0;
		while (result.next()) {
				balance += result.getDouble("Amount");
			
			if (balance <0){
				balance -= 35;
			}

		}
		
		return balance;
	}
	

	//WITHDRAWAL TRANSACTION 
	public boolean withdrawalTransaction(double amount) throws SQLException, ParseException {
		boolean isEnough = false;
		if (this.getCurrentBalance() < amount) {
			return isEnough;
		} else {
			Date myDate = new Date();
			DateFormat shortDf = DateFormat.getDateInstance(DateFormat.SHORT);
			String dateStr= shortDf.format(myDate);
			amount *=-1;
			Transcation transcation = new Transcation(4, amount, dateStr);
			if (transcation.addNewTransaction(accountID)) {
				isEnough = true;
			}
		}
		return isEnough;
	}
	
	//DEPOSIT TRANSACTION
	public void depositTranscation(double amount) throws SQLException {

		Date myDate = new Date();
		DateFormat shortDf = DateFormat.getDateInstance(DateFormat.SHORT);
		String dateStr = shortDf.format(myDate);
		amount *= -1;
		Transcation transcation = new Transcation(1, amount, dateStr);
		transcation.addNewTransaction(accountID);

	}


	//CHECK TRANSACTION
	public void checkTranscation(double amount, Date date1) throws SQLException {
		Date myDate = new Date();
		DateFormat shortDf = DateFormat.getDateInstance(DateFormat.SHORT);
		String dateStr= shortDf.format(myDate);
		amount *= -1;
		Transcation transcation = new Transcation(4, amount, dateStr);
		transcation.addNewTransaction(accountID);
	}
	
	
	//GET ALL ACCOUNT TRANSACTIONS FROM DATABASE
	public String getAccountTransaction() throws SQLException{
	
		String url = "jdbc:oracle:thin:system/password@localhost";

		// properties for creating connection to Oracle database
		Properties props = new Properties();
		props.setProperty("user", "TESTUSERDB");
		props.setProperty("password", "password");

		// creating connection to Oracle database using JDBC
		Connection conn = DriverManager.getConnection(url, props);
		String sql = "select Amount, Transaction_Date, Description from transaction, transaction_type where Account_Id ="
				+ this.accountID + "AND transaction.type = transaction_type.type";
		
		PreparedStatement preStatement = conn.prepareStatement(sql);
		ResultSet result = preStatement.executeQuery();
		String str="";
		
		while(result.next()){

		str += "Transaction Type: " + result.getString("description");
		str+= "\nTransaction amount: " + result.getDouble("Amount");
		str+= "\nTransaction Date: " + result.getString("TRANSACTION_DATE");
		str +="\n";
		}
		
		return str;
	}
	public void closeConnection() throws SQLException{
		conn.close();
	}
}

/**
		
	
	public int searchAccount(ArrayList<Account> myAccount, String accountNum ){
		
		int index=-1;
		for (int i = 0; i < myAccount.size(); i++) {
			if (myAccount.get(i).getAccountNum().equals(accountNum)) {
				index = i;}
			
		}
		
		return index;
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
