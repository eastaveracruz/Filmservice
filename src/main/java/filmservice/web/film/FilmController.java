package filmservice.web.film;

import filmservice.Profiles;
import filmservice.model.Film;
import filmservice.service.FilmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;

@Controller
public class FilmController {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final FilmService service;

    @Autowired
    public FilmController(FilmService service) {
        this.service = service;
    }

    public Film get(int id){
        log.info("get film by id {}", id);
        return service.get(id);
    }

    public List<Film> getAll(){
        log.info("get all films");
        return service.getAll();
    }

}
