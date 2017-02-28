package patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class BankAccount
{
	private long accountNumber;
	private String owner;
	private double balance;
	private double interestRate;
	private String branch;
	private double rate;
		
	private BankAccount()
	{
		
	}
	public static class Builder
	{
		private long accountNumber;
		private String owner;
		private double balance;
		private double interestRate;
		private String branch;
		private double rate;
		
		public Builder(long accountNumber)
		{
			this.accountNumber = accountNumber;
		}
		public Builder withOwner(String owner)
		{
			this.owner = owner;
			return this;
		}
		public Builder atBranch(String branch)
		{
			this.branch = branch;
			return this;
		}
		public Builder openingBalance(double balance)
		{
			this.balance = balance;
			return this;
		}
		public Builder atRate(double rate)
		{
			this.rate = rate;
			return this;
		}
		
		public BankAccount build()
		{
            //Here we create the actual bank account object, which is always in a fully initialised state when it's returned.
            BankAccount account = new BankAccount();  //Since the builder is in the BankAccount class, we can invoke its private constructor.
            account.accountNumber = this.accountNumber;
            account.owner = this.owner;
            account.branch = this.branch;
            account.balance = this.balance;
            account.interestRate = this.interestRate;
            return account;
		}
	}
	
    public static void main(String args[])
    {
		System.out.println(Integer.toBinaryString(-5) + "\t-5");
		System.out.println(Integer.toBinaryString(-1) + "\t-1");
		System.out.println(Integer.toBinaryString(1<<31) + "\t" + (1<<31));
		int num = (1 << 31);
		System.out.println(Integer.toBinaryString(num/-1) + "\t" + (num/-1));
		//num = 0x7FFFFFFF & num)
		BankAccount account = new BankAccount.Builder(1234L)
					.withOwner("Marge")
					.atBranch("Springfield")
					.openingBalance(100)
					.atRate(2.5)
					.build();
		BankAccount anotherAccount = new BankAccount.Builder(4567L)
					.withOwner("Homer")
					.atBranch("Springfield")
					.openingBalance(100)
					.atRate(2.5)
					.build();
    }
}
