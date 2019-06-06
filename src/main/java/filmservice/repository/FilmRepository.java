package filmservice.repository;

import filmservice.model.Film;
import filmservice.model.Rating;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FilmRepository {

    Film save(Film film);

    boolean delete(int id);

    Film get(int id);

    List<Film> getAll();

    List<Film> getByTitle(String title);

    Rating save(Rating rating);
}
