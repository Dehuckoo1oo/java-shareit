package ru.yandex.practicum.ShareIt.item;

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
