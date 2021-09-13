package main.java.tasks.round260div2;

import java.util.Scanner;

public class Round260Div2E {
    private static Scanner in = new Scanner(System.in);

    private static class DSU{
        private int[] store;
        private int[] rankMin;
        private int[] rankMax;

        public DSU(int vertexQuantity) {
            store = new int[vertexQuantity + 1];
            rankMin = new int[vertexQuantity + 1];
        }

        public void merge(int a, int b){
            int rootA = getRoot(a);
            int rootB = getRoot(b);

            if(rootA == rootB){
                return;
            } else {
                if (rankMin[rootA] < rankMin[rootB]){
                    store[rootA] = rootB;
                } else if (rankMin[rootB] < rankMin[rootA]){
                    store[rootB] = rootA;
                } else {
                    store[rootA] = rootB;
                    rankMin[rootB]++;
                }
            }
        }

        public int getRoot(int v){
            if(store[v] == v){
                return v;
            } else {
                return store[v] = getRoot(store[v]);
            }
        }
    }

    public static void resolve(){

    }

    private static void input(){
        int n = in.nextInt();
        int m = in.nextInt();
        int q = in.nextInt();

        DSU dsu = new DSU(n);

        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();

            dsu.merge(a, b);
        }

        for (int i = 0; i < q; i++) {

        }
    }

    private static void output(){

    }
}
