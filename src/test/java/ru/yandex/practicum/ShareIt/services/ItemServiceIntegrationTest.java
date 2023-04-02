package ru.yandex.practicum.ShareIt.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.ShareIt.booking.BookingService;
import ru.yandex.practicum.ShareIt.booking.DTO.BookingDTORequest;
import ru.yandex.practicum.ShareIt.booking.DTO.BookingDTOResponse;
import ru.yandex.practicum.ShareIt.exception.NoSuchBodyException;
import ru.yandex.practicum.ShareIt.exception.UnsupportedStatusException;
import ru.yandex.practicum.ShareIt.item.DTO.ItemDTO;
import ru.yandex.practicum.ShareIt.item.DTO.ItemForRequestDTO;
import ru.yandex.practicum.ShareIt.item.Item;
import ru.yandex.practicum.ShareIt.item.ItemService;
import ru.yandex.practicum.ShareIt.item.comments.Comment;
import ru.yandex.practicum.ShareIt.item.comments.CommentDTO;
import ru.yandex.practicum.ShareIt.user.UserDTO;
import ru.yandex.practicum.ShareIt.user.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class ItemServiceIntegrationTest {
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void truncateTables() {
        jdbcTemplate.execute("DELETE FROM users");
        jdbcTemplate.execute("DELETE FROM items");
        jdbcTemplate.execute("DELETE FROM bookings");
        jdbcTemplate.execute("DELETE FROM comments");

    }

    @Test
    public void createTest() {
        ItemForRequestDTO item = makeItem("someItem");
        item.setAvailable(false);
        UserDTO user = userService.create(makeUserDTO("TestUser"));
        ItemDTO itemDTOTest = itemService.create(makeItemDTO("TestItem"), user.getId());
        ItemDTO itemDTO = itemService.findItemById(user.getId(), itemDTOTest.getId());
        assertThat(itemDTOTest, equalTo(itemDTO));
    }

    @Test
    public void updateTest() {
        UserDTO user = userService.create(makeUserDTO("TestUser"));
        ItemDTO itemDTOTest = itemService.create(makeItemDTO("TestItem"), user.getId());
        itemDTOTest.setName("updatedItem");
        itemService.update(itemDTOTest, user.getId(), itemDTOTest.getId());
        ItemDTO itemDTO = itemService.findItemById(user.getId(), itemDTOTest.getId());
        assertThat(itemDTOTest, equalTo(itemDTO));
    }

    @Test
    public void findItemsByTextTest() {
        UserDTO user = userService.create(makeUserDTO("TestUser"));
        itemService.create(makeItemDTO("TestItem1"), user.getId());
        itemService.create(makeItemDTO("TestItem2"), user.getId());
        itemService.create(makeItemDTO("TestFindMe"), user.getId());
        itemService.create(makeItemDTO("TestTryToFindMe"), user.getId());
        List<ItemDTO> itemDTOs = itemService.findItemsByText("FindMe", user.getId());
        assertThat("Не верная длина массива", itemDTOs.size() == 2);
    }

    @Test
    public void createCommentTest() {
        UserDTO owner = userService.create(makeUserDTO("owner"));
        ItemDTO itemDTO = itemService.create(makeItemDTO("TestItem1"), owner.getId());
        UserDTO booker = userService.create(makeUserDTO("booker"));
        BookingDTOResponse bookingDTOResponse = bookingService.create(makeNewBooking(booker, itemDTO), booker.getId());
        bookingService.updateStatus(owner.getId(), bookingDTOResponse.getId(), true);
        CommentDTO commentDTO = itemService.createComment(makeCommentDTO(booker), booker.getId(), itemDTO.getId());
        ItemDTO itemWithComment = itemService.findItemById(owner.getId(), itemDTO.getId());
        assertThat(commentDTO, equalTo(itemWithComment.getComments().get(0)));
    }

    @Test
    public void findItemByIdNotExistTest() {
        UserDTO owner = userService.create(makeUserDTO("owner"));
        try {
            itemService.findItemById(owner.getId(), 999L);
            fail("Expected NoSuchBodyException");
        } catch (NoSuchBodyException e) {
            assertEquals(e.getParameter(), String.format("Предмет с id %s отсутствует", 999L));
        }
    }

    @Test
    public void createCommentFailByOwnedItemTest() {
        UserDTO owner = userService.create(makeUserDTO("owner"));
        ItemDTO itemDTO = itemService.create(makeItemDTO("TestItem1"), owner.getId());
        UserDTO booker = userService.create(makeUserDTO("booker"));
        UserDTO anotherUser = userService.create(makeUserDTO("anotherUser"));
        BookingDTOResponse bookingDTOResponse = bookingService.create(makeNewBooking(booker, itemDTO), booker.getId());
        bookingService.updateStatus(owner.getId(), bookingDTOResponse.getId(), true);
        try {
            itemService.createComment(makeCommentDTO(anotherUser), anotherUser.getId(), itemDTO.getId());
            fail("Expected UnsupportedStatusException");
        } catch (UnsupportedStatusException e) {
            assertEquals(e.getParameter(), String.format("Данный пользователь не брал в аренду предмет с id %s", itemDTO.getId()));
        }
    }

    @Test
    public void getItemByUserIdTest() {
        UserDTO owner = userService.create(makeUserDTO("owner"));
        ItemDTO itemDTO = itemService.create(makeItemDTO("TestItem1"), owner.getId());
        List<ItemDTO> items = itemService.getItemByUserId(owner.getId());
        assertEquals(1, items.size());
    }

    private UserDTO makeUserDTO(String name) {
        return new UserDTO(1L, name, name + "@icloud.com");
    }

    private ItemDTO makeItemDTO(String name) {
        return new ItemDTO(1L, "name", "just " + name, true, null,
                null, null, null);
    }

    private ItemForRequestDTO makeItem(String name) {
        return new ItemForRequestDTO(1L, "name", "just " + name, true, 1L);
    }

    private BookingDTORequest makeNewBooking(UserDTO userDTO, ItemDTO itemDTO) {
        return new BookingDTORequest(null,
                LocalDateTime.now().minusDays(1L),
                LocalDateTime.now().plusDays(1L),
                itemDTO.getId(),
                userDTO.getId());
    }

    private CommentDTO makeCommentDTO(UserDTO booker) {
        Comment commentEnt = new Comment();
        commentEnt.setItem(new Item());
        commentEnt.setText("");
        return new CommentDTO(null, "Nice item",LocalDateTime.now(), 1L, booker.getName());
    }
}
