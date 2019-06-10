package filmservice.web.user;

import filmservice.model.User;
import filmservice.util.assertion.UserCreationHelper;
import filmservice.web.AbstractControllerTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Map;

import static filmservice.util.assertion.UserCreationHelper.USER_ID;
import static filmservice.web.json.JacksonObjectMapper.getMAPPER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestControllerTest extends AbstractControllerTest {

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + USER_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(userService.get(USER_ID)));
    }

    @Disabled
    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + USER_ID))
                .andExpect(status().isNoContent());

        Map<Integer, User> userList = UserCreationHelper.getUserMap();
        userList.remove(USER_ID);
        ArrayList expected = new ArrayList(userList.values());
        assertThat(userService.getAll()).isEqualTo(expected);
    }

    @Test
    void testUpdate() throws Exception {
        User updated = UserCreationHelper.getUserMap().get(USER_ID);
        updated.setLogin("UpdatedName");
        mockMvc.perform(put(REST_URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getMAPPER().writeValueAsString(updated)))
                .andExpect(status().isNoContent());

        assertThat(updated).isEqualTo(userService.get(USER_ID));
    }
}