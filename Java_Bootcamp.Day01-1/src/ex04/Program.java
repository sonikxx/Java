public class Program {

    public static void main(String[] args) {
        TransactionsService transactionService = new TransactionsService();
        User user1 = new User("User1", 100);
        User user2 = new User("User2", 200);
        User user3 = new User("User3", 300);
        transactionService.addUser(user1);
        transactionService.addUser(user2);
        transactionService.addUser(user3);
        System.out.println(transactionService.getUsersList());
        transactionService.performTransaction(user1, user2, 10);
        transactionService.performTransaction(user1, user3, 50);
        try {
            transactionService.performTransaction(user1, user3, 41);
        } catch (IllegalTransactionException e) {
            System.err.println(e.getMessage());
        }
        System.out.print("After transactions ");
        System.out.println(transactionService.getUsersList());
        System.out.println("Balance user1: " + transactionService.getUserBalance(user1));
        System.out.println("Transactions user1:");
        Transaction[] transactions = transactionService.getTransactions(user1);
        for (int i = 0; i < transactions.length; ++i) {
            System.out.println("\t" + transactions[i]);
        }
        System.out.println("Check transactions:");
        Transaction[] checkTransactions = transactionService.checkTransactions();
        for (int i = 0; i < checkTransactions.length; ++i) {
            System.out.println("\t" + checkTransactions[i]);
        }
        transactionService.removeTransaction(user1, transactions[0].getIdentifier());
        System.out.println("Check transactions after removing:");
        checkTransactions = transactionService.checkTransactions();
        for (int i = 0; i < checkTransactions.length; ++i) {
            System.out.println("\t" + checkTransactions[i]);
        }
    }
}