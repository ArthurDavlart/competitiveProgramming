package tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Round197Div2D {
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

    private static int n, requestsQuantity;
    private static int[] tree;
    private static int elementsQuantity;

    public static void resolve(){
        enter();
        fillTree();
        enterAndResolveRequests();
    }

    private static void enter(){
        n = in.nextInt();
        requestsQuantity = in.nextInt();

        elementsQuantity = 1;
        for (int i = 0; i < n; i++) {
            elementsQuantity *= 2;
        }

        tree = new int[elementsQuantity*2];

        for (int i = 0; i < elementsQuantity; i++) {
            tree[i] = in.nextInt();
        }
    }

    private static void fillTree(){
        boolean or = true;
        int len = elementsQuantity;
        int start = 0;
        len /= 2;
        for (int i = 0; i < n; i++) {
            if (or){
                orMethod(start, len);
                or = false;
            } else{
                xorMethod(start, len);
                or = true;
            }

            start = start + len * 2;
            len /= 2;
        }
    }

    private static void orMethod(int start, int len){
        int nextStart = start + len * 2;
        for (int i = 0; i < len; i++) {
            tree[nextStart + i] = tree[i*2 + start] | tree[i*2 + 1 + start];
        }
    }

    private static void xorMethod(int start, int len){
        int nextStart = start + len * 2;
        for (int i = 0; i < len; i++) {
            tree[nextStart + i] = tree[i*2 + start] ^ tree[i*2 + 1 + start];
        }
    }

    private static void enterAndResolveRequests(){
        for (int i = 0; i < requestsQuantity; i++) {
            int p = in.nextInt();
            int d = in.nextInt();

            resolveRequest(p - 1, d);
        }
    }

    private static void resolveRequest(int changeElementIndex, int newElement){
        tree[changeElementIndex] = newElement;
        boolean or = true;
        int beforeChangeElementIndex = changeElementIndex;
        int nextChangeElementIndex = changeElementIndex / 2 + elementsQuantity;
        int d = 0;
        for (int i = 0; i < n; i++) {
            if (or){
                tree[nextChangeElementIndex] = beforeChangeElementIndex % 2 == 0
                        ? tree[beforeChangeElementIndex] | tree[beforeChangeElementIndex + 1]
                        : tree[beforeChangeElementIndex] | tree[beforeChangeElementIndex - 1];
                or = false;
            } else {
                tree[nextChangeElementIndex] = beforeChangeElementIndex % 2 == 0
                        ? tree[beforeChangeElementIndex] ^ tree[beforeChangeElementIndex + 1]
                        : tree[beforeChangeElementIndex] ^ tree[beforeChangeElementIndex - 1];
                or = true;
            }
            beforeChangeElementIndex = nextChangeElementIndex;
            nextChangeElementIndex = nextChangeElementIndex / 2 + elementsQuantity;
        }

        display();
    }

    private static void display(){
        System.out.println(tree[tree.length - 2]);
    }
}
