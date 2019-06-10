package filmservice.web;

import filmservice.model.Film;
import filmservice.model.Rating;
import filmservice.service.FilmService;
import filmservice.service.RatingService;
import filmservice.service.SecurityService;
import filmservice.web.user.AbstractUserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/ajax")
public class AjaxController extends AbstractUserController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private RatingService ratingService;

    @PostMapping(path = "/save/rating")
    public String saveRating(@RequestParam int filmId, @RequestParam int rating) {

        /*Достаем у юзера его оценки*/
        Map<Integer, Rating> userRating = SecurityService.get().getUserRating();
        Rating filmRating = null;

        /*Если оценка сущестует изменяем ее, иначе создаем новую*/
        if (userRating.containsKey(filmId)) {
            filmRating = userRating.get(filmId);
            filmRating.setRating(rating);
        } else {
            int userId = SecurityService.getId();
            filmRating = new Rating(userId, filmId, rating);
        }

        /*Сохраняем оценку в базе*/
        Rating newRating = ratingService.save(filmRating);

        /*Обновляем среднюю оценку фильма*/
        Film film = filmService.get(filmId);
        double avgFilmRating = ratingService.getAvgFilmRating(filmId);
        film.setRating(avgFilmRating);
        filmService.update(film);

        /*Отправляем клиенту ответ*/
        if (newRating != null) {
            userRating.put(filmId, newRating);
            return String.format(Locale.ENGLISH, "{ \"scs\": true, \"rating\": %.1f}", avgFilmRating);
        }

        return "{ \"scs\": false }";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "/delete")
    public String deleteFilm(@RequestParam int id){
        filmService.delete(id);
        return "true";
    }

}
