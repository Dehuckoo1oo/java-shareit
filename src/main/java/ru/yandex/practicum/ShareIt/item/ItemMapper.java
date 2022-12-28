package ru.yandex.practicum.ShareIt.item;

public class ItemMapper {
    public static ItemDTO makeItemDtoFromItem(Item item) {
        return new ItemDTO(item.getId(), item.getName(), item.getDescription(), item.getAvailable());
    }

    public static Item makeItemFromItemDTO(ItemDTO itemDTO, Long owner) {
        return new Item(itemDTO.getId(), itemDTO.getName(), itemDTO.getDescription(), owner, itemDTO.getAvailable());
    }

}
