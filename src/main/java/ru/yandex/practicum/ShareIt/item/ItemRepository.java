package ru.yandex.practicum.ShareIt.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.ShareIt.requests.DTO.OwnedRequestDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findItemById(Long userId);

    List<Item> findItemsByRequestId(Long requestId);


}
