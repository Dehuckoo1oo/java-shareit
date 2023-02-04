package ru.yandex.practicum.ShareIt.requests.DTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.ShareIt.item.DTO.ItemForRequestDTO;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class OwnedRequestDTO {
    private Long id;
    private String description;
    private LocalDateTime created;
    private List<ItemForRequestDTO> items;
}
