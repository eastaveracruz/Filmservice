package filmservice.web.film;

import filmservice.model.Film;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = FilmRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class FilmRestController extends FilmController{

    public static final String REST_URL = "/rest/profile/films";

    @GetMapping
    public List<Film> getAll() {
        return super.getAll();
    }

    @GetMapping("/{id}")
    public Film get(@PathVariable int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Film> createWithLocation(@RequestBody Film film) {
        Film created = super.create(film);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Film film, @PathVariable int id) {
        super.update(film, id);
    }

}
