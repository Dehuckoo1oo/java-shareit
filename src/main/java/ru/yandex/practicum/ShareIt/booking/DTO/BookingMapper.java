package ru.yandex.practicum.ShareIt.booking.DTO;

import ru.yandex.practicum.ShareIt.booking.Booking;

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

    /*public static Booking mapDTOToEntity(BookingDTOResponse bookingDTOResponse) {
        return new Booking(bookingDTOResponse.getId(), bookingDTOResponse.getStart(), bookingDTOResponse.getEnd(),
                bookingDTOResponse.getItem(), bookingDTOResponse.getBooker(),null);
    }*/
}
