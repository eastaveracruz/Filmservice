package filmservice.service;

import filmservice.model.Film;
import filmservice.model.RatedFilm;
import filmservice.model.Rating;
import filmservice.model.util.Sort;
import filmservice.repository.FilmRepository;
import filmservice.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

import static filmservice.util.ValidationUtil.checkNotFoundWithId;

@Service
public class FilmServiceImpl implements FilmService {

    @Autowired
    private FilmRepository repository;



    @Override
    @Transactional
    public Film create(Film film) {
        Assert.notNull(film, "film must be not null");
        return checkNotFoundWithId(repository.save(film), film.getId());
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Film get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public RatedFilm getRatedFilm(int id) {
        return new RatedFilm(get(id));
    }

    @Override
    public void update(Film film) {
        Assert.notNull(film, "film must be not null");
        checkNotFoundWithId(repository.save(film), film.getId());
    }

    @Override
    public List<Film> getAll(int page, Sort sort) {
        return repository.getAll(page, sort);
    }

    @Override
    public List<RatedFilm> getAllRatedFilm(int page, Sort sort) {
        return getAll(page, sort).stream().map(RatedFilm::new).collect(Collectors.toList());
    }

    @Override
    public List<Film> getByTitle(String title, int page, Sort sort) {
        return repository.getByTitle(title, page, sort);
    }

    @Override
    public List<RatedFilm> getRatedFilmByTitle(String title, int page, Sort sort) {
        return getByTitle(title, page, sort).stream().map(RatedFilm::new).collect(Collectors.toList());
    }

    @Override
    public Rating save(Rating rating) {
        Assert.notNull(rating, "rating must be not null");
        return repository.save(rating);
    }

    @Override
    public int recordsCount() {
        return repository.recordsCount();
    }

    @Override
    public int recordsCount(String title) {
        return repository.recordsCount(title);
    }

}
