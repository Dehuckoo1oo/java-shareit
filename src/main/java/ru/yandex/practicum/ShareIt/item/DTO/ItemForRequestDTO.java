package ru.yandex.practicum.ShareIt.item.DTO;


import lombok.*;

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
