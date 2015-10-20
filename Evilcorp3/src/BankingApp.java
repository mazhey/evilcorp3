import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

public class BankingApp {

	public static void main(String[] args) throws SQLException {
		
		 //URL of Oracle database server
        String url = "jdbc:oracle:thin:system/password@localhost"; 
      
        //properties for creating connection to Oracle database
        Properties props = new Properties();
        props.setProperty("user", "TESTUSERDB");
        props.setProperty("password", "password");
      
        //creating connection to Oracle database using JDBC
        Connection conn = DriverManager.getConnection(url,props);

        String sql ="select sysdate as current_day from dual";

        //creating PreparedStatement object to execute query
        PreparedStatement preStatement = conn.prepareStatement(sql);
    
        ResultSet result = preStatement.executeQuery();
      
        while(result.next()){
            System.out.println("Current Date from Oracle : " +         result.getString("current_day"));
        }
        System.out.println("done");
      
		
		

		Scanner keyboard = new Scanner(System.in);
		String accountNum, accountName, type, response, answer;
		double balance, amount, withdraw, deposit;
		Date date = new Date();
		int accountID;
		//Account dummyAccount = new Account();
		//ArrayList<Account> myAccount = new ArrayList<Account>();

		System.out.println("Welcome to Evil Corp Savings and Loan");

		// list accounts
		//System.out.println("List of Accounts ");
		//for (int i = 0; i < myAccount.size(); i++) {
			//System.out.println("\n" + myAccount.get(i));

		//}

		// list options

		response = Validator
				.getString(
						keyboard,
						"What you want to do:\n 1.Add accounts.\n 2.Remove accounts.\n 3.Close accounts.\n 4. Withdraw. \n 5.Deposit \n 6.Check.\n7. Print account history. \n 8. Quit.");
		while (!response.equals("8")) {
			
			
			
			//Add account
			if (response.equals("1")) {
				
				accountNum = Validator
						.getString(keyboard,
								"Enter an account # or -1 to stop entering accounts : ");

				while (!accountNum.equals("-1")) {
				
					sql = "select  ACCOUNT_NUM from  ACCOUNT where ACCOUNT_NUM = '" + accountNum +"'";
			        preStatement = conn.prepareStatement(sql);
					    
				        result = preStatement.executeQuery();
				       if(!result.next()){
				    	 //account does not exists; we can add a new account ===> we need a method
							accountName = Validator.getString(keyboard,
									"Enter the name for acct #" + accountNum);
							String sqlIdentifier = "SELECT SEQ_ACCOUNT.NEXTVAL FROM DUAL";
							preStatement = conn.prepareStatement(sqlIdentifier);
							result = preStatement.executeQuery();
							
							if (result.next()){
								accountID= result.getInt("NEXTVAL");
								System.out.println(accountID);
								
								sql = "INSERT INTO ACCOUNT (ACCOUNT_ID,ACCOUNT_NUM,ACCOUNT_NAME,STATUS) VALUES (" + accountID +",'" + accountNum +"', '" +accountName +"',1)"; //1 for open status
						        preStatement = conn.prepareStatement(sql);
								    
							        result = preStatement.executeQuery();
							        System.out.println("Account Created Successfully.");
					
					
				          }else {
								System.out.println("An error occurred. Please try again later.");
							}
				       }
							else{
					//account already exists
					System.out.println("Account already exists. ");
				}
					
				}
				
					//ask user if he wants to enter new account or exit
					System.out.println("\n\n");
					accountNum = Validator
							.getString(keyboard,
									"Enter an account # or -1 to stop entering accounts : ");

			
				// remove method
			} else if (response.equals("2")) {
				
				accountNum = Validator.getString(keyboard,
						"Give the account# you want to remove : ");
				
				
					sql = "select  * from  ACCOUNT where Account_num ='" + accountNum +"'" ;
			        preStatement = conn.prepareStatement(sql);
					    
				        result = preStatement.executeQuery();
				        if(result.next()){
				        	sql = "delete from ACCOUNT where Account_NUM='" + result.getString("Account_Num") +"'" ;
	
							System.out.println("Account found and removed ");
						}
				       
				        else{
				        	System.out.println("Account not found.");
				        }
				        }
			
				// close account
			else if (response.equals("3")) {

				
				accountNum = Validator
						.getString(keyboard,
								"Enter an account # or -1 to stop entering accounts : ");
				sql = "select  * from  ACCOUNT where Account_num ='" + accountNum +"'" ;
		        preStatement = conn.prepareStatement(sql);
				    
			      result = preStatement.executeQuery();
			      if(result.next()){
			    	  //need code to calculate balance from transaction table
			        	sql = "update ACCOUNT set status=0 where Account_NUM='" + result.getString("Account_Num") +"'" ;
			        	System.out.println(sql);
			        	
			        	preStatement = conn.prepareStatement(sql);
					    
					      result = preStatement.executeQuery();
						System.out.println("Account closed " );
					} else{
			        	System.out.println("Account not found.");
			        }
			}
			      
			       
				
		
			// withdraw
			else if (response.equals("4")) {
				
				accountNum = Validator
						.getString(keyboard,
								"Enter an account # or -1 to stop entering accounts : ");
				sql = "select * from  ACCOUNT where Account_num ='" + accountNum +"'" ;
		        preStatement = conn.prepareStatement(sql);
		        result = preStatement.executeQuery();
		        if(result.next()){
		        	if(result.getInt("status")==1){
		        		sql="select * from transaction where Account_Id =" +result.getInt("ACCOUNT_ID");
		        		preStatement = conn.prepareStatement(sql);
				        result = preStatement.executeQuery();
				        
		        	}
		        }
		        	
		        }
		        
				while (!accountNum.equals("-1")) {
					int index =	dummyAccount.searchAccount(myAccount, accountNum);
					if (index == -1){
						//account does not exists
						System.out.println("Account not found ");
					}else {
						if (myAccount.get(index).isStatus()) {
						System.out.println("You current balance is: "
								+ myAccount.get(index).getBalance());

						withdraw = Validator.getDouble(keyboard,
								"How much you want to withdraw?");
						boolean isSuccess = myAccount.get(index).withdrawalTransaction(withdraw);
						if (isSuccess == true){
							System.out
							.println("Withdraw successfully.");
							System.out
							.println("New balance: " + myAccount.get(index).getBalance());
						}else
							System.out.println("Run into deficit.");
					}else {
						System.out
						.println("You account already closed.");
					}
					}
		

					accountNum = Validator
							.getString(keyboard,
									"Enter an account # or -1 to stop entering accounts : ");
				}
				
				/*
				
			} else if (response.equals("5")) {
				accountNum = Validator
						.getString(keyboard,
								"Enter an account # or -1 to stop entering accounts : ");
				while (!accountNum.equals("-1")) {
					
					int index =	dummyAccount.searchAccount(myAccount, accountNum);
					if (index == -1){
						//account does not exists
						System.out.println("Account not found ");
					}else {
						if (myAccount.get(index).isStatus()) {
						System.out.println("You current balance is: "
								+ myAccount.get(index).getBalance());
						deposit = Validator.getDouble(keyboard,
								"How much you want to deposit?");
						myAccount.get(index).depositTranscation(deposit);

						System.out
						.println("You deposit money successfully.");

				System.out.println("You current balance is: "
						+ myAccount.get(index).getBalance());
						}else{
							System.out
							.println("You account already closed.");
						}
					

						
				}
					 accountNum = Validator
								.getString(keyboard,
										"Enter an account # or -1 to stop entering accounts : ");
			}
			}// Create Transactions
			else if (response.equals("6")) {
				
				
				answer = "";
				accountNum = Validator.getString(keyboard,
						"Enter an account # ");
				int index=0;
				while (!answer.equals("-1")) {
					
					index =	dummyAccount.searchAccount(myAccount, accountNum);
					if (index == -1){
						//account does not exists
						System.out.println("Account not found ");
					}else {
						if (myAccount.get(index).isStatus()) {
						System.out.println("You current balance is: "
								+ myAccount.get(index).getBalance());

					
					amount = Validator.getDouble(keyboard,
							"Enter the amount of the check: ");
					date = Validator.getDate(keyboard,
							"Enter the date of the check:");

					Transcation tempTranscation = new Transcation("C", amount, date);

					
							myAccount.get(index).addTranscation(tempTranscation);
							
						}else {
							System.out
							.println("You account already closed.");
						}

					}
					System.out.println("\n\n");
					answer = Validator.getString(keyboard,
							"More Check? or -1 to finish : ").toLowerCase();
				}
			
				System.out.print("The new balance is: "
						+ myAccount.get(index).calculateNewBalance());
			}
			else if (response.equals("7")){
				for (int i = 0; i < myAccount.size(); i++) {
					System.out.println("\n"
							+ myAccount.get(i).getAccountTransaction());
					
				}
			}
			else {
				System.out.println("\n Out of options range ");
			}
			response = Validator
					.getString(
							keyboard,
	
							"What you want to do:\n 1.Add accounts.\n 2.Remove accounts.\n 3.Close accounts.\n 4. Withdraw. \n 5.Deposit \n 6.Check.\n7. Print account history. \n 8. Quit.");
	*/
	}
	
	}

}
