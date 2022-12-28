package ru.yandex.practicum.ShareIt.user;

import java.util.List;


public interface UserService {

    public UserDTO create(UserDTO userDTO);

    public UserDTO findUserById(Long id);

    public User getUserById(Long id);

    public List<UserDTO> findAll();

    public UserDTO remove(Long id);

    public UserDTO update(UserDTO userDTO);
}
