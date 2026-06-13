package ru.utmn.dayagunov.functional_modeling_of_systems.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import ru.utmn.dayagunov.functional_modeling_of_systems.model.rule.Rule;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.rule.dto.CreateRuleRequestBodyDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.rule.dto.RuleResponseDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.rule.dto.UpdateRuleRequestBodyDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.service.RuleService;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
@RequiredArgsConstructor
public class RuleController {
    private final RuleService ruleService;

    @Operation(summary = "Создаёт новое правило")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Правило успешно создано",
                    content = @Content(schema = @Schema(implementation = RuleResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping
    public ResponseEntity<RuleResponseDto> create(
            @Valid @RequestBody CreateRuleRequestBodyDto body
    ) {
        Rule rule = ruleService.createRule(body);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ruleService.prepareRuleResponseDto(rule));
    }

    @Operation(summary = "Возвращает правило по его id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Правило найдено",
                    content = @Content(schema = @Schema(implementation = RuleResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "Правило с указанным id не найдено"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RuleResponseDto> get(@PathVariable Integer id) {
        Rule rule = ruleService.getRule(id);

        return  ResponseEntity.status(HttpStatus.OK)
                .body(ruleService.prepareRuleResponseDto(rule));
    }

    @Operation(summary = "Возвращает список правил")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список правил (может быть пустым)",
                    content = @Content(array = @ArraySchema(
                            schema = @Schema(implementation = RuleResponseDto.class)))
            ),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping
    public ResponseEntity<List<RuleResponseDto>> list(
            @Parameter(description = "Возвращать только действующие правила")
            @RequestParam(defaultValue = "true") boolean onlyActive
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ruleService.listRules(onlyActive).stream()
                .map(ruleService::prepareRuleResponseDto)
                .toList());
    }

    @Operation(summary = "Обновляет правило по его id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Правило успешно обновлено",
                    content = @Content(schema = @Schema(implementation = RuleResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "404", description = "Правило с указанным id не найдено"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PatchMapping
    public ResponseEntity<RuleResponseDto> update(
            @Valid @RequestBody UpdateRuleRequestBodyDto body
    ) {
        Rule rule = ruleService.updateRule(body);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ruleService.prepareRuleResponseDto(rule));
    }

    @Operation(summary = "Удаляет правило по его id",
            description = "Связанные условия также удаляются.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Правило успешно удалено"),
            @ApiResponse(responseCode = "404", description = "Правило с указанным id не найдено"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        ruleService.deleteRule(id);
    }
}