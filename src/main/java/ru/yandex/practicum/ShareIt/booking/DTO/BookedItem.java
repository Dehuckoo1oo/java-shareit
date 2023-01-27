package ru.yandex.practicum.ShareIt.booking.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookedItem {
    private Long id;
    private String name;


    public BookedItem(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
