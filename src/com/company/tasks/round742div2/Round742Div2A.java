package com.company.tasks.round742div2;

import java.util.HashMap;
import java.util.Scanner;

public class Round742Div2A {
    private final static Scanner in = new Scanner(System.in);
    private static HashMap<Character, Character> keyToKey = new HashMap<>(){{
        put('L', 'L');
        put('R', 'R');
        put('U', 'D');
        put('D', 'U');
    }};
    public static void resolve(){
        input();
    }
    
    private static void input(){
        int l = in.nextInt();
        for (int i = 0; i < l; i++) {
            int inputCount = in.nextInt();
            String str = in.next();
            for (int j = 0; j < inputCount; j++) {
                System.out.print(keyToKey.get(str.charAt(j)));
            }
            System.out.println();
        }
    }
}
