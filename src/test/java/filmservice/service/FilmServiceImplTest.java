package filmservice.service;

import filmservice.Profiles;
import filmservice.Settings;
import filmservice.model.Film;
import filmservice.model.util.Genre;
import filmservice.model.util.GetParameters;
import filmservice.model.util.Sort;
import filmservice.util.assertion.FilmCreationHelper;
import filmservice.util.exception.NotFoundException;
import filmservice.web.TimingExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.slf4j.LoggerFactory.getLogger;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(Profiles.ACTIVE_DB)
@ExtendWith(TimingExtension.class)
public class FilmServiceImplTest {

    private static final Logger log = getLogger("result");

    private static final int ID = 1;
    private static final Film FILM = new Film("title", "some image", "some description", Genre.ACTION.name(), LocalDate.parse("2020-05-06"));
    private GetParameters getParameters = new GetParameters();

    {
        getParameters.setId(7);
        getParameters.setSort(Sort.init());
    }

    @Autowired
    private FilmService service;

    @Test
    public void getAll() {
        List<Film> films = service.getAll(1, getParameters);
        List<Film> filmsExp = FilmCreationHelper.getFilmsList().stream().limit(Settings.PAGINATION_MAX_RESULTS).collect(Collectors.toList());
        assertThat(films).usingElementComparatorIgnoringFields("rating", "file", "rawDate").isEqualTo(filmsExp);
    }

    @Test
    void getByTitle() {
        String title = "Film 01";
        getParameters.setTitle(title);
        List<Film> films = service.getAll(1, getParameters);
        List<Film> filmsExp = FilmCreationHelper.getFilmsList().stream().filter(film -> film.getTitle().contains(title)).collect(Collectors.toList());
        assertThat(films).usingElementComparatorIgnoringFields("rating", "file", "rawDate").isEqualTo(filmsExp);
    }

    @Test
    void get() {
        Film film = service.get(ID);
        Film filmExp = FilmCreationHelper.getFilmsList().stream().filter(film1 -> film.getId() == ID).findFirst().get();
        assertThat(film).isEqualToIgnoringGivenFields(filmExp, "rating", "file", "rawDate");
    }

    @Test
    void create() {
        Film filmExp = service.create(FILM);
        Film film = service.get(filmExp.getId());
        assertThat(film).isEqualToIgnoringGivenFields(filmExp, "rating", "file", "rawDate");
    }

    @Test
    void delete() {
        List<Film> filmsS = service.getAll(1, getParameters);
        service.delete(ID);
        List<Film> filmsE = service.getAll(1, getParameters);
        assertThat(filmsS).usingElementComparatorIgnoringFields("rating", "file", "rawDate").isNotEqualTo(filmsE);
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
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(1000));
    }

    @Test
    void sortByTitleDesc(){
        getParameters.setSort(Sort.init(Sort.TITLE_DESC));
        List<Film> films = service.getAll(1, getParameters);
        List<Film> filmsExp = FilmCreationHelper.getFilmsList().stream().sorted(Comparator.comparing(Film::getTitle).reversed()).limit(Settings.PAGINATION_MAX_RESULTS).collect(Collectors.toList());
        assertThat(films).usingElementComparatorIgnoringFields("rating", "file", "rawDate").isEqualTo(filmsExp);
    }

}