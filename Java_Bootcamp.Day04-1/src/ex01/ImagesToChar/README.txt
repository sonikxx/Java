Перед компиляцией удалить все .class файлы:
rm -rf target && mkdir target

Компиляция группы классов Program.java и Printer.java:
javac -d target src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/Printer.java
-d флаг, после которого следует указать расположение, куда попадут скомпилированные классы

Копирование файлов resources в папку сборки:
cp src/resources/* target

Создание JAR файла:
jar -cmf src/manifest.txt target/images-to-chars-printer.jar -C target .
-cmf флаг, после которого следует указать путь к файлу манифеста
-С флаг, после которого указывается путь к скомпилированным классам

Запуск JAR файла:
java -jar target/images-to-chars-printer.jar WHITE_SYMBOL BLACK_SYMBOL

Например:
java -jar target/images-to-chars-printer.jar . o