package ru.yandex.practicum.ShareIt.booking.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Booker {
    private Long id;

    public Booker(Long id) {
        this.id = id;
    }

}
