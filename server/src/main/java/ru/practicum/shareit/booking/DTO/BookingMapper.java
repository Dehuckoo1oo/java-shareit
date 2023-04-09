package ru.practicum.shareit.booking.DTO;

import ru.practicum.shareit.booking.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingMapper {

    public static BookingDTOResponse mapEntityToDTO(Booking booking) {
        return new BookingDTOResponse(booking.getId(), booking.getStart(), booking.getEnd(), booking.getStatus(),
                new BookedItem(booking.getItem().getId(), booking.getItem().getName()),
                new Booker(booking.getBooker().getId()));
    }

    public static List<BookingDTOResponse> mapEntityToDTOList(List<Booking> bookings) {
        List<BookingDTOResponse> dtoBookings = new ArrayList<>();
        bookings.forEach(booking -> dtoBookings.add(new BookingDTOResponse(booking.getId(), booking.getStart(),
                booking.getEnd(), booking.getStatus(),
                new BookedItem(booking.getItem().getId(), booking.getItem().getName()),
                new Booker(booking.getBooker().getId()))));
        return dtoBookings;
    }

}
