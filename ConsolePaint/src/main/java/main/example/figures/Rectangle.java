package main.example.figures;

import main.example.Color;
import main.example.Drawable;

public class Rectangle extends Figure implements Drawable {

    @Override
    public void area() {
        System.out.print("Площадь прямоугольника = ----.");
    }

    @Override
    public void perimeter() {
        System.out.print("Периметр прямоугольника = ----.");
    }

    @Override
    public void draw() {
        System.out.print("Нарисовал прямоугольник.");
    }

    @Override
    public void draw(Color color) {
        System.out.print("Нарисовал прямоугольник цветом " + color + ".");
    }
}
