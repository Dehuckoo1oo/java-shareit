package ru.practicum.shareit.item.DTO;

import lombok.*;
import ru.practicum.shareit.groups.Create;
import ru.practicum.shareit.item.LastOrNextBooking;
import ru.practicum.shareit.item.comments.CommentDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDTO {

    private Long id;
    @NotBlank(groups = {Create.class})
    private String name;
    @NotBlank(groups = {Create.class})
    private String description;
    @NotNull(groups = {Create.class})
    private Boolean available;
    private LastOrNextBooking lastBooking;
    private LastOrNextBooking nextBooking;
    private List<CommentDTO> comments;
    private Long requestId;
}
