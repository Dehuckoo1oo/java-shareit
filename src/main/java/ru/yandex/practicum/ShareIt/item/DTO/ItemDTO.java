package ru.yandex.practicum.ShareIt.item.DTO;

import lombok.*;
import ru.yandex.practicum.ShareIt.groups.Create;
import ru.yandex.practicum.ShareIt.item.LastOrNextBooking;
import ru.yandex.practicum.ShareIt.item.comments.CommentDTO;

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
