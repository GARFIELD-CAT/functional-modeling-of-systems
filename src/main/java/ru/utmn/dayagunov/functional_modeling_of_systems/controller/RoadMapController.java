package ru.utmn.dayagunov.functional_modeling_of_systems.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map.RoadMap;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.User;
import ru.utmn.dayagunov.functional_modeling_of_systems.service.RoadMapService;
import ru.utmn.dayagunov.functional_modeling_of_systems.service.UserService;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;


@RestController
@RequestMapping("/api/road-maps")
public class RoadMapController {
    private final RoadMapService roadMapService;
    private final UserService userService;

    public RoadMapController(RoadMapService roadMapService, UserService userService) {
        this.roadMapService = roadMapService;
        this.userService = userService;
    }

    @Operation(summary = "Создает новую дорожную карту", description = "Создает дорожную карту. Принимает на вход id пользователя.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Дорожная карта успешно создана",
                    content = @Content(schema = @Schema(implementation = RoadMap.class))
            ),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping
    public ResponseEntity<Object> createRoadMap(
            @Schema(requiredMode = REQUIRED, description = "Id пользователя")
            @RequestBody Integer user_id
    ) {
        User user = userService.getUser(user_id);
        RoadMap roadMap = roadMapService.createRoadMap(user);

        return new ResponseEntity<>(roadMap, HttpStatus.CREATED);
    }

    @Operation(summary = "Возвращает дорожную карту по ее id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Дорожная карта успешно найдена",
                    content = @Content(schema = @Schema(implementation = RoadMap.class))
            ),
            @ApiResponse(responseCode = "404", description = "Дорожная карта не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getRoadMap(
            @PathVariable("id") Integer id
    ) {
        return new ResponseEntity<>(roadMapService.getRoadMap(id), HttpStatus.OK);
    }

}
