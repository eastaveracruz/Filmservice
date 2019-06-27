call mvn -B -s settings.xml -DskipTests=true clean package
call java -Dspring.profiles.active=heroku -DDATABASE_URL="postgres://root:root@localhost:5432/filmservice" -jar target/dependency/webapp-runner.jar target/*.war
