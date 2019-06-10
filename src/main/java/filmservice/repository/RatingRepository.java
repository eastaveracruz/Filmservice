package filmservice.repository;

import filmservice.model.Rating;
import org.springframework.transaction.annotation.Transactional;

public interface RatingRepository {

    @Transactional
    Rating save(Rating rating);

    @Transactional
    boolean delete(int id);

    double getAvgFilmRating(int filmId);
}
