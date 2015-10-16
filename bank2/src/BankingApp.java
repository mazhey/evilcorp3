import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class BankingApp {

	public static void main(String[] args) {

		Scanner keyboard = new Scanner(System.in);
		String accountNum, accountName, type, response, answer;
		double balance, amount, withdraw, deposit;
		Date date = new Date();
		Account dummyAccount = new Account();
		ArrayList<Account> myAccount = new ArrayList<Account>();

		System.out.println("Welcome to Evil Corp Savings and Loan");

		// list accounts
		System.out.println("List of Accounts ");
		for (int i = 0; i < myAccount.size(); i++) {
			System.out.println("\n" + myAccount.get(i));

		}

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
				int index =	dummyAccount.searchAccount(myAccount, accountNum);
				if (index == -1){
					
					//account does not exists; we can add a new account ===> we need a method
					accountName = Validator.getString(keyboard,
							"Enter the name for acct #" + accountNum);

					balance = Validator.getDouble(keyboard,
							"Enter the balance for acct #" + accountNum);

					Account tempAccount = new Account(accountNum,
							accountName, balance);
					myAccount.add(tempAccount);
					
				}else
				{
					//account already exists
					System.out.println("Account already exists. ");
				}
				
					//ask user if he wants to enter new account or exit
					System.out.println("\n\n");
					accountNum = Validator
							.getString(keyboard,
									"Enter an account # or -1 to stop entering accounts : ");

				}
				// remove method
			} else if (response.equals("2")) {
				
				accountNum = Validator.getString(keyboard,
						"Give the account# you want to remove : ");
				
				int index =	dummyAccount.searchAccount(myAccount, accountNum);
				if (index == -1){
					//account does not exists
					System.out.println("Account not found ");
				}else{
					myAccount.remove(index);
			
					System.out.println("Account found and removed ");
				}
				
			} // close account
			else if (response.equals("3")) {

				
				accountNum = Validator
						.getString(keyboard,
								"Enter an account # or -1 to stop entering accounts : ");
				while (!accountNum.equals("-1")) {
				int index =	dummyAccount.searchAccount(myAccount, accountNum);
				if (index == -1){
					//account does not exists
					System.out.println("Account not found ");
				}else{
					System.out.println("You current balance is: "
							+ myAccount.get(index).getBalance());
					//if balance == 0, then we can close the account
					if (myAccount.get(index).getBalance() == 0.0) {
						myAccount.get(index).setStatus(false);
						System.out
								.println("You account close succefully.");
					} else {
						System.out.println("You current balance is: "
								+ myAccount.get(index).getBalance());
						System.out
								.println("You cannot close this account.");
					}
				}
				accountNum = Validator
						.getString(keyboard,
								"Enter an account # or -1 to stop entering accounts : ");
			}	
				
			} 
			// withdraw
			else if (response.equals("4")) {
				
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
		}

	}

}
