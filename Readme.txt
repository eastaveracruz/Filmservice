Запуск на Postgresql
username=root
password=root
databaseName=filmservice

D:
cd %FILM_ROOT%
mvn clean package cargo:run -Dspring.profiles.active=postgres -Dmaven.test.skip=true

***************************************************************************************

Запуск на Hsql
%CATALINA_HOME%\bin\catalina.bat run

d:
cd %FILM_ROOT%
mvn clean package -Dmaven.test.skip=true

COPY %FILM_ROOT%\target\filmservice.war %CATALINA_HOME%\webapps\

{FILM_ROOT} - путь к проекту



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


