import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    public class LinkedListNode {
        public Transaction value;
        public LinkedListNode next;

        public LinkedListNode(Transaction value) {
            this.value = value;
            this.next = null;
        }
    }

    private LinkedListNode head;

    private Integer numberOfTransaction = 0;

    public TransactionsLinkedList() {
        head = null;
    }

    public Integer getNumberOfTransaction() {
        return numberOfTransaction;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        LinkedListNode newNode = new LinkedListNode(transaction);
        if (head == null) {
            head = newNode;
        } else {
            // reverse linkes list
            LinkedListNode storageHead = head;
            head = newNode;
            head.next = storageHead;
        }
        ++numberOfTransaction;
    }

    @Override
    public Transaction removeTransactionById(UUID id) {
        LinkedListNode current = head;
        boolean found = false;
        if (head != null) {
            if (head.value.getIdentifier().equals(id)) {
                current = head;
                head = head.next;
                --numberOfTransaction;
            } else {
                LinkedListNode previous = head;
                current = head.next;
                while (current != null) {
                    if (current.value.getIdentifier().equals(id)) {
                        previous.next = current.next;
                        --numberOfTransaction;
                        found = true;
                        break;
                    }
                    previous = current;
                    current = current.next;
                }
                if (!found)
                    throw new TransactionNotFoundException("Transaction with id " + id + " not found");
            }
        } else {
            throw new TransactionNotFoundException("Transaction with id " + id + " not found");
        }
        return current.value;
    }

    @Override
    public Transaction[] toArray() {
        TransactionsLinkedList transactionsLinkedList = new TransactionsLinkedList();
        LinkedListNode current = head;
        while (current != null) {
            transactionsLinkedList.addTransaction(current.value);
            current = current.next;
        }
        Transaction[] transactions = new Transaction[numberOfTransaction];
        int i = 0;
        current = transactionsLinkedList.head;
        while (current != null) {
            transactions[i] = current.value;
            current = current.next;
            ++i;
        }
        return transactions;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("TransactionsLinkedList:");
        Transaction[] reverseList = this.toArray();
        for (int i = 0; i < reverseList.length; ++i) {
            res.append("\n\t").append(reverseList[i]);
        }
        return res.toString();
    }

}