package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.groups.Create;
import ru.practicum.shareit.item.DTO.ItemDTO;
import ru.practicum.shareit.item.comments.CommentDTO;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @GetMapping("/search")
    public ResponseEntity<Object> findItemsByText(@RequestHeader("X-Sharer-User-Id") long userId,
                                                  @RequestParam String text) {
        return itemClient.findItemsByText(text, userId);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") long userId,
                                         @Validated(Create.class) @RequestBody ItemDTO itemDTO) {
        return itemClient.createItem(userId, itemDTO);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable Long itemId,
                                         @RequestBody ItemDTO itemDTO) {
        return itemClient.updateItem(userId, itemDTO, itemId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(@RequestHeader("X-Sharer-User-Id") long userId,
                                              @PathVariable Long itemId) {
        return itemClient.getItemById(userId, itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getItemsByUserId(@RequestHeader("X-Sharer-User-Id") long ownerId) {
        return itemClient.getItemsByUserId(ownerId);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@RequestHeader("X-Sharer-User-Id") long userId,
                                                @PathVariable Long itemId,
                                                @Validated(Create.class) @RequestBody CommentDTO commentDTO) {
        return itemClient.createComment(commentDTO, userId, itemId);
    }
}
