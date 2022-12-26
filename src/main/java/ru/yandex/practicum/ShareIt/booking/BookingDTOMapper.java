package ru.yandex.practicum.ShareIt.booking;

public class BookingDTOMapper {
    public static BookingDTO mapBooking(Booking booking) {
        return new BookingDTO(booking.getItemId(), booking.getItemId(), booking.getUserId(), booking.getDateFrom(),
                booking.getDateTo());
    }
}
