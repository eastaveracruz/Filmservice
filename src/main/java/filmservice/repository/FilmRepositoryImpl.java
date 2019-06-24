package filmservice.repository;

import filmservice.model.Film;
import filmservice.model.util.Genre;
import filmservice.model.util.GetParameters;
import filmservice.model.util.Sort;
import filmservice.util.Pagination;
import filmservice.util.QuerryHelper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public class FilmRepositoryImpl implements FilmRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Film save(Film film) {
        if (film.isNew()) {
            em.persist(film);
            return film;
        } else {
            return em.merge(film);
        }
    }

    @Override
    public boolean delete(int id) {
        return em.createNamedQuery(Film.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public Film get(int id) {
        return em.find(Film.class, id);
    }

    @Override
    @Transactional
    public List<Film> getAll(int page, GetParameters parameters) {
        QuerryHelper<Film> querryHelper = new QuerryHelper<>(em, parameters, Film.class);
        TypedQuery<Film> typedQuery = querryHelper.createQuerry();
        return Pagination.getPaginatedResult(typedQuery, page);
    }

    @Override
    @Transactional
    public int recordsCount(GetParameters parameters) {
        QuerryHelper<Film> querryHelper = new QuerryHelper<>(em, parameters, Film.class);
        querryHelper.setCounter(true);
        TypedQuery typedQuery = querryHelper.createQuerry();
        Long result = (Long) typedQuery.getSingleResult();
        return result.intValue();
    }
}