package filmservice.repository;

import filmservice.model.Film;
import filmservice.model.Rating;
import filmservice.model.util.Sort;
import filmservice.util.Pagination;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
    public List<Film> getAll(int page, Sort sort) {
//        CriteriaQuery<Film> querry = sortSetting(sort);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Film> querry = cb.createQuery(Film.class);
        Root<Film> root = querry.from(Film.class);
        root.join("rating");
        querry.select(root);


        TypedQuery<Film> typedQuery = em.createQuery(querry);
        return Pagination.getPaginatedResult(typedQuery, page);
    }

    @Override
    public List<Film> getByTitle(String title, int page, Sort sort) {
        CriteriaQuery<Film> querry = sortSetting(sort, title);

        TypedQuery<Film> typedQuery = em.createQuery(querry);

        return Pagination.getPaginatedResult(typedQuery, page);
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

    private CriteriaQuery<Film> sortSetting(Sort sort, String... arg) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Film> querry = cb.createQuery(Film.class);
        Root<Film> root = querry.from(Film.class);
        querry.select(root);

        switch (sort.getDirections().toLowerCase()) {
            case "asc":
                querry.orderBy(cb.asc(root.get(sort.getString())));
                break;
            case "desc":
                querry.orderBy(cb.desc(root.get(sort.getString())));
                break;
        }

        if (arg.length != 0) {
            querry.where(cb.like(root.get("title"), "%" + arg[0] + "%"));
        }

        return querry;
    }
}
