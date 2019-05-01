package filmservice.repository;

import filmservice.model.Film;

import java.util.List;

public interface FilmRepository {

    Film save(Film film);

    boolean delete(int id);

    Film get(int id);

    List<Film> getAll();

}
