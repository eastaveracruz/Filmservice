package filmservice.util.assertion;

import filmservice.model.Role;
import filmservice.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class UserCreationHelper {
    private static List<User> userList = new CopyOnWriteArrayList<>();

    static {
        for (int i = 1; i <= 5; i++) {
            userList.add(new User(i, "User" + i, "123", Role.ROLE_USER));
        }
        userList.add(new User(6 , "Admin", "123", Role.ROLE_ADMIN));
    }

    public static List<User> getUserList() {
        return new ArrayList<>(userList);
    }

    public static Map<Integer, User> getUserMap() {
        return userList.stream().collect(Collectors.toMap(User::getId, o -> o));
    }
}
