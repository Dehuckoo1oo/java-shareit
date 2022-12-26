package ru.yandex.practicum.ShareIt.item;

import java.util.List;
import java.util.Optional;

public interface ItemStorage {
    Item create(Item item);

    Optional<Item> getItemById(Long itemId);

    List<Item> getItemsByUserId(Long userId);

    Item remove(Item item);

    Item update(Item item);

    List<Item> getAllItems();
}
