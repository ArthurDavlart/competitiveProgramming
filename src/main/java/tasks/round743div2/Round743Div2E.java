package tasks.round743div2;

import java.util.*;

public class Round743Div2E {

    private static Scanner in = new Scanner(System.in);



    public static void resolve(){
        input();
    }

    private static void input(){
        int t = in.nextInt();

        for (int i = 0; i < t; i++) {
            init();
            output(calc());
        }
    }

    private static int n;
    private static ArrayList<Integer>[] adjacency;

    private static void init() {
        n = in.nextInt();
        adjacency = new ArrayList[n + 1];
        for (int i = 1; i < n + 1; i++) {
            int k = in.nextInt();
            adjacency[i] = new ArrayList<Integer>();
            for (int j = 0; j < k; j++) {
                adjacency[i].add(in.nextInt());
            }
        }
    }

    private static int[] neededReadingQuantity;
    private static boolean[] alreadyKnow;
    private static boolean isFundedCircle;
    private static int maxDepth;
    private static int calc(){
        neededReadingQuantity = new int[adjacency.length];
        alreadyKnow = new boolean[adjacency.length];
        isFundedCircle = false;
        maxDepth = 1;

        for (int i = 1; i < adjacency.length; i++) {
            if (alreadyKnow[i]){
                continue;
            }
            inChain = new HashSet<Integer>();
            inChain.add(i);
            dfs(i);
            alreadyKnow[i] = true;
            if (isFundedCircle){
                return -1;
            }
            if(maxDepth < neededReadingQuantity[i]){
                maxDepth = neededReadingQuantity[i];
            }
        }
        return maxDepth;
    }

    private static HashSet<Integer> inChain;

    private static void dfs(int currentNode){
        int d = 0;
        int maxDepth = 1;
        for (Integer i: adjacency[currentNode]) {
            d = currentNode < i ? 1 : 0;
            if (inChain.contains(i) || isFundedCircle){
                isFundedCircle = true;
                break;
            }

            if(!alreadyKnow[i]){
                inChain.add(i);
                dfs(i);
                alreadyKnow[i]=true;
            }

            if (neededReadingQuantity[i] + d > maxDepth){
                maxDepth = neededReadingQuantity[i] + d;
            }
            inChain.remove(i);
        }
        neededReadingQuantity[currentNode] = maxDepth;
    }


    private static void output(int answer){
        System.out.println(answer);
    }
}
