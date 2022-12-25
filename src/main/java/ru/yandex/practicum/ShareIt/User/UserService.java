package ru.yandex.practicum.ShareIt.User;

import java.util.List;


public interface UserService {

    public User create(User user);

    public User getUserById(Long id);

    public List<User> findAll();

    public User remove(Long id);

    public User update(User user);

}
