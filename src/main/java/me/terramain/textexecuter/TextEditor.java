package me.terramain.textexecuter;

import java.util.ArrayList;
import java.util.List;

public class TextEditor {
    private String text;
    private StringBuilder stringBuilder;
    private TextIterator textIterator;

    public TextEditor(String text) {
        setText(text);
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
        this.stringBuilder = new StringBuilder(text);
        this.textIterator = new TextIterator(text);
    }
    private void saveText(){
        this.textIterator = new TextIterator(stringBuilder.toString());
        this.textIterator = new TextIterator(stringBuilder.toString());
    }
    private void saveTextIterator(){
        this.stringBuilder = new StringBuilder(textIterator.getText());
        this.text = textIterator.getText();
    }
    private void saveStringBuilder(){
        this.textIterator = new TextIterator(stringBuilder.toString());
        this.text = stringBuilder.toString();
    }


    public String[] getLinesArray(){
        return text.split("\n");
    }
    public List<Line> getLines(){
        List<Line> lines = new ArrayList<>();
        int i = 0;
        for (String line:getLinesArray()){
            lines.add(new Line(line,i));
            i++;
        }
        return lines;
    }


    public void linesForeach(LinesForeachInterface action){
        this.stringBuilder = new StringBuilder();
        for (Line line:getLines()){
            action.editLine(line);
            stringBuilder.append(line.getText()).append('\n');
        }
        saveStringBuilder();
    }

    public void addSpacesToLines(int spacesNum){
        String spaces = " ".repeat(spacesNum);
        linesForeach(line -> {
            line.setText(spaces+line.getText());
        });
    }
    public void stripLines(){
        linesForeach(line -> {
            line.setText(line.getText().strip());
        });
    }

    public void removeChar(char c){
        stringBuilder = new StringBuilder();
        textIterator.forEachRemaining(character -> {
            if (character!=c){
                stringBuilder.append(character);
            }
        });
        saveStringBuilder();
    }
    public void replaceChar(char c, char newChar){
        int i = 0;
        textIterator.previous();
        while (textIterator.hasNext()){
            char character = textIterator.next();
            if (character==c){
                stringBuilder.setCharAt(i,newChar);
            }
            i++;
        }
        saveStringBuilder();
    }
}

