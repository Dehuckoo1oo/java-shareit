package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.DTO.ItemDTO;
import ru.practicum.shareit.item.comments.CommentDTO;

import java.util.List;

@RestController
@RequestMapping("/items")
@Slf4j
public class ItemAPIController {
    private final ItemService itemService;

    public ItemAPIController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/search")
    public List<ItemDTO> findItemsByText(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @RequestParam String text) {
        return itemService.findItemsByText(text, userId);
    }

    @PostMapping
    public ItemDTO create(@RequestHeader("X-Sharer-User-Id") Long userId,
                          @RequestBody ItemDTO itemDTO) {
        return itemService.create(itemDTO, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDTO update(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long itemId,
                          @RequestBody ItemDTO itemDTO) {
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
                                    @RequestBody CommentDTO commentDTO) {
        return itemService.createComment(commentDTO, userId, itemId);
    }
}
