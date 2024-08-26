package edu.school21.printer.app;

import edu.school21.printer.logic.Printer;

public class Program {
    public static void main(String[] args) {
        if (args[0].length() != 1 || args[1].length() != 1) {
            System.err.println(
                    "Arguments: [characters of white pixel] [characters of black pixel]");
            System.exit(-1);
        }
        char whiteSymbol = args[0].charAt(0);
        char blackSymbol = args[1].charAt(0);
        new Printer(whiteSymbol, blackSymbol,
                "src/resources/image.bmp")
                .print();
    }
}