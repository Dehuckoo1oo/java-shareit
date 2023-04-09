package ru.practicum.shareit.item.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemForRequestDTO {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private Long requestId;
}
