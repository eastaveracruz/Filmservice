package filmservice.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResourceControllerTest extends AbstractControllerTest {

    @Test
    void testResorces() throws Exception {
        mockMvc.perform(get("/resources/css/films.css"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.valueOf("text/css")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/resources/js/film.js"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.valueOf("application/javascript")))
                .andExpect(status().isOk());

    }

}
