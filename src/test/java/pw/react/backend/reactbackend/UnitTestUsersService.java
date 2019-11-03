package pw.react.backend.reactbackend;

        import com.fasterxml.jackson.core.type.TypeReference;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import org.junit.After;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.http.MediaType;
        import org.springframework.test.context.ActiveProfiles;
        import org.springframework.test.context.junit4.SpringRunner;
        import org.springframework.test.web.servlet.MockMvc;
        import org.springframework.test.web.servlet.MvcResult;
        import pw.react.backend.reactbackend.Models.User;

        import java.time.LocalDate;
        import java.util.Arrays;
        import java.util.List;

        import static org.hamcrest.core.StringContains.containsString;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("it")
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UnitTestUsersService {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static List<User> users = Arrays.asList(
            new User().setAllData("testowyjan", "Jan", "Testowy", LocalDate.of(1998,9,23),true),
            new User().setAllData("przykladowaanna", "Anna", "Przykladowa",LocalDate.of(1976, 3, 27), true),
            new User().setAllData("testowykarol", "Karol", "Test", LocalDate.of(1945,5,29), false),
            new User().setAllData("teeest1", "Test", "Testowy1", LocalDate.of(1934,4,15),false),
            new User().setAllData("haloktoto", "Halo", "Ktoto",LocalDate.of(1967, 3, 25), true),
            new User().setAllData("tabaluga1","Taba","Luga1",LocalDate.of(1985,6,18),true)
    );

    @After
    public void clean() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/Users")).andReturn();
        String content = result.getResponse().getContentAsString();
        List<User> users = objectMapper.readValue(content, new TypeReference<List<User>>() {
        });

        for (int i = 0; i < users.size(); ++i) {
            this.mockMvc.perform(delete("/Users/" + users.get(i).getId()));
        }
    }

    @Test
    public void test_RequestGet_ReturnError() throws Exception {
        this.mockMvc.perform(get("/users/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void test_RequestPost_ReturnCorrectValue() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/Users/").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(users.get(3)))).andExpect(status().is2xxSuccessful()).andReturn();
        String content = result.getResponse().getContentAsString();
        User user = objectMapper.readValue(content, User.class);

        this.mockMvc.perform(get("/users/")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(users.get(3).getLastName())));

        this.mockMvc.perform(get("/users/" + user.getId())).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(users.get(3).getLastName())));

        this.mockMvc.perform(get("/users?login=" + users.get(3).getLogin())).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(users.get(3).getLastName())));
    }

    @Test
    public void test_RequestForUser_ReturnUser() throws Exception {
        this.mockMvc.perform(post("/Users/").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(users.get(0)))).andExpect(status().is2xxSuccessful());

        this.mockMvc.perform(get("/Users/?login=" + users.get(0).getLogin())).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(users.get(0).getLastName())));
    }

    @Test
    public void test_RequestUpdate_ReturnError() throws Exception {
        this.mockMvc.perform(put("/Users/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(users.get(2)))).andExpect(status().isNotFound());
    }

    @Test
    public void test_RequestDelete_ReturnErrors() throws Exception {
        // when then
        this.mockMvc.perform(delete("/Users/1")).andExpect(status().isNotFound());
    }

}