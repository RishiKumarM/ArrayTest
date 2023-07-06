package com.test.Test.Array;
import java.util.Arrays;
import java.util.Scanner;

public class Array {

	public static void main(String[] args) {
        int[][] array = new int[4][4];

        // Take values from the user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the values for the 4x4 array:");

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                array[i][j] = scanner.nextInt();
            }
        }

        sortArrayByRows(array);

        // Called function A with the sorted array
        functionA(array);

        // Create the inverse of the array and save it in B
        int[][] inverseArray = createInverseArray(array);
        printArray(inverseArray, "B");
    }

    // Sort the array by rows
    public static void sortArrayByRows(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            Arrays.sort(array[i]);
        }
    }

    // Function A
    public static void functionA(int[][] array) {
        System.out.println("Array in function A:");
        printArray(array, "A");
    }

    // Create the inverse of the array
    public static int[][] createInverseArray(int[][] array) {
        int[][] inverseArray = new int[array.length][array[0].length];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                inverseArray[i][j] = array[j][i];
            }
        }

        return inverseArray;
    }

    // Print the array
    public static void printArray(int[][] array, String arrayName) {
        System.out.println(arrayName + ":");
        for (int[] row : array) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }

}
