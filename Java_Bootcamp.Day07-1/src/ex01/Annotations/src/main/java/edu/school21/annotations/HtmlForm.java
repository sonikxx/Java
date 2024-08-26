package edu.school21.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE) // аннотация доступна только во время компиляции
public @interface HtmlForm {
    String fileName();
    String action();
    String method();
}
