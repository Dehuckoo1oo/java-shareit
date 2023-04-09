package ru.practicum.shareit.item.DTO;

import lombok.*;
import ru.practicum.shareit.item.LastOrNextBooking;
import ru.practicum.shareit.item.comments.CommentDTO;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDTO {

    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private LastOrNextBooking lastBooking;
    private LastOrNextBooking nextBooking;
    private List<CommentDTO> comments;
    private Long requestId;
}
