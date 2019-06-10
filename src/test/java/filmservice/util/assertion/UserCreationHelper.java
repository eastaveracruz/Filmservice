package filmservice.util.assertion;

import filmservice.model.util.Role;
import filmservice.model.User;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class UserCreationHelper {
    private static List<User> userList = new CopyOnWriteArrayList<>();
    public static final int ADMIN_ID = 7;
    public static final int USER_ID = 1;
    public static final User ADMIN = new User(7, "admin", "$2a$11$tnqA0FyrsfDjSxFHCXPj7.7jViAHhwOGzP2EkkcQ3hErfI4B1gDNq", Role.ROLE_USER, Role.ROLE_ADMIN);
    public static final User USER = new User(1, "user", "$2a$11$zDhuFTfr4bYpeYevidJZ.utCNhs8al79Fzt.29bEErW8UbIhKrTim", Role.ROLE_USER);

    static {
        userList.add(new User(1, "user", "$2a$11$zDhuFTfr4bYpeYevidJZ.utCNhs8al79Fzt.29bEErW8UbIhKrTim", Role.ROLE_USER));
        for (int i = 2; i <= 6; i++) {
            userList.add(new User(i, "user" + (i - 1), "$2a$11$zDhuFTfr4bYpeYevidJZ.utCNhs8al79Fzt.29bEErW8UbIhKrTim", Role.ROLE_USER));
        }
        userList.add(new User(7, "admin", "$2a$11$tnqA0FyrsfDjSxFHCXPj7.7jViAHhwOGzP2EkkcQ3hErfI4B1gDNq", Role.ROLE_USER, Role.ROLE_ADMIN));
    }

    public static List<User> getUserList() {
        return new ArrayList<>(userList);
    }

    public static Map<Integer, User> getUserMap() {
        return userList.stream().collect(Collectors.toMap(User::getId, o -> o));
    }
}
