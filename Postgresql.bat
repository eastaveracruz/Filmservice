rem set FILM_ROOT= d:\Filmservice-master\

D:
cd %FILM_ROOT%
mvn clean package cargo:run  -Dmaven.test.skip=true-Dspring.profiles.active=postgres