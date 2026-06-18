package ru.utmn.dayagunov.functional_modeling_of_systems.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.user.OwnedByUserInterface;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.user.User;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.user.UserRoles;

@Component
@RequiredArgsConstructor
public class AccessGuard {
    private final UserService userService;

    public void checkOwnerOrAdmin(OwnedByUserInterface entity) {
        User currentUser = userService.getCurrentUser();

        if (UserRoles.ADMIN.equals(currentUser.getRole())) {
            return;
        }

        String currentLogin = currentUser.getLogin();
        String ownerLogin = entity.getUser().getLogin();

        if (!currentLogin.equals(ownerLogin)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Доступ к этому ресурсу запрещён."
            );
        }
    }
}