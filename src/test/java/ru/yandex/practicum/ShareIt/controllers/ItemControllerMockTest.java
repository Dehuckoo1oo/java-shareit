package ru.yandex.practicum.ShareIt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.yandex.practicum.ShareIt.item.DTO.ItemDTO;
import ru.yandex.practicum.ShareIt.item.Item;
import ru.yandex.practicum.ShareIt.item.ItemController;
import ru.yandex.practicum.ShareIt.item.ItemService;
import ru.yandex.practicum.ShareIt.item.comments.CommentDTO;
import ru.yandex.practicum.ShareIt.requests.Request;
import ru.yandex.practicum.ShareIt.user.User;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ItemControllerMockTest {

    @Mock
    private ItemService itemService;
    @InjectMocks
    private ItemController itemController;
    private final ObjectMapper mapper = new ObjectMapper();
    private MockMvc mvc;
    private Item item;
    private ItemDTO itemDTO;
    private CommentDTO comment;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(itemController)
                .build();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        item = new Item();
        item.setRequest(new Request());
        item.setId(1L);
        item.setName("name");
        item.setDescription("something");
        item.setAvailable(true);

        User user = new User(1L, "test", "test@icloud.com");

        comment = CommentDTO.builder().id(1L).text("text")
                .itemId(1L).authorName("test").build();

        itemDTO = ItemDTO.builder()
                .description("something")
                .name("testItemName")
                .available(true)
                .id(1L).build();
    }

    @Test
    public void createCommentTest() throws Exception {
        when(itemService.createComment(any(), any(), any())).thenReturn(comment);
        mvc.perform(post("/items/1/comment")
                        .content(mapper.writeValueAsString(comment))
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(comment.getId()), Long.class))
                .andExpect(jsonPath("$.itemId", is(comment.getItemId()), Long.class))
                .andExpect(jsonPath("$.text", is(comment.getText())))
                .andExpect(jsonPath("$.authorName", is(comment.getAuthorName())));
    }

    @Test
    public void getItemByIdTest() throws Exception {
        when(itemService.findItemById(any(), any())).thenReturn(itemDTO);
        mvc.perform(get("/items/1")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(itemDTO.getName())))
                .andExpect(jsonPath("$.description", is(itemDTO.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDTO.getAvailable())));
    }

    @Test
    public void getItemsByUserId() throws Exception {
        when(itemService.getItemByUserId(any())).thenReturn(List.of(itemDTO));
        mvc.perform(get("/items")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(comment.getId()), Long.class))
                .andExpect(jsonPath("$[0].name", is(itemDTO.getName())))
                .andExpect(jsonPath("$[0].description", is(itemDTO.getDescription())))
                .andExpect(jsonPath("$[0].available", is(itemDTO.getAvailable())));
    }

    @Test
    public void findItemsByTextTest() throws Exception {
        when(itemService.findItemsByText(any(), any())).thenReturn(List.of(itemDTO));
        mvc.perform(get("/items/search?text=text")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(comment.getId()), Long.class))
                .andExpect(jsonPath("$[0].name", is(itemDTO.getName())))
                .andExpect(jsonPath("$[0].description", is(itemDTO.getDescription())))
                .andExpect(jsonPath("$[0].available", is(itemDTO.getAvailable())));
    }
}
