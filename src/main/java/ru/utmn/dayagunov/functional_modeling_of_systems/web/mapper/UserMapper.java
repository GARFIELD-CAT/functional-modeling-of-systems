package ru.utmn.dayagunov.functional_modeling_of_systems.web.mapper;

import org.springframework.stereotype.Component;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.user.User;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.user.UserResponseDto;

@Component
public class UserMapper {

    public UserResponseDto toResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setLogin(user.getLogin());

        return dto;
    }
}
