# Курсовой проект "Сервис перевода денег"

## Описание проекта

Приложение - REST-сервис для перевода денег с одной карты на другую по спецификации описанной в MoneyTransferServiceSpecification.yaml. Приложение состоит из веб-приложения (FRONT) которое подключается к сервису (BACK) и использует его функционал для перевода денег.

## Требования к приложению

- Сервис предоставлят REST интерфейс для интеграции с FRONT
- Сервис реализовывает все методы перевода с одной банковской карты на другую описанные в протоколе MoneyTransferServiceSpecification.yaml
- Все операции записываются в файл (лог переводов с указанием даты, времени, карта с которой было списание, карта зачисления, сумма, комиссия, результат операции)

## Реализованные требования

- Приложение разработано с использованием Spring Boot
- Использован сборщик пакетов gradle/maven
- Для запуска используется docker, docker-compose
- Код размещен на github
- Код покрыт unit тестами с использованием mockito
- Добавлены интеграционные тесты с использованием testcontainers

## Описание  интеграции с FRONT
- Приложение развертывается в 3 контейнерах: BACKEND, FRONTEND и прокси сервер NGINX 
- После запуска контейнеров сервис становится доступным по адресу http://localhost:5500
- Взаимодейтвие с FRONT происходит через прокси по адресу http://localhost:5500/card-transfer
- Взаимодействие с BACK через прокси по адресу http://localhost:5500/transfer и http://localhost:5500/confirmOperation
