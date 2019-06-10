package filmservice.web.user;

import filmservice.model.util.Role;
import filmservice.model.User;
import filmservice.util.assertion.UserCreationHelper;
import filmservice.web.AbstractControllerTest;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static filmservice.util.assertion.UserCreationHelper.ADMIN;
import static filmservice.util.assertion.UserCreationHelper.ADMIN_ID;
import static filmservice.web.json.JacksonObjectMapper.getMAPPER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AdminRestControllerTest extends AbstractControllerTest {

    @Test
    void testGet() throws Exception {
        RequestPostProcessor requestPostProcessor = SecurityMockMvcRequestPostProcessors.httpBasic(ADMIN.getLogin(), ADMIN.getPassword());
        mockMvc.perform(get(REST_URL + ADMIN_ID)
                .with(requestPostProcessor))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(userService.get(ADMIN_ID)));
    }


    @Test
    void testGetAll() throws Exception {
        RequestPostProcessor requestPostProcessor = SecurityMockMvcRequestPostProcessors.httpBasic(ADMIN.getLogin(), ADMIN.getPassword());
        mockMvc.perform(get(REST_URL)
                .with(requestPostProcessor))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(userService.getAll()));
    }

    @Disabled
    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + ADMIN_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        Map<Integer, User> userList = UserCreationHelper.getUserMap();
        userList.remove(ADMIN_ID);
        ArrayList expected = new ArrayList(userList.values());
        assertThat(userService.getAll()).isEqualTo(expected);
    }

    @Disabled
    @Test
    void testCreate() throws Exception {
        User expected = new User("User" + 100, "123", Role.ROLE_USER);
        User admin = UserCreationHelper.getUserList().get(6);
        RequestPostProcessor requestPostProcessor = SecurityMockMvcRequestPostProcessors.httpBasic(admin.getLogin(), admin.getPassword());
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(requestPostProcessor)
                .content(getMAPPER().writeValueAsString(expected)))
                .andExpect(status().isCreated());

        User returned = getMAPPER().readValue(action.andReturn().getResponse().getContentAsString(), User.class);
        expected.setId(returned.getId());

        List<User> userList = UserCreationHelper.getUserList();
        userList.add(expected);
        assertThat(userService.getAll()).isEqualTo(userList);
    }

    @Test
    void testUpdate() throws Exception {
        User updated = UserCreationHelper.getUserMap().get(ADMIN_ID);
        updated.setLogin("UpdatedName");
        mockMvc.perform(put(REST_URL + ADMIN_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getMAPPER().writeValueAsString(updated)))
                .andExpect(status().isNoContent());

        assertThat(updated).isEqualTo(userService.get(ADMIN_ID));
    }
}