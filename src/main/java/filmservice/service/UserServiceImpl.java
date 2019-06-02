package filmservice.service;

import filmservice.model.User;
import filmservice.repository.UserRepository;
import filmservice.util.exception.NotFoundException;
import filmservice.web.AuthorizedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static filmservice.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository repository;


    @Override
    public User create(User user) {
        Assert.notNull(user, "user must be not null");
        return repository.save(user);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public void update(User user) {
        Assert.notNull(user, "user must be not null");
        checkNotFoundWithId(repository.save(user), user.getId());
    }

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    public User getByLogin(String userName) {
        User user = repository.getByLogin(userName);
        if (user == null){
            throw new UsernameNotFoundException(String.format("User with login %s not found in database", userName));
        }
        return user;
    }


    @Override
    public AuthorizedUser loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = repository.getByLogin(userName);

        if (user == null){
            throw new UsernameNotFoundException(String.format("User with login %s not found in database", userName));
        }
        return new AuthorizedUser(user);
    }
}
