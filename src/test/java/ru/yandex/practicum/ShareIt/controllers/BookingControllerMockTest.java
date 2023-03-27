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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.yandex.practicum.ShareIt.booking.*;
import ru.yandex.practicum.ShareIt.booking.DTO.Booker;
import ru.yandex.practicum.ShareIt.booking.DTO.BookingDTORequest;
import ru.yandex.practicum.ShareIt.booking.DTO.BookingDTOResponse;
import ru.yandex.practicum.ShareIt.exception.ErrorHandler;
import ru.yandex.practicum.ShareIt.exception.NotFoundResourceException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookingControllerMockTest {
    @Mock
    private BookingService bookingService;
    @InjectMocks
    private BookingController bookingController;
    private final ObjectMapper mapper = new ObjectMapper();
    private MockMvc mvc;
    private BookingDTORequest bookingDTORequest;
    private BookingDTOResponse bookingDTOResponse;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(bookingController)
                .setControllerAdvice(new ErrorHandler())
                .build();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        bookingDTORequest = BookingDTORequest.builder()
                .itemId(1L)
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(2))
                .bookerId(1L)
                .build();

        bookingDTOResponse = BookingDTOResponse.builder()
                .id(1L)
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(2))
                .booker(new Booker(1L))
                .status(Status.APPROVED)
                .build();
    }

    @Test
    public void testHandleValidationException() throws Exception {
        doThrow(new NotFoundResourceException("Дата старта бронирования позже даты окончания"))
                .when(bookingService).create(any(),any());
        bookingDTORequest.setStart(LocalDateTime.now().plusDays(1));
        bookingDTORequest.setEnd(LocalDateTime.now().plusHours(22));
        mvc.perform(post("/bookings")
                        .content(mapper.writeValueAsString(bookingDTORequest))
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createBookingTest() throws Exception {
        when(bookingService.create(any(), any())).thenReturn(bookingDTOResponse);
        mvc.perform(post("/bookings")
                        .content(mapper.writeValueAsString(bookingDTORequest))
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookingDTOResponse.getId()), Long.class))
                .andExpect(jsonPath("$.start", is(notNullValue())))
                .andExpect(jsonPath("$.end", is(notNullValue())))
                .andExpect(jsonPath("$.booker", is(notNullValue())));
    }

    @Test
    public void getBookingByIdTest() throws Exception {
        when(bookingService.getBookingById(any(), any())).thenReturn(bookingDTOResponse);

        mvc.perform(get("/bookings/1")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookingDTOResponse.getId()), Long.class))
                .andExpect(jsonPath("$.start", is(notNullValue())))
                .andExpect(jsonPath("$.end", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(bookingDTOResponse.getStatus().toString())));
    }

    @Test
    public void getBookingByCurrentUserTest() throws Exception {
        when(bookingService.getBookingByCurrentUser(any(), any(), any(), any())).thenReturn(List.of(bookingDTOResponse));
        State state = State.CURRENT;
        mvc.perform(get("/bookings?state={state}", state)
                        .content(mapper.writeValueAsString(bookingDTOResponse))
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(bookingDTOResponse.getId()), Long.class))
                .andExpect(jsonPath("$[0].start", is(notNullValue())))
                .andExpect(jsonPath("$[0].end", is(notNullValue())))
                .andExpect(jsonPath("$[0].status", is(bookingDTOResponse.getStatus().toString())));
    }

    @Test
    public void getBookingByOwnerItemsTest() throws Exception {
        when(bookingService.getBookingByOwnerItems(any(), any(), any(), any())).thenReturn(List.of(bookingDTOResponse));
        State state = State.CURRENT;
        mvc.perform(get("/bookings/owner?state={state}", state)
                        .content(mapper.writeValueAsString(bookingDTORequest))
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(bookingDTOResponse.getId()), Long.class))
                .andExpect(jsonPath("$[0].start", is(notNullValue())))
                .andExpect(jsonPath("$[0].end", is(notNullValue())))
                .andExpect(jsonPath("$[0].status", is(bookingDTOResponse.getStatus().toString())));
    }
}

