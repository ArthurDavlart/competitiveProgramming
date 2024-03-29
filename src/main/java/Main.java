import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
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
    static PrintWriter out;

    public static void main(String args[]) {
        out = new PrintWriter(System.out);
        resolve();
        out.close();
    }


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

    private static void output(int d){
        out.println(d);
    }
}

