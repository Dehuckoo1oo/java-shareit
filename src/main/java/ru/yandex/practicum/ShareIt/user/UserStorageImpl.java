package ru.yandex.practicum.ShareIt.user;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserStorageImpl implements UserStorage {
    private Long id = 1L;
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User create(User user) {
        Long userId = makeId();
        user.setId(userId);
        users.put(userId, user);
        return user;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        if (users.containsKey(id)) {
            return Optional.of(users.get(id));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User remove(User user) {
        return users.remove(user.getId());
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return users.values().stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }

    private Long makeId() {
        return id++;
    }
}
