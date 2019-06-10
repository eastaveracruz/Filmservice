package filmservice.service;

import filmservice.model.Rating;

public interface RatingService {

    Rating save(Rating rating);

    void delete(int id);

    double getAvgFilmRating(int filmId);
}
