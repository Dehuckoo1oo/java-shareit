package ru.yandex.practicum.ShareIt.booking;

import ru.yandex.practicum.ShareIt.booking.DTO.BookingDTOResponse;

public class BookingMapper {
    public static BookingDTOResponse mapEntityToDTO(Booking booking) {
        return new BookingDTOResponse(booking.getId(), booking.getStart(), booking.getEnd(), booking.getItem(),
                booking.getBooker());
    }

    public static Booking mapDTOToEntity(BookingDTOResponse bookingDTOResponse) {
        return new Booking(bookingDTOResponse.getId(), bookingDTOResponse.getStart(), bookingDTOResponse.getEnd(),
                bookingDTOResponse.getItem(), bookingDTOResponse.getBooker(),null);
    }
}
