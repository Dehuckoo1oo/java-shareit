package ru.yandex.practicum.ShareIt.item.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.ShareIt.item.Item;
import ru.yandex.practicum.ShareIt.item.ItemRepository;
import ru.yandex.practicum.ShareIt.user.User;
import ru.yandex.practicum.ShareIt.user.UserRepository;

@Component
public class CommentMapper {
    UserRepository userRepository;
    ItemRepository itemRepository;

    @Autowired
    public CommentMapper(UserRepository userRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public Comment mapDTOToEntity(CommentDTO commentDTO, Long userId, Long itemId) {
        User user = userRepository.findById(userId).orElse(null);
        Item item = itemRepository.findById(itemId).orElse(null);
        return new Comment(commentDTO.getId(), commentDTO.getText(), item, user);
    }

    public CommentDTO mapEntityToDTO(Comment comment) {
        return new CommentDTO(comment.getId(), comment.getText(), comment.getItem().getId(),
                comment.getAuthor().getName());
    }

}
