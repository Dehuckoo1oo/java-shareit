package ru.yandex.practicum.ShareIt.item;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Item {

    private Long id;
    private String name;
    private String description;
    private Long owner;
    private Boolean available;
}
