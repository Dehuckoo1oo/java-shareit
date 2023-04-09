package ru.practicum.shareit.item;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LastOrNextBooking {
    private Long id;
    private Long bookerId;
}
