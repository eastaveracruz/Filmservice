package filmservice.web;

import filmservice.Profiles;
import filmservice.web.film.FilmController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

public class FilmServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(FilmServlet.class);
    private ConfigurableApplicationContext springContext;
    private FilmController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        springContext = new ClassPathXmlApplicationContext(new String[]{"spring/spring-app.xml", "spring/spring-db.xml"}, false);
        springContext.getEnvironment().setActiveProfiles(Profiles.ACTIVE_DB);
        springContext.refresh();
        controller = springContext.getBean(FilmController.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to films");
        req.setAttribute("filmsList", controller.getAll());
        resp.setLocale(new Locale("en"));
        req.getRequestDispatcher("/films.jsp").forward(req, resp);
    }
}
