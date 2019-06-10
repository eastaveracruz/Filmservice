package filmservice.service;

import filmservice.model.Film;
import filmservice.model.util.GetParameters;
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
    public List<Film> getAll(int page, GetParameters parameters) {
        return repository.getAll(page, parameters);
    }

    @Override
    public List<Film> getByTitle(String title, int page, GetParameters parameters) {
        return repository.getByTitle(title, page, parameters);
    }

    @Override
    public int recordsCount(GetParameters parameters) {
        return repository.recordsCount(parameters);
    }
}
