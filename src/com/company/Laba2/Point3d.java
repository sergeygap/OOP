package com.company.Laba2;

public class Point3d extends Point2d {
    protected double zCoord; // Координата x

    public Point3d(double x, double y, double z) { //Конструктор инициализации
        xCoord = x;
        yCoord = y;
        zCoord = z;
    }

    public Point3d() { // Конструктор по умолчанию.
        this(0, 0, 0);
    }

    public double getZ() {
        return zCoord;
    }

    public void setZ(double val) {
        zCoord = val;
    }

    public boolean equals(Point3d p ){
        return (p.getX() == xCoord) &&  (p.getY() == yCoord) && (p.getZ() == zCoord);
    }

    public double distanceTo( Point3d p ){
        return Math.ceil(Math.sqrt(Math.pow(xCoord - p.getX(),2) + Math.pow(yCoord - p.getY(),2) + Math.pow(zCoord - p.getZ(),2))*100)/100;
    }
}

