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
import ru.yandex.practicum.ShareIt.item.DTO.ItemForRequestDTO;
import ru.yandex.practicum.ShareIt.requests.DTO.OwnedRequestDTO;
import ru.yandex.practicum.ShareIt.requests.DTO.RequestDTO;
import ru.yandex.practicum.ShareIt.requests.RequestController;
import ru.yandex.practicum.ShareIt.requests.RequestService;
import ru.yandex.practicum.ShareIt.user.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RequestControllerMockTest {
    @Mock
    private RequestService requestService;
    @InjectMocks
    private RequestController requestController;
    private final ObjectMapper mapper = new ObjectMapper();
    private MockMvc mvc;
    private RequestDTO requestDTO;
    private OwnedRequestDTO ownedRequestDTO;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(requestController)
                .build();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        requestDTO = RequestDTO.builder()
                .id(1L)
                .created(LocalDateTime.now())
                .description("something").build();

        ownedRequestDTO = OwnedRequestDTO.builder()
                .items(List.of(new ItemForRequestDTO()))
                .created(LocalDateTime.now())
                .id(1L)
                .description("something new")
                .build();

    }

    @Test
    public void getRequestsByIdAndUserTest() throws Exception {
        when(requestService.findRequestByOwnerAndId(any(), any())).thenReturn(ownedRequestDTO);

        mvc.perform(get("/requests/1")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ownedRequestDTO.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(ownedRequestDTO.getDescription())))
                .andExpect(jsonPath("$.created", is(notNullValue())))
                .andExpect(jsonPath("$.items.size()", is(ownedRequestDTO.getItems().size())));
    }


    @Test
    public void getAllTest() throws Exception {

        mvc.perform(get("/requests/all")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void createRequest() throws Exception {
        when(requestService.create(any(), any())).thenReturn(requestDTO);

        mvc.perform(post("/requests")
                        .content(mapper.writeValueAsString(requestDTO))
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(requestDTO.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(requestDTO.getDescription())))
                .andExpect(jsonPath("$.created", is(notNullValue())));
    }

}
