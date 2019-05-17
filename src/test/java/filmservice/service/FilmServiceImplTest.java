package filmservice.service;

import filmservice.Profiles;
import filmservice.model.Film;
import filmservice.model.Role;
import filmservice.model.User;
import filmservice.util.exception.NotFoundException;
import filmservice.util.mock.FilmsUtil;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.slf4j.LoggerFactory.getLogger;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(Profiles.ACTIVE_DB)
public class FilmServiceImplTest {

    private static final Logger log = getLogger("result");
    private static StringBuilder results = new StringBuilder();

    public static final int ID = 1;
    public static final Film FILM = new Film("testFilm", "no image", "some text", "test genre");

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


    @Autowired
    private FilmService service;

    @Test
    public void getAll() {
        List<Film> films = service.getAll();
        List<Film> filmsExp = FilmsUtil.getFilmsList();
        assertThat(films).isEqualTo(filmsExp);
    }

    @Test
    public void getByTitle() {
        String title = "Film 01";
        List<Film> films = service.getByTitle(title);
        List<Film> filmsExp = FilmsUtil.getFilmsList().stream().filter(film -> film.getTitle().contains(title)).collect(Collectors.toList());
        assertThat(films).isEqualTo(filmsExp);
    }

    @Test
    public void get() {
        Film film = service.get(ID);
        Film filmExp = FilmsUtil.getFilmsList().stream().filter(film1 -> film.getId() == ID).findFirst().get();
        assertThat(film).isEqualTo(filmExp);
    }

    @Test
    public void create() {
        service.create(FILM);
        List<Film> films = service.getAll();

        List<Film> filmsExp = FilmsUtil.getFilmsList();
        filmsExp.add(FILM);

        //TODO FILM без id, тест проходит???
        assertThat(films).isEqualTo(filmsExp);
    }

    @Test
    public void delete() {
        service.delete(ID);
        List<Film> films = service.getAll();

        List<Film> filmsExp = FilmsUtil.getFilmsList();
        Film filmsRemove = filmsExp.stream().filter(film -> film.getId() == ID).findFirst().get();
        filmsExp.remove(filmsRemove);

        assertThat(films).isEqualTo(filmsExp);
    }

    @Test
    public void update() {
        Film film = service.get(ID);

        film.setTitle("This is new Test Title");
        service.update(film);

        Film filmExp = service.get(ID);

        assertThat(film).isEqualTo(filmExp);
    }

    @Test
    public void getNotFound(){
        thrown.expect(NotFoundException.class);
        service.get(1000);
    }

}