# Functional Modeling of Systems

## Описание проекта

Этот проект представляет собой REST API для управления пользователями и дорожными картами. Реализованы основные
операции, такие как создание, получение, обновление и удаление пользователей, а также создание и получение дорожных
карт.

## Технологии

- **Java 17**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **Swagger (OpenAPI 3)**
- **Postgres**

## Запуск проекта

1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/GARFIELD-CAT/functional-modeling-of-systems
   ```
2. Перейдите в директорию проекта:
   ```bash
   cd functional-modeling-of-system
   ```
3. Запустите приложение:
   ```bash
   ./mvnw spring-boot:run
   ```

## Эндпоинты

### Пользователи

#### Создание нового пользователя

- **URL:** `/api/users`
- **Метод:** `POST`
- **Тело запроса:**
  ```json
  {
    "login": "string",
    "password": "string"
  }
  ```
- **Ответ:**
    - `201 Created` - пользователь успешно создан
    - `400 Bad Request` - некорректные данные
    - `409 Conflict` - пользователь с таким логином уже существует

#### Получение пользователя по ID

- **URL:** `/api/users/{id}`
- **Метод:** `GET`
- **Ответ:**
    - `200 OK` - пользователь найден
    - `404 Not Found` - пользователь не найден

#### Обновление пользователя по ID

- **URL:** `/api/users`
- **Метод:** `PUT`
- **Тело запроса:**
  ```json
    {
      "id": "integer",
      "login": "string",
      "password": "string",
      "checkInDate": "date",
      "plannedDurationOfStay": "integer",
      "purposeOfVisitId": "integer",
      "countryOfCitizenshipId": "integer",
      "healthInsurancePolicyAvailable": "boolean",
      "medicalExaminationResultAvailable": "boolean"
    }
  ```
- **Ответ:**
    - `200 OK` - пользователь успешно обновлен
    - `404 Not Found` - пользователь не найден

#### Удаление пользователя по ID

- **URL:** `/api/users/{id}`
- **Метод:** `DELETE`
- **Ответ:**
    - `204 No Content` - пользователь успешно удален
    - `404 Not Found` - пользователь не найден

### Дорожные карты

#### Создание новой дорожной карты

- **URL:** `/api/road-maps`
- **Метод:** `POST`
- **Тело запроса:**
  ```json
  {
    "user_id": "integer"
  }
  ```
- **Ответ:**
    - `201 Created` - дорожная карта успешно создана
    - `400 Bad Request` - некорректные данные запроса
    - `404 Not Found` - пользователь не найден

#### Получение дорожной карты по ID

- **URL:** `/api/road-maps/{id}`
- **Метод:** `GET`
- **Ответ:**
    - `200 OK` - дорожная карта успешно найдена
    - `404 Not Found` - дорожная карта не найдена

## Документация

Для доступа к документации API откройте [Swagger UI](http://localhost:8080/swagger-ui.html) после запуска приложения.
