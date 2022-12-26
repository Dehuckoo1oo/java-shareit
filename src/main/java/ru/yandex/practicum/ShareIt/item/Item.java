package ru.yandex.practicum.ShareIt.item;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
