package ru.practicum.shareit.requests.DTO;

import lombok.*;
import ru.practicum.shareit.item.DTO.ItemForRequestDTO;

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
