package ru.yandex.practicum.ShareIt.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.ShareIt.user.UserDTO;
import ru.yandex.practicum.ShareIt.user.UserService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void truncateTables() {
        jdbcTemplate.execute("delete from users");
    }

    @Test
    public void createTest() {
        UserDTO userDTOTest = userService.create(makeUserDTO("TestUser"));
        UserDTO userDTO = userService.findUserById(userDTOTest.getId());
        List<UserDTO> users = userService.findAll();
        assertThat("В БД 1 запись", users.size() == 1);
        assertThat(userDTOTest, equalTo(userDTO));
    }

    @Test
    public void updateTest() {
        UserDTO userDTOTest = userService.create(makeUserDTO("TestUser"));
        UserDTO userDTOUpdate = userService.update(makeUserDTO("UpdateUser"));
        Long id = userDTOTest.getId();
        userDTOTest = userService.findUserById(id);
        assertThat(userDTOTest, equalTo(userDTOUpdate));
    }

    @Test
    public void findAllTest() {
        userService.create(makeUserDTO("TestUser"));
        userService.create(makeUserDTO("TestUser1"));
        userService.create(makeUserDTO("TestUser2"));
        List<UserDTO> users = userService.findAll();
        assertThat("exist 3 users", users.size() == 3);
    }


    private UserDTO makeUserDTO(String name) {
        return new UserDTO(1L, name, name + "@icloud.com");
    }


}
