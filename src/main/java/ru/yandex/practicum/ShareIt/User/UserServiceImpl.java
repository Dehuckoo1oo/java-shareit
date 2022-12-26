package ru.yandex.practicum.ShareIt.User;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.ShareIt.exception.ConflictException;
import ru.yandex.practicum.ShareIt.exception.NoSuchBodyException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserStorage userStorage;

    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public User create(User user) {
        checkEmailByUser(user.getEmail());
        return userStorage.create(user);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> user = userStorage.getUserById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NoSuchBodyException(String.format("Пользователь с id %s отсутствует", id));
        }

    }

    @Override
    public List<User> findAll() {
        return userStorage.findAll();
    }

    @Override
    public User remove(Long id) {
        return userStorage.remove(getUserById(id));
    }

    @Override
    public User update(User newUser) {
        checkEmailByUser(newUser.getEmail());
        User user = getUserById(newUser.getId());
        if(newUser.getEmail() != null){
            user.setEmail(newUser.getEmail());
        }
        if(newUser.getName() != null){
            user.setName(newUser.getName());
        }
        return userStorage.update(user);
    }

    private void checkEmailByUser(String email){
        if(userStorage.findUserByEmail(email).isPresent()){
            throw new ConflictException(String.format("Пользователь с эмейлом %s уже сущетвует",email));
        }
    }
}
