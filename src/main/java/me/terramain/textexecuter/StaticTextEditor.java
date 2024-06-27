package me.terramain.textexecuter;

public class StaticTextEditor {

    public static String linesForeach(String text, LinesForeachInterface action){
        TextEditor textEditor = new TextEditor(text);
        textEditor.linesForeach(action);
        return textEditor.getText();
    }

    public static String addSpacesToLines(String text, int spacesNum){
        TextEditor textEditor = new TextEditor(text);
        textEditor.addSpacesToLines(spacesNum);
        return textEditor.getText();
    }

    public static String removeChar(String text, char c){
        TextEditor textEditor = new TextEditor(text);
        textEditor.removeChar(c);
        return textEditor.getText();
    }
    public static String stripLines(String text){
        TextEditor textEditor = new TextEditor(text);
        textEditor.stripLines();
        return textEditor.getText();
    }
}
