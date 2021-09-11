package tasks.round742div2;

import java.util.*;

public class Round742Div2E {
    private final static Scanner in = new Scanner(System.in);

    private static class SegmentTree {
        private int[] originalArray;
        private Segment[] segmentTreeStore;

        private static class Segment {

        }

        public SegmentTree(int[] originalArray) {
            this.originalArray = originalArray;
            this.segmentTreeStore = new Segment[4 * originalArray.length];
            build(0, 0, originalArray.length);
        }

        private void build(int index, int left, int right) {
            if (left == right) {
                segmentTreeStore[index] = new Segment();
            } else {
                int leftChildIndex = index * 2;
                int rightChildIndex = index * 2 + 1;
                build(leftChildIndex, left, right/2);
                build(rightChildIndex, right/2, right);
                merge(leftChildIndex, rightChildIndex);
            }
        }

        private void merge(int leftChildIndex, int rightChildIndex){

        }
    }

    public static void resolve() {
        input();
    }

    private static void input() {
        int n = in.nextInt();
        int[] arr = new int[n];
        int q = in.nextInt();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = in.nextInt();
        }

    }

}
