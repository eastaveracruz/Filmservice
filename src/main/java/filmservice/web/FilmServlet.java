package filmservice.web;

import filmservice.web.film.FilmController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilmServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(FilmServlet.class);
    private ConfigurableApplicationContext springContext;
    private FilmController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        springContext = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");
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
        req.getRequestDispatcher("/films.jsp").forward(req, resp);
    }
}
