<h1>Тестовое задание для x5</h1>
<div>RestFull API, обеспечивающее полнофункциональное покрытие интерактивных действий с дендраграммой</div>
<h3>Стэк:</h3>
<li>Spring Boot, Security, Data</li>
<li>Liquibase, Postgres</li>
<li>Docker, Swagger, Postman</li>

<h3>Postman</h3>
<div>В корневой папке postman/ лежит коллекция</div>

<h3>Swagger</h3>
<div>В корневой папке swagger/ лежит openapi.json</div>

<h3>Инструкция запуска</h3>
<li>Из корня проекта запустить `./gradlew build` (jdk11)</li>
<li>Затем `docker-compose up --build`</li>
<li>После применения миграций в бд будут роли `ROLE_USER`, `ROLE_ADMIN`, 
юзер admin:admin с ролью`ROLE_ADMIN` 
через него можно добавлять/изменять/удалять других пользователей и роли</li>


<h3>Описание сущностей:</h3>
<li>users</li>
<li>roles</li>
<li>user_roles          many to many</li>
<li>cluster             кластеры</li>
<li>group_              группы</li>
<li>product             продукты</li>
<li>group_cluster       группа-кластер many to many</li>
<li>product_group       продукт-группа many to many</li>

<h3>Описание пакетов:</h3>
<li>com.sbelan.x5testproject.configuration     конфиги</li>
<li>com.sbelan.x5testproject.controller        контроллеры</li>
<li>com.sbelan.x5testproject.model             модели(включая сущности для jpa)</li>
<li>com.sbelan.x5testproject.repository        репозитории</li>
<li>com.sbelan.x5testproject.security          реализация oauth2</li>
<li>com.sbelan.x5testproject.service           сервисный слой</li>
