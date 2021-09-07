package com.company.tasks.round742div2;

import java.util.*;

public class Round742Div2E {
    private final static Scanner in = new Scanner(System.in);

    private static class ArrayHash{
        private int[] arr;
        private List<Section> sections;

        public ArrayHash(int arr[]){
            init(arr);
        }

        private void init(int[] arr){
            this.arr = arr;
            sections = new ArrayList<>();
            createSections();
        }

        private void createSections(){
            Section currentSection = new Section();
            currentSection.head = 0;
            sections.add(currentSection);

            int currentSubElementArray = 1;
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] >= arr[i - 1]){
                    currentSubElementArray += 1;
                }

                currentSection.end = currentSubElementArray - 1;
                currentSection = new Section();
                currentSection.head = currentSubElementArray;
                currentSubElementArray = 1;
            }
        }

        public int getSubArrQuantity(int r, int l){
            return 0;
        }

        public void setValue(int index, int value){
            int sectionIndex = findSectionIndex(index);
        }
        private int findSectionIndex(int index){
            return Collections.binarySearch(sections, index);
        }

        private boolean isNeededDeleteInTheSection(Section section, int index, int value){
            return (index != section.end && arr[index + 1] < value) ||
                    (index != section.head && arr[index - 1] > value);
        }

        private void createNewSection(int index){

        }

        private void merge(Section prev){

        }
    }

    private static class Section implements Comparable<Integer>{
        int head;
        int end;

        @Override
        public int compareTo(Integer o) {
            int value = o.intValue();
            return value < head ? -1 : value > end ? 1 : 0;
        }
    }

    public static void resolve(){
        input();
    }

    private static void input(){
        int n = in.nextInt();
        int[] arr = new int[n];
        int q = in.nextInt();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = in.nextInt();
        }

        ArrayHash arrayHash = new ArrayHash(arr);

        for (int i = 0; i < q; i++) {
            int t = in.nextInt();
            if (t == 1){
                arrayHash.setValue(in.nextInt(), in.nextInt());
            } else {
                output(arrayHash.getSubArrQuantity(in.nextInt(), in.nextInt()));
            }
        }
    }

//    private static void changeValues(int index, int value){
//        arr[index] = value;
//    }
//
    private static void output(int quantity){
        System.out.println(quantity);
    }
////
////    private static int findSubArrayQuantity(int l, int r){
////        int quantity = 0;
////        int currentSubElementArray = 1;
////        for (int i = l + 1; i <= r; i++) {
////            if (arr[i] >= arr[i - 1]){
////                currentSubElementArray += 1;
////                continue;
////            }
////
////            quantity += currentSubElementArray * (currentSubElementArray + 1) / 2;
////            currentSubElementArray = 1;
////        }
////
////        quantity += currentSubElementArray * (currentSubElementArray + 1) / 2;
////
////        return quantity;
////    }
}
