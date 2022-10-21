package org.example;

import java.util.Scanner;

public class Lab1 {
    /** Вычисление площади треугольника по заданным точкам **/

    public static void main(String[] args) {
        System.out.println("Write coordinates of 3 points below:");
        Scanner sc = new Scanner(System.in);

        String[] firstPointCoords = sc.nextLine().split(" ");
        String[] secondPointCoords = sc.nextLine().split(" ");
        String[] thirdPointCoords = sc.nextLine().split(" ");

        Point3d firstPoint = new Point3d(Double.parseDouble(firstPointCoords[0]), Double.parseDouble(firstPointCoords[1]), Double.parseDouble(firstPointCoords[2]));
        Point3d secondPoint = new Point3d(Double.parseDouble(secondPointCoords[0]), Double.parseDouble(secondPointCoords[1]), Double.parseDouble(secondPointCoords[2]));
        Point3d thirdPoint = new Point3d(Double.parseDouble(thirdPointCoords[0]), Double.parseDouble(thirdPointCoords[1]), Double.parseDouble(thirdPointCoords[2]));

        if (firstPoint.matches(secondPoint) || secondPoint.matches(thirdPoint) || thirdPoint.matches(firstPoint))
            System.out.println("There are matching points, can't compute triangle area");
        else
            System.out.println(computeArea(firstPoint, secondPoint, thirdPoint));
    }

    /** Вычисление формулы Герона **/
    public static double computeArea(Point3d firstPoint, Point3d secondPoint, Point3d thirdPoint) {
        double firstSide = firstPoint.distanceTo(secondPoint);
        double secondSide = secondPoint.distanceTo(thirdPoint);
        double thirdSide = thirdPoint.distanceTo(firstPoint);

        double p = (firstSide + secondSide + thirdSide)/2;
        return Double.parseDouble(String.format("%.2f", Math.sqrt(p*(p - firstSide)*(p - secondSide)*(p - thirdSide))).replace(",", "."));
    }
}
