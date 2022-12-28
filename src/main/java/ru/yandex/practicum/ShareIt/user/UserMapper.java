package ru.yandex.practicum.ShareIt.user;

public class UserMapper {
    public static UserDTO makeUserDTOFromUser(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }

    public static User makeUserFromUserDTO(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail());
    }
}
