package filmservice.repository;

import filmservice.model.User;
import filmservice.util.mock.UserUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Override
    public User save(User user) {
        UserUtil.getUserList().add(user);
        return user;
    }

    @Override
    public boolean delete(int id) {
        return UserUtil.getUserMap().remove(id) != null;
    }


    @Override
    public User get(int id) {
        return UserUtil.getUserMap().get(id);
    }

    @Override
    public List<User> getAll() {
        return UserUtil.getUserList();
    }
}
