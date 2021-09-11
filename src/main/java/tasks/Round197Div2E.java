package tasks;

import java.util.Scanner;

public class Round197Div2E {
    private static Scanner in = new Scanner(System.in);
    private static int horseQuantity;
    private static int[] lastHorsesPosition;

    private static Command[] commandStack = new Command[3];
    private static int lastIndexInStack = 2;

    public static void resolve(String args[]){
        enter();
        findCommand();
        display();
    }

    private static void enter(){
        horseQuantity = in.nextInt();
        lastHorsesPosition = new int[horseQuantity + 1];
        for (int i = 1; i < lastHorsesPosition.length; i++) {
            lastHorsesPosition[i] = in.nextInt();
        }
    }

    private static void findCommand(){
        int maxAwayElementIndex = -1;
        int maxAway = 0;
        while (true){
            for (int i = 1; i < lastHorsesPosition.length; i++) {
                int currentAway = (i - lastHorsesPosition[i]) >= 0
                        ? i - lastHorsesPosition[i]
                        : lastHorsesPosition[i] - i;
                if (maxAway < currentAway){
                    maxAway = currentAway;
                    maxAwayElementIndex = i;
                }
            }

            //
            if (maxAwayElementIndex != -1){
                Command command = lastHorsesPosition[maxAwayElementIndex] > maxAwayElementIndex
                        ? initCommand(maxAwayElementIndex, lastHorsesPosition[maxAwayElementIndex])
                        : initCommand(lastHorsesPosition[maxAwayElementIndex], maxAwayElementIndex);
                reverse(command);
                setInStack(command);
                maxAwayElementIndex = -1;
                maxAway = 0;

                continue;
            }

            break;
        }

    }

    private static Command initCommand(int l, int r){
        return new Command(l, r);
    }

    private static void reverse(Command command){
        for (int i = 0, l = command.l, r = command.r; i < (command.r - command.l + 1) / 2; i++, l++, r--) {
            int tmp = lastHorsesPosition[l];
            lastHorsesPosition[l] = lastHorsesPosition[r];
            lastHorsesPosition[r] = tmp;
        }
    }

    private static void setInStack(Command command){
        commandStack[lastIndexInStack] = command;
        lastIndexInStack--;
    }



    private static class Command{
        int r;
        int l;

        Command(int l, int r){
            this.l = l;
            this.r = r;
        }

        @Override
        public String toString() {
            return l + " " + r;
        }
    }

    private static void display(){
        System.out.println(2 - lastIndexInStack);
        for (int i = lastIndexInStack + 1; i < commandStack.length; i++) {
            System.out.println(commandStack[i].toString());
        }
    }
}
