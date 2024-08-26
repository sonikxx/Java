import java.util.UUID;

public class TransactionsService {
    private UsersList usersList = new UsersArrayList();

    public UsersList getUsersList() {
        return usersList;
    }

    public void addUser(User user) {
        usersList.addUser(user);
    }

    public Integer getUserBalance(User user) {
        return usersList.getUserById(user.getIdentifier()).getBalance();
    }

    public void performTransaction(User sender, User recipient, Integer transferAmount) {
        if (transferAmount > sender.getBalance())
            throw new IllegalTransactionException(
                    "User with id " + sender.getIdentifier() + " has only " + sender.getBalance() + " balance");
        UUID generalID = UUID.randomUUID();
        Transaction transactionIn = new Transaction(sender, recipient, Transaction.TransferCategory.CREDIT,
                -transferAmount, generalID);
        Transaction transactionOut = new Transaction(recipient, sender, Transaction.TransferCategory.DEBIT,
                transferAmount, generalID);
        sender.getTransactionsList().addTransaction(transactionIn);
        recipient.getTransactionsList().addTransaction(transactionOut);
    }

    public Transaction[] getTransactions(User user) {
        return usersList.getUserById(user.getIdentifier()).getTransactionsList().toArray();
    }

    public void removeTransaction(User user, UUID idTrascation) {
        usersList.getUserById(user.getIdentifier()).getTransactionsList().removeTransactionById(idTrascation);
    }

    public Transaction[] checkTransactions() {
        TransactionsLinkedList allTransactions = new TransactionsLinkedList();
        for (int i = 0; i < usersList.getNumberOfUsers(); ++i) {
            User user = usersList.getUserByIndex(i);
            for (int j = 0; j < user.getTransactionsList().getNumberOfTransaction(); ++j) {
                allTransactions.addTransaction(user.getTransactionsList().toArray()[j]);
            }
        }
        Transaction[] allTransactionsArray = allTransactions.toArray();
        TransactionsLinkedList result = new TransactionsLinkedList();
        for (int i = 0; i < allTransactionsArray.length; ++i) {
            Integer counter = 0;
            for (int j = 0; j < allTransactionsArray.length; ++j) {
                if (allTransactionsArray[i].getIdentifier().equals(allTransactionsArray[j].getIdentifier())) {
                    ++counter;
                }
            }
            if (counter != 2) {
                result.addTransaction(allTransactionsArray[i]);
            }
        }
        return result.toArray();
    }

}