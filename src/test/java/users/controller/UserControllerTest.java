package users.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import users.AbstractMvcTest;
import users.model.Address;
import users.model.User;
import users.model.UserRole;
import users.repository.UserRepository;
import users.service.UserService;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest extends AbstractMvcTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Before
    public void setUp() throws Exception {
        reset(userRepository, userService);

        when(userService.isNew(any(User.class))).thenCallRealMethod();
    }

    @Test
    public void testList() throws Exception {
        User user1 = new User("admin", "pwd1", "admin@gmail.com", UserRole.editor, Arrays.asList(
                new Address("Russia", "Moscow", "Arbat, 9"),
                new Address("Russia", "Novorossiysk", "Vidova, 1")
        ));
        User user2 = new User("demo", "pwd2", "demo@gmail.com", UserRole.user, Arrays.asList(
                new Address("Russia", "Krasnodar", "Krasnaya, 1")
        ));
        when(userRepository.findAll(notNull(Sort.class))).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("name", is(user1.getName())),
                                hasProperty("password", is(user1.getPassword())),
                                hasProperty("email", is(user1.getEmail())),
                                hasProperty("role", is(user1.getRole())),
                                hasProperty("addresses", hasSize(2)),
                                hasProperty("addresses", contains(user1.getAddresses().get(0), user1.getAddresses().get(1))
                                )))))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("name", is(user2.getName())),
                                hasProperty("password", is(user2.getPassword())),
                                hasProperty("email", is(user2.getEmail())),
                                hasProperty("role", is(user2.getRole())),
                                hasProperty("addresses", hasSize(1)),
                                hasProperty("addresses", hasItem(user2.getAddresses().get(0))
                                )))));
    }

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(get("/user/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("user"))
                .andExpect(model().attribute("user", notNullValue()));
    }

    @Test
    public void testUpdate() throws Exception {
        mockMvc.perform(get("/user/update/{id}", 0L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddAddress() throws Exception {
        redirect(mockMvc.perform(post("/user/address")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "0")
                        .param("addAddress", "")
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/user/create"))
                .andReturn())

        .andExpect(status().isOk())
        .andExpect(view().name("user"))
        .andExpect(model().attributeHasNoErrors())
        .andExpect(model().attribute("user", allOf(
                hasProperty("id", is(0L)),
                hasProperty("addresses", hasSize(1)),
                hasProperty("addresses", hasItem(new Address()))
        )));
    }

    @Test
    public void testRemoveAddress() throws Exception {
        redirect(mockMvc.perform(post("/user/address")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "1")
                        .param("addresses[0].country", "USA")
                        .param("addresses[0].city", "New-York")
                        .param("addresses[0].street", "Broadway, 1")
                        .param("removeAddress", "0")
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/user/update/1"))
                .andReturn())

                .andExpect(status().isOk())
                .andExpect(view().name("user"))
                .andExpect(model().attributeHasNoErrors())
                .andExpect(model().attribute("user", allOf(
                        hasProperty("id", is(1L)),
                        hasProperty("addresses", empty())
                )));
    }

    @Test
    public void testSaveFailValidation() throws Exception {
        redirect(mockMvc.perform(post("/user/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "1")
                        .param("email", "jon@gmail.com")
        )
                .andExpect(status().isFound()).andReturn())

        .andExpect(status().isOk())
        .andExpect(view().name("user"))
        .andExpect(model().attributeHasFieldErrors("user", "name", "addresses"))
        .andExpect(model().attribute("user", allOf(
                hasProperty("id", is(1L)),
                hasProperty("email", is("jon@gmail.com"))
        )));
    }

    @Test
    public void testSave() throws Exception {
        mockMvc.perform(post("/user/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "1")
                        .param("email", "jon@gmail.com")
                        .param("name", "Jon")
                        .param("addresses[0].country", "USA")
                        .param("addresses[0].city", "New-York")
                        .param("addresses[0].street", "Broadway, 1")
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
    }
}