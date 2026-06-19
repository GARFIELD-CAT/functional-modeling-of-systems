Понял — это системные диаграммы последовательностей (SSD): Система как единый чёрный ящик, только сообщения «действующее лицо ↔ Система», имена операций без внутренних классов. По одной на прецедент, основной сценарий + существенные альтернативы из твоих спецификаций.

**Регистрация** (осн. + «логин занят» 3a + валидация):

```plantuml
@startuml SSD_Registration
actor "Иностранный гражданин" as Citizen
participant "Система" as System

Citizen -> System : createUser(login, password)
alt Логин свободен, данные корректны
    System --> Citizen : u: User
else Логин пользователя уже занят
    System --> Citizen : Сообщение: "Пользователь с таким login уже существует"
else Ошибка валидации полей
    System --> Citizen : Сообщение об ошибках заполнения формы
end
@enduml
```

**Внесение данных о себе** (осн. + проверка корректности):

```plantuml
@startuml SSD_MigrantData
actor "Иностранный гражданин" as Citizen
participant "Система" as System

Citizen -> System : createMigrant(migrantData)
note right of Citizen
  migrantData:
  • страна гражданства, дата въезда,
    срок пребывания, цель въезда
  • статусы (Да/Нет): ВКС, дактилоскопия,
    фотографирование, мигр. учёт, полис ОМС/ДМС,
    мед. освидетельствование, сертификат о языке,
    советский аттестат, диплом РФ, ИНН
end note
alt У пользователя нет профиля мигранта, данные корректны
    System --> Citizen : m: Migrant
else У пользователя уже есть профиль мигранта
    System --> Citizen : Сообщение: "Профиль мигранта для текущего пользователя уже существует"
else Ошибка валидации полей
    System --> Citizen : Сообщение об ошибках заполнения формы
end
@enduml
```

**Просмотр дорожной карты** (осн. + «нет подходящих правил» 3a + «просрочка» 4a):

```plantuml
@startuml SSD_RoadMap
actor "Иностранный гражданин" as Citizen
participant "Система" as System

Citizen -> System : showRoadMap()
alt Найдены подходящие правила
    alt Все сроки актуальны
        System --> Citizen : rm: RoadMap
    else Есть просроченные сроки
        System --> Citizen : rm: RoadMap (с пометкой в просроченных шагах: \n"Вы пропустили сроки получения необходимых документов. Возможны штрафы")
    end
else Ни одно правило не подошло
    System --> Citizen : rm: RoadMap (с одим шагом: "Невозможно сформировать дорожную карту")
end
@enduml
```

**Управление правилами** (осн.: список + сохранение + «ошибка валидации» 6a):

```plantuml
@startuml SSD_ManageRules_all
actor "Оператор" as Operator
participant "Система" as System

Operator -> System : getRules()
System --> Operator : Список r: Rule (список всех правил)

alt Редактирование правила
    Operator -> System : getRule(rule_id)
    System --> Operator : r: Rule
    Operator -> System : updateRule(ruleData)
    alt Данные корректны
       System --> Operator : r: Rule
    else Ошибка валидации полей
        System --> Operator : Сообщение об ошибках заполнения формы
    end
else Создание правила
    Operator -> System : createRule(ruleData)
    alt Данные корректны
       System --> Operator : r: Rule
    else Ошибка валидации полей
        System --> Operator : Сообщение об ошибках заполнения формы
    end
end
@enduml
```

```plantuml
@startuml SSD_ManageRules_create
actor "Оператор" as Operator
participant "Система" as System

Operator -> System : getRules()
System --> Operator : Список r: Rule (список всех правил)

Operator -> System : createRule(ruleData)
alt Данные корректны
   System --> Operator : r: Rule
else Ошибка валидации полей
   System --> Operator : Сообщение об ошибках заполнения формы
end
@enduml
```

```plantuml
@startuml SSD_ManageRules_create
actor "Оператор" as Operator
participant "Система" as System

Operator -> System : getRules()
System --> Operator : Список r: Rule (список всех правил)

Operator -> System : createRule(ruleData)
alt Данные корректны
   System --> Operator : r: Rule
else Ошибка валидации полей
   System --> Operator : Сообщение об ошибках заполнения формы
end
@enduml
```

```plantuml
@startuml SSD_ManageRules_update
actor "Оператор" as Operator
participant "Система" as System

Operator -> System : getRules()
System --> Operator : Список r: Rule (список всех правил)

Operator -> System : getRule(rule_id)
System --> Operator : r: Rule
Operator -> System : updateRule(ruleData)
alt Данные корректны
   System --> Operator : r: Rule
else Ошибка валидации полей
    System --> Operator : Сообщение об ошибках заполнения формы
end
@enduml
```

```plantuml
@startuml SSD_ManageRules_delete
actor "Оператор" as Operator
participant "Система" as System

Operator -> System : getRules()
System --> Operator : Список r: Rule (список всех правил)

Operator -> System : getRule(rule_id)
System --> Operator : r: Rule
Operator -> System : deleteRule(rule_id)
alt Данные корректны
    System --> Operator : Сообщение об успешном удалении
else Ошибка валидации полей
    System --> Operator : Сообщение об ошибках заполнения формы
end
@enduml
```
