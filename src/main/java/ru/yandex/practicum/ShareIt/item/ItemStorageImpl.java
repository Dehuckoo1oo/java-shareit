package ru.yandex.practicum.ShareIt.item;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ItemStorageImpl implements ItemStorage {
    private Long id = 1L;
    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public Item create(Item item) {
        Long itemId = makeId();
        item.setId(itemId);
        items.put(itemId, item);
        return item;
    }

    @Override
    public Optional<Item> getItemById(Long itemId) {
        if (items.containsKey(itemId)) {
            return Optional.of(items.get(itemId));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Item> getItemsByUserId(Long userId) {
        return items.values().stream().filter(item -> item.getOwner().getId().equals(userId)).collect(Collectors.toList());
    }

    @Override
    public Item remove(Item item) {
        return items.remove(item.getId());
    }

    @Override
    public Item update(Item item) {
        return items.put(item.getId(), item);
    }

    @Override
    public List<Item> getAllItems() {
        return new ArrayList<>(items.values());
    }


    private long makeId() {
        return id++;
    }
}
