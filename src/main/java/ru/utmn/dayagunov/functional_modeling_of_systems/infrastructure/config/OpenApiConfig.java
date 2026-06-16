package ru.utmn.dayagunov.functional_modeling_of_systems.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI migrantHelperOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API системы помощи мигрантам")
                        .description("""
                                REST API для формирования персональной дорожной карты мигранта.
                                
                                Администратор задаёт правила с условиями, пользователь заполняет
                                профиль мигранта и получает список шагов с дедлайнами.
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Команда 12")
                                .email("dayagunov@example.ru"))
                        .license(new License()
                                .name("Учебный проект ТюмГУ")));
    }
}