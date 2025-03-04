package main.example.figures;

import main.example.Color;
import main.example.Drawable;

public class Square extends Rectangle implements Drawable {

    @Override
    public void area() {
        System.out.print("Площадь квадрата = ----.");
    }

    @Override
    public void perimeter() {
        System.out.print("Периметр квадрата = ----.");
    }

    @Override
    public void draw() {
        System.out.print("Нарисовал квадрат.");
    }

    @Override
    public void draw(Color color) {
        System.out.print("Нарисовал квадрат цветом " + color + ".");
    }
}
