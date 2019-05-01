package filmservice.repository;

import filmservice.model.Film;
import filmservice.util.mock.FilmsUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FilmRepositoryImpl implements FilmRepository{

    @Override
    public Film save(Film film) {
        FilmsUtil.getFilmsList().add(film);
        return film;
    }

    @Override
    public boolean delete(int id) {
        return FilmsUtil.getFilmMap().remove(id) != null;
    }

    @Override
    public Film get(int id) {
        return FilmsUtil.getFilmMap().get(id);
    }

    @Override
    public List<Film> getAll() {
        return FilmsUtil.getFilmsList();
    }
}
