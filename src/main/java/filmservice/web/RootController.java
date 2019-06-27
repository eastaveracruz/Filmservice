package filmservice.web;

import filmservice.AuthorizedUser;
import filmservice.model.Film;
import filmservice.model.Rating;
import filmservice.model.User;
import filmservice.model.util.Genre;
import filmservice.model.util.GetParameters;
import filmservice.model.util.Sort;
import filmservice.service.FilmService;
import filmservice.service.SecurityService;
import filmservice.service.UserService;
import filmservice.util.DateTimeUtil;
import filmservice.util.FileUtil;
import filmservice.util.Pagination;
import filmservice.validator.UserValidator;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RootController {

    private static final Logger log = LoggerFactory.getLogger(RootController.class);

    @Autowired
    private FilmService filmService;

    @Autowired
    private UserService userService;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private SecurityService securityService;


    @GetMapping("/")
    public String films() {
        return "redirect:/1";
    }

    @GetMapping("/{page}")
    public String films(@PathVariable Integer page,
                        @ModelAttribute("getParameters") GetParameters parameters,
                        Model model) {
        List filmsList;
        int recordsCount;
        parameters.setId(SecurityService.safeGet() != null ? SecurityService.getId() : null);

        recordsCount = filmService.recordsCount(parameters);
        page = Pagination.pageValid(page, recordsCount);
        filmsList = filmService.getAll(page, parameters);

        String paginationBlock = Pagination.generatePaginationBlock(page, recordsCount, parameters.toString());

        if (SecurityService.safeGet() != null) {
            Map<Integer, Rating> userRating = SecurityService.get().getUserRating();
            model.addAttribute("userRatingMap", userRating);
        }

        model.addAttribute("paginationBlock", paginationBlock);
        model.addAttribute("filmsList", filmsList);
        model.addAttribute("userList", userService.getAll());
        model.addAttribute("user", SecurityService.safeGet() != null ? SecurityService.safeGet().getName() : null);
        model.addAttribute("genre", Genre.values());
        model.addAttribute("getParameters", parameters);
        return "films";
    }

    @GetMapping("/film")
    public String getFilm(@RequestParam int id, Model model) {
        Film film = filmService.get(id);
        if (SecurityService.safeGet() != null) {
            Map<Integer, Rating> userRating = SecurityService.get().getUserRating();
            model.addAttribute("userRatingMap", userRating);
        }
        model.addAttribute("film", film);
        return "filmPage";
    }

    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("title", "Add New Film");
        model.addAttribute("action", "./film/add");
        model.addAttribute("film", new Film());
        model.addAttribute("genre", Genre.values());
        return "add";
    }

    @PostMapping("/film/add")
    public String addFilm(@ModelAttribute("film") Film film, Model model) {

        if (film.getRawDate().isEmpty() || film.getTitle().isEmpty() || film.getFile().isEmpty() || film.getDescription().isEmpty()) {
            return "redirect:/add";
        }

        fileDowload(film);
        film.setRating(-1);

        LocalDate filmDate = LocalDate.parse(film.getRawDate());
        film.setDate(filmDate);
        Film newFilm = filmService.create(film);
        log.info("The database entry created successfully: {}", newFilm.toString());

        return "redirect:/";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        Film film = filmService.get(id);
        film.setRawDate(DateTimeUtil.toString(film.getDate()));
        model.addAttribute("title", "Edit " + film.getTitle());
        model.addAttribute("film", film);
        model.addAttribute("action", "./edit");
        model.addAttribute("genre", Genre.values());
        return "add";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("film") Film film, Model model) throws IOException {

        Film originalFilm = filmService.get(film.getId());
        originalFilm.setTitle(film.getTitle());
        originalFilm.setDescription(film.getDescription());
        originalFilm.setGenre(film.getGenre());

        if (!film.getFile().isEmpty()) {
            fileDowload(film);
            new File(servletContext.getRealPath(originalFilm.getImage())).delete();
            originalFilm.setImage(film.getImage());
        }

        if (!"".equals(film.getRawDate())) {
            LocalDate filmDate = LocalDate.parse(film.getRawDate());
            originalFilm.setDate(filmDate);
        }

        Film newFilm = filmService.create(originalFilm);
        log.info("The database entry created successfully: {}", newFilm.toString());
        return "redirect:/film?id=" + film.getId();
    }


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.create(userForm);
        securityService.autoLogin(userForm.getLogin(), userForm.getConfirmPassword());
        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect");
        }
        if (logout != null) {
            model.addAttribute("message", "Logged out successfully");
        }
        return "login";
    }

    public void fileDowload(Film film) {
        MultipartFile file = film.getFile();
        File uploadedFile = null;
        String pathTodirecotry = "/resources/films_img";
        try {
            File directory = new File(servletContext.getRealPath(pathTodirecotry));
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().length() - 3);
            uploadedFile = FileUtil.uploadFile(directory, file, String.format("%s_%s.%s", film.getTitle(), RandomStringUtils.randomAlphanumeric(10), ext));
            log.info("Файл успешно загружен: {}", uploadedFile.getAbsolutePath());
            film.setImage(String.format(".%s/%s", pathTodirecotry, uploadedFile.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
