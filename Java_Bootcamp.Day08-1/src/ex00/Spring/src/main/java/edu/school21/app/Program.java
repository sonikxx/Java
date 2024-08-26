package edu.school21.app;

import edu.school21.preprocessor.*;
import edu.school21.printer.*;
import edu.school21.renderer.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Program {
    public static void main(String[] args) {
//        System.out.println("Standard way using class:");
//        PreProcessor preProcessor = new PreProcessorToUpperImpl();
//        Renderer renderer = new RendererErrImpl(preProcessor);
//        PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
//        printer.setPrefix("Prefix");
//        printer.print("Hello!");

        System.out.println("Using components with Spring:");
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        Printer printer = context.getBean("printerPrefErrUp", Printer.class);
        printer.print("Hello!");
    }
}

