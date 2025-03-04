package main.example.figures;

import main.example.Color;
import main.example.Drawable;

public class Triangle extends Figure implements Drawable {

    @Override
    public void area() {
        System.out.print("Площадь треугольника = ----.");
    }

    @Override
    public void perimeter() {
        System.out.print("Периметр треугольника = ----.");
    }

    @Override
    public void draw() {
        System.out.print("Нарисовал треугольник.");
    }

    @Override
    public void draw(Color color) {
        System.out.print("Нарисовал треугольник цветом " + color + ".");
    }
}
