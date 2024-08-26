public class User {
    private Integer identifier;
    private String name;
    private Integer balance;
    private TransactionsLinkedList transactionsList;

    public User(String name, Integer balance) {
        if (balance >= 0)
            this.balance = balance;
        else {
            this.balance = 0;
            throw new RuntimeException("Incorrect init balance");
        }
        this.identifier = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        this.transactionsList = new TransactionsLinkedList();
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public Integer getBalance() {
        return balance;
    }

    public TransactionsLinkedList getTransactionsList() {
        return transactionsList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public void setTransactionsList(TransactionsLinkedList transactionsList) {
        this.transactionsList = transactionsList;
    }

    @Override
    public String toString() {
        return "User: id=" + getIdentifier() + ", name='" + getName() + '\'' + ", balance=" + getBalance();
    }
}