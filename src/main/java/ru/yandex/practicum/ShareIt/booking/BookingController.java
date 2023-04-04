package ru.yandex.practicum.ShareIt.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.ShareIt.booking.DTO.BookingDTORequest;
import ru.yandex.practicum.ShareIt.booking.DTO.BookingDTOResponse;
import ru.yandex.practicum.ShareIt.groups.Create;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@Slf4j
public class BookingController {

    BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping()
    public BookingDTOResponse create(@RequestHeader("X-Sharer-User-Id") Long userId,
                                     @Validated(Create.class) @RequestBody BookingDTORequest bookingDTORequest) {
        return bookingService.create(bookingDTORequest, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDTOResponse updateStatus(@RequestHeader("X-Sharer-User-Id") Long userId,
                                           @PathVariable Long bookingId,
                                           @RequestParam String approved) {
        return bookingService.updateStatus(userId, bookingId, Boolean.valueOf(approved));
    }

    @GetMapping("/{bookingId}")
    public BookingDTOResponse getBookingById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                             @PathVariable Long bookingId) {
        return bookingService.getBookingById(userId, bookingId);
    }

    @GetMapping("/owner")
    public List<BookingDTOResponse> getBookingByOwnerItems(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                           @RequestParam(name = "state", required = false,
                                                                   defaultValue = "ALL") String state,
                                                           @RequestParam(required = false,
                                                                   defaultValue = "0") String from,
                                                           @RequestParam(required = false,
                                                                   defaultValue = "9999999") String size) {
        return bookingService.getBookingByOwnerItems(from, size, userId, state);
    }

    @GetMapping()
    public List<BookingDTOResponse> getBookingByCurrentUser(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                            @RequestParam(name = "state", required = false,
                                                                    defaultValue = "ALL") String state,
                                                            @RequestParam(required = false,
                                                                    defaultValue = "0") String from,
                                                            @RequestParam(required = false,
                                                                    defaultValue = "9999999") String size) {
        List<BookingDTOResponse> lst = bookingService.getBookingByCurrentUser(from, size, userId, state);
        return lst;
    }

}
