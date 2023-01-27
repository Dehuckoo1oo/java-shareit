package ru.yandex.practicum.ShareIt.booking.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Booker {
    private Long id;

    public Booker(Long id) {
        this.id = id;
    }
}
