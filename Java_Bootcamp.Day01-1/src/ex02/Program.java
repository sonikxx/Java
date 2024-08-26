public class Program {
    public static void main(String[] args) {
        UsersArrayList usersArrayList = new UsersArrayList();
        usersArrayList.addUser(new User("User1", 100));
        usersArrayList.addUser(new User("User2", 200));
        usersArrayList.addUser(new User("User3", 300));
        System.out.println(usersArrayList);
        System.out.println("User with id 2: " + usersArrayList.getUserById(2));
        System.out.println("User with index 1: " + usersArrayList.getUserByIndex(1));
        System.out.println("Number of users: " + usersArrayList.getNumberOfUsers());
        try {
            usersArrayList.getUserByIndex(4);
        } catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}