package com.rissandimo.bankwebapp3;

import com.rissandimo.bankwebapp3.dao.Transaction;
import com.rissandimo.bankwebapp3.dao.Type;
import com.rissandimo.bankwebapp3.dao.User;
import com.rissandimo.bankwebapp3.repository.TransactionRepository;
import com.rissandimo.bankwebapp3.repository.UserRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Transient;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionFunctionalityTests {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private User user;

	@BeforeClass
	private void init()
	{
		user = createUser();
	}

	@Test
	public void transactionDepositTest() {

		Transaction transaction = new Transaction(25.00, "Sample Deposit", Type.DEPOSIT);

		transaction.setUser(user);
		transaction.setBalance(transaction.getAmount(), transaction.getType());

		user.addTransaction(transaction);

		userRepository.save(user);

		List<Transaction> transactionList = transactionRepository.findTransactionsByUser(user);

		transactionRepository.save(transaction);

		logger.info("Transaction: {}", transactionList);
	}

	@Transient
	@Test
	public void transactionWithdrawalTest()
	{
		Transaction withdrawalTransaction = new Transaction(30.00, "Sample Withdrwal", Type.WITHDRAWAL);
		withdrawalTransaction.setUser(user);
		withdrawalTransaction.setBalance(withdrawalTransaction.getAmount(), withdrawalTransaction.getType());

		user.addTransaction(withdrawalTransaction);

		userRepository.save(user);

		List<Transaction> transactionList = transactionRepository.findTransactionsByUser(user);
		transactionRepository.save(withdrawalTransaction);

		logger.info("transaction: {}", transactionList );
	}

	@Transient
	@Test
	public void transactionPurchaseTest()
	{

		Transaction transaction = new Transaction(35.00, "sample transaction", Type.PURCHASE);

		user.setCurrentBalance(user.getCurrentBalance() - transaction.getAmount());

		transactionRepository.save(transaction);

		transaction.setUser(user);

	}

	private User createUser()
	{
		User user = new User("Omid", "Nassir",
				"rissandimo", passwordEncoder.encode("password"));
		user.setActive(1);
		user.setRoles("USER");
		return user;
	}

}
