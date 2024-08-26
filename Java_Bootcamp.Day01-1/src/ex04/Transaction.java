import java.util.UUID;

public class Transaction {
    public enum TransferCategory {
        DEBIT,
        CREDIT
    }

    private UUID identifier;
    private User recipient;
    private User sender;
    private TransferCategory transferCategory;
    private Integer transferAmount;

    public Transaction(User sender, User recipient, TransferCategory transferCategory, Integer transferAmount,
            UUID id) {
        this.identifier = id;
        this.recipient = recipient;
        this.sender = sender;
        this.transferCategory = transferCategory;
        setTransferAmount(transferAmount);
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public TransferCategory getTransferCategory() {
        return transferCategory;
    }

    public Integer getTransferAmount() {
        return transferAmount;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setTransferCategory(TransferCategory transferCategory) {
        this.transferCategory = transferCategory;
    }

    public void setTransferAmount(Integer transferAmount) {
        this.transferAmount = transferAmount;
        sender.setBalance(sender.getBalance() + transferAmount);
    }

    @Override
    public String toString() {
        return "Transaction: id=" + getIdentifier() + ", recipient=" + getRecipient().getName() + ", sender="
                + getSender().getName()
                + ", transferCategory=" + getTransferCategory() + ", transferAmount=" + getTransferAmount();
    }

}