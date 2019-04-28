package filmservice.service;

import filmservice.model.Film;
import filmservice.util.exception.NotFoundException;

import java.util.List;

public interface FilmService {

    Film create(Film film);

    void delete(int id) throws NotFoundException;

    Film get(int id) throws NotFoundException;

    void update(Film film);

    List<Film> getAll();

}
