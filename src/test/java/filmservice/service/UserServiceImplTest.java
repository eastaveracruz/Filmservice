package filmservice.service;

import filmservice.Profiles;
import filmservice.model.Role;
import filmservice.model.User;
import filmservice.util.assertion.UserCreationHelper;
import filmservice.util.exception.NotFoundException;
import filmservice.web.TimingExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ExtendWith(SpringExtension.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(Profiles.ACTIVE_DB)
@ExtendWith(TimingExtension.class)
public class UserServiceImplTest {

    @Autowired
    private UserService service;

    private static final int ID = 1;
    private static final int ID_LAST = 7;
    private static final User USER = new User(ID_LAST, "User" + ID_LAST, "123", Role.ROLE_USER);



    @Test
    void getAll() {
        List<User> usersExp = UserCreationHelper.getUserList();
        List<User> users = service.getAll();
        assertThat(users).isEqualTo(usersExp);
    }

    @Test
    void get() {
        User userExp = new User(ID, "User" + ID, "123", Role.ROLE_USER);
        User user = service.get(ID);
        assertThat(user).isEqualTo(userExp);
    }

    @Test
    void getNotFound(){
        assertThrows(NotFoundException.class, () -> service.get(1000));
    }

    @Test
    void create() {
        service.create(USER);
        List<User> users = service.getAll();

        List<User> usersExp = UserCreationHelper.getUserList();
        usersExp.add(USER);

        assertThat(users).isEqualTo(usersExp);
    }

    @Test
    void update() {
        //Hibernate кэширует и проваливает тест get, где SpunchBob != User, поэтому ID+1
        User user = service.get(ID+1);

        user.setLogin("SpunchBob");
        service.update(user);

        User userExp = service.get(ID+1);

        assertThat(user).isEqualTo(userExp);
    }

    @Test
    void delete() {
        service.delete(ID);
        List<User> users = service.getAll();

        List<User> usersExp = UserCreationHelper.getUserList();
        User uRemove = usersExp.stream().filter(user -> user.getId() == ID).findFirst().get();
        usersExp.remove(uRemove);

        assertThat(users).isEqualTo(usersExp);
    }








}