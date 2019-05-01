package filmservice.util.mock;

import filmservice.model.Film;
import filmservice.model.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class UserUtil {
    private static List<User> userList = new CopyOnWriteArrayList<>();

    static {
        for (int i = 0; i < 5; i++) {
            userList.add(new User(i, "User " + i));
        }
    }

    public static List<User> getUserList() {
        return userList;
    }

    public static Map<Integer, User> getUserMap() {
        return userList.stream().collect(Collectors.toMap(User::getId, o -> o));
    }
}
