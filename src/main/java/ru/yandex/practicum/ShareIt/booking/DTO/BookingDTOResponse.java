package ru.yandex.practicum.ShareIt.booking.DTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.ShareIt.booking.Status;
import ru.yandex.practicum.ShareIt.groups.Create;
import ru.yandex.practicum.ShareIt.item.Item;
import ru.yandex.practicum.ShareIt.user.User;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class BookingDTOResponse {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Status status;
    private BookedItem item;
    private Booker booker;
}
