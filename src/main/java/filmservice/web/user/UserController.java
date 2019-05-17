package filmservice.web.user;

import filmservice.model.User;
import filmservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    public List<User> getAll(){
        log.info("get all users");
        return service.getAll();
    }
}
