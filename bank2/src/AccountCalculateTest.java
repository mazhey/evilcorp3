import static org.junit.Assert.*;

import org.junit.Test;


public class AccountCalculateTest {

	@Test
	public void test() {
		System.out.println("Test if calculation works") ;
	      Account A = new Account("111","rabbit",1000) ;
	      
	      assertTrue(A.withdrawalTransaction(500) == true) ;
	}

}
