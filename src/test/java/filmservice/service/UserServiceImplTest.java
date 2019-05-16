package filmservice.service;

import filmservice.model.Role;
import filmservice.model.User;
import filmservice.util.exception.NotFoundException;
import filmservice.util.mock.UserUtil;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserServiceImplTest {

    @Autowired
    private UserService service;

    private static final Logger log = getLogger("result");
    private static StringBuilder results = new StringBuilder();

    public static final int ID = 1;
    public static final int ID_LAST = 7;
    public static final User USER = new User(ID_LAST, "User" + ID_LAST, "123", Role.ROLE_USER);


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    // http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("\n%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result);
            log.info(result + " ms\n");
        }
    };

    static {
        // needed only for java.util.logging (postgres driver)
        SLF4JBridgeHandler.install();
    }

    @AfterClass
    public static void printResult() {
        log.info("\n---------------------------------" +
                "\nTest                 Duration, ms" +
                "\n---------------------------------" +
                results +
                "\n---------------------------------");
    }

    @Test
    public void getAll() {
        List<User> usersExp = UserUtil.getUserList();
        List<User> users = service.getAll();
        assertThat(users).isEqualTo(usersExp);
    }

    @Test
    public void get() {
        User userExp = new User(ID, "User" + ID, "123", Role.ROLE_USER);
        User user = service.get(ID);
        assertThat(user).isEqualTo(userExp);
    }

    @Test
    public void getNotFound(){
        thrown.expect(NotFoundException.class);
        service.get(1000);
    }

    @Test
    public void create() {
        service.create(USER);
        List<User> users = service.getAll();

        List<User> usersExp = UserUtil.getUserList();
        usersExp.add(USER);

        assertThat(users).isEqualTo(usersExp);
    }

    @Test
    public void update() {
        //Hibernate кэширует и проваливает тест get, где SpunchBob != User, поэтому ID+1
        User user = service.get(ID+1);

        user.setLogin("SpunchBob");
        service.update(user);

        User userExp = service.get(ID+1);

        assertThat(user).isEqualTo(userExp);
    }

    @Test
    public void delete() {
        service.delete(ID);
        List<User> users = service.getAll();

        List<User> usersExp = UserUtil.getUserList();
        User uRemove = usersExp.stream().filter(user -> user.getId() == ID).findFirst().get();
        usersExp.remove(uRemove);

        assertThat(users).isEqualTo(usersExp);
    }








}