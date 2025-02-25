package org.example;

class ArraySorterBubbleAndQuick {


    public static Object printer(int[] sortArr) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < sortArr.length; i++) {
            str.append(sortArr[i]);
            str.append(" ");
        }
        return str.toString();
    }

    private static void toSwap(int[] sortArr, int first, int second) {
        int dummy = sortArr[first];
        sortArr[first] = sortArr[second];
        sortArr[second] = dummy;
    }

    public static void bubbleSorter(int[] sortArr) {
        for (int out = sortArr.length - 1; out >= 1; out--) {
            for (int in = 0; in < out; in++) {
                if (sortArr[in] > sortArr[in + 1])
                    toSwap(sortArr, in,in + 1);
            }
        }
    }


    public static Object quickSort(int[] sortArr, int low, int high) {

        if (sortArr.length == 0 || low >= high);

        int middle = low + (high - low) / 2;
        int border = sortArr[middle];

        int i = low, j = high;
        while (i <= j) {
            while (sortArr[i] < border) i++;
            while (sortArr[j] > border) j--;
            if (i <= j) {
                int swap = sortArr[i];
                sortArr[i] = sortArr[j];
                sortArr[j] = swap;
                i++;
                j--;
            }
        }

        if (low < j) quickSort(sortArr, low, j);
        if (high > i) quickSort(sortArr, i, high);
        return null;
    }

}











//public class Main {
//    public static void main(String[] args) {
//
//        int[] arr = {1313, 4312, 1, 32, 52, 3335, 24, 52};
//
//        ArrayBubble.printer(arr);
//        ArrayBubble.quickSort(arr, 0, arr.length-1);
//        ArrayBubble.printer(arr);
//
//        int[] arr2 = {1313, 4312, 1, 32, 52, 3335, 24, 52};
//
//        ArrayBubble.printer(arr2);
//        ArrayBubble.quickSort(arr2, 0, arr.length-1);
//        ArrayBubble.printer(arr2);
//
//
//
//    }
//}