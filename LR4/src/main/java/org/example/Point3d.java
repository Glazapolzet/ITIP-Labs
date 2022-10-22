package org.example;

public class Point3d extends Point2d{
    /** координата Z **/
    private double zCoord;

    /** Конструктор инициализации **/
    public Point3d (double x, double y, double z) {
        super(x, y); //обращение к родительскому конструктору Point2d
        zCoord = z;
    }

    /** Конструктор по умолчанию. **/
    public Point3d () {
        this(0.0, 0.0, 0.0);
    }

    /** Возвращение координаты Z **/
    public double getZ () {
        return zCoord;
    }

    /** Установка значения координаты Z. **/
    public void setZ (double val) {
        zCoord = val;
    }

    /** Сравнение двух объектов **/
    public boolean matches (Point3d object) {
        return (object.getX() == getX()) && (object.getY() == getY()) && (object.getZ() == getZ());
    }

    /** Поиск расстояния между точками **/
    public double distanceTo (Point3d object) {
        return Double.parseDouble(String.format("%.2f", Math.sqrt(Math.pow(object.getX() - getX(), 2) + Math.pow(object.getY() - getY(), 2) + Math.pow(object.getZ() - getZ(), 2))).replace(",", "."));
    }

}