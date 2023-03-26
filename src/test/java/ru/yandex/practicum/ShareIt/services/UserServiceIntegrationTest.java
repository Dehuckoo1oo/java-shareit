package ru.yandex.practicum.ShareIt.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.ShareIt.user.User;
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
    void beforeEach() {
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
        List<UserDTO> users = userService.findAll();
        Long id = users.get(0).getId();
        UserDTO userDTOUpdate = makeUserDTO("UpdateUser");
        userDTOUpdate.setId(id);
        userDTOTest.setEmail(userDTOUpdate.getEmail());
        userService.update(userDTOUpdate);
        UserDTO userDTOResult = userService.findUserById(id);
        assertThat(userDTOResult.getName(), equalTo(userDTOUpdate.getName()));
    }

    @Test
    public void findAllTest() {
        userService.create(makeUserDTO("TestUser"));
        userService.create(makeUserDTO("TestUser1"));
        userService.create(makeUserDTO("TestUser2"));
        List<UserDTO> users = userService.findAll();
        assertThat("exist 3 users", users.size() == 3);
    }

    @Test
    public void removeTest() {
        userService.create(makeUserDTO("TestUser"));
        List<UserDTO> cntUsersFirst = userService.findAll();
        userService.remove(cntUsersFirst.get(0).getId());
        List<UserDTO> cntUsersSecond = userService.findAll();
        assertThat("exist 0 users", cntUsersSecond.size() == 0);
    }

    private UserDTO makeUserDTO(String name) {
        return new UserDTO(1L, name, name + "@icloud.com");
    }
}
