package filmservice.service;

import filmservice.Profiles;
import filmservice.model.Rating;
import filmservice.model.util.Role;
import filmservice.model.User;
import filmservice.util.assertion.UserCreationHelper;
import filmservice.util.exception.NotFoundException;
import filmservice.web.TimingExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static filmservice.util.assertion.UserCreationHelper.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(Profiles.ACTIVE_DB)
@ExtendWith(TimingExtension.class)
public class UserServiceImplTest {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    private RatingService ratingService;

    private static final int FILM_ID = 1;
    private static final int ID_LAST = 8;
    private static final User USER = new User(ID_LAST, "User" + ID_LAST, "123", Role.ROLE_USER);


    @Test
    void getAll() {
        List<User> usersExp = UserCreationHelper.getUserList();
        List<User> users = this.userService.getAll();
        assertThat(usersExp).usingElementComparatorIgnoringFields("confirmPassword", "file", "rating").isEqualTo(users);
    }

    @Test
    void get() {
        User userExp = UserCreationHelper.getUserList().get(0);
        User user = userService.get(USER_ID);
        assertThat(user).isEqualToIgnoringGivenFields(userExp, "confirmPassword", "file", "rating");
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> userService.get(1000));
    }

    @Test
    void create() {
        User user = userService.create(USER);
        User userExp = userService.get(user.getId());
        assertThat(user).isEqualToIgnoringGivenFields(userExp, "confirmPassword", "file", "rating");
    }

    @Test
    void update() {
        User user = userService.get(USER_ID);
        user.setLogin("SpunchBob");
        userService.update(user);
        User userExp = userService.get(USER_ID);
        assertThat(user).isEqualToIgnoringGivenFields(userExp, "confirmPassword", "file", "rating");
    }

    @Test
    void delete() {
        userService.delete(USER_ID);
        List<User> users = userService.getAll();
        List<User> usersExp = UserCreationHelper.getUserList();
        User uRemove = usersExp.stream().filter(user -> user.getId() == USER_ID).findFirst().get();
        usersExp.remove(uRemove);
        assertThat(users).usingElementComparatorIgnoringFields("confirmPassword", "file", "rating").isEqualTo(usersExp);
    }

    @Test
    void rating(){
        Rating rating = new Rating(USER_ID, FILM_ID, 2);
        ratingService.save(rating);
        User user = userService.get(USER_ID);
        assertThat(rating).isEqualTo(user.getRating().get(FILM_ID));
    }
}