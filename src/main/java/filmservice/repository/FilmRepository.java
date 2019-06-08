package filmservice.repository;

import filmservice.model.Film;
import filmservice.model.Rating;
import filmservice.model.util.Sort;

import java.util.List;

public interface FilmRepository {

    Film save(Film film);

    boolean delete(int id);

    Film get(int id);

    List<Film> getAll(int page, Sort sort);

    List getByTitle(String title, int page, Sort sort);

    Rating save(Rating rating);

    int recordsCount();

    int recordsCount(String title);
}
