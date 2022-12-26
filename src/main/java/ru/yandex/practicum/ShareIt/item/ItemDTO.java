package ru.yandex.practicum.ShareIt.item;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class ItemDTO {
    interface New {

    }

    interface Exist {

    }

    interface Update extends Exist {

    }

    private Long id;
    @NotBlank(groups = {ItemDTO.New.class})
    private String name;
    @NotBlank(groups = {ItemDTO.New.class})
    private String description;
    @NotNull(groups = {ItemDTO.New.class})
    private Boolean available;
}
