Запуск на Postgresql
username=root
password=root
databaseName=filmservice

f: cd %FILM_ROOT%
mvn clean package cargo:run -Dspring.profiles.active=postgres -Dmaven.test.skip=true

***************************************************************************************

Запуск на Hsql
%CATALINA_HOME%\bin\catalina.bat run

f: cd %FILM_ROOT%
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


