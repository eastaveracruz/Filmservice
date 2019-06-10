package filmservice.service;

import filmservice.model.Film;
import filmservice.model.util.GetParameters;
import filmservice.util.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FilmService {

    @Transactional
    Film create(Film film);

    void delete(int id) throws NotFoundException;

    Film get(int id) throws NotFoundException;

    @Transactional
    void update(Film film);

    List<Film> getAll(int page, GetParameters parameters);

    int recordsCount(GetParameters parameters);

}
