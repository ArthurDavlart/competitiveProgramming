package com.company.tasks.yandexcup;

import java.util.LinkedList;
import java.util.Scanner;

public class YandexCup2020C {
    private static final Scanner in = new Scanner(System.in);

    static abstract class Figure{
        public abstract Coordinate getCenter();
    }

    static class Rectangle extends Figure{
        Coordinate[] coordinates;

        Rectangle(Coordinate[] coordinates){
            this.coordinates = coordinates;
        }

        @Override
        public Coordinate getCenter() {
            Coordinate c1 = coordinates[0];
            Coordinate c2 = coordinates[2];
            return new Coordinate((c1.x + c2.x / 2), (c1.y + c2.y / 2));
        }
    }

    static class Circle extends Figure{
        Coordinate centerCoordinate;
        int radios;

        Circle (Coordinate coordinate, int radios){
            this.centerCoordinate = coordinate;
            this.radios = radios;
        }

        @Override
        public Coordinate getCenter() {
            return this.centerCoordinate;
        }
    }

    static class Coordinate{
        int x;
        int y;

        Coordinate(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    private static int targetQuantity;
    private static LinkedList<Figure> figures;

    public static void resolve(){
        init();
        output(canMakeLine());
    }

    private static void init(){
        input();
    }

    private static void input(){
        targetQuantity = in.nextInt();
        figures = new LinkedList<>();
        for (int i = 0; i < targetQuantity; i++) {
            figures.add(newFigure());
        }
    }

    private static Figure newFigure(){
        if (in.nextInt() == 0){
            int radios = in.nextInt();
            Coordinate coordinate = new Coordinate(in.nextInt(), in.nextInt());
            return new Circle(coordinate, radios);
        } else {
            Coordinate[] coordinates = new Coordinate[4];
            for (int i = 0; i < 4; i++) {
                coordinates[i] = new Coordinate(in.nextInt(), in.nextInt());
            }

            return new Rectangle(coordinates);
        }
    }

    private static boolean canMakeLine(){
        if (figures.size() < 3){
            return true;
        }

        Coordinate[] line = new Coordinate[]{
                figures.get(0).getCenter(),
                figures.get(1).getCenter()
        };

        for (int i = 2; i < figures.size(); i++) {
            Coordinate coordinate = figures.get(i).getCenter();
            if (!belongsToLine(line, coordinate)){
                return false;
            }
        }

        return true;
    }

    private static boolean belongsToLine(Coordinate[] line, Coordinate coordinate){
        // вычисления с помощью косого произвенедения
        // Links : [ https://habr.com/ru/post/147691/ , https://habr.com/ru/post/148325/ ]
        // Если векторы заданы своими координатами a(x1, y1), b(x2, y2)
        // то косое произведение [a, b] = x1y2 — x2y1.
        // Поэтому нам достаточно посчитать косое произведение векторов P1P2 и P1M
        // и по его знаку сделать вывод. Если косое произведение = 0, то точка лежит на прямой
        Coordinate p1 = line[0];
        Coordinate p2 = line[1];
        Coordinate m = coordinate;

        Coordinate p1p2 = new Coordinate(p2.x - p1.x, p2.y - p1.y);
        Coordinate p1m = new Coordinate(m.x - p1.x, m.y - p1.y);
        return p1p2.x * p1m.y - p1p2.y * p1m.x == 0;
    }

    private static void output(boolean flag){
        System.out.println(flag ? "Yes" : "No");
    }
}
