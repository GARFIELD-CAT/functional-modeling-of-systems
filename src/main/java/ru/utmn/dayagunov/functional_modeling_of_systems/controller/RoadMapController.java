package ru.utmn.dayagunov.functional_modeling_of_systems.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map.RoadMap;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map.dto.RoadMapResponseDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.service.RoadMapService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/road-maps")
public class RoadMapController {
    private final RoadMapService roadMapService;

    @Operation(summary = "Создает новую дорожную карту", description = "Создает дорожную карту для текущего пользователя.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Дорожная карта успешно создана",
                    content = @Content(schema = @Schema(implementation = RoadMapResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping
    public ResponseEntity<RoadMapResponseDto> showRoadMap() {
        RoadMap roadMap = roadMapService.createRoadMap();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roadMapService.prepareRoadMapResponseDto(roadMap));
    }

    @Operation(summary = "Возвращает дорожную карту по ее id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Дорожная карта успешно найдена",
                    content = @Content(schema = @Schema(implementation = RoadMapResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "Дорожная карта не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RoadMapResponseDto> getRoadMap(
            @PathVariable("id") Integer id
    ) {
        RoadMap roadMap = roadMapService.getRoadMap(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(roadMapService.prepareRoadMapResponseDto(roadMap));
    }
}
