class Program {
    public static void main(String[] args) {
        User user1 = new User("User1", 100);
        User user2 = new User("User2", 100);
        User user3 = new User("User3", 100);
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user3);
        System.out.println("Get next id: " + UserIdsGenerator.getInstance().generateId());
        User user4 = new User("User4", 100);
        System.out.println(user4);
    }
}