package filmservice.repository;

import filmservice.model.Film;
import filmservice.model.Rating;
import filmservice.util.Pagination;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public class FilmRepositoryImpl implements FilmRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Film save(Film film) {
        if (film.isNew()) {
            em.persist(film);
            return film;
        } else {
            return em.merge(film);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(Film.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public Film get(int id) {
        return em.find(Film.class, id);
    }

    @Override
    public List<Film> getAll(int page) {
        TypedQuery<Film> namedQuery = em.createNamedQuery(Film.GET_ALL, Film.class);
        return Pagination.getPaginatedResult(namedQuery, page);
    }

    @Override
    public List<Film> getByTitle(String title, int page) {
        TypedQuery<Film> namedQuery = em.createNamedQuery(Film.GET_BY_TITLE, Film.class);
        namedQuery.setParameter("title", '%' + title + '%');
        return Pagination.getPaginatedResult(namedQuery, page);
    }

    @Override
    @Transactional
    public Rating save(Rating rating) {
        if (rating.isNew()) {
            em.persist(rating);
            return rating;
        } else {
            return em.merge(rating);
        }
    }

    @Override
    public int recordsCount() {
        BigInteger count = (BigInteger) em.createNativeQuery("select count (*) from films").getSingleResult();
        return count.intValue();
    }

    @Override
    public int recordsCount(String title) {
        BigInteger count = (BigInteger) em.createNativeQuery("select count (*) from films WHERE upper(title) LIKE upper(?)")
                .setParameter(1, '%' + title + '%')
                .getSingleResult();
        return count.intValue();
    }
}
