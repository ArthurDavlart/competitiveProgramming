package com.company;

import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }

        int[] intArr(int n) {
            int res[] = new int[n];
            for (int i = 0; i < n; i++)
                res[i] = nextInt();
            return res;
        }

        long[] longArr(int n) {
            long res[] = new long[n];
            for (int i = 0; i < n; i++)
                res[i] = nextLong();
            return res;
        }
    }

    static FastReader in = new FastReader();

    public static void main(String args[]) {
        resolver();
    }

    private static char serviceChar = '0';
    private static String inLine; // [2, 200_000]
    private static LinkedList<String> palindromeWith2Symbol = new LinkedList<>();
    private static LinkedList<String> palindromeWith3Symbol = new LinkedList<>();

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
        ;
        tryAddPalindromeWith2Symbol(line2Symbols);

        for (int i = 2; i < inLine.length(); i++) {
            nextLine2Symbols(line2Symbols, i);
            tryAddPalindromeWith2Symbol(line2Symbols);
            nextLine3Symbols(line3Symbols, i);
            tryAddPalindromeWith3Symbol(line3Symbols);
        }

        int minPalindromeIndex = -1;

        if (palindromeWith2Symbol.size() > 0) {
            minPalindromeIndex = 0;
            for (int i = 0; i < palindromeWith2Symbol.size(); i++) {
                if (palindromeWith2Symbol.get(i).charAt(0) < palindromeWith2Symbol.get(minPalindromeIndex).charAt(0)) {
                    minPalindromeIndex = i;
                }
            }

            System.out.println(palindromeWith2Symbol.get(minPalindromeIndex));
        } else if (palindromeWith3Symbol.size() > 0) {
            minPalindromeIndex = 0;
            for (int i = 0; i < palindromeWith3Symbol.size(); i++) {
                if (palindromeWith3Symbol.get(i).charAt(0) < palindromeWith3Symbol.get(minPalindromeIndex).charAt(0)) {
                    minPalindromeIndex = i;
                }
            }

            System.out.println(palindromeWith3Symbol.get(minPalindromeIndex));
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
        if (line[0] == line[1]) {
            palindromeWith2Symbol.add(String.valueOf(line));
        }
    }

    private static void tryAddPalindromeWith3Symbol(char[] line) {
        if (line[0] == line[2]) {
            palindromeWith3Symbol.add(String.valueOf(line));
        }
    }
}

