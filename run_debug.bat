@echo off
echo Starting backend... > backend_debug.log
where mvn >> backend_debug.log 2>&1
mvn spring-boot:run >> backend_debug.log 2>&1
echo Done. >> backend_debug.log
