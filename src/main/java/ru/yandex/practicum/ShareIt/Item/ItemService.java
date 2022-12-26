package ru.yandex.practicum.ShareIt.Item;

import java.util.List;

public interface ItemService {

    public Item create(Item item, Long userId);

    public Item remove(Item item);

    public Item update(Item item, Long userId, Long itemId) ;

    public List<Item> getItemByUserId(Long ownerId);

    public Item getItemById(Long id);

    public List<Item> findItemsByText(String text);

}
