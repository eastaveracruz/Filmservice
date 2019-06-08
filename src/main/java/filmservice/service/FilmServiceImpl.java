package filmservice.service;

import filmservice.model.Film;
import filmservice.model.RatedFilm;
import filmservice.model.Rating;
import filmservice.repository.FilmRepository;
import filmservice.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

import static filmservice.util.ValidationUtil.checkNotFoundWithId;

@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepository repository;

    @Autowired
    public FilmServiceImpl(FilmRepository repository) {
        this.repository = repository;
    }

    @Override
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
    public List<Film> getAll(int page) {
        return repository.getAll(page);
    }

    @Override
    public List<RatedFilm> getAllRatedFilm(int page) {
        return getAll(page).stream().map(RatedFilm::new).collect(Collectors.toList());
    }

    @Override
    public List<Film> getByTitle(String title, int page) {
        return repository.getByTitle(title, page);
    }

    @Override
    public List<RatedFilm> getRatedFilmByTitle(String title, int page) {
        return getByTitle(title, page).stream().map(RatedFilm::new).collect(Collectors.toList());
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
