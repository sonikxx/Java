public class User {
    private Integer identifier;
    private String name;
    private Integer balance;

    public User(String name, Integer balance, Integer identifier) {
        this.identifier = identifier;
        this.name = name;
        if (balance >= 0)
            this.balance = balance;
        else {
            this.balance = 0;
            System.err.println("Incorrect balance");
            System.exit(-1);
        }
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

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User: id=" + getIdentifier() + ", name='" + getName() + '\'' + ", balance=" + getBalance();
    }
}