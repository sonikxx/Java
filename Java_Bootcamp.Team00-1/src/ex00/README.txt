Cоздание JAR файла:

cd ChaseLogic && mvn install  && cd .. && cd Game && mvn install && cd .. && java -jar Game/target/Game-1.0-SNAPSHOT.jar --enemiesCount=2 --wallsCount=5 --size=8 --profile=production