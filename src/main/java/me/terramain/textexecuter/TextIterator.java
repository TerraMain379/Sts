package terramain.textexecuter;

import java.util.Iterator;
import java.util.function.Consumer;

public class TextIterator implements Iterator<Character> {
    private String text;
    private char[] chars;
    private int charNumber;

    public TextIterator(String text) {
        this.text = text;
        this.chars = text.toCharArray();
        this.charNumber = 0;
    }
    private TextIterator(String text, char[] chars, int charNumber) {
        this.text = text;
        this.chars = chars;
        this.charNumber = charNumber;
    }

    public int getCharNumber(){
        return this.charNumber;
    }
    public String getText(){
        return this.text;
    }
    public int getTextLength(){
        return this.chars.length;
    }

    public Character getCharAt(int number){
        if (this.charNumber >= 0 && this.charNumber < getTextLength()){
            return this.chars[number];
        }
        return null;
    }
    public Character getChar(){
        return getCharAt(this.charNumber);
    }
    public Character getNextCharAt(int number){
        return getCharAt(this.charNumber+number);
    }
    public Character getPreviousCharAt(int number){
        return getCharAt(this.charNumber-number);
    }
    public Character getNextChar(){
        return getNextCharAt(1);
    }
    public Character getPreviousChar(){
        return getPreviousCharAt(1);
    }
    public boolean hasNextAt(int number) {
        number=this.charNumber+number;
        return number >= 0 && number < getTextLength();
    }
    public boolean hasPreviousAt(int number) {
        number=this.charNumber-number;
        return number >= 0 && number < getTextLength();
    }
    @Override
    public boolean hasNext() {
        return hasNextAt(1);
    }
    public boolean hasPrevious() {
        return hasPreviousAt(1);
    }
    public Character nextAt(int number){
        this.charNumber+=number;
        return getChar();
    }
    public Character previousAt(int number){
        this.charNumber-=number;
        return getChar();
    }
    @Override
    public Character next(){
        return nextAt(1);
    }
    public Character previous(){
        return previousAt(1);
    }

    @Override
    public void forEachRemaining(Consumer<? super Character> action) {
        while (this.hasNextAt(0)){
            action.accept(getChar());
            next();
        }
    }

    public int readInt(){
        StringBuilder stringBuilder = new StringBuilder();
        while (getChar()!=null && Character.isDigit(getChar())){
            stringBuilder.append(getChar());
            next();
        }
        return Integer.parseInt(stringBuilder.toString());
    }
    public String getSubstring(int startChar, int endChar){
        if (endChar==-1) return text.substring(startChar);
        else return text.substring(startChar,endChar);
    }
    public String getSubstring(int startChar){
        return getSubstring(startChar,-1);
    }
    public String readStringFromLength(int length){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length && getChar()!=null; i++) {
            stringBuilder.append(getChar());
            next();
        }
        return stringBuilder.toString();
    }


    public TextIterator clone(){
        return new TextIterator(text,chars,charNumber);
    }
}
