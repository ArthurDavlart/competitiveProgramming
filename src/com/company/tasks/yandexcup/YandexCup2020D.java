package com.company.tasks.yandexcup;

public class YandexCup2020D {

    private static final java.util.Scanner in = new java.util.Scanner(System.in);

    static class MKey{
        int i;
        int j;

        public MKey(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public MKey copy(){
            return new MKey(i, j);
        }
    }

    private static int[] arrayN;
    private static int[] arrayM;

    private static long[] buffer;
    private static MKey[] keys;

    public static void resolve(){
        input();
        findMaxPath();
        output();
    }

    private static void input(){
        int n = in.nextInt();

        int m = in.nextInt();

        arrayN = initArray(n);
        arrayM = initArray(m);

        int bufferLength = Math.min(n, m);

        buffer = new long[bufferLength];
        keys = new MKey[bufferLength];
    }

    private static void output(){
        System.out.println(buffer[0]);
    }

    private static int[] initArray(int length){
        int[] newArray = new int[length];
        for (int i = 0; i < length; i++) {
            newArray[i] = in.nextInt();
        }

        return newArray;
    }

    private static void findMaxPath(){
        calculateHeight();
        calculateStagnation();
        calculateDecrease();
    }

    private static void calculateHeight(){
        buffer[0] = getCellQuantity(0, 0);
        keys[0] = new MKey(0, 0);

        for (int i = 1; i < buffer.length; i++) {
            nextHeightKey(i);
            fillBuffer(i);
            //print();
        }
    }

    private static void fillBuffer(int nextLevel){
        long tmp = buffer[0] + getCellQuantity(keys[0]);

        for (int i = 1; i < nextLevel; i++) {
            long max = Math.max(buffer[i - 1], buffer[i]);
            buffer[i - 1] = tmp;
            tmp = max + getCellQuantity(keys[i]);
        }

        buffer[nextLevel] = buffer[nextLevel - 1] + getCellQuantity(keys[nextLevel]);
        buffer[nextLevel - 1] = tmp;
    }

    private static void nextHeightKey(int nextLevel){
        for (int i = 0; i < nextLevel - 1; i++) {
            keys[i].i += 1;
        }

        keys[nextLevel] = keys[nextLevel - 1].copy();
        keys[nextLevel - 1].i += 1;
        keys[nextLevel].j  += 1;
    }

    private static void calculateDecrease(){
        for (int i = buffer.length - 2; i >= 0; i--) {
            nextDecreaseKeys(i);
            fillBufferInDecreaseMode(i);
            //print();
        }
    }

    private static void nextDecreaseKeys(int nextLevel){
        for (int i = 0; i <= nextLevel; i++) {
            keys[i].j = keys[i + 1].j;
        }
    }

    private static void fillBufferInDecreaseMode(int nextLeve){
        for (int i = 0; i <= nextLeve ; i++) {
            long tmp = Math.max(buffer[i], buffer[i + 1]);
            buffer[i] = tmp + getCellQuantity(keys[i]);
        }
    }

    private static void calculateStagnation(){
        if(arrayN.length > arrayM.length){
            for (int i = 0; i < arrayN.length - arrayM.length; i++) {
                nextNMStagnationKeys();
                fillBufferInNMStagnationMode();
                //print();
            }
        } else {
            for (int i = 0; i < arrayM.length - arrayN.length; i++) {
                nextMNStagnationKeys();
                fillBufferInMNStagnationMode();
            }
        }
    }

    private static void nextNMStagnationKeys(){
        for (MKey key : keys) {
            key.i += 1;
        }
    }

    private static void nextMNStagnationKeys(){
        for (MKey key : keys) {
            key.j += 1;
        }
    }

    private static void fillBufferInMNStagnationMode(){
        for (int i = 0; i < buffer.length - 1; i++) {
            long tmp = Math.max(buffer[i], buffer[i + 1]);
            buffer[i] = tmp + getCellQuantity(keys[i]);
        }

        buffer[buffer.length - 1] += getCellQuantity(keys[buffer.length - 1]);
    }

    private static void fillBufferInNMStagnationMode(){
        long tmp = buffer[0] + getCellQuantity(keys[0]);
        for (int i = 1; i < buffer.length; i++) {
            long max = Math.max(buffer[i], buffer[i - 1]);
            buffer[i - 1] = tmp;
            tmp = max + getCellQuantity(keys[i]);
        }
    }

    private static final long serviceNumber = 1_000_000_000L; //10^9

    private static long getCellQuantity(MKey mKey){
        return getCellQuantity(mKey.i, mKey.j);
    }

    private static long getCellQuantity(int i, int j){
        return arrayN[i] * serviceNumber + arrayM[j];
    }

}
