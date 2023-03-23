package ru.yandex.practicum.ShareIt.requests.DTO;

import lombok.*;
import ru.yandex.practicum.ShareIt.item.DTO.ItemForRequestDTO;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnedRequestDTO {
    private Long id;
    private String description;
    private LocalDateTime created;
    private List<ItemForRequestDTO> items;
}
