package ru.yandex.practicum.ShareIt.requests;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.ShareIt.user.User;

import java.util.List;

@Repository
public interface RequestRepository extends PagingAndSortingRepository<Request,Long> {
    List<Request> findAllByUserId(Long userId, Sort sort);

    @Query("SELECT e FROM Request e WHERE e.user <> :value")
    Page<Request> findAllExcept(User value, Pageable pageable);

    @Query("SELECT e FROM Request e WHERE e.user <> :value")
    List<Request> findAllExcept(User value);
}
