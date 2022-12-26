package ru.yandex.practicum.ShareIt.Item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@Slf4j
public class ItemController {
    ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/search")
    public List<Item> findItemsByText(@RequestParam String text){
        return itemService.findItemsByText(text);
    }

    @PostMapping
    public Item create(@RequestHeader("X-Sharer-User-Id") Long userId,
                       @Validated(Item.New.class) @RequestBody Item item) {
        return itemService.create(item,userId);
    }

    @PatchMapping("/{itemId}")
    public Item update(@RequestHeader("X-Sharer-User-Id") Long userId,@PathVariable Long itemId,
                       @Validated(Item.Update.class) @RequestBody Item item) {
        return itemService.update(item,userId,itemId);
    }

    @GetMapping("/{itemId}")
    public Item getItemById(@PathVariable Long itemId){
        return itemService.getItemById(itemId);
    }

    @GetMapping
    public List<Item> getItemsByUserId(@RequestHeader("X-Sharer-User-Id") Long ownerId){
        return itemService.getItemByUserId(ownerId);
    }
}
