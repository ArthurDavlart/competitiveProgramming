package main.java.tasks.round260div2;

import java.util.ArrayList;
import java.util.Scanner;

public class Round260Div2E {
    private static Scanner in = new Scanner(System.in);

    private static class DSU{
        private ArrayList<Integer>[] adjacentVertices;

        private int[] store;
        private int[] rank;

        private int[] diameter;

        public DSU(int vertexQuantity) {
            adjacentVertices = new ArrayList[vertexQuantity + 1];
            store = new int[vertexQuantity + 1];
            rank = new int[vertexQuantity + 1];
            diameter = new int[vertexQuantity  + 1];
            init();
        }

        private void init(){
            for (int i = 1; i < store.length; i++) {
                store[i] = i;
                rank[i] = 1;
                adjacentVertices[i] = new ArrayList<>();
            }
        }

        public void merge(int a, int b){
            int mainRoot = getRoot(a);
            int mergedRoot = getRoot(b);

            adjacentVertices[a].add(b);
            adjacentVertices[b].add(a);

            if (rank[mainRoot] > rank[mergedRoot]){
                int tmp = mergedRoot;
                mainRoot = mergedRoot;
                mergedRoot = tmp;
            }

            store[mergedRoot] = mainRoot;
            rank[mainRoot] += rank[mergedRoot];
        }

        public void mergeV2(int a, int b){
            int mainRoot = getRoot(a);
            int mergedRoot = getRoot(b);

            if (rank[mainRoot] > rank[mergedRoot]){
                int tmp = mergedRoot;
                mainRoot = mergedRoot;
                mergedRoot = tmp;
            }
            store[mergedRoot] = mainRoot;
            rank[mainRoot] += rank[mergedRoot];
            diameter[mainRoot] = Math.max(rad(mainRoot) + rad(mergedRoot) + 1,Math.max(diameter[mainRoot], diameter[mergedRoot]));
        }

        private int rad(int d){
            return (diameter[d] + 1 ) / 2;
        }

        public int getRoot(int v){
            if(store[v] == v){
                return v;
            } else {
                return store[v] = getRoot(store[v]);
            }
        }

        private boolean[] visitedVertex;

        public void findAllDiameters(){
            visitedVertex = new boolean[store.length];
            for (int i = 1; i < store.length; i++) {
                int currentRoot = getRoot(i);
                if(!visitedVertex[currentRoot]){
                    visitedVertex[currentRoot] = true;
                    diameter[currentRoot] = findDiameter(currentRoot);
                }
            }
        }

        private int maxDepth;
        private int endLongestPathVertex;

        private int findDiameter(int vertex){
            dfs(vertex, 0);

            int farVertexForCurrentVertex = endLongestPathVertex;
            endLongestPathVertex = -1;
            maxDepth = 0;

            dfs(farVertexForCurrentVertex, 0);

            return maxDepth;
        }

        private void dfs(int currentVertex, int currentDepth){
            if (currentDepth > maxDepth){
                maxDepth = currentDepth;
                endLongestPathVertex = currentVertex;
            }

            for (Integer vertex: adjacentVertices[currentVertex]){
                dfs(vertex, currentDepth + 1);
            }
        }

        public int getDiameter(int vertex){
            return diameter[getRoot(vertex)];
        }
    }

    public static void resolve(){
        input();
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

        dsu.findAllDiameters();

        for (int i = 0; i < q; i++) {
            if (in.nextInt() == 1){
                output(dsu.getDiameter(in.nextInt()));
            } else {
                int x = in.nextInt();
                int y = in.nextInt();
                dsu.mergeV2(x, y);
            }
        }
    }

    private static void output(int d){
        System.out.println(d);
    }
}
