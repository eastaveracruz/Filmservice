package filmservice;

import filmservice.model.BaseEntity;
import filmservice.model.Rating;
import filmservice.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public AuthorizedUser(User user) {
        super(user.getLogin(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int getId() {
        return user.getId();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<Integer, Rating> getUserRating() {
        return user.getRating();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
