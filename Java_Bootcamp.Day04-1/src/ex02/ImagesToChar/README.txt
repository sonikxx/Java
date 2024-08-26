Перед компиляцией удалить все .class файлы и библиотеки:
rm -rf target lib && mkdir target lib

Скачать библиотеки JCommander и JCDP:
curl https://repo1.maven.org/maven2/com/beust/jcommander/1.82/jcommander-1.82.jar -o lib/jcommander-1.82.jar
curl https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.2/JCDP-4.0.2.jar -o lib/JCDP-4.0.2.jar

Извлечение jar файлов в папку target:
cd target
jar xf ../lib/jcommander-1.82.jar
jar xf ../lib/JCDP-4.0.2.jar
cd ..

Компиляция:
javac -classpath "lib/jcommander-1.82.jar:lib/JCDP-4.0.2.jar" src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/Printer.java src/java/edu/school21/printer/logic/PrinterArgs.java -d ./target
-classpath флаг, после которого следует указать местоположение скомпилированных классов
-d флаг, после которого следует указать расположение, куда попадут скомпилированные классы

Копирование файлов resources в папку сборки:
cp src/resources/* target

Создание JAR файла:
jar -cmf src/manifest.txt target/images-to-chars-printer.jar -C target .
-cmf флаг, после которого следует указать путь к файлу манифеста
-С флаг, после которого указывается путь к скомпилированным классам

Запуск JAR файла:
java -jar target/images-to-chars-printer.jar --white=WHITE_COLOR --black=BLACK_COLOR

Например:
java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN