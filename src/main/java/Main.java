import java.io.*;
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
        resolve();
    }

    private static int[] arr;

    public static void resolve(){
        input();
    }

    private static void input(){
        int n = in.nextInt();
        arr = new int[n + 1];
        int q = in.nextInt();

        for (int i = 1; i < arr.length; i++) {
            arr[i] = in.nextInt();
        }

        for (int i = 0; i < q; i++) {
            int t = in.nextInt();
            if (t == 1){
                changeValues(in.nextInt(), in.nextInt());
            } else {
                findSubArrayQuantity(in.nextInt(), in.nextInt());
            }
        }
    }

    private static void changeValues(int index, int value){
        arr[index] = value;
    }

    private static void findSubArrayQuantity(int l, int r){
        int quantity = 0;
        int currentSubElementArray = 1;
        for (int i = l + 1; i <= r; i++) {
            if (arr[i] >= arr[i - 1]){
                currentSubElementArray += 1;
                continue;
            }

            quantity += currentSubElementArray * (currentSubElementArray + 1) / 2;
            currentSubElementArray = 1;
        }

        quantity += currentSubElementArray * (currentSubElementArray + 1) / 2;

        System.out.println(quantity);
    }
}

