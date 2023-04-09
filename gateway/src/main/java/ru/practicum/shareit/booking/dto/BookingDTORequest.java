package ru.practicum.shareit.booking.dto;

import lombok.*;
import ru.practicum.shareit.groups.Create;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDTORequest {
    private Long id;
    @NotNull(groups = {Create.class})
    @FutureOrPresent(groups = {Create.class})
    private LocalDateTime start;
    @NotNull(groups = {Create.class})
    @FutureOrPresent(groups = {Create.class})
    private LocalDateTime end;
    @NotNull(groups = {Create.class})
    private Long itemId;
    private Long bookerId;
}
