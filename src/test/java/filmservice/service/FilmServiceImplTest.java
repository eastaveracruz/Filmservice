package filmservice.service;

import filmservice.Profiles;
import filmservice.model.Film;
import filmservice.util.assertion.FilmCreationHelper;
import filmservice.util.exception.NotFoundException;
import filmservice.web.TimingExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.slf4j.LoggerFactory.getLogger;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ExtendWith(SpringExtension.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(Profiles.ACTIVE_DB)
@ExtendWith(TimingExtension.class)
public class FilmServiceImplTest {

    private static final Logger log = getLogger("result");
    private static StringBuilder results = new StringBuilder();

    private static final int ID = 1;
    private static final Film FILM = new Film("testFilm", "no image", "some text", "test genre");

    @Autowired
    private FilmService service;

    @Test
    public void getAll() {
        List<Film> films = service.getAll();
        List<Film> filmsExp = FilmCreationHelper.getFilmsList();
        assertThat(films).isEqualTo(filmsExp);
    }

    @Test
    void getByTitle() {
        String title = "Film 01";
        List<Film> films = service.getByTitle(title);
        List<Film> filmsExp = FilmCreationHelper.getFilmsList().stream().filter(film -> film.getTitle().contains(title)).collect(Collectors.toList());
        assertThat(films).isEqualTo(filmsExp);
    }

    @Test
    void get() {
        Film film = service.get(ID);
        Film filmExp = FilmCreationHelper.getFilmsList().stream().filter(film1 -> film.getId() == ID).findFirst().get();
        assertThat(film).isEqualTo(filmExp);
    }

    @Test
    void create() {
        service.create(FILM);
        List<Film> films = service.getAll();

        List<Film> filmsExp = FilmCreationHelper.getFilmsList();
        filmsExp.add(FILM);

        //TODO FILM без id, тест проходит???
        assertThat(films).isEqualTo(filmsExp);
    }

    @Test
    void delete() {
        service.delete(ID);
        List<Film> films = service.getAll();

        List<Film> filmsExp = FilmCreationHelper.getFilmsList();
        Film filmsRemove = filmsExp.stream().filter(film -> film.getId() == ID).findFirst().get();
        filmsExp.remove(filmsRemove);

        assertThat(films).isEqualTo(filmsExp);
    }

    @Test
    void update() {
        Film film = service.get(ID);

        film.setTitle("This is new Test Title");
        service.update(film);

        Film filmExp = service.get(ID);

        assertThat(film).isEqualTo(filmExp);
    }

    @Test
    void getNotFound(){
        assertThrows(NotFoundException.class, () -> service.get(1000));
    }

}