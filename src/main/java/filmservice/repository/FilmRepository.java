package filmservice.repository;

import filmservice.model.Film;

import java.util.Collection;

public interface FilmRepository {

    Film save(Film film);

    boolean delete(int id);

    Film get(int id);

    Collection<Film> getAll();

}
