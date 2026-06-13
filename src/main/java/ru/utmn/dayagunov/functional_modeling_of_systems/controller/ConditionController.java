package ru.utmn.dayagunov.functional_modeling_of_systems.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.condition.Condition;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.condition.dto.ConditionResponseDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.condition.dto.CreateConditionRequestBodyDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.condition.dto.UpdateConditionRequestBodyDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.service.ConditionService;

import java.util.List;

@RestController
@RequestMapping("/api/conditions")
@RequiredArgsConstructor
public class ConditionController {
    private final ConditionService conditionService;

    @Operation(summary = "Создаёт новое условие для правила")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Условие успешно создано",
                    content = @Content(schema = @Schema(implementation = ConditionResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "404", description = "Правило с указанным id не найдено"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping
    public ResponseEntity<ConditionResponseDto> create(
            @Valid @RequestBody CreateConditionRequestBodyDto body
    ) {
        Condition condition = conditionService.createCondition(body);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(conditionService.prepareConditionResponseDto(condition));
    }

    @Operation(summary = "Возвращает условие по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Условие найдено",
                    content = @Content(schema = @Schema(implementation = ConditionResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "Условие с указанным id не найдено"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ConditionResponseDto> get(@PathVariable Integer id) {
        Condition condition = conditionService.getCondition(id);

        return  ResponseEntity.status(HttpStatus.OK)
                .body(conditionService.prepareConditionResponseDto(condition));
    }

    @Operation(summary = "Возвращает список из всех условий для указанного правила")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список условий (может быть пустым)",
                    content = @Content(array = @ArraySchema(
                            schema = @Schema(implementation = ConditionResponseDto.class)))
            ),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "404", description = "Правило с указанным id не найдено"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping
    public ResponseEntity<List<ConditionResponseDto>> listByRule(@RequestParam Integer ruleId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(conditionService.getConditionsByRuleId(ruleId).stream()
                .map(conditionService::prepareConditionResponseDto)
                .toList());
    }

    @Operation(summary = "Обновляет условие по его id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Условие успешно обновлено",
                    content = @Content(schema = @Schema(implementation = ConditionResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "404", description = "Условие или правило не найдено"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PatchMapping
    public ResponseEntity<ConditionResponseDto> update(
            @Valid @RequestBody UpdateConditionRequestBodyDto body
    ) {
        Condition condition = conditionService.updateCondition(body);

        return ResponseEntity.status(HttpStatus.OK)
                .body(conditionService.prepareConditionResponseDto(condition));
    }

    @Operation(summary = "Удаляет условие по его id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Условие успешно удалено"),
            @ApiResponse(responseCode = "404", description = "Условие с указанным id не найдено"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        conditionService.deleteCondition(id);
    }
}