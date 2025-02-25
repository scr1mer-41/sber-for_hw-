package org.example;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArraySorterBubbleAndQuickTest {

    @Test
    public void printer() {
        int[] arr1 = {1,2,3};
        assertEquals("1 2 3 ", ArraySorterBubbleAndQuick.printer(arr1));
    }

    @Test
    public void bubbleSorter() {
        int[] arr1 = {4,3,2,1};
        ArraySorterBubbleAndQuick.quickSort(arr1,0, arr1.length-1);
        int[] arr2 = {1,2,3,4};
        assertEquals(ArraySorterBubbleAndQuick.printer(arr2), ArraySorterBubbleAndQuick.printer(arr1));
    }

    @Test
    public void quickSort() {
        int[] arr1 = {4,3,2,1};
        ArraySorterBubbleAndQuick.quickSort(arr1,0, arr1.length-1);
        int[] arr2 = {1,2,3,4};
        assertEquals(ArraySorterBubbleAndQuick.printer(arr2), ArraySorterBubbleAndQuick.printer(arr1));
    }

}