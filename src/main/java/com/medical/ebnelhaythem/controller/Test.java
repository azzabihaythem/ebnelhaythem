package com.medical.ebnelhaythem.controller;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);
        int num ;
        int i = sc.nextInt();



        System.out.print("facteur(i) = "  + facteur(i));
    }

    private static String facteur(int n) {
        int num;
        num = n;
        String result ="";
        for (int j = 2; j <= n /2; j++) {
            while (num % j == 0) {
                num = num / j;
                result = result + j;
                if (num != 1)
                    result = result + "*";
            }
        }

        return result;
    }


}
