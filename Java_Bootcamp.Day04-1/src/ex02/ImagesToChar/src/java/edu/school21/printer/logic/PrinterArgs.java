package edu.school21.printer.logic;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=") // default: parameters are separated by spaces
public class PrinterArgs {
    @Parameter(names = "--white", required = true)
    public String whiteColor;

    @Parameter(names = "--black", required = true)
    public String blackColor;
}