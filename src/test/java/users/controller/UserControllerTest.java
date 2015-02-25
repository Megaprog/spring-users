package users.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import users.AbstractMvcTest;
import users.model.Address;
import users.model.User;
import users.model.UserRole;
import users.repository.UserRepository;
import users.service.UserService;

import java.util.Arrays;

import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class UserControllerTest extends AbstractMvcTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Before
    public void setUp() throws Exception {
        reset(userRepository, userService);
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
                .andExpect(view().name("users"));
    }

    @Test
    public void testCreate() throws Exception {

    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testAddAddress() throws Exception {

    }

    @Test
    public void testRemoveAddress() throws Exception {

    }

    @Test
    public void testSave() throws Exception {

    }
}