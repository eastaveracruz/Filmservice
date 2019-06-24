package filmservice.web;

import filmservice.model.Film;
import filmservice.model.Rating;
import filmservice.service.FilmService;
import filmservice.service.RatingService;
import filmservice.service.SecurityService;
import filmservice.web.user.AbstractUserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Map;

import static filmservice.util.AssessmentUtil.updateFilmRating;

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
        Double avgFilmRating = null;

        /*Удаление оценку*/
        if (rating == 0) {
            if (userRating.size() != 0){
                Integer ratingId = userRating.get(filmId).getId();
                ratingService.delete(ratingId);
                userRating.remove(filmId);
                avgFilmRating = updateFilmRating(filmService, ratingService, filmId);
            }
            return String.format(Locale.ENGLISH, "{ \"scs\": true, \"rating\": %.1f}", avgFilmRating);
        }

        /*Если оценка сущестует изменяем ее, иначе создаем новую*/
        if (userRating.containsKey(filmId)) {
            filmRating = userRating.get(filmId);
            filmRating.setRating(rating);
        } else {
            int userId = SecurityService.getId();
            filmRating = new Rating(userId, filmId, rating);
            userRating.put(filmId, filmRating);
        }

        /*Сохраняем оценку в базе*/
        Rating newRating = ratingService.save(filmRating);

        /*Обновляем среднюю оценку фильма*/
        avgFilmRating = updateFilmRating(filmService, ratingService, filmId);

        /*Отправляем клиенту ответ*/
        if (newRating != null) {
            return String.format(Locale.ENGLISH, "{ \"scs\": true, \"rating\": %.1f}", avgFilmRating);
        }

        return "{ \"scs\": false }";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "/delete")
    public String deleteFilm(@RequestParam int id) {
        filmService.delete(id);
        return "true";
    }

}
