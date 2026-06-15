package ru.utmn.dayagunov.functional_modeling_of_systems.infrastructure.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Condition;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Operators;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Rule;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.user.User;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.user.UserRoles;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.rule.RuleRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.user.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RuleRepository ruleRepository;

    @Override
    public void run(String... args) {
        seedUser("user", "userPass", UserRoles.USER);
        seedUser("admin", "adminPass", UserRoles.ADMIN);
        seedExampleRules();
    }

    private void seedUser(String login, String password, UserRoles role) {
        if (userRepository.findByLoginIgnoreCase(login).isPresent()) {
            log.info("Пользователь '{}' уже существует — пропуск", login);
            return;
        }
        User user = new User(login, passwordEncoder.encode(password));
        user.setRole(role);
        userRepository.save(user);
        log.info("Создан пользователь '{}' с ролью {}", login, role);
    }

    // Демонстрационные правила в актуальной модели (поле/оператор/значение).
    private void seedExampleRules() {
        if (ruleRepository.count() > 0) {
            log.info("Правила уже есть — генерация примеров пропущена");
            return;
        }

        ruleRepository.save(buildRule(
                "Медицинское освидетельствование",
                "Иностранным гражданам необходимо пройти медицинское освидетельствование в течение 30 дней со дня въезда.",
                "Результат медицинского освидетельствования",
                "Обратиться в уполномоченную медицинскую организацию",
                30, "hasMedicalExamination", "false"));

        ruleRepository.save(buildRule(
                "Полис ОМС/ДМС",
                "Для законного пребывания требуется действующий полис медицинского страхования.",
                "Полис обязательного или добровольного медицинского страхования",
                "Обратиться в любую страховую компанию по месту пребывания",
                30, "hasMedicalInsurance", "false"));

        log.info("Созданы демонстрационные правила: {} шт.", ruleRepository.count());
    }

    private Rule buildRule(
            String title,
            String description,
            String requiredResult,
            String requiredAction,
            int period,
            String field,
            String value
    ) {
        Rule rule = new Rule();
        rule.setTitle(title);
        rule.setDescription(description);
        rule.setRequiredResult(requiredResult);
        rule.setRequiredAction(requiredAction);
        rule.setPeriod(period);

        Condition condition = new Condition();
        condition.setField(field);
        condition.setOperator(Operators.EQ);
        condition.setValue(value);
        rule.addCondition(condition);

        return rule;
    }
}