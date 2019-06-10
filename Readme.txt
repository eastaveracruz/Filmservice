Запуск на Postgresql
username=root
password=root
databaseName=filmservice

Postgresql.bat

----------------------------------------------------------------------------------------------------

Запуск на Hsql

catalina.bat
Hsql.bat

----------------------------------------------------------------------------------------------------
URL - http://localhost:8080/filmservice/

Пользователи:
login: admin, password: admin, roles: admin, user
login: user, password: user, roles: user
login: user1, password: user, roles: user
...
login: user6, password: user, roles: user

REV-3************************************************************************************************

Комментарии к замечаниям:

- case insensitive - чтобы Film и film - находило по обоим
    исправил

- if(title == null || "".equals(title)){
  такое обычно делается каким-нибудь утилитным классом StringUtils.isEmpty(title)
  Такой класс можно написать самому или подключить каккую-нибудь известную библиотеку, например Apache
    Добавил Strings.isNullOrEmpty из com.google.common.base.Strings

- <file>${FILM_ROOT}/log/filmservice.log</file>  - а где этот файл найти?
    при старте приложения он появится по этому адресу


REV-2*************************************************************************************************

Комментарии к замечаниям:
- README.TXT добавил
- про лишний статик и ленивую инициализацию не до конца понял
- package filmservice.util.assertion исправил
- FilmCreationHelper, UserCreationHelper исправил
- assertThat(films).isEqualTo(filmsExp) – сравнение двух списков
- NotFoundException – над этим надо подумать
- SecurityUtil – временный костыль, который хранит id залогиненого пользователя
- редактирование фильма будет происходить на странице фильма (которой пока нет) с помощью ajax.


Изменения:
- пофиксил баги связанные c базами данных
- прикрутил spring-webmvc
- создал 2 профиля spring: postgres и hsql
- изменил стиль логирование
- плагин cargo-maven2-plugin
- поиск по названию фильма


