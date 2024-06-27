package me.terramain.textexecuter;

public class Line {
    private String text;
    private int number;

    public Line(String text, int number) {
        this.text = text;
        this.number = number;
    }

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}
    public int getNumber() {return number;}
}
