import java.util.UUID;

public class Program {

    public static void main(String[] args) {
        TransactionsLinkedList transactionsLinkedList = new TransactionsLinkedList();
        User user1 = new User("User1", 100);
        User user2 = new User("User2", 200);
        Transaction transaction1 = new Transaction(user1, user2, Transaction.TransferCategory.CREDIT, -10);
        transactionsLinkedList.addTransaction(transaction1);
        transactionsLinkedList.addTransaction(new Transaction(user2, user1, Transaction.TransferCategory.DEBIT, 10));
        System.out.println(transactionsLinkedList);
        transactionsLinkedList.removeTransactionById(transaction1.getIdentifier());
        System.out.print("After removing ");
        System.out.println(transactionsLinkedList);
        System.out.println("Removing random transaction:");
        try {
            transactionsLinkedList.removeTransactionById(UUID.randomUUID());
        } catch (TransactionNotFoundException e) {
            System.err.println(e.getMessage());
        }
        Transaction[] transactions = transactionsLinkedList.toArray();
        System.out.println("Transactions array: ");
        for (Transaction transaction : transactions) {
            System.out.println("\t" + transaction);
        }
    }
}
