package com.company.Laba2;

public class Point2d {
    /**
     * двумерный класс точки.
     **/

        protected double xCoord; // Координата x
        protected double yCoord; // Координата y

        public Point2d(double x, double y) { //Конструктор инициализации
            xCoord = x;
            yCoord = y;
        }

        public Point2d() { // Конструктор по умолчанию.
            //Вызовите конструктор с двумя параметрами и определите источник.
            this(0, 0);
        }

        public double getX() {
            return xCoord;
        }

        public double getY() {
            return yCoord;
        }

        public void setX(double val) {
            xCoord = val;
        }

        public void setY(double val) {
            yCoord = val;
        }
    }

