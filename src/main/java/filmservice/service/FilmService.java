package filmservice.service;

import filmservice.model.Film;
import filmservice.model.RatedFilm;
import filmservice.model.Rating;
import filmservice.util.exception.NotFoundException;

import java.util.List;

public interface FilmService {

    Film create(Film film);

    void delete(int id) throws NotFoundException;

    Film get(int id) throws NotFoundException;

    RatedFilm getRatedFilm(int id);

    void update(Film film);

    List<Film> getAll(int page);

    List<RatedFilm> getAllRatedFilm(int page);

    List<Film> getByTitle(String title, int page);

    List<RatedFilm> getRatedFilmByTitle(String title, int page);

    Rating save(Rating rating);

    int recordsCount();

    int recordsCount(String title);
}
