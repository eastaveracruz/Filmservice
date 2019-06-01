package filmservice.web;

import filmservice.model.Film;
import filmservice.service.FilmService;
import filmservice.service.UserService;
import filmservice.util.FileUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
public class RootController {

    private static final Logger log = LoggerFactory.getLogger(RootController.class);

    @Autowired
    private FilmService filmService;

    @Autowired
    private UserService userService;

    @Autowired
    private ServletContext servletContext;

    @GetMapping("/")
    public String films(@RequestParam(required = false) String title, Model model) {
        if (title == null || "".equals(title)) {
            model.addAttribute("filmsList", filmService.getAll());
        } else {
            model.addAttribute("filmsList", filmService.getByTitle(title));
        }
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

}
