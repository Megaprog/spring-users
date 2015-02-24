# spring-users

Users list and form for edit. Using Spring Boot, Spring Security, Thymeleaf, JPA, H2

Веб-приложение со следующим функционалом:

- Страница логина. поля: email, пароль (минимум 6 символов, должна быть цифра и буква в верхнем регистре)
- После того как пользователь успешно залогинится он попадает на страницу со списком пользователей системы, список состоит из:
    - email
    - имя
    - роль в системе (user, editor)
    - ссылка на страницу редактирования пользователя (ссылка показывается только если текущий пользователь имеет роль editor)
    - также должна быть возможность добавить нового пользователя (для роли editor)

- Страница редактирования пользователя системы содержит форму редактирования пользователя с полями:
    - email
    - имя
    - роль - выпадающий список (текущий пользователь не может менять свою роль)
    - timezone пользователя
    - datetime когда запись была добавлена, выровненный по timezone текущего пользователя (read only)
    - datetime когда запись была последний раз отредактирована, выровненный по timezone текущего пользователя (read only)
    - список адресов пользователя с ссылкой "Редактировать" при клике на которую на этой же странице появляется форма редактирования адреса, набор полей адреса на усмотрение разработчика
    - также должна быть возможность добавить новый адрес
    - при добавлении нового пользователя хотя бы один адрес обязательно должен быть добавлен
	
You can run the application server using mvn spring-boot:run
then open in browser the link: http://localhost:8080

Requirements: Java 1.8, Maven 3.2, Chrome or Firefox browser (IE not supported).