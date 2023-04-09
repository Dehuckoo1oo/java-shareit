package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.DTO.BookingDTORequest;
import ru.practicum.shareit.booking.DTO.BookingDTOResponse;

import java.util.List;

public interface BookingService {
    public BookingDTOResponse create(BookingDTORequest bookingDTORequest, Long userId);

    public BookingDTOResponse updateStatus(Long userId, Long bookingId, Boolean approved);

    public BookingDTOResponse getBookingById(Long userId, Long bookingId);

    public List<BookingDTOResponse> getBookingByCurrentUser(String from, String size, Long userId, String strState);

    public List<BookingDTOResponse> getBookingByOwnerItems(String from, String size, Long userId, String strState);
}
