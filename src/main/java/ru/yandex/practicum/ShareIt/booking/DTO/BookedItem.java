package ru.yandex.practicum.ShareIt.booking.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookedItem {
    Long id;
    String name;


    public BookedItem(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
