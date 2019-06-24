package filmservice.util;

import filmservice.model.Film;
import filmservice.service.FilmService;
import filmservice.service.RatingService;

import javax.persistence.NoResultException;

public class AssessmentUtil {

    public static Double updateFilmRating(FilmService filmService, RatingService ratingService, int filmId){
        Film film = filmService.get(filmId);
        Double avgFilmRating = null;
        try {
            avgFilmRating = ratingService.getAvgFilmRating(filmId);
        }catch (NoResultException e){
            avgFilmRating = -1d;
        }
        film.setRating(avgFilmRating);
        filmService.update(film);
        return avgFilmRating;
    }
}
