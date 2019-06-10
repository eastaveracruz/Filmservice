package filmservice.repository;

import filmservice.model.Rating;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RatingRepositoryImpl implements RatingRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Rating save(Rating rating) {
        if (rating.isNew()) {
            em.persist(rating);
            return rating;
        } else {
            return em.merge(rating);
        }
    }

    @Override
    public boolean delete(int id) {
        return em.createQuery("DELETE FROM Rating r WHERE r.id=:id")
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public double getAvgFilmRating(int filmId) {
        Double avgRating = (Double) em.createQuery("SELECT AVG(r.rating) FROM Rating r WHERE r.filmId = :id GROUP BY r.filmId")
                .setParameter("id", filmId)
                .getSingleResult();

        return avgRating;
    }
}
