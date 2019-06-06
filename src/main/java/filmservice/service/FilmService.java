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

    List<Film> getAll();

    List<RatedFilm> getAllRatedFilm();

    List<Film> getByTitle(String title);

    List<RatedFilm> getRatedFilmByTitle(String title);

    Rating save(Rating rating);
}
