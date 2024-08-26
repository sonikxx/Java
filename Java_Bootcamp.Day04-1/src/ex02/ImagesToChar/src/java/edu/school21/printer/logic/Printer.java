package edu.school21.printer.logic;

import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

public class Printer {
    private String whiteColor;
    private String blackColor;
    private String path;

    public Printer(String whiteColor, String blackColor, String path) {
        this.whiteColor = whiteColor;
        this.blackColor = blackColor;
        this.path = path;
    }

    public void print() {
        try {
            File file = new File(path); // jpg, png, bmp
            BufferedImage image = ImageIO.read(file);
            ColoredPrinter printer = new ColoredPrinter();
            for (int y = 0; y < image.getHeight(); ++y) {
                for (int x = 0; x < image.getWidth(); ++x) {
                    if (image.getRGB(x, y) == Color.WHITE.getRGB())
                        printer.print(" ", Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(whiteColor));
                    else
                        printer.print(" ", Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(blackColor));
                }
                System.out.print("\n");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

}