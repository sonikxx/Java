class Program {
    public static void main(String[] args) {
        User sender = new User("User1", 100, 1);
        User recipient = new User("User2", 100, 2);
        System.out.println(sender);
        System.out.println(recipient);
        Transaction transactionDebit = new Transaction(sender, recipient,
                Transaction.TransferCategory.CREDIT, -10);
        Transaction transactionCredit = new Transaction(recipient, sender,
                Transaction.TransferCategory.DEBIT, 10);
        System.out.println(transactionDebit);
        System.out.println(transactionCredit);
        System.out.println(sender);
        System.out.println(recipient);
    }
}