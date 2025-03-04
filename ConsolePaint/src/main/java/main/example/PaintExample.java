package main.example;

import main.example.figures.*;


public class PaintExample {
    public static void main(String[] args) {
        FigureUtil FU = FigureUtil.getInstance();

        Triangle triangle = new Triangle();
        Circle circle = new Circle();
        Square square = new Square();
        Rectangle rectangle = new Rectangle();

        FigureUtil.draw(triangle);
        FigureUtil.draw(triangle, Color.RED);
        FigureUtil.draw(rectangle, Color.BLUE);

    }
}
