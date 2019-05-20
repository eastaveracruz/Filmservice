package filmservice.web;

import filmservice.service.FilmService;
import filmservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RootController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String films(@RequestParam(required = false) String title, Model model) {
        if(title == null || "".equals(title)){
            model.addAttribute("filmsList", filmService.getAll());
        }else {
            model.addAttribute("filmsList", filmService.getByTitle(title));
        }
        model.addAttribute("userList", userService.getAll());
        return "films";
    }
}
