@echo off
ECHO "Starting Icing..."
mvn -DskipTests clean wildfly-swarm:run
