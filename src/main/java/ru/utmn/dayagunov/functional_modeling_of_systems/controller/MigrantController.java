package ru.utmn.dayagunov.functional_modeling_of_systems.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.migrant.dto.CreateMigrantRequestBodyDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.migrant.dto.UpdateMigrantRequestBodyDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.migrant.Migrant;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.migrant.dto.MigrantResponseDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.service.MigrantService;


@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/migrants")
public class MigrantController {
    private final MigrantService migrantService;

    @Operation(summary = "Создает новый профиль мигранта")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Профиль мигранта успешно создан",
                    content = @Content(schema = @Schema(implementation = MigrantResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "409", description = "Профиль мигранта для текущего пользователя уже существует"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping
    public ResponseEntity<Object> createMigrant(
            @Valid @RequestBody CreateMigrantRequestBodyDto body
    ) {
        Migrant migrant = migrantService.createMigrant(body);

        return new ResponseEntity<>(migrantService.prepareMigrantResponseDto(migrant), HttpStatus.CREATED);
    }

    @Operation(summary = "Возвращает профиль мигранта по его id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Профиль мигранта успешно найден",
                    content = @Content(schema = @Schema(implementation = MigrantResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "Профиль мигранта не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getMigrant(
            @PathVariable("id") Integer id
    ) {
        Migrant migrant = migrantService.getMigrant(id);

        return new ResponseEntity<>(migrantService.prepareMigrantResponseDto(migrant), HttpStatus.OK);
    }

    @Operation(summary = "Обновляет профиль мигранта по его id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Профиль мигранта успешно обновлен",
                    content = @Content(schema = @Schema(implementation = MigrantResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "Профиль мигранта не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PatchMapping
    public ResponseEntity<Object> updateMigrant(
            @Valid @RequestBody UpdateMigrantRequestBodyDto body
    ) {
        Migrant migrant = migrantService.updateMigrant(body);

        return new ResponseEntity<>(migrantService.prepareMigrantResponseDto(migrant), HttpStatus.OK);
    }

    @Operation(summary = "Удаляет профиль мигранта по его id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Профиль мигранта успешно удален"
            ),
            @ApiResponse(responseCode = "404", description = "Профиль мигранта не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMigrant(
            @PathVariable("id") Integer id
    ) {
        migrantService.deleteMigrant(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
