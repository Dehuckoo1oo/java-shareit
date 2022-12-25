package ru.yandex.practicum.ShareIt.Item;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ItemStorage {
    Long id = 1L;
    private final Map<Long, Item> items = new HashMap<>();

    public Item create(Item item) {
        Long itemId = makeId();
        item.setId(itemId);
        items.put(itemId, item);
        return item;
    }

    public Optional<Item> getItemById(Long itemId) {
        if (items.containsKey(itemId)) {
            return Optional.of(items.get(itemId));
        } else {
            return Optional.empty();
        }
    }

    public List<Item> getItemsByUserId(Long userId) {
        return items.values().stream().filter(item -> item.getOwner().equals(userId)).collect(Collectors.toList());
    }

    public Item remove(Item item) {
        return items.remove(item.getId());
    }

    public Item update(Item item) {
        return items.put(item.getId(), item);
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(items.values());
    }


    private long makeId() {
        return id++;
    }
}
