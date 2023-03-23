package ru.yandex.practicum.ShareIt.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.ShareIt.booking.BookingService;
import ru.yandex.practicum.ShareIt.booking.DTO.BookingDTORequest;
import ru.yandex.practicum.ShareIt.item.DTO.ItemDTO;
import ru.yandex.practicum.ShareIt.item.ItemService;
import ru.yandex.practicum.ShareIt.requests.DTO.OwnedRequestDTO;
import ru.yandex.practicum.ShareIt.requests.DTO.RequestDTO;
import ru.yandex.practicum.ShareIt.requests.RequestService;
import ru.yandex.practicum.ShareIt.user.UserDTO;
import ru.yandex.practicum.ShareIt.user.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class RequestServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void truncateTables() {
        jdbcTemplate.execute("DELETE FROM users");
        jdbcTemplate.execute("DELETE FROM items");
        jdbcTemplate.execute("DELETE FROM bookings");
        jdbcTemplate.execute("DELETE FROM requests");
    }

    @Test
    public void createTest() {
        UserDTO requester = userService.create(makeUserDTO("Mike"));
        RequestDTO requestDTO = requestService.create(makeRequestDTO(), requester.getId());
        OwnedRequestDTO ownedRequestDTO = requestService.findRequestByOwnerAndId(requester.getId(), requestDTO.getId());
        assertThat("Не найден запрос вещи", requestDTO.getId().equals(ownedRequestDTO.getId()));
    }

    @Test
    public void findAllTest() {
        UserDTO requester = userService.create(makeUserDTO("Mike"));
        UserDTO secondRequester = userService.create(makeUserDTO("Carl"));
        RequestDTO requestDTO = requestService.create(makeRequestDTO(), requester.getId());
        List<OwnedRequestDTO> ownedRequestDTOs = requestService.findAll("0", "15", secondRequester.getId());
        assertThat("Не найден запрос вещи", ownedRequestDTOs.size() == 1);
    }

    @Test
    public void findRequestsByOwner() {
        UserDTO requester = userService.create(makeUserDTO("Mike"));
        RequestDTO requestDTO = requestService.create(makeRequestDTO(), requester.getId());
        List<OwnedRequestDTO> ownedRequestDTOs = requestService.findRequestsByOwner(requester.getId());
        assertThat("Не найден запрос вещи", ownedRequestDTOs.size() == 1);
    }

    private RequestDTO makeRequestDTO() {
        return new RequestDTO(1L, LocalDateTime.now(), "I need some Item");
    }

    private UserDTO makeUserDTO(String name) {
        return new UserDTO(1L, name, name + "@icloud.com");
    }

}
