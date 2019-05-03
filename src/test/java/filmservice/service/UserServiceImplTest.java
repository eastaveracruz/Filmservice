package filmservice.service;

import filmservice.model.User;
import filmservice.util.exception.NotFoundException;
import filmservice.util.mock.UserUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserServiceImplTest {

    @Autowired
    private UserService service;
    public static final int ID = 1;

//    @Before
//    public void setUp() throws Exception {
//    }
//
//    @After
//    public void tearDown() throws Exception {
//    }

    @Test
    public void getAll() {
        List<User> usersExp = UserUtil.getUserList();
        List<User> users = service.getAll();
        assertThat(users).usingElementComparatorIgnoringFields("role", "password").isEqualTo(usersExp);
    }

    @Test
    public void create() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void get() {
        User userExp = new User(ID, "User " + ID);
        User user = service.get(ID);
        assertThat(user).isEqualToIgnoringGivenFields(userExp, "role", "password");
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound(){
        service.get(1000);
    }

    @Test
    public void update() {
    }


}