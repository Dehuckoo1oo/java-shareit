package ru.practicum.shareit.booking.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
