Перед компиляцией удалить все .class файлы:
rm -rf target && mkdir target

Компиляция группы классов Program.java и Printer.java:
javac -d target src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/Printer.java
-d флаг, после которого следует указать расположение, куда попадут скомпилированные классы

Запуск скомпилированных файлов:
java -classpath ./target edu.school21.printer.app.Program WHITE_SYMBOL BLACK_SYMBOL PATH

Например:
java -classpath ./target edu.school21.printer.app.Program . o image.bmp