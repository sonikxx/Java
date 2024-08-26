import java.util.UUID;

public interface TransactionsList {
    public void addTransaction(Transaction transaction);

    public Transaction removeTransactionById(UUID id);

    public Transaction[] toArray();
}