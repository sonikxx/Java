package edu.school21.processor;

import com.google.auto.service.AutoService;
import edu.school21.annotations.HtmlForm;
import edu.school21.annotations.HtmlInput;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

// обработчик аннотаций
@SupportedAnnotationTypes({"edu.school21.annotations.HtmlForm", "edu.school21.annotations.HtmlInput"})
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {
    // RoundEnvironment roundEnv - состояние аннотаций, которые были собраны в этом раунде
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (Element elemForm: roundEnvironment.getElementsAnnotatedWith(HtmlForm.class)) {
            HtmlForm htmlForm = elemForm.getAnnotation(HtmlForm.class);
            StringBuilder html = new StringBuilder("<form action = \"" + htmlForm.action() + "\" method = \"" + htmlForm.method() + "\">\n");
            TypeElement typeElement = (TypeElement) elemForm;
            for (Element enclosedElement : typeElement.getEnclosedElements()) {
                HtmlInput htmlInput = enclosedElement.getAnnotation(HtmlInput.class);
                if (htmlInput != null) {
                    html.append("<input type = \"").append(htmlInput.type()).append("\" name = \"").append(htmlInput.name()).append("\" placeholder = \"").append(htmlInput.placeholder()).append("\">\n");
                }
            }
            html.append("<input type = \"submit\" value = \"Send\">");
            html.append("</form>");
            String content = html.toString();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("target/classes/" + htmlForm.fileName()))) {
                writer.write(content);
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        return true;
    }
}
