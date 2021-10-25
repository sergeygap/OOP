package com.company.Laba2;

public class lab1 {
    public static void main(String[] args) {
        Point3d a = new Point3d(1.0, 2.0, 3.0);
        Point3d b = new Point3d(2.0, 3.0, 4.0);
        Point3d c = new Point3d(5.0, 5.0, 6.0);
        if (a.equals(b) || a.equals(c) || b.equals(c)) {
            System.out.println("Такого треугольника не бывает");
        } else {
            System.out.println("Площадь треугольника: ");
            System.out.println(computeArea(a, b, c));
        }
    }

    public static double computeArea(Point3d a, Point3d b, Point3d c) {
        double x = a.distanceTo(b);//расстояние от a до b
        double y = a.distanceTo(c);//расстояние от a до c
        double z = b.distanceTo(c);//расстояние от b до c
        double p = (x + y + z) / 2;
        return Math.ceil(Math.sqrt(p * (p - x) * (p - y) * (p - z)) * 100) / 100; // Умножаем а потом делим на сто, чтобы точность была два знака после запятой.
    }
}
