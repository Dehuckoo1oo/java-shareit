package ru.yandex.practicum.ShareIt.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.ShareIt.booking.BookingService;
import ru.yandex.practicum.ShareIt.booking.DTO.BookingDTORequest;
import ru.yandex.practicum.ShareIt.booking.DTO.BookingDTOResponse;
import ru.yandex.practicum.ShareIt.booking.Status;
import ru.yandex.practicum.ShareIt.item.DTO.ItemDTO;
import ru.yandex.practicum.ShareIt.item.ItemService;
import ru.yandex.practicum.ShareIt.user.UserDTO;
import ru.yandex.practicum.ShareIt.user.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest
public class BookingServiceIntegrationTests {
    @Autowired
    UserService userService;
    @Autowired
    ItemService itemService;
    @Autowired
    BookingService bookingService;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void truncateTables() {
        jdbcTemplate.execute("DELETE FROM users");
        jdbcTemplate.execute("DELETE FROM items");
        jdbcTemplate.execute("DELETE FROM bookings");
        jdbcTemplate.execute("DELETE FROM comments");
    }

    @Test
    public void createTest() {
        UserDTO owner = userService.create(makeUserDTO("owner"));
        ItemDTO itemDTO = itemService.create(makeItemDTO("TestItem1"), owner.getId());
        UserDTO booker = userService.create(makeUserDTO("booker"));
        BookingDTOResponse bookingDTOResponse = bookingService.create(makeNewBooking(booker, itemDTO), booker.getId());
        BookingDTOResponse bookingDTOResponse1 = bookingService.getBookingById(booker.getId(), bookingDTOResponse.getId());
        assertThat(bookingDTOResponse, equalTo(bookingDTOResponse1));
    }

    @Test
    public void updateStatusTest() {
        UserDTO owner = userService.create(makeUserDTO("owner"));
        ItemDTO itemDTO = itemService.create(makeItemDTO("TestItem1"), owner.getId());
        UserDTO booker = userService.create(makeUserDTO("booker"));
        BookingDTOResponse bookingDTOResponse = bookingService.create(makeNewBooking(booker, itemDTO), booker.getId());
        bookingService.updateStatus(owner.getId(), bookingDTOResponse.getId(), true);
        BookingDTOResponse bookingDTOResponse1 = bookingService.getBookingById(booker.getId(), bookingDTOResponse.getId());
        assertThat("Неверно обновляется статус в бронировании",
                bookingDTOResponse1.getStatus().equals(Status.APPROVED));
    }

    @Test
    public void getBookingByCurrentUserWithALLTest() {
        UserDTO owner = userService.create(makeUserDTO("owner"));
        ItemDTO itemDTO = itemService.create(makeItemDTO("TestItem1"), owner.getId());
        UserDTO booker = userService.create(makeUserDTO("booker"));
        BookingDTORequest bookingDTORequest = makeNewBooking(booker, itemDTO);
        BookingDTOResponse bookingDTOResponse = bookingService.create(bookingDTORequest, booker.getId());
        List<BookingDTOResponse> bookingDTOResponses = bookingService.
                getBookingByCurrentUser("0", "15", booker.getId(), "ALL");
        assertThat("Некорректно работают критерии отбора", bookingDTOResponses.size() == 1);
    }

    @Test
    public void getBookingByCurrentUserWithCURRENTTest() {
        UserDTO owner = userService.create(makeUserDTO("owner"));
        ItemDTO itemDTO = itemService.create(makeItemDTO("TestItem1"), owner.getId());
        UserDTO booker = userService.create(makeUserDTO("booker"));
        BookingDTORequest bookingDTORequest = makeNewBooking(booker, itemDTO);
        BookingDTOResponse bookingDTOResponse = bookingService.create(bookingDTORequest, booker.getId());
        List<BookingDTOResponse> bookingDTOResponses = bookingService.
                getBookingByCurrentUser("0", "15", booker.getId(), "CURRENT");
        assertThat("Некорректно работают критерии отбора", bookingDTOResponses.size() == 1);
    }

    @Test
    public void getBookingByCurrentUserInPASTTest() {
        UserDTO owner = userService.create(makeUserDTO("owner"));
        ItemDTO itemDTO = itemService.create(makeItemDTO("TestItem1"), owner.getId());
        UserDTO booker = userService.create(makeUserDTO("booker"));
        BookingDTORequest bookingDTORequest = makeNewBooking(booker, itemDTO);
        bookingDTORequest.setEnd(LocalDateTime.now().minusHours(4));
        BookingDTOResponse bookingDTOResponse = bookingService.create(bookingDTORequest, booker.getId());
        List<BookingDTOResponse> bookingDTOResponses = bookingService.
                getBookingByCurrentUser("0", "15", booker.getId(), "PAST");
        assertThat("Некорректно работают критерии отбора", bookingDTOResponses.size() == 1);
    }

    @Test
    public void getBookingByCurrentUserInFUTURETest() {
        UserDTO owner = userService.create(makeUserDTO("owner"));
        ItemDTO itemDTO = itemService.create(makeItemDTO("TestItem1"), owner.getId());
        UserDTO booker = userService.create(makeUserDTO("booker"));
        BookingDTORequest bookingDTORequest = makeNewBooking(booker, itemDTO);
        bookingDTORequest.setStart(LocalDateTime.now().plusDays(1));
        bookingDTORequest.setEnd(LocalDateTime.now().plusDays(4));
        BookingDTOResponse bookingDTOResponse = bookingService.create(bookingDTORequest, booker.getId());
        List<BookingDTOResponse> bookingDTOResponses = bookingService.
                getBookingByCurrentUser("0", "15", booker.getId(), "FUTURE");
        assertThat("Некорректно работают критерии отбора", bookingDTOResponses.size() == 1);
    }

    @Test
    public void getBookingByCurrentUserWithWAITINTest() {
        UserDTO owner = userService.create(makeUserDTO("owner"));
        ItemDTO itemDTO = itemService.create(makeItemDTO("TestItem1"), owner.getId());
        UserDTO booker = userService.create(makeUserDTO("booker"));
        BookingDTORequest bookingDTORequest = makeNewBooking(booker, itemDTO);
        BookingDTOResponse bookingDTOResponse = bookingService.create(bookingDTORequest, booker.getId());
        List<BookingDTOResponse> bookingDTOResponses = bookingService.
                getBookingByCurrentUser("0", "15", booker.getId(), "WAITING");
        assertThat("Некорректно работают критерии отбора", bookingDTOResponses.size() == 1);
    }

    @Test
    public void getBookingByCurrentUserWithREJECTEDTest() {
        UserDTO owner = userService.create(makeUserDTO("owner"));
        ItemDTO itemDTO = itemService.create(makeItemDTO("TestItem1"), owner.getId());
        UserDTO booker = userService.create(makeUserDTO("booker"));
        BookingDTORequest bookingDTORequest = makeNewBooking(booker, itemDTO);
        BookingDTOResponse bookingDTOResponse = bookingService.create(bookingDTORequest, booker.getId());
        bookingService.updateStatus(owner.getId(), bookingDTOResponse.getId(), false);
        List<BookingDTOResponse> bookingDTOResponses = bookingService.
                getBookingByCurrentUser("0", "15", booker.getId(), "REJECTED");
        assertThat("Некорректно работают критерии отбора", bookingDTOResponses.size() == 1);
    }

    @Test
    public void getBookingByOwnerItemsWithALLTest() {
        UserDTO owner = userService.create(makeUserDTO("owner"));
        ItemDTO itemDTO = itemService.create(makeItemDTO("TestItem1"), owner.getId());
        UserDTO booker = userService.create(makeUserDTO("booker"));
        BookingDTORequest bookingDTORequest = makeNewBooking(booker, itemDTO);
        BookingDTOResponse bookingDTOResponse = bookingService.create(bookingDTORequest, booker.getId());
        List<BookingDTOResponse> bookingDTOResponses = bookingService.
                getBookingByOwnerItems("0", "15", owner.getId(), "ALL");
        assertThat("Некорректно работают критерии отбора", bookingDTOResponses.size() == 1);
    }

    @Test
    public void getBookingByOwnerItemsWithCURRENTTest() {
        UserDTO owner = userService.create(makeUserDTO("owner"));
        ItemDTO itemDTO = itemService.create(makeItemDTO("TestItem1"), owner.getId());
        UserDTO booker = userService.create(makeUserDTO("booker"));
        BookingDTORequest bookingDTORequest = makeNewBooking(booker, itemDTO);
        BookingDTOResponse bookingDTOResponse = bookingService.create(bookingDTORequest, booker.getId());
        List<BookingDTOResponse> bookingDTOResponses = bookingService.
                getBookingByOwnerItems("0", "15", owner.getId(), "CURRENT");
        assertThat("Некорректно работают критерии отбора", bookingDTOResponses.size() == 1);
    }

    @Test
    public void getBookingByOwnerItemsInPASTTest() {
        UserDTO owner = userService.create(makeUserDTO("owner"));
        ItemDTO itemDTO = itemService.create(makeItemDTO("TestItem1"), owner.getId());
        UserDTO booker = userService.create(makeUserDTO("booker"));
        BookingDTORequest bookingDTORequest = makeNewBooking(booker, itemDTO);
        bookingDTORequest.setEnd(LocalDateTime.now().minusHours(4));
        BookingDTOResponse bookingDTOResponse = bookingService.create(bookingDTORequest, booker.getId());
        List<BookingDTOResponse> bookingDTOResponses = bookingService.
                getBookingByOwnerItems("0", "15", owner.getId(), "PAST");
        assertThat("Некорректно работают критерии отбора", bookingDTOResponses.size() == 1);
    }

    @Test
    public void getBookingByOwnerItemsInFUTURETest() {
        UserDTO owner = userService.create(makeUserDTO("owner"));
        ItemDTO itemDTO = itemService.create(makeItemDTO("TestItem1"), owner.getId());
        UserDTO booker = userService.create(makeUserDTO("booker"));
        BookingDTORequest bookingDTORequest = makeNewBooking(booker, itemDTO);
        bookingDTORequest.setStart(LocalDateTime.now().plusDays(1));
        bookingDTORequest.setEnd(LocalDateTime.now().plusDays(4));
        BookingDTOResponse bookingDTOResponse = bookingService.create(bookingDTORequest, booker.getId());
        List<BookingDTOResponse> bookingDTOResponses = bookingService.
                getBookingByOwnerItems("0", "15", owner.getId(), "FUTURE");
        assertThat("Некорректно работают критерии отбора", bookingDTOResponses.size() == 1);
    }

    @Test
    public void getBookingByOwnerItemsWithWAITINTest() {
        UserDTO owner = userService.create(makeUserDTO("owner"));
        ItemDTO itemDTO = itemService.create(makeItemDTO("TestItem1"), owner.getId());
        UserDTO booker = userService.create(makeUserDTO("booker"));
        BookingDTORequest bookingDTORequest = makeNewBooking(booker, itemDTO);
        BookingDTOResponse bookingDTOResponse = bookingService.create(bookingDTORequest, booker.getId());
        List<BookingDTOResponse> bookingDTOResponses = bookingService.
                getBookingByOwnerItems("0", "15", owner.getId(), "WAITING");
        assertThat("Некорректно работают критерии отбора", bookingDTOResponses.size() == 1);
    }

    @Test
    public void getBookingByOwnerItemsWithREJECTEDTest() {
        UserDTO owner = userService.create(makeUserDTO("owner"));
        ItemDTO itemDTO = itemService.create(makeItemDTO("TestItem1"), owner.getId());
        UserDTO booker = userService.create(makeUserDTO("booker"));
        BookingDTORequest bookingDTORequest = makeNewBooking(booker, itemDTO);
        BookingDTOResponse bookingDTOResponse = bookingService.create(bookingDTORequest, booker.getId());
        bookingService.updateStatus(owner.getId(), bookingDTOResponse.getId(), false);
        List<BookingDTOResponse> bookingDTOResponses = bookingService.
                getBookingByOwnerItems("0", "15", owner.getId(), "REJECTED");
        assertThat("Некорректно работают критерии отбора", bookingDTOResponses.size() == 1);
    }

    private UserDTO makeUserDTO(String name) {
        return new UserDTO(1L, name, name + "@icloud.com");
    }

    private ItemDTO makeItemDTO(String name) {
        return new ItemDTO(1L, "name", "just " + name, true, null,
                null, null, null);
    }

    private BookingDTORequest makeNewBooking(UserDTO userDTO, ItemDTO itemDTO) {
        return new BookingDTORequest(null,
                LocalDateTime.now().minusDays(1L),
                LocalDateTime.now().plusDays(1L),
                itemDTO.getId(),
                userDTO.getId());
    }

}


