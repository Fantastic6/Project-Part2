package com.unrealkz;

import java.io.IOException;
import java.util.Scanner;

public class NumberFinder {

    public static void main(String[] args) throws IOException {
        // write your code here
        int counter = 0;
//        Vector myVector = new Vector();
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter a number: ");
        int arraySize = 5;
        int uArray[] = new int[arraySize];
        while (counter < arraySize) {
            int n = reader.nextInt();
//            myVector.add(n);
            uArray[counter] = n;
            counter++;
        }
        System.out.print("Enter a number you looking for:");

        int uNumber = reader.nextInt();
        int distance = Math.abs(uArray[0] - uNumber);
        int SearchedNumIdx = 0;
        for (int i = 1; i < uArray.length; i++) {
            int newDistance = Math.abs(uArray[i] - uNumber);
            if (newDistance < distance){
                SearchedNumIdx = i;
                distance = newDistance;
            }
        }
        int yourNumber = uArray[SearchedNumIdx];
        System.out.println("The closest number is "+yourNumber);
    }
}
