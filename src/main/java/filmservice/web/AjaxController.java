package filmservice.web;

import filmservice.model.Rating;
import filmservice.service.FilmService;
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

    @PostMapping(path = "/save/rating")
    public String saveRating(@RequestParam int filmId, @RequestParam int rating) {

        Map<Integer, Rating> userRating = SecurityService.get().getUserRating();
        Rating filmRating = null;
        if (userRating.containsKey(filmId)) {
            filmRating = userRating.get(filmId);
            filmRating.setRating(rating);
        } else {
            int userId = SecurityService.getId();
            filmRating = new Rating(userId, filmId, rating);
        }
        Rating newRating = filmService.save(filmRating);
        if (newRating != null) {
            userRating.put(filmId, newRating);
            double avgRating = filmService.getRatedFilm(filmId).getAvgRating();
            return String.format(Locale.ENGLISH, "{ \"scs\": true, \"rating\": %.1f}", avgRating);
        }
        return "{scs: false}";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "/delete")
    public String deleteFilm(@RequestParam int id){
        filmService.delete(id);
        return "true";
    }

}
