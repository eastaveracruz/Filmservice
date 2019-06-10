rem set FILM_ROOT= d:\trainSpring2019\michael\Filmservice-master\
d:
cd %FILM_ROOT%

rem mvn clean package && COPY /Y %FILM_ROOT%\target\filmservice.war %CATALINA_HOME%\webapps\
mvn clean package -Dmaven.test.skip=true && COPY /Y %FILM_ROOT%\target\filmservice.war %CATALINA_HOME%\webapps\
