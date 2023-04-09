package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.DTO.BookingDTORequest;
import ru.practicum.shareit.booking.DTO.BookingDTOResponse;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@Slf4j
public class BookingAPIController {

    private final BookingService bookingService;

    @Autowired
    public BookingAPIController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping()
    public BookingDTOResponse create(@RequestHeader("X-Sharer-User-Id") Long userId,
                                     @RequestBody BookingDTORequest bookingDTORequest) {
        return bookingService.create(bookingDTORequest, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDTOResponse approveBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                             @PathVariable("bookingId") Long bookingId,
                                             @RequestParam("approved") Boolean approved) {
        return bookingService.updateStatus(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDTOResponse getBookingById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                             @PathVariable Long bookingId) {
        return bookingService.getBookingById(userId, bookingId);
    }

    @GetMapping("/owner")
    public List<BookingDTOResponse> getBookingByOwnerItems(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                           @RequestParam String state,
                                                           @RequestParam(required = false) String from,
                                                           @RequestParam(required = false) String size) {
        return bookingService.getBookingByOwnerItems(from, size, userId, state);
    }

    @GetMapping()
    public List<BookingDTOResponse> getBookingByCurrentUser(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                            @RequestParam String state,
                                                            @RequestParam(required = false) String from,
                                                            @RequestParam(required = false) String size) {
        return bookingService.getBookingByCurrentUser(from, size,userId, state);
    }

}
