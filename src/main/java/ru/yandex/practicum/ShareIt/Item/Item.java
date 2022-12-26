package ru.yandex.practicum.ShareIt.Item;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Item {

    interface New {

    }

    interface Exist {

    }

    interface Update extends Exist {

    }

    private Long id;
    @NotBlank(groups = {Item.New.class})
    private String name;
    @NotBlank(groups = {Item.New.class})
    private String description;
    private Long owner;
    @NotNull(groups = {Item.New.class})
    private Boolean available;
}
