package filmservice.web;

import filmservice.Settings;
import filmservice.model.Film;
import filmservice.model.util.GetParameters;
import filmservice.model.util.Sort;
import filmservice.util.assertion.FilmCreationHelper;
import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.List;
import java.util.stream.Collectors;

import static filmservice.util.assertion.UserCreationHelper.ADMIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class RootControllerTest extends AbstractControllerTest {

    @Test
    void getAllfilms() throws Exception {
        RequestPostProcessor requestPostProcessor = SecurityMockMvcRequestPostProcessors.httpBasic(ADMIN.getLogin(), ADMIN.getPassword());
        GetParameters getParameters = new GetParameters();
        getParameters.setSort(Sort.init(Sort.TITLE_ACS));
        mockMvc.perform(get("/1")
                .with(requestPostProcessor))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("films"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/films.jsp"))
                .andExpect(model().attribute("filmsList",
                        new AssertionMatcher<List<Film>>() {
                            @Override
                            public void assertion(List<Film> actual) throws AssertionError {
                                List<Film> films = filmService.getAll(1, getParameters);
                                List<Film> filmsExp = FilmCreationHelper.getFilmsList().stream().limit(Settings.PAGINATION_MAX_RESULTS).collect(Collectors.toList());
                                assertThat(films).usingElementComparatorIgnoringFields("rating", "file", "rawDate").isEqualTo(filmsExp);
                            }
                        }));
    }


}