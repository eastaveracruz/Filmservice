package filmservice.web;

import filmservice.model.Film;
import filmservice.model.RatedFilm;
import filmservice.model.Rating;
import filmservice.model.User;
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
                        @RequestParam(required = false) String title,
                        @RequestParam(required = false) String sort,
                        Model model) {

        String paginationBlock = "";
        List filmsList = null;
        int recordsCount;
        Sort sortObj = Sort.init(sort);

        Map<String, String> parameters = new HashMap();
        parameters.put("sort", sortObj.getOriginalString());

        if (title == null || "".equals(title)) {

            recordsCount = filmService.recordsCount();
            page = Pagination.pageValid(page, recordsCount);
            filmsList = filmService.getAllRatedFilm(page, sortObj);
        } else {
            parameters.put("title", title);
            recordsCount = filmService.recordsCount(title);
            page = Pagination.pageValid(page, recordsCount);
            filmsList = filmService.getRatedFilmByTitle(title, page, sortObj);
        }

        paginationBlock = Pagination.generatePaginationBlock(page, recordsCount, parameters);

        if (SecurityService.safeGet() != null) {
            Map<Integer, Rating> userRating = SecurityService.get().getUserRating();
            model.addAttribute("userRatingMap", userRating);
        }
        model.addAttribute("paginationBlock", paginationBlock);
        model.addAttribute("filmsList", filmsList);
        model.addAttribute("userList", userService.getAll());
        return "films";
    }

    @GetMapping("/film")
    public String getFilm(@RequestParam int id, Model model) {
        RatedFilm ratedFilm = filmService.getRatedFilm(id);
        if (SecurityService.safeGet() != null) {
            Map<Integer, Rating> userRating = SecurityService.get().getUserRating();
            model.addAttribute("userRatingMap", userRating);
        }
        model.addAttribute("film", ratedFilm);
        return "filmPage";
    }

    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("title", "Add New Film");
        model.addAttribute("action", "./film/add");
        model.addAttribute("film", new Film());
        return "add";
    }

    @PostMapping("/film/add")
    public String addFilm(@ModelAttribute("film") Film film, Model model) {
        if (film.getId() == null) {
            if (film.getRawDate().isEmpty() && film.getTitle().isEmpty() && film.getFile().isEmpty() && film.getDescription().isEmpty()) {
                model.addAttribute("error", "заполните все поля");
                model.addAttribute("title", "Add New Film");
                model.addAttribute("action", "./film/add");
                return "add";
            }
        } else {
            edit(film, model);
        }

        fileDowload(film);

        LocalDate filmDate = LocalDate.parse(film.getRawDate());
        film.setDate(filmDate);
        Film newFilm = filmService.create(film);
        log.info("The database entry created successfully: {}", newFilm.toString());

        model.addAttribute("error", "фильм добавлен");
        return "redirect:/add";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        Film film = filmService.get(id);
        film.setRawDate(DateTimeUtil.toString(film.getDate()));
        model.addAttribute("title", "Edit " + film.getTitle());
        model.addAttribute("film", film);
        model.addAttribute("action", "./edit");
        return "add";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("film") Film film, Model model) {
        Film exFilm = filmService.get(film.getId());
        exFilm.setTitle(film.getTitle());
        exFilm.setDescription(film.getDescription());

        if (!film.getFile().isEmpty()) {
            fileDowload(film);
            exFilm.setImage(film.getImage());
        }

        LocalDate filmDate = LocalDate.parse(film.getRawDate());
        exFilm.setDate(filmDate);

        Film newFilm = filmService.create(exFilm);
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
