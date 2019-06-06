package filmservice.web;

import filmservice.AuthorizedUser;
import filmservice.model.Film;
import filmservice.model.RatedFilm;
import filmservice.model.Rating;
import filmservice.model.User;
import filmservice.service.FilmService;
import filmservice.service.SecurityService;
import filmservice.service.UserService;
import filmservice.util.FileUtil;
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
import java.util.List;
import java.util.Locale;
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
    public String films(@RequestParam(required = false) String title, Model model) {
        List<RatedFilm> filmsList = null;
        if (title == null || "".equals(title)) {
            filmsList = filmService.getAllRatedFilm();
        } else {
            filmsList = filmService.getRatedFilmByTitle(title);
        }
        if (SecurityService.safeGet() != null){
            Map<Integer, Rating> userRating = SecurityService.get().getUserRating();
            model.addAttribute("userRatingMap", userRating);
        }
        model.addAttribute("filmsList", filmsList);
        model.addAttribute("userList", userService.getAll());
        return "films";
    }

    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("film", new Film());
        return "add";
    }

    @PostMapping("/film/add")
    public String addFilm(@RequestParam("file") MultipartFile file,
                          @RequestParam("title") String title,
                          @RequestParam("description") String description,
                          @RequestParam("genre") String genre) {

//        File download
        File uploadedFile = null;
        String pathTodirecotry = "/resources/films_img";
        try {
            File directory = new File(servletContext.getRealPath(pathTodirecotry));
//          https://stackoverflow.com/questions/825678/what-is-the-best-way-to-generate-a-unique-and-short-file-name-in-java
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().length() - 3);
            uploadedFile = FileUtil.uploadFile(directory, file, String.format("%s_%s.%s", title, RandomStringUtils.randomAlphanumeric(10), ext));
            log.info("Файл успешно загружен: {}", uploadedFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Create new Film
        Film newFilm = filmService.create(new Film(title, String.format(".%s/%s", pathTodirecotry, uploadedFile.getName()), description, genre));
        log.info("The database entry created successfully: {}", newFilm.toString());

        return "redirect:/add";
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

}
