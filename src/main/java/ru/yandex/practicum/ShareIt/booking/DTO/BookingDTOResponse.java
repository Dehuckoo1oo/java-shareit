package ru.yandex.practicum.ShareIt.booking;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
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
    @NotBlank(groups = {Create.class})
    private LocalDateTime start;
    @NotBlank(groups = {Create.class})
    private LocalDateTime end;
    @NotBlank(groups = {Create.class})
    private Item item;
    private User booker;
}
