package main.example;

import main.example.figures.Figure;

public class FigureUtil {
    private static FigureUtil instance;

    private FigureUtil() {

    };

    public static FigureUtil getInstance() {
        if (instance == null) {
            instance = new FigureUtil();
        }
        return instance;
    }

    void area(Figure figure) {
        System.out.print("Посчитал площадь фигуры.\n");
    }

    static void perimeter(Figure figure) {
        System.out.print("Посчитал периметр фигуры.\n");

    }

    static void draw(Figure figure) {
        System.out.print("Вывел на экран фигуру с черным цветом и координатами ("
                + figure.point.x + ", " + figure.point.y + ").\n");
    }

    static void draw(Figure figure, Color color) {
        System.out.print("Вывел на экран фигуру с цветом " + color
                + " и координатами (" + figure.point.x + ", " + figure.point.y + ").\n" );
    }
}
