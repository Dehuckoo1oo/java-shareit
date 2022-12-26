package ru.yandex.practicum.ShareIt.user;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.ShareIt.exception.ConflictException;
import ru.yandex.practicum.ShareIt.exception.NoSuchBodyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        checkEmailByUser(userDTO.getEmail());
        User user = UserMapper.makeUserFromUserDTO(userDTO);
        return UserMapper.makeUserDTOFromUser(userStorage.create(user));
    }

    @Override
    public UserDTO findUserById(Long id) {
        return UserMapper.makeUserDTOFromUser(getUserById(id));
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
    public List<UserDTO> findAll() {
        List<User> users = userStorage.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();
        users.forEach(user -> usersDTO.add(UserMapper.makeUserDTOFromUser(user)));
        return usersDTO;
    }

    @Override
    public UserDTO remove(Long id) {
        return UserMapper.makeUserDTOFromUser(userStorage.remove(getUserById(id)));
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        checkEmailByUser(userDTO.getEmail());
        User user = getUserById(userDTO.getId());
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        return UserMapper.makeUserDTOFromUser(userStorage.update(user));
    }

    private void checkEmailByUser(String email) {
        if (userStorage.findUserByEmail(email).isPresent()) {
            throw new ConflictException(String.format("Пользователь с эмейлом %s уже сущетвует", email));
        }
    }
}
