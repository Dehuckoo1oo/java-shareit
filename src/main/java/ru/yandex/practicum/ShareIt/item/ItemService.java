package ru.yandex.practicum.ShareIt.item;

import java.util.List;

public interface ItemService {

    public ItemDTO create(ItemDTO itemDTO, Long userId);

    public ItemDTO remove(ItemDTO itemDTO, Long owner);

    public ItemDTO update(ItemDTO itemDTO, Long userId, Long itemId);

    public List<ItemDTO> getItemByUserId(Long ownerId);

    public ItemDTO findItemById(Long id);

    public List<ItemDTO> findItemsByText(String text);

}
