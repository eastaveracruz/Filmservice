package filmservice.service;

import filmservice.model.Film;
import filmservice.model.Role;
import filmservice.model.User;
import filmservice.util.exception.NotFoundException;
import filmservice.util.mock.FilmsUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class FilmServiceImplTest {

    public static final int ID = 1;
    public static final Film FILM = new Film("testFilm", "no image", "some text", "test genre");

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

    @Test(expected = NotFoundException.class)
    public void getNotFound(){
        service.get(1000);
    }

}