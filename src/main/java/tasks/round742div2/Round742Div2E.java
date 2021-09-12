package tasks.round742div2;

import java.util.*;

public class Round742Div2E {
    private final static Scanner in = new Scanner(System.in);

    private static class SegmentTree {
        private int[] originalArray;
        private Segment[] segmentTreeStore;

        private static class Segment {
            int prefix;
            int prefixElementQuantity;
            int suffix;
            int suffixElementQuantity;
            boolean isNonDecInTotalSegment;
            long nonDecSeqInMiddleSegment;

            @Override
            public String toString() {
                return "Segment{" +
                        "prefix=" + prefix +
                        ", prefixElementQuantity=" + prefixElementQuantity +
                        ", suffix=" + suffix +
                        ", suffixElementQuantity=" + suffixElementQuantity +
                        ", isNonDecInTotalSegment=" + isNonDecInTotalSegment +
                        ", nonDecSeqInMiddleSegment=" + nonDecSeqInMiddleSegment +
                        '}';
            }
        }

        public SegmentTree(int[] originalArray) {
            this.originalArray = originalArray;
            this.segmentTreeStore = new Segment[4 * originalArray.length];
            build(1, 0, originalArray.length - 1);
        }

        private void build(int index, int left, int right) {
            // System.out.println(left + " " + right);
            if (left == right) {
                segmentTreeStore[index] = createSegment(left, right);
                //System.out.println(segmentTreeStore[index].toString());
            } else {
                int leftChildIndex = index * 2;
                int rightChildIndex = index * 2 + 1;
                int seg = right - left;
                build(leftChildIndex, left, left + seg / 2);
                build(rightChildIndex, left + seg / 2 + 1, right);
                segmentTreeStore[index] = merge(leftChildIndex, rightChildIndex);
            }
        }

        private Segment createSegment(int left, int right) {
            Segment result = new Segment();
            result.prefix = originalArray[left];
            result.prefixElementQuantity = 1;
            result.suffix = originalArray[right];
            result.suffixElementQuantity = 1;
            result.isNonDecInTotalSegment = true;
            result.nonDecSeqInMiddleSegment = 0;
            return result;
        }

        public void update(int index, int value) {
            update(1, 0, originalArray.length - 1, index, value);
        }

        private void update(int currentIndex, int left, int right, int index, int value) {
            //System.out.println(currentIndex + " " + left + " " + right + " " + index + " " + value);
            if (left == right) {
                segmentTreeStore[currentIndex].prefix = value;
                segmentTreeStore[currentIndex].suffix = value;
                return;
            } else if (left <= index && index <= left + (right - left) / 2) {
                update(currentIndex * 2, left, getMiddleElementIndexOnSegment(left, right), index, value);
            } else {
                update(currentIndex * 2 + 1, getMiddleElementIndexOnSegment(left, right) + 1, right, index, value);
            }
            segmentTreeStore[currentIndex] = merge(currentIndex * 2, currentIndex * 2 + 1);
            //System.out.println(segmentTreeStore[currentIndex]);
        }

        private Segment merge(int leftChildIndex, int rightChildIndex) {
            return merge(segmentTreeStore[leftChildIndex], segmentTreeStore[rightChildIndex]);
        }

        public long get(int left, int right) {
            //System.out.println(left + " " + right);
            Segment result = findSegment(
                    1,
                    0,
                    originalArray.length - 1,
                    left,
                    right);
            return result.isNonDecInTotalSegment ?
                    calculateNonDecreasingSequencesQuantity(result.prefixElementQuantity) :
                    calculateNonDecreasingSequencesQuantity(result.prefixElementQuantity) +
                            result.nonDecSeqInMiddleSegment +
                            calculateNonDecreasingSequencesQuantity(result.suffixElementQuantity);
        }

        private Segment findSegment(int currentSegmentIndex, int leftSegment, int rightSegment, int findLeft, int findRight) {
//            System.out.println(currentSegmentIndex + " " + leftSegment + " " + rightSegment + " "
//            + findLeft + " " +findRight);

            Segment segment = segmentTreeStore[currentSegmentIndex];
//            System.out.println(segment.toString());

            if (leftSegment == findLeft && rightSegment == findRight) {
                return segment;
            } else if (leftSegment <= findLeft &&
                    findRight <= leftSegment + (rightSegment - leftSegment) / 2) {
                return findSegment(currentSegmentIndex * 2,
                        leftSegment,
                        leftSegment + (rightSegment - leftSegment) / 2,
                        findLeft,
                        findRight);
            } else if (leftSegment + (rightSegment - leftSegment) / 2 + 1 <= findLeft &&
                    findRight <= rightSegment) {
                return findSegment(currentSegmentIndex * 2 + 1,
                        leftSegment + (rightSegment - leftSegment) / 2 + 1,
                        rightSegment,
                        findLeft, findRight);
            } else {
                Segment lSegment = findSegment(
                        currentSegmentIndex * 2,
                        leftSegment,
                        leftSegment + (rightSegment - leftSegment) / 2,
                        findLeft,
                        leftSegment + (rightSegment - leftSegment) / 2);
                Segment rSegment = findSegment(
                        currentSegmentIndex * 2 + 1,
                        leftSegment + (rightSegment - leftSegment) / 2 + 1,
                        rightSegment,
                        leftSegment + (rightSegment - leftSegment) / 2 + 1,
                        findRight);

                return merge(lSegment, rSegment);
            }
        }

        private Segment merge(Segment leftChildSegment, Segment rightChildSegment) {
            Segment segment = new Segment();

            if (!leftChildSegment.isNonDecInTotalSegment && !rightChildSegment.isNonDecInTotalSegment) {
                segment.prefix = leftChildSegment.prefix;
                segment.prefixElementQuantity = leftChildSegment.prefixElementQuantity;

                segment.nonDecSeqInMiddleSegment = leftChildSegment.nonDecSeqInMiddleSegment + rightChildSegment.nonDecSeqInMiddleSegment;
                segment.nonDecSeqInMiddleSegment += leftChildSegment.suffix <= rightChildSegment.prefix ?
                        calculateNonDecreasingSequencesQuantity(
                                leftChildSegment.suffixElementQuantity + rightChildSegment.prefixElementQuantity
                        ) :
                        calculateNonDecreasingSequencesQuantity(leftChildSegment.suffixElementQuantity) +
                                calculateNonDecreasingSequencesQuantity(rightChildSegment.prefixElementQuantity);


                segment.suffix = rightChildSegment.suffix;
                segment.suffixElementQuantity = rightChildSegment.suffixElementQuantity;

                segment.isNonDecInTotalSegment = false;
            } else if (!leftChildSegment.isNonDecInTotalSegment && rightChildSegment.isNonDecInTotalSegment) {
                segment.prefix = leftChildSegment.prefix;
                segment.prefixElementQuantity = leftChildSegment.prefixElementQuantity;

                if (leftChildSegment.suffix <= rightChildSegment.prefix) {
                    segment.nonDecSeqInMiddleSegment = leftChildSegment.nonDecSeqInMiddleSegment;
                    segment.suffixElementQuantity = leftChildSegment.suffixElementQuantity + rightChildSegment.prefixElementQuantity;
                } else {
                    segment.nonDecSeqInMiddleSegment = leftChildSegment.nonDecSeqInMiddleSegment
                            + calculateNonDecreasingSequencesQuantity(leftChildSegment.suffixElementQuantity);

                    segment.suffixElementQuantity = rightChildSegment.prefixElementQuantity;
                }
                segment.suffix = rightChildSegment.suffix;

                segment.isNonDecInTotalSegment = false;
            } else if (leftChildSegment.isNonDecInTotalSegment && !rightChildSegment.isNonDecInTotalSegment) {
                segment.prefix = leftChildSegment.prefix;

                if (leftChildSegment.suffix <= rightChildSegment.prefix) {
                    segment.prefixElementQuantity = leftChildSegment.prefixElementQuantity + rightChildSegment.prefixElementQuantity;
                    segment.nonDecSeqInMiddleSegment = rightChildSegment.nonDecSeqInMiddleSegment;
                } else {
                    segment.prefixElementQuantity = leftChildSegment.prefixElementQuantity;
                    segment.nonDecSeqInMiddleSegment = calculateNonDecreasingSequencesQuantity(rightChildSegment.prefixElementQuantity)
                            + rightChildSegment.nonDecSeqInMiddleSegment;
                }

                segment.suffix = rightChildSegment.suffix;
                segment.suffixElementQuantity = rightChildSegment.suffixElementQuantity;

                segment.isNonDecInTotalSegment = false;
            } else {
                segment.prefix = leftChildSegment.prefix;

                if (leftChildSegment.suffix <= rightChildSegment.prefix) {
                    segment.prefixElementQuantity = leftChildSegment.prefixElementQuantity + rightChildSegment.prefixElementQuantity;
                    segment.suffixElementQuantity = segment.prefixElementQuantity;
                    segment.nonDecSeqInMiddleSegment = 0;
                    segment.isNonDecInTotalSegment = true;
                } else {
                    segment.prefixElementQuantity = leftChildSegment.prefixElementQuantity;
                    segment.suffixElementQuantity = rightChildSegment.prefixElementQuantity;
                    segment.nonDecSeqInMiddleSegment = 0;
                    segment.isNonDecInTotalSegment = false;
                }

                segment.suffix = rightChildSegment.suffix;
            }

//            System.out.println(segment.toString());
            return segment;
        }

        private int getMiddleElementIndexOnSegment(int left, int right) {
            return left + (right - left) / 2;
        }

        private long calculateNonDecreasingSequencesQuantity(int elementQuantity) {
            return (long) elementQuantity * (elementQuantity + 1) / 2;
        }
    }

    private static SegmentTree segmentTree;
    private static int quotationQuantity;

    public static void resolve() {
        input();

        for (int i = 0; i < quotationQuantity; i++) {
            int c = in.nextInt();
            int l = in.nextInt();
            int r = in.nextInt();
//            System.out.println(c + " " + l + " " + r);
            if (c == 1) {
                segmentTree.update(l - 1, r);
            } else {
                output(segmentTree.get(l - 1, r - 1));
            }
        }

    }

    private static void input() {
        int n = in.nextInt();
        int[] arr = new int[n];
        quotationQuantity = in.nextInt();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = in.nextInt();
        }

        segmentTree = new SegmentTree(arr);
    }

    private static void output(long value) {
        System.out.println(value);
    }

}
