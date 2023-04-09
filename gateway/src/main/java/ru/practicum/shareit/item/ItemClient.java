package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.DTO.ItemDTO;
import ru.practicum.shareit.item.comments.CommentDTO;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> findItemsByText(String text, Long userId) {
        Map<String, Object> parameters = Map.of(
                "text", text
        );
        return get("/search?text={text}", userId, parameters);
    }

    public ResponseEntity<Object> createItem(long userId, ItemDTO itemDTO) {
        return post("", userId, itemDTO);
    }

    public ResponseEntity<Object> updateItem(long userId, ItemDTO itemDTO, long itemId) {
        return patch("/" + itemId, userId, itemDTO);
    }

    public ResponseEntity<Object> getItemById(long userId, long itemId) {
        return get("/" + itemId, userId);
    }

    public ResponseEntity<Object> getItemsByUserId(Long ownerId) {
        return get("", ownerId);
    }

    public ResponseEntity<Object> createComment(CommentDTO commentDTO, Long userId, long itemId) {
        return post("/" + itemId + "/comment", userId, commentDTO);
    }

}
