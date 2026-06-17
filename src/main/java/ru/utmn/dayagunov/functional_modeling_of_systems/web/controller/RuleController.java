package ru.utmn.dayagunov.functional_modeling_of_systems.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Rule;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.service.RuleService;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.rule.CreateRuleRequestBodyDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.rule.RuleResponseDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.rule.UpdateRuleRequestBodyDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.mapper.RuleMapper;

import java.util.List;

@Tag(name = "Правила", description = "Создание правил с условиями и управление ими (Доступно ADMIN)")
@RestController
@RequestMapping("/admin/rules")
@RequiredArgsConstructor
public class RuleController {
    private final RuleService ruleService;
    private final RuleMapper ruleMapper;

    @Operation(summary = "Создаёт новое правило")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Правило успешно создано",
                    content = @Content(schema = @Schema(implementation = RuleResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @PostMapping
    public ResponseEntity<RuleResponseDto> create(
            @Valid @RequestBody CreateRuleRequestBodyDto body
    ) {
        Rule rule = ruleService.createRule(body);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ruleMapper.toResponseDto(rule));
    }

    @Operation(summary = "Возвращает правило по его id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Правило найдено",
                    content = @Content(schema = @Schema(implementation = RuleResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "Правило с указанным id не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<RuleResponseDto> get(@PathVariable Integer id) {
        Rule rule = ruleService.getRule(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ruleMapper.toResponseDto(rule));
    }

    @Operation(summary = "Возвращает список правил")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список правил (может быть пустым)",
                    content = @Content(array = @ArraySchema(
                            schema = @Schema(implementation = RuleResponseDto.class)))
            ),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<RuleResponseDto>> getRules(
            @Parameter(description = "Возвращать только действующие правила")
            @RequestParam(defaultValue = "true") boolean onlyActive
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ruleService.getRules(onlyActive).stream()
                        .map(ruleMapper::toResponseDto)
                        .toList());
    }

    @Operation(summary = "Обновляет правило по его id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Правило успешно обновлено",
                    content = @Content(schema = @Schema(implementation = RuleResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса", content = @Content),
            @ApiResponse(responseCode = "404", description = "Правило с указанным id не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @PatchMapping
    public ResponseEntity<RuleResponseDto> update(
            @Valid @RequestBody UpdateRuleRequestBodyDto body
    ) {
        Rule rule = ruleService.updateRule(body);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ruleMapper.toResponseDto(rule));
    }

    @Operation(summary = "Удаляет правило по его id",
            description = "Связанные условия также удаляются.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Правило успешно удалено", content = @Content),
            @ApiResponse(responseCode = "404", description = "Правило с указанным id не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        ruleService.deleteRule(id);
    }
}