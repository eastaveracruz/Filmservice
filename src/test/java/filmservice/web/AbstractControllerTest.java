package filmservice.web;


import com.fasterxml.jackson.databind.ObjectReader;
import filmservice.Profiles;
import filmservice.model.User;
import filmservice.service.FilmService;
import filmservice.service.UserService;
import filmservice.web.user.AdminRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.PostConstruct;
import java.util.List;

import static filmservice.web.json.JacksonObjectMapper.getMAPPER;
import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitWebConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@Transactional
@ActiveProfiles(Profiles.ACTIVE_DB)
public class AbstractControllerTest {
    protected static final String REST_URL = AdminRestController.REST_URL + "/";
    protected static final int ADMIN_ID = 6;
    protected static final int USER_ID = 1;
    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    @Autowired
    protected UserService userService;

    @Autowired
    protected FilmService filmService;

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .build();
    }

    protected ResultMatcher contentJson(User expected) {
        return result -> assertThat(getMAPPER().readValue(result.getResponse().getContentAsString(), User.class)).isEqualTo(expected);
    }

    protected ResultMatcher contentJson(List<User> expected) {
        ObjectReader reader = getMAPPER().readerFor(User.class);
        return result -> assertThat(reader.<User>readValues(result.getResponse().getContentAsString()).readAll()).isEqualTo(expected);
    }
}
