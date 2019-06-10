package filmservice.repository;

import filmservice.model.Film;
import filmservice.model.util.GetParameters;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FilmRepository {

    @Transactional
    Film save(Film film);

    @Transactional
    boolean delete(int id);

    Film get(int id);

    List<Film> getAll(int page, GetParameters parameters);

    List getByTitle(String title, int page, GetParameters parameters);

    int recordsCount(GetParameters parameters);

}
