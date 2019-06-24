rem set FILM_ROOT= d:\Filmservice-master\
rem set CATALINA_HOME= c:\dev\tomcat\apache-tomcat-9.0.20\

e:
cd %FILM_ROOT%

mvn clean package -Dmaven.test.skip=true && COPY /Y %FILM_ROOT%\target\filmservice.war %CATALINA_HOME%\webapps\
