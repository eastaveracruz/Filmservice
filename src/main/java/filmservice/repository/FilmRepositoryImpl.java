package filmservice.repository;

import filmservice.Profiles;
import filmservice.model.Film;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public List<Film> getAll() {
        return em.createNamedQuery(Film.GET_ALL, Film.class).getResultList();
    }

    @Override
    public List<Film> getByTitle(String title) {
        return em.createNamedQuery(Film.GET_BY_TITLE, Film.class).setParameter("title", title).getResultList();
    }

}
