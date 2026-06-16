package ru.utmn.dayagunov.functional_modeling_of_systems.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.service.ReferenceDataService;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.migrant.CountryResponseDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.migrant.PurposeOfVisitResponseDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.mapper.ReferenceMapper;

import java.util.List;

@Tag(name = "Каталоги", description = "Просмотр доступных каталогов (Доступно всем пользователям)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReferenceController {
    private final ReferenceDataService referenceDataService;
    private final ReferenceMapper referenceMapper;

    @Operation(summary = "Возвращает список всех стран")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список стран",
                    content = @Content(array = @ArraySchema(
                            schema = @Schema(implementation = CountryResponseDto.class)))
            ),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @GetMapping("/countries")
    public ResponseEntity<List<CountryResponseDto>> getCountries() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(referenceDataService.getAllCountries().stream()
                        .map(referenceMapper::toCountryDto)
                        .toList());
    }

    @Operation(summary = "Возвращает список всех целей визита")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список целей визита",
                    content = @Content(array = @ArraySchema(
                            schema = @Schema(implementation = PurposeOfVisitResponseDto.class)))
            ),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @GetMapping("/purposes-of-visit")
    public ResponseEntity<List<PurposeOfVisitResponseDto>> getPurposesOfVisit() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(referenceDataService.getAllPurposesOfVisit().stream()
                        .map(referenceMapper::toPurposeOfVisitDto)
                        .toList());
    }
}