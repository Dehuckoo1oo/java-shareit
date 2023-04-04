package ru.yandex.practicum.ShareIt.booking.DTO;

import lombok.*;
import ru.yandex.practicum.ShareIt.booking.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDTOResponse {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Status status;
    private BookedItem item;
    private Booker booker;

}
