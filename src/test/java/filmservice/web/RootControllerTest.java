package filmservice.web;

import filmservice.model.Film;
import filmservice.util.assertion.FilmCreationHelper;
import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class RootControllerTest extends AbstractControllerTest {

    @Test
    void getAllfilms() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("films"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/films.jsp"))
                .andExpect(model().attribute("filmsList",
                        new AssertionMatcher<List<Film>>() {
                            @Override
                            public void assertion(List<Film> actual) throws AssertionError {
                                List<Film> films = filmService.getAll();
                                List<Film> filmsExp = FilmCreationHelper.getFilmsList();
                                assertThat(films).isEqualTo(filmsExp);
                            }
                        }));
    }


}