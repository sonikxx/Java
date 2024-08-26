import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private boolean isDev;
    private Scanner scanner;
    private TransactionsService transactionsService;

    public Menu(boolean isDev) {
        this.isDev = isDev;
        scanner = new Scanner(System.in);
        transactionsService = new TransactionsService();
    }

    public void run() {
        while (true) {
            if (isDev) {
                System.out.println("1. Add a user");
                System.out.println("2. View user balances");
                System.out.println("3. Perform a transfer");
                System.out.println("4. View all transactions for a specific user");
                System.out.println("5. DEV – remove a transfer by ID");
                System.out.println("6. DEV – check transfer validity");
                System.out.println("7. Finish execution");
            } else {
                System.out.println("1. Add a user");
                System.out.println("2. View user balances");
                System.out.println("3. Perform a transfer");
                System.out.println("4. View all transactions for a specific user");
                System.out.println("5. Finish execution");
            }
            Integer userChoice = getUserChoice(isDev);
            switch (userChoice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    viewUserBalances();
                    break;
                case 3:
                    performTransfer();
                    break;
                case 4:
                    viewAllTransactions();
                    break;
                case 5:
                    if (!isDev) {
                        scanner.close();
                        return;
                    }
                    removeTransaction();
                    break;
                case 6:
                    checkTransaction();
                    break;
                case 7:
                    scanner.close();
                    return;
                default:
                    return;
            }
        }
    }

    private Integer getUserChoice(boolean isDev) {
        Integer res = 0;
        try {
            if (scanner.hasNextInt()) {
                res = scanner.nextInt();
                if ((isDev && (res != 1 && res != 2 && res != 3 && res != 4 && res != 5 && res != 6 && res != 7))
                        || (!isDev && (res != 1 && res != 2 && res != 3 && res != 4 && res != 5)))
                    throw new RuntimeException("Invalid input, please try again:");
            } else {
                throw new RuntimeException("Invalid input, please try again:");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            scanner.nextLine();
            res = getUserChoice(isDev);
        }
        return res;
    }

    private void addUser() {
        String trash = scanner.nextLine();
        System.out.println("Enter a user name and a balance");
        String input = scanner.nextLine();
        String name = input.split(" ")[0];
        try {
            Integer balance = Integer.parseInt(input.split(" ")[1]);
            User user = new User(name, balance);
            transactionsService.addUser(user);
            System.out.println("User with id = " + user.getIdentifier() + " is added");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            System.out.println("---------------------------------------------------------");
        }
    }

    private void viewUserBalances() {
        System.out.println("Enter a user ID");
        try {
            if (scanner.hasNextInt()) {
                Integer userId = scanner.nextInt();
                User user = transactionsService.getUserById(userId);
                System.out.println(user.getName() + " - " + user.getBalance());
            } else {
                scanner.nextLine();
                scanner.nextLine();
                throw new RuntimeException("Invalid input, please try again:");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            System.out.println("---------------------------------------------------------");
        }
    }

    private void performTransfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        try {
            if (scanner.hasNextInt()) {
                Integer senderID = scanner.nextInt();
                if (scanner.hasNextInt()) {
                    Integer recipientID = scanner.nextInt();
                    if (scanner.hasNextInt()) {
                        Integer transferAmount = scanner.nextInt();
                        User sender = transactionsService.getUserById(senderID);
                        User recipient = transactionsService.getUserById(recipientID);
                        transactionsService.performTransaction(sender, recipient, transferAmount);
                        System.out.println("The transfer is completed");
                    } else {
                        scanner.nextLine();
                        throw new RuntimeException("Invalid input, please try again:");
                    }
                } else {
                    scanner.nextLine();
                    throw new RuntimeException("Invalid input, please try again:");
                }
            } else {
                scanner.nextLine();
                throw new RuntimeException("Invalid input, please try again:");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            System.out.println("---------------------------------------------------------");
        }
    }

    private void viewAllTransactions() {
        System.out.println("Enter a user ID");
        try {
            if (scanner.hasNextInt()) {
                Integer userId = scanner.nextInt();
                User user = transactionsService.getUserById(userId);
                Transaction[] transactions = transactionsService.getTransactions(user);
                if (transactions.length == 0) {
                    System.out.println("The user has no transactions");
                } else {
                    for (int i = 0; i < transactions.length; ++i) {
                        System.out.println(
                                "To " + transactions[i].getRecipient().getName() + "(id = "
                                        + transactions[i].getRecipient().getIdentifier() + ") "
                                        + transactions[i].getTransferAmount()
                                        + " with id = " + transactions[i].getIdentifier());
                    }
                }
            } else
                throw new RuntimeException("Invalid input, please try again:");
        } catch (Exception e) {
            scanner.nextLine();
            System.err.println(e.getMessage());
        } finally {
            System.out.println("---------------------------------------------------------");
        }
    }

    private void removeTransaction() {
        String trash = scanner.nextLine();
        System.out.println("Enter a user ID and a transfer ID");
        String input = scanner.nextLine();
        try {
            Integer userId = Integer.parseInt(input.split(" ")[0]);
            UUID transferId = UUID.fromString(input.split(" ")[1]);
            Transaction transactionRemove = transactionsService
                    .removeTransaction(transactionsService.getUserById(userId), transferId);
            System.out.println("Transfer To " +
                    transactionRemove.getRecipient().getName() + "(id = "
                    + transactionRemove.getRecipient().getIdentifier()
                    + ") " + Math.abs(transactionRemove.getTransferAmount()) + " removed");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            System.out.println("---------------------------------------------------------");
        }
    }

    private void checkTransaction() {
        System.out.println("Check results:");
        try {
            Transaction[] check = transactionsService.checkTransactions();
            if (check.length == 0)
                System.out.println("No transactions");
            else {
                for (int i = 0; i < check.length; ++i) {
                    System.out.println(check[i].getSender().getName() + "(id = " + check[i].getSender().getIdentifier()
                            + ") has an unacknowledged transfer id = " + check[i].getIdentifier() + " form "
                            + check[i].getRecipient().getName() + "(id = " + check[i].getRecipient().getIdentifier()
                            + ") for " + check[i].getTransferAmount());
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            System.out.println("---------------------------------------------------------");
        }
    }

}