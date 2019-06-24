rem set FILM_ROOT= d:\Filmservice-master\

D:
cd %FILM_ROOT%
mvn clean package cargo:run -Dspring.profiles.active=postgres -Dmaven.test.skip=true