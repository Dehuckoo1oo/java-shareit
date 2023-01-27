package ru.yandex.practicum.ShareIt.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.ShareIt.groups.Create;
import ru.yandex.practicum.ShareIt.groups.Update;
import ru.yandex.practicum.ShareIt.item.DTO.ItemDTO;
import ru.yandex.practicum.ShareIt.item.comments.CommentDTO;

import java.util.List;

@RestController
@RequestMapping("/items")
@Slf4j
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/search")
    public List<ItemDTO> findItemsByText(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @RequestParam String text) {
        return itemService.findItemsByText(text, userId);
    }

    @PostMapping
    public ItemDTO create(@RequestHeader("X-Sharer-User-Id") Long userId,
                          @Validated(Create.class) @RequestBody ItemDTO itemDTO) {
        return itemService.create(itemDTO, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDTO update(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long itemId,
                          @Validated(Update.class) @RequestBody ItemDTO itemDTO) {
        return itemService.update(itemDTO, userId, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemDTO getItemById(@RequestHeader("X-Sharer-User-Id") Long userId,
                               @PathVariable Long itemId) {
        return itemService.findItemById(userId, itemId);
    }

    @GetMapping
    public List<ItemDTO> getItemsByUserId(@RequestHeader("X-Sharer-User-Id") Long ownerId) {
        return itemService.getItemByUserId(ownerId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDTO createComment(@RequestHeader("X-Sharer-User-Id") Long userId,
                                    @PathVariable Long itemId,
                                    @Validated(Create.class) @RequestBody CommentDTO commentDTO) {
        return itemService.createComment(commentDTO, userId, itemId);
    }
}
