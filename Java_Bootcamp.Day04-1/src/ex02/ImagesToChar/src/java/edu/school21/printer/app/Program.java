package edu.school21.printer.app;

import com.beust.jcommander.JCommander;
import edu.school21.printer.logic.Printer;
import edu.school21.printer.logic.PrinterArgs;

public class Program {
    public static void main(String[] args) {
        PrinterArgs jArgs = new PrinterArgs();
        JCommander.newBuilder().addObject(jArgs).build().parse(args);
        new Printer(jArgs.whiteColor, jArgs.blackColor, "src/resources/image.bmp").print();
    }
}