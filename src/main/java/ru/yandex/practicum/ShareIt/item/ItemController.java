package ru.yandex.practicum.ShareIt.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.ShareIt.groups.Create;
import ru.yandex.practicum.ShareIt.groups.Update;

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
    public List<ItemDTO> findItemsByText(@RequestParam String text) {
        return itemService.findItemsByText(text);
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
    public ItemDTO getItemById(@PathVariable Long itemId) {
        return itemService.findItemById(itemId);
    }

    @GetMapping
    public List<ItemDTO> getItemsByUserId(@RequestHeader("X-Sharer-User-Id") Long ownerId) {
        return itemService.getItemByUserId(ownerId);
    }
}
