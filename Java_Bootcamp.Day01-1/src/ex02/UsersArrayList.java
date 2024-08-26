public class UsersArrayList implements UsersList {
    private Integer maxLength = 10;
    private User[] users = new User[maxLength];
    private Integer numberOfUsers = 0;

    @Override
    public void addUser(User user) {
        if (numberOfUsers.equals(maxLength)) {
            maxLength *= 2;
            User[] newUsers = new User[maxLength];
            System.arraycopy(users, 0, newUsers, 0, users.length); // 0 - start index in users
            users = newUsers;
        }
        users[numberOfUsers] = user;
        ++numberOfUsers;
    }

    @Override
    public User getUserById(Integer id) {
        for (User user : users) {
            if (user.getIdentifier().equals(id)) {
                return user;
            }
        }
        throw new UserNotFoundException("User with id " + id + " not found");
    }

    @Override
    public User getUserByIndex(Integer index) {
        if (index >= 0 && index < numberOfUsers) {
            return users[index];
        }
        throw new UserNotFoundException("User with index " + index + " not found");
    }

    @Override
    public Integer getNumberOfUsers() {
        return numberOfUsers;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("UsersArrayList:");
        for (int i = 0; i < numberOfUsers; ++i) {
            res.append("\n\t").append(users[i]);
        }
        return res.toString();
    }
}