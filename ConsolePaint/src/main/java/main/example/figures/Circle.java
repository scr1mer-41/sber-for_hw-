package main.example.figures;

import main.example.Color;
import main.example.Drawable;

public class Circle extends Figure implements Drawable {


    @Override
    public void area() {
        System.out.print("Площадь круга = ----.");
    }

    @Override
    public void perimeter() {
        System.out.print("Периметр круга = ----.");
    }

    @Override
    public void draw() {
        System.out.print("Нарисовал круг.");
    }

    @Override
    public void draw(Color color) {
        System.out.print("Нарисовал круг цветом " + color + ".");
    }
}
