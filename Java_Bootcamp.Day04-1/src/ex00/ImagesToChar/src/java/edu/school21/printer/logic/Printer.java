package edu.school21.printer.logic;

import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Printer {
    private char whiteSymbol;
    private char blackSymbol;
    private String path;

    public Printer(char whiteSymbol, char blackSymbol, String path) {
        this.whiteSymbol = whiteSymbol;
        this.blackSymbol = blackSymbol;
        this.path = path;
    }

    public void print() {
        try {
            File file = new File(path); // jpg, png, bmp
            BufferedImage image = ImageIO.read(file);
            for (int y = 0; y < image.getHeight(); ++y) {
                for (int x = 0; x < image.getWidth(); ++x) {
                    char symbolToPrint = (image.getRGB(x, y) == Color.WHITE.getRGB()) ? whiteSymbol : blackSymbol;
                    System.out.print(symbolToPrint);
                }
                System.out.print("\n");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

}