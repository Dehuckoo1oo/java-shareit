package ru.yandex.practicum.ShareIt.booking.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class BookedItem {
    private Long id;
    private String name;


    public BookedItem(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
