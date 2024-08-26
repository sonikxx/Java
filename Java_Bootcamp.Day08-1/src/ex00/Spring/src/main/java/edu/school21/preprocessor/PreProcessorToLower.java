package edu.school21.preprocessor;

public class PreProcessorToLower implements PreProcessor{
    @Override
    public String process(String str) {
        return str.toLowerCase();
    }
}
