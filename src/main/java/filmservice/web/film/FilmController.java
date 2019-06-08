package filmservice.web.film;

import filmservice.model.Film;
import filmservice.model.User;
import filmservice.service.FilmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

import static filmservice.util.ValidationUtil.assureIdConsistent;
import static filmservice.util.ValidationUtil.checkNew;

@Controller
public class FilmController {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @Autowired
    private FilmService service;

    public Film get(int id){
        log.info("get film by id {}", id);
        return service.get(id);
    }

    public List<Film> getAll(){
        log.info("get all films");
        return service.getAll(1);
    }

    public Film create(Film film) {
        log.info("create {}", film);
        checkNew(film);
        return service.create(film);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(Film film, int id) {
        log.info("update {} with id={}", film, id);
        assureIdConsistent(film, id);
        service.update(film);
    }

}
