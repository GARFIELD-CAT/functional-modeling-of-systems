package ru.utmn.dayagunov.functional_modeling_of_systems.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.roadmap.RoadMap;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.service.RoadMapService;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.roadmap.RoadMapResponseDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.mapper.RoadMapMapper;

@Tag(name = "Дорожные карты", description = "Создание дорожных карт (Доступно USER и ADMIN)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/road-maps")
public class RoadMapController {
    private final RoadMapService roadMapService;
    private final RoadMapMapper roadMapMapper;

    @Operation(summary = "Создает новую дорожную карту", description = "Создает дорожную карту для текущего пользователя.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Дорожная карта успешно создана",
                    content = @Content(schema = @Schema(implementation = RoadMapResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @PostMapping
    public ResponseEntity<RoadMapResponseDto> showRoadMap() {
        RoadMap roadMap = roadMapService.createRoadMap();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roadMapMapper.toResponseDto(roadMap));
    }
}