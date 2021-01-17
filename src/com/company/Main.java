package com.company;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner _in = null;
    private static ArrayList<int[]> _tree;

    public static void main(String[] args) {
        _in = new Scanner(System.in);

        int n = _in.nextInt();
        int m = _in.nextInt();

        _tree = new ArrayList<>(n + 1);

        int[] firstArr = new int[(int) Math.pow(2, n)];

        fillArrayFromConsole(firstArr);

        _tree.add(firstArr);

        fillTree(n);

//        outputTreeTest(tree);
        int p, b;
        for (int i = 0; i < m; i ++){
            p = _in.nextInt();
            b = _in.nextInt();

            updateTree(p, b);

            System.out.println(_tree.get(_tree.size() - 1)[0]);
        }
    }

    private static void updateTree(int p, int b){
        int[] workArr = _tree.get(0);
        int index = p - 1;

        workArr[index] = b;

        int nextP = (index) / 2;
        int nextB = index % 2 == 0 ? workArr[index] | workArr[index + 1] : workArr[index] | workArr[index - 1];

        boolean isOr = false;
        for (int i = 1; i < _tree.size() - 1; i++){
            workArr = _tree.get(i);
            workArr[nextP] = nextB;

            nextP = nextP / 2;
            if (isOr){
                nextB = nextP % 2 == 0 ? workArr[nextP] | workArr[nextP + 1] : workArr[nextP] | workArr[nextP - 1];
                isOr = false;
            } else {
                nextB = nextP % 2 == 0 ? workArr[nextP] ^ workArr[nextP + 1] : workArr[nextP] ^ workArr[nextP - 1];
                isOr = true;
            }
        }

        _tree.get(_tree.size() - 1)[nextP] = nextB;
    }

    private static void fillArrayFromConsole(int[] arr){
        for (int i = 0; i < arr.length; i++){
            arr[i] = _in.nextInt();
        }
    }

    private static void fillTree(int longTree){
        boolean isOr = true;
        for (int i = longTree - 1; i >= 0; i--){
            int[] arr = new int[(int) Math.pow(2, i)];
            int[] beforeArr = _tree.get(_tree.size() - 1);
            if (isOr){
                for (int j = 0; j < arr.length; j++){
                    arr[j] = beforeArr[j * 2] | beforeArr[j * 2 + 1];
                }
                isOr = false;
            } else {
                for (int j = 0; j < arr.length; j ++){
                    arr[j] = beforeArr[j * 2] ^ beforeArr[j * 2 + 1];
                }
                isOr = true;
            }

            _tree.add(arr);
        }
    }

//    private static void outputTreeTest(ArrayList<int[]>tree){
//        for (int[] arr : tree){
//            for (int i : arr){
//                System.out.print(i + " ");
//            }
//            System.out.println();
//        }
//    }

}

