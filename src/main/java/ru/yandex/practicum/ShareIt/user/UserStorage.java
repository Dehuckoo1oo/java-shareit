package ru.yandex.practicum.ShareIt.user;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    User create(User user);

    Optional<User> getUserById(Long id);

    List<User> findAll();

    User remove(User user);

    User update(User user);

    Optional<User> findUserByEmail(String email);
}
