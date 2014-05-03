package com.jamesshore.finances;

public class StockMarketYear {
	
	private int startingBalance;
	private int startingPrincipal;
	private int interestRate;
	private int totalWithdrawals;
	
	public StockMarketYear(int startingBalance, int startingPrincipal, int interestRate) {
		this.startingBalance = startingBalance;
		this.startingPrincipal = startingPrincipal;
		this.interestRate = interestRate;
		this.totalWithdrawals = 0;
	}

	public int startingBalance() {
		return startingBalance;
	}

	public int startingPrincipal() {
		return startingPrincipal;
	}

	public int interestRate() {
		return interestRate;
	}

	public void withdraw(int amount) {
		this.totalWithdrawals  += amount;
	}

	public int capitalGainsWithdrawn() { 
		int result = -1 * (startingPrincipal() - totalWithdrawals);
		return Math.max(0,  result);
	}

	public int capitalGainsTaxIncurred(int taxRate) {
		double dblTaxRate = taxRate;
		double dblCapGains = capitalGainsWithdrawn();
		
		return (int)(dblCapGains / (1 - dblTaxRate/100) - dblCapGains);
	}

	public int totalWithdrawn(int capitalGainsTax) {
		return totalWithdrawals + capitalGainsTaxIncurred(capitalGainsTax);
	}

	public int interestEarned(int capitalGainsTaxRate) {
		return (startingBalance - totalWithdrawn(capitalGainsTaxRate)) * interestRate / 100;
	}

	public int endingPrincipal() {
		int result = startingPrincipal() - totalWithdrawals;
		return Math.max(0, result);
	}

	public int endingBalance(int capitalGainsTaxRate) {
		int modifiedStart = startingBalance() - totalWithdrawn(capitalGainsTaxRate);
		return modifiedStart + interestEarned(capitalGainsTaxRate);
	}

	public StockMarketYear nextYear(int capitalGainsTaxRate) {
		return new StockMarketYear(this.endingBalance(capitalGainsTaxRate), this.endingPrincipal(), this.interestRate);
	}

}
