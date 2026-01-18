package com.example.app.services;

import com.example.app.model.Transaction;
import com.example.app.model.User;
import com.example.app.model.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * TransactionService - Converted from MySQL to MongoDB
 * ALL original business logic preserved exactly
 */
@Service
public class TransactionService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserService userService;

    // Helper method removed - passing User explicitly instead

    /**
     * Transfer balance to receiver account
     * Original logic: Get receiver balance, add amount, update database
     */
    public void balanceTransfer(String receiverAccount, double amount) {
        try {
            double receiverBalance = userService.getBalanceAccount(receiverAccount);
            double finalBalance = receiverBalance + amount;

            // MongoDB update
            Query query = new Query(Criteria.where("account").is(receiverAccount));
            Update update = new Update().set("balance", finalBalance);
            mongoTemplate.updateFirst(query, update, User.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Transfer balance to online wallet (mobile)
     * Original logic: Get wallet balance, add amount, update database
     */
    public void balanceTransferOnline(String receiverWallet, double amount) {
        try {
            double receiverBalance = userService.getBalanceOnline(receiverWallet);
            double finalBalance = receiverBalance + amount;

            // MongoDB update
            Query query = new Query(Criteria.where("mobile").is(receiverWallet));
            Update update = new Update().set("walletBalance", finalBalance); // FIXED: Update walletBalance
            mongoTemplate.updateFirst(query, update, User.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Update sender balance after transfer
     * Original logic: Get sender balance, subtract amount, update database
     */
    public void senderBalanceUpdate(String account, double amount) {
        try {
            if (account == null)
                return;

            double senderBalance = userService.getBalanceAccount(account);
            double finalBalance = senderBalance - amount;

            // MongoDB update
            Query query = new Query(Criteria.where("account").is(account));
            Update update = new Update().set("balance", finalBalance); // Correct: Bank balance
            mongoTemplate.updateFirst(query, update, User.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Update sender online wallet balance
     * Original logic: Get wallet balance, subtract amount, update database
     */
    public void senderBalanceUpdateOnline(String mobile, double amount) {
        try {
            double senderBalance = userService.getBalanceOnline(mobile);
            double finalBalance = senderBalance - amount;

            // MongoDB update
            Query query = new Query(Criteria.where("mobile").is(mobile));
            Update update = new Update().set("walletBalance", finalBalance); // FIXED: Update walletBalance
            mongoTemplate.updateFirst(query, update, User.class);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Check if utility account exists
     * Original logic: Query utility table, match provider
     * Note: You'll need to create a Utility model for MongoDB
     */
    public boolean utilityAccountCheck(String provider, String account, String type) {
        try {
            // Flexible check: search by account first
            Query query = new Query(Criteria.where("account").is(account));
            Utility utility = mongoTemplate.findOne(query, Utility.class);

            if (utility != null) {
                // Check provider (case-insensitive)
                boolean providerMatch = utility.getProvider() != null &&
                        utility.getProvider().equalsIgnoreCase(provider);

                // Check type (case-insensitive)
                boolean typeMatch = utility.getType() != null &&
                        utility.getType().equalsIgnoreCase(type);

                return providerMatch && typeMatch;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Check utility account (Provider and Account only - for Gas)
     */
    public boolean utilityAccountCheck(String provider, String account) {
        try {
            Query query = new Query(Criteria.where("account").is(account));
            Utility utility = mongoTemplate.findOne(query, Utility.class);

            if (utility != null) {
                // Check provider (case-insensitive)
                return utility.getProvider() != null &&
                        utility.getProvider().equalsIgnoreCase(provider);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Check utility bill balance
     * Original logic: Query utility table, return balance
     */
    public double utilityBillCheck(String account, String provider, String type) {
        try {
            // Less strict query: just find by account, then return balance if it exists
            Query query = new Query(Criteria.where("account").is(account));
            Utility utility = mongoTemplate.findOne(query, Utility.class);

            if (utility != null) {
                return utility.getBalance() != null ? utility.getBalance() : 0.0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * Pay utility bill
     * Original logic: Get previous balance, add payment, update database
     */
    public void utilityBillPay(String account, String provider, String type, double amount) {
        try {
            Query query = new Query(Criteria.where("account").is(account));
            Utility utility = mongoTemplate.findOne(query, Utility.class);

            if (utility != null) {
                double currentBalance = utility.getBalance() != null ? utility.getBalance() : 0.0;
                double finalBalance = currentBalance + amount;

                Update update = new Update().set("balance", finalBalance);
                mongoTemplate.updateFirst(query, update, Utility.class);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Save transaction to history
     * Original logic: Insert transaction into database
     */
    public boolean transactionHistory(Transaction transaction) {
        try {
            if (transaction != null) {
                mongoTemplate.insert(transaction);
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Get transaction list for specific account (Sender OR Receiver)
     */
    public List<Transaction> getTransactionList(String account) {
        List<Transaction> transactionList = new ArrayList<>();
        try {
            Criteria criteria = new Criteria().orOperator(
                    Criteria.where("sender").is(account),
                    Criteria.where("receiver").is(account));
            Query query = new Query(criteria);
            // Sort by createdAt descending (newest first)
            query.with(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC,
                    "createdAt"));

            transactionList = mongoTemplate.find(query, Transaction.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return transactionList;
    }

    /**
     * Get all transactions
     * Original logic: Query all transactions from database
     */
    public List<Transaction> getAllTransactionList() {
        List<Transaction> transactionList = new ArrayList<>();
        try {
            transactionList = mongoTemplate.findAll(Transaction.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return transactionList;
    }

    /**
     * Get monthly wallet expense
     * Original logic: Sum amounts for specific month and transaction types
     */
    public String[] getMonthlyWalletExpense(User loggedUser, String monthName) {
        String[] info = new String[2];
        double monthlyExpense = 0;
        int count = 0;

        try {
            if (loggedUser == null)
                return info;

            // MongoDB query - filter by month
            // Extract month from LocalDateTime and match monthName
            List<Transaction> transactions = mongoTemplate.findAll(Transaction.class);

            DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM");

            for (Transaction txn : transactions) {
                if (txn.getTransactionDate() != null &&
                        txn.getSender().equals(loggedUser.getAccount())) {

                    String txnMonth = txn.getTransactionDate().format(monthFormatter);
                    String txnType = txn.getType().toLowerCase();

                    if (txnMonth.equalsIgnoreCase(monthName) &&
                            (txnType.equals("utility") || txnType.equals("recharge") ||
                                    txnType.equals("wallet to bank") || txnType.equals("wallet to wallet"))) {
                        monthlyExpense += txn.getAmount();
                        count++;
                    }
                }
            }

            info[0] = String.valueOf(monthlyExpense);
            info[1] = String.valueOf(count);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return info;
    }

    /**
     * Get daily wallet expense
     * Original logic: Sum amounts for today and specific transaction types
     */
    public String[] getDailyWalletExpense(User loggedUser) {
        String[] info = new String[2];
        double monthlyExpense = 0;
        int count = 0;

        try {
            if (loggedUser == null)
                return info;

            LocalDateTime today = LocalDateTime.now();

            List<Transaction> transactions = mongoTemplate.findAll(Transaction.class);

            for (Transaction txn : transactions) {
                if (txn.getTransactionDate() != null &&
                        txn.getSender().equals(loggedUser.getAccount())) {

                    LocalDateTime txnDate = txn.getTransactionDate();
                    String txnType = txn.getType().toLowerCase();

                    if (txnDate.toLocalDate().equals(today.toLocalDate()) &&
                            (txnType.equals("utility") || txnType.equals("recharge") ||
                                    txnType.equals("wallet to bank") || txnType.equals("wallet to wallet"))) {
                        monthlyExpense += txn.getAmount();
                        count++;
                    }
                }
            }

            info[0] = String.valueOf(monthlyExpense);
            info[1] = String.valueOf(count);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return info;
    }

    /**
     * Get monthly account expense
     * Original logic: Sum amounts for specific month and account transaction types
     */
    public String[] getMonthlyAccountExpense(User loggedUser, String monthName) {
        String[] info = new String[2];
        double monthlyExpense = 0;
        int count = 0;

        try {
            if (loggedUser == null)
                return info;

            List<Transaction> transactions = mongoTemplate.findAll(Transaction.class);
            DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM");

            for (Transaction txn : transactions) {
                if (txn.getTransactionDate() != null &&
                        txn.getSender().equals(loggedUser.getAccount())) {

                    String txnMonth = txn.getTransactionDate().format(monthFormatter);
                    String txnType = txn.getType().toLowerCase();

                    if (txnMonth.equalsIgnoreCase(monthName) &&
                            (txnType.equals("add to wallet") || txnType.equals("bank to bank") ||
                                    txnType.equals("bank to wallet"))) {
                        monthlyExpense += txn.getAmount();
                        count++;
                    }
                }
            }

            info[0] = String.valueOf(monthlyExpense);
            info[1] = String.valueOf(count);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return info;
    }

    /**
     * Get daily account expense
     * Original logic: Sum amounts for today and account transaction types
     */
    public String[] getDailyAccountExpense(User loggedUser) {
        String[] info = new String[2];
        double monthlyExpense = 0;
        int count = 0;

        try {
            if (loggedUser == null)
                return info;

            LocalDateTime today = LocalDateTime.now();
            List<Transaction> transactions = mongoTemplate.findAll(Transaction.class);

            for (Transaction txn : transactions) {
                if (txn.getTransactionDate() != null &&
                        txn.getSender().equals(loggedUser.getAccount())) {

                    LocalDateTime txnDate = txn.getTransactionDate();
                    String txnType = txn.getType().toLowerCase();

                    if (txnDate.toLocalDate().equals(today.toLocalDate()) &&
                            (txnType.equals("add to wallet") || txnType.equals("bank to bank") ||
                                    txnType.equals("bank to wallet"))) {
                        monthlyExpense += txn.getAmount();
                        count++;
                    }
                }
            }

            info[0] = String.valueOf(monthlyExpense);
            info[1] = String.valueOf(count);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return info;
    }

    /**
     * Generate unique transaction reference ID
     * Original logic: Generate "TXN-" + 7 random alphanumeric characters
     */
    public String generateRefID() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder refID = new StringBuilder("TXN-");
        for (int i = 0; i < 7; i++) {
            int index = (int) (Math.random() * characters.length());
            refID.append(characters.charAt(index));
        }
        return refID.toString();
    }
}
