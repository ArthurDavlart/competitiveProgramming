package com.company.tasks.yandexcup;

import java.util.Scanner;

public class YandexCup2020B {
    private static Scanner in = new Scanner(System.in);

    private static char serviceChar = '0';
    private static String inLine; // [2, 200_000]
    private static String palindromeWith2Symbol = null;
    private static String palindromeWith3Symbol = null;

    public static void resolver() {
        init();
        findMinPalindromeSubLine();
    }

    private static void init() {
        input();
    }

    private static void input() {
        inLine = in.next();
        inLine += serviceChar;
    }

    private static void findMinPalindromeSubLine() {
        char[] line2Symbols = new char[]{inLine.charAt(0), inLine.charAt(1)};
        char[] line3Symbols = new char[]{serviceChar, inLine.charAt(0), inLine.charAt(1)};
        tryAddPalindromeWith2Symbol(line2Symbols);

        for (int i = 2; i < inLine.length(); i++) {
            nextLine2Symbols(line2Symbols, i);
            tryAddPalindromeWith2Symbol(line2Symbols);
            nextLine3Symbols(line3Symbols, i);
            tryAddPalindromeWith3Symbol(line3Symbols);
        }

        if (palindromeWith2Symbol != null) {
            System.out.println(palindromeWith2Symbol);
        } else if (palindromeWith3Symbol != null) {
            System.out.println(palindromeWith3Symbol);
        } else {
            System.out.println(-1);
        }
    }

    private static void nextLine2Symbols(char[] line, int nextIndex) {
        line[0] = line[1];
        line[1] = inLine.charAt(nextIndex);
    }

    private static void nextLine3Symbols(char[] line, int nextIndex) {
        line[0] = line[1];
        line[1] = line[2];
        line[2] = inLine.charAt(nextIndex);
    }

    private static void tryAddPalindromeWith2Symbol(char[] line) {
        if (line[0] == line[1] && (
                palindromeWith2Symbol == null || palindromeWith2Symbol.charAt(0) > line[0]
        )) {
            palindromeWith2Symbol = String.valueOf(line);
        }
    }

    private static void tryAddPalindromeWith3Symbol(char[] line) {
        if (line[0] == line[2] && (
                palindromeWith3Symbol == null || palindromeWith3Symbol.charAt(0) > line[0]
        )) {
            palindromeWith3Symbol = String.valueOf(line);
        }
    }
}
