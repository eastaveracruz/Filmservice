package filmservice.service;

import filmservice.Profiles;
import filmservice.model.Film;
import filmservice.repository.FilmRepository;
import filmservice.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

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
    public void update(Film film) {
        Assert.notNull(film, "film must be not null");
        checkNotFoundWithId(repository.save(film), film.getId());
    }

    @Override
    public List<Film> getAll() {
        return repository.getAll();
    }

    @Override
    public List<Film> getByTitle(String title) {
        return repository.getByTitle(title);
    }
}
