# Диаграммы последовательностей (взаимодействие объектов)
### Система помощи мигрантам

Проектные диаграммы последовательностей — по одной на каждую системную функцию из SSD.
Система раскрыта на реальные объекты (контроллеры, сервисы, репозитории, доменные классы,
мапперы). Нотация PlantUML (рендер: plantuml.com / плагин IDE).

**Условные обозначения:** `alt/else` — взаимоисключающие исходы; `loop` — повторение;
`opt` — необязательная ветка; самосообщение объекта — внутреннее действие. Полосы активации
опущены для надёжного рендеринга; при необходимости добавляются.

---

## Прецедент «Регистрация» → createUser

```plantuml
@startuml SD_createUser
actor "Иностранный гражданин" as Citizen
participant "UserController" as UC
participant "UserService" as US
participant "UserRepository" as UR
participant "PasswordEncoder" as PE
participant "UserMapper" as UM

Citizen -> UC ++ : createUser(dto: CreateUserRequestBodyDto)
alt Ошибка валидации полей
    UC --> Citizen : Сообщение об ошибках \nзаполнения формы
else Данные корректны
    UC -> US ++ : createUser(login, password)
    US -> UR ++ : findByLoginIgnoreCase(login)
    UR --> US -- : Optional<u: User>
    alt Логин уже занят
        US --> UC : ResponseStatusException(409)
        UC --> Citizen : Сообщение: "Пользователь с \nтаким login уже существует"
    else Логин свободен
        US -> PE ++ : encode(password)
        PE --> US -- : passwordHash
        create "u: User" as User
        US -> User : <<create>>
        US -> UR : save(u: User)
        US --> UC -- : u: User
        UC -> UM ++ : toResponseDto(u: User)
        UM --> UC -- : out: UserResponseDto
        UC --> Citizen -- : out: UserResponseDto
    end
end
@enduml
```

---

## Прецедент «Внесение данных о себе» → createMigrant

```plantuml
@startuml SD_createMigrant
actor "Иностранный гражданин" as Citizen
participant "MigrantController" as MC
participant "MigrantService" as MS
participant "UserService" as US
participant "UserRepository" as UR
participant "Migrant\nRepository" as MR
participant "Country\nRepository" as CR
participant "Purpose\nOfVisitRepository" as PR
participant "Migrant\nMapper" as MM

Citizen -> MC ++ : createMigrant(\n  dto: CreateMigrantRequestBodyDto\n)
alt Ошибка валидации полей
    MC --> Citizen :  Сообщение об ошибках \nзаполнения формы
else Данные корректны
    MC -> MS ++ : createMigrant(\n  dto: CreateMigrantRequestBodyDto\n)
    MS -> US ++ : getCurrentUser()
    US -> US : getCurrentUserLogin()
    US -> UR ++ : findByLoginIgnoreCase(login)
    UR --> US -- : Optional<u: User>
    US --> MS -- : u: User
    MS -> MR ++ : findByUserLogin(login)
    MR --> MS -- : Optional<m: Migrant>
    alt У пользователя уже есть профиль мигранта
        MS --> MC : ResponseStatusException(409)
        MC --> Citizen : Сообщение: "Профиль мигранта \nдля текущего пользователя уже существует"
    else У пользователя нет профиля мигранта
        MS -> CR ++ : findById(country_id)
        CR --> MS -- : Optional<c: Country>
        MS -> PR ++ : findById(purpose_id)
        PR --> MS -- : Optional<p: PurposeOfVisit>
        alt Сущность не найдена
            MS --> MC : ResponseStatusException(404)
            MC --> Citizen : Сообщение:  "Сущности с id не существует"
        else Все сущности найдены
            create "m: Migrant" as Migrant
            MS -> Migrant: <<create>>
            MS -> MR : save(m: Migrant)
            MS --> MC -- : m: Migrant
            MC -> MM ++ : toResponseDto(m: Migrant)
            MM --> MC -- : out: MigrantResponseDto
            MC --> Citizen -- : out: MigrantResponseDto
        end
    end
end
@enduml
```

---

## Прецедент «Просмотр дорожной карты» → showRoadMap

```plantuml
@startuml SD_showRoadMap
actor "Иностранный гражданин" as Citizen
participant "RoadMap\nController" as RC
participant "RoadMap\nService" as RS
participant "Migrant\nService" as MS
participant "User\nService" as US
participant "Migrant\nRepository" as MR
participant "Rule\nRepository" as RR
participant "r:\nRule" as RULE
participant "с:\nCondition" as CON
participant "RoadMap\nMapper" as RM

Citizen -> RC ++ : showRoadMap()
RC -> RS ++ : createRoadMap()
RS -> MS ++ : getCurrentMigrant()
MS -> US ++ : getCurrentUserLogin()
US -> MS -- : userLogin
MS -> MR ++ : findByUserLogin(userLogin)
MR -> MS -- : Optional<m: Migrant>
alt Профиль мигранта не найден
    MS --> RS : ResponseStatusException(404)
    RS --> RC : ResponseStatusException(404)
    RC --> Citizen : Сообщение: "Профиль мигранта\n для текущего пользователя не найден"
else Профиль мигранта найден
    MS --> RS -- : m: Migrant
    create "rm: RoadMap" as RoadMap
    RS -> RoadMap: <<create>>
    RS -> RR ++ : findEffectiveOn(today)
    RR --> RS -- : List<r: Rule>
    loop По каждому правилу
        RS -> RULE ++ : matches(m: Migrant)
        loop По условиям правила
            RULE -> CON ++: matches(m: Migrant)
            CON --> RULE --: true / false
        end
        RULE --> RS : Подходит ли правило (true / false)
        opt Правило подходит
            RS -> RULE : calculateDeadline(m: Migrant)
            RULE --> RS -- : deadline
            RS -> RS : generateMessage(r: Rule, deadline)
            note right of RS : Если deadline просрочен,\nв сообщение \nдобавляется предупреждение
            create "s: Step" as Step
            RS -> Step: <<create>>
            note right of RS : Производится дедупликация шагов
        end
    end
    opt Ни одно правило не подошло
        RS -> RS : Создаем Step("Невозможно \nсформировать\nдорожную карту")
    end
    RS --> RoadMap : setSteps(List<s: Step>)
    RS --> RC -- : rm: RoadMap
    RC -> RM ++ : toResponseDto(rm: RoadMap)
    RM --> RC --: out: RoadMapResponseDto
    RC --> Citizen -- : out: RoadMapResponseDto
end
@enduml
```

---

## Прецедент «Управление правилами»

### getRules — список правил

```plantuml
@startuml SD_getRules
actor "Оператор" as Operator
participant "RuleController" as RC
participant "RuleService" as RS
participant "RuleRepository" as RR
participant "RuleMapper" as RM

Operator -> RC ++ : getRules(onlyActive)
RC -> RS ++ : getRules(onlyActive)
alt onlyActive = true
    RS -> RR ++: findEffectiveOn(today)
else onlyActive = false
    RS -> RR : findAllWithConditions()
end
RR --> RS --: List<r: Rule>
RS --> RC --: List<r: Rule>
loop По каждому правилу
    RC -> RM ++: toResponseDto(r: Rule)
    RM --> RC --: out: RuleResponseDto
end
RC --> Operator --: List<out: RuleResponseDto>
@enduml
```

### getRule — одно правило

```plantuml
@startuml SD_getRule
actor "Оператор" as Operator
participant "RuleController" as RC
participant "RuleService" as RS
participant "RuleRepository" as RR
participant "RuleMapper" as RM

Operator -> RC ++: getRule(id)
RC -> RS ++: getRule(id)
RS -> RR ++: findById(id)
RR --> RS --: Optional<r: Rule>
alt Правило не найдено
    RS --> RC : ResponseStatusException(404)
    RC --> Operator : Сообщение: "Правило с id не найдено"
else Правило найдено
    RS --> RC --: r: Rule
    RC -> RM ++: toResponseDto(r: Rule)
    RM --> RC --: out: RuleResponseDto
    RC --> Operator --: out: RuleResponseDto
end
@enduml
```

### createRule — создание правила с условиями

```plantuml
@startuml SD_createRule
actor "Оператор" as Operator
participant "RuleController" as RC
participant "RuleService" as RS
participant "r: Rule" as RULE
participant "RuleRepository" as RR
participant "RuleMapper" as RM

Operator -> RC ++: createRule(dto: CreateRuleRequestBodyDto)
alt Ошибка валидации полей
    RC --> Operator : Сообщение об ошибках \nзаполнения формы
else Данные корректны
    RC -> RS ++: createRule(dto: CreateRule\nRequestBodyDto)
    RS -> RULE: <<create>>
    RS -> RS : buildConditions(r_dto: RuleConditionDto)
    create "c: Condition" as Condition
    loop Для каждого условия
       RS -> Condition: <<create>>
    end
    RS -> RULE: replaceConditions(List<c: Condition>)
    loop для каждого условия
        RULE -> RULE: addCondition(c: Condition)
    end
    RS -> RR : save(r: Rule)
    RS --> RC --: r: Rule
    RC -> RM ++: toResponseDto(r: Rule)
    RM --> RC --: out: RuleResponseDto
    RC --> Operator --: out: RuleResponseDto
end
@enduml
```

### updateRule — редактирование правила

```plantuml
@startuml SD_updateRule
actor "Оператор" as Operator
participant "RuleController" as RC
participant "RuleService" as RS
participant "r: Rule" as RULE
participant "RuleRepository" as RR
participant "RuleMapper" as RM

Operator -> RC ++: updateRule(dto: UpdateRule\nRequestBodyDto)
alt Ошибка валидации полей
    RC --> Operator : Сообщение об ошибках \nзаполнения формы
else Данные корректны
    RC -> RS ++: updateRule(dto: UpdateRule\nRequestBodyDto)
    RS -> RR ++: findById(rule_id)
    RR --> RS --: Optional<r: Rule>
    alt Правило не найдено
        RS --> RC : ResponseStatusException(404)
        RC --> Operator : Сообщение: "Правило с id не найдено"
    else Правило найдено
        opt Переданы условия
           RS -> RS : buildConditions(r_dto: RuleConditionDto)
            create "c: Condition" as Condition
           loop Для каждого условия
              RS -> Condition: <<create>>
           end
           RS -> RULE: replaceConditions(List<c: Condition>)
           loop для каждого условия
              RULE -> RULE: addCondition(c: Condition)
           end
        end
        RS -> RR : save(r: Rule)
        RS --> RC --: r: Rule
        RC -> RM ++: toResponseDto(r: Rule)
        RM --> RC --: out: RuleResponseDto
        RC --> Operator -- : out: RuleResponseDto
    end
end
@enduml
```

### deleteRule — удаление правила

```plantuml
@startuml SD_deleteRule
actor "Оператор" as Operator
participant "RuleController" as RC
participant "RuleService" as RS
participant "RuleRepository" as RR

Operator -> RC ++: deleteRule(rule_id)
RC -> RS ++: deleteRule(rule_id)
RS -> RR ++: findById(rule_id)
RR --> RS --: Optional<r: Rule>
alt Правило не найдено
    RS --> RC : ResponseStatusException(404)
    RC --> Operator : Сообщение: "Правило с id не найдено"
else Правило найдено
    RS -> RR --: delete(r: Rule)
    note right of RR : связанные условия (Condition) удаляются\nкаскадно
    RC --> Operator --: Правило удалено (204 статус)
end
@enduml
```
